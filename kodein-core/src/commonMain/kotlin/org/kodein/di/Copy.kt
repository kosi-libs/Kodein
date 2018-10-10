package org.kodein.di

/**
 * Defines which bindings are to be copied from a parent Kodein to a child Kodein.
 *
 * @property all Whether the specs are describing multiple matching bindings or only one.
 */
class CopySpecs(val all: Boolean) : SearchSpecs()

/**
 * Simple interface that returns a the keys to copy from a container.
 */
interface Copy {
    /**
     * The keys to copy from the given tree, according to the rules defined in this object.
     *
     * @param tree The tree that contains bindings to copy.
     */
    fun keySet(tree: KodeinTree): Set<Kodein.Key<*, *, *>>

    /**
     * A [Copy] spec that copies all bindings.
     */
    object All: Copy {
        override fun keySet(tree: KodeinTree) = tree.bindings.keys
    }

    /**
     * A [Copy] spec that copies no bindings.
     */
    object None: Copy {
        override fun keySet(tree: KodeinTree) = emptySet<Kodein.Key<*, *, *>>()
    }

    /**
     * A [Copy] spec that copies only the bindings that retain no status / reference.
     */
    object NonCached: Copy {
        override fun keySet(tree: KodeinTree) = tree.bindings.filter { it.value.first().binding.copier == null } .keys
    }

    companion object {
        /**
         * Creates copy specs where the DSL defines which bindings are to be [copied][BaseDSL.copy].
         */
        operator fun invoke(f: DSL.() -> Unit) = DSL().apply(f)

        /**
         * Creates copy specs where all bindings are copied by default, and the DSL defines which bindings are to be [ignored][BaseDSL.ignore].
         */
        fun allBut(f: AllButDSL.() -> Unit) = AllButDSL().apply(f)

        internal fun specsToKeys(tree: KodeinTree, it: CopySpecs): List<Kodein.Key<*, *, *>> {
            val list = tree.find(it)
            if (list.isEmpty()) {
                throw Kodein.NoResultException(it, "No binding found that match this search: $it")
            }
            if (!it.all && list.size > 1) {
                throw Kodein.NoResultException(it, "There were ${list.size} matches for this search: $it\n${list.toMap().description(false)}")
            }
            return list.map { it.first }
        }
    }

    /**
     * Base class for the Copy DSL.
     */
    abstract class BaseDSL : SearchDSL(), Copy {
        internal val copySpecs = ArrayList<CopySpecs>()
        internal val ignoreSpecs = ArrayList<CopySpecs>()

        /**
         * Second half of the sentence `[copy|ignore] the` and `[copy|ignore] all`
         */
        class Sentence(private val specs: MutableList<CopySpecs>) {
            /**
             * Defines that this spec will only correspond to **one** matching binding.
             *
             * @param binding The binding it should match.
             * @return The binding itself to configure it (e.g. `copy the binding<Whatever>() with context<MyContext>()`).
             */
            infix fun the(binding: Binding): SearchSpecs {
                return CopySpecs(all = false).also { binding.apply(it) ; specs += it }
            }

            /**
             * Defines that this spec will only correspond to **all** matching binding.
             *
             * @param spec The specs of the search.
             * @return The binding itself to configure it (e.g. `copy all context<MyContext>() with tag("whatever")`).
             */
            infix fun all(spec: Spec): SearchSpecs {
                return CopySpecs(all = true).also { spec.apply(it) ; specs += it }
            }
        }

        /**
         * Beginning of the following DSLs: `copy the ...` and `copy all ...`.
         */
        val copy = Sentence(copySpecs)

        /**
         * Beginning of the following DSLs: `ignore the ...` and `ignore all ...`.
         */
        val ignore = Sentence(ignoreSpecs)
    }

    /**
     * [Copy] DSL.
     */
    class DSL : BaseDSL() {
        override fun keySet(tree: KodeinTree): Set<Kodein.Key<*, *, *>> {
            val ignored = ignoreSpecs.flatMap { specsToKeys(tree, it) }
            return copySpecs
                    .flatMap { specsToKeys(tree, it) }
                    .minus(ignored)
                    .toSet()
        }
    }

    /**
     * [Copy.allBut] DSL
     */
    class AllButDSL : BaseDSL() {
        override fun keySet(tree: KodeinTree): Set<Kodein.Key<*, *, *>> {
            val kept = copySpecs.flatMap { specsToKeys(tree, it) }
            val ignored = ignoreSpecs
                    .flatMap { specsToKeys(tree, it) }
                    .minus(kept)
            return tree.bindings.keys.minus(ignored)
        }
    }
}
