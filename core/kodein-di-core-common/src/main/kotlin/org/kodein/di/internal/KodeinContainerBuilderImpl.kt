package org.kodein.di.internal

import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.KodeinContainer
import org.kodein.di.KodeinDefining
import org.kodein.di.bindings.KodeinBinding

/**
 * This is where you configure the bindings.
 *
 * @param allowOverride Whether or not the bindings defined by this builder or its imports are allowed to **explicitly** override existing bindings.
 * @param silentOverride Whether or not the bindings defined by this builder or its imports are allowed to **silently** override existing bindings.
 * @param bindingsMap The map that contains the bindings. Can be set at construction to construct a sub-builder (with different override permissions).
 */
internal class KodeinContainerBuilderImpl(
        allowOverride: Boolean,
        silentOverride: Boolean,
        internal val bindingsMap: MutableMap<Kodein.Key<*, *, *>, MutableList<KodeinDefining<*, *, *>>>,
        internal val callbacks: MutableList<DKodein.() -> Unit>
) : KodeinContainer.Builder {

    /**
     * The override permission for a builder.
     */
    private enum class OverrideMode {

        /**
         * Bindings are allowed to **non-explicit** overrides.
         */
        ALLOW_SILENT {
            override val isAllowed: Boolean get() = true
            override fun must(overrides: Boolean?) = overrides
        },

        /**
         * Bindings are allowed to overrides, but only **explicit**.
         */
        ALLOW_EXPLICIT {
            override val isAllowed: Boolean get() = true
            override fun must(overrides: Boolean?) = overrides ?: false
        },

        /**
         * Bindings are forbidden to override.
         */
        FORBID {
            override val isAllowed: Boolean get() = false
            override fun must(overrides: Boolean?) = if (overrides != null && overrides) throw Kodein.OverridingException("Overriding has been forbidden") else false
        };

        /**
         * Whether or not this mode allows overrides.
         */
        abstract val isAllowed: Boolean

        /**
         * Given a binding overriding declaration (true=yes, false=no, null=maybe), returns whether or not the binding **must** override an existing binding.
         *
         * @return `true` if it must override, `false` if it must not, `null` if it can but is not required to.
         * @throws Kodein.OverridingException If it asks to override, if the binding overriding declaration is not permitted.
         */
        abstract fun must(overrides: Boolean?): Boolean?

        companion object {

            /**
             * Get the mode according to the given permissions.
             *
             * @param allow The permission to override explicitly.
             * @param silent The permission to override silently.
             * @return The mode corresponding to the permissions.
             */
            fun get(allow: Boolean, silent: Boolean): OverrideMode {
                if (!allow)
                    return FORBID
                if (silent)
                    return ALLOW_SILENT
                return ALLOW_EXPLICIT
            }
        }
    }

    /**
     * The mode that defines the overriding permissions for this builder.
     */
    private val _overrideMode = OverrideMode.get(allowOverride, silentOverride)

    /**
     * Checks that the bindings conforms to it's overriding declaration.
     *
     * If [overrides] is null, only checks if the binding is allowed to override, else checks if it conforms.
     *
     * @param key The key that the binding must, may or must not override.
     * @param overrides `true` if it must override, `false` if it must not, `null` if it can but is not required to.
     * @throws Kodein.OverridingException If overrides is `null` or `true` but the permission to override is not granted,
     *                                    or if the binding is allowed to override, but does not conforms to it's overriding declaration.
     */
    private fun checkOverrides(key: Kodein.Key<*, *, *>, overrides: Boolean?) {
        val mustOverride = _overrideMode.must(overrides)

        if (mustOverride != null) {
            if (mustOverride && key !in bindingsMap)
                throw Kodein.OverridingException("Binding $key must override an existing binding.")
            if (!mustOverride && key in bindingsMap)
                throw Kodein.OverridingException("Binding $key must not override an existing binding.")
        }
    }

    override fun <C, A, T: Any> bind(key: Kodein.Key<C, A, T>, binding: KodeinBinding<in C, in A, out T>, fromModule: String?, overrides: Boolean?) {
        key.type.checkIsReified(key)
        key.argType.checkIsReified(key)
        checkOverrides(key, overrides)

        val bindings = bindingsMap.getOrPut(key) { newLinkedList() }
        bindings.add(0, KodeinDefining(binding, fromModule))
    }

    /**
     * Checks whether the given overriding declaration is allowed.
     *
     * @param allowOverride The overriding declaration.
     * @throws Kodein.OverridingException If it is not allowed to bind.
     */
    private fun checkMatch(allowOverride: Boolean) {
        if (!_overrideMode.isAllowed && allowOverride)
            throw Kodein.OverridingException("Overriding has been forbidden")
    }

    override fun extend(container: KodeinContainer, allowOverride: Boolean, copy: Set<Kodein.Key<*, *, *>>) {
        checkMatch(allowOverride)

        container.tree.bindings.forEach { (key, bindings) ->
            if (!allowOverride)
                checkOverrides(key, null)

            val newBindings = if (key in copy) {
                newLinkedList<KodeinDefining<*, *, *>>().also {
                    bindings.mapTo(it) { KodeinDefining(it.binding.copier?.copy(this@KodeinContainerBuilderImpl) ?: it.binding, it.fromModule) }
                }
            }
            else {
                newLinkedList<KodeinDefining<*, *, *>>(bindings)
            }

            bindingsMap[key] = newBindings
        }
    }

    override fun subBuilder(allowOverride: Boolean, silentOverride: Boolean): KodeinContainerBuilderImpl {
        checkMatch(allowOverride)
        return KodeinContainerBuilderImpl(allowOverride, silentOverride, bindingsMap, callbacks)
    }

    /**
     * Adds a callback that will be called once the Kodein object has been initialized.
     *
     * @param cb A callback.
     */
    override fun onReady(cb: DKodein.() -> Unit) {
        callbacks += cb
    }
}
