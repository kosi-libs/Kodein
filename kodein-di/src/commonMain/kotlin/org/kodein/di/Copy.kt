package org.kodein.di

/**
 * Defines which bindings are to be copied from a parent DI to a child DI.
 *
 * @property all Whether the specs are describing multiple matching bindings or only one.
 */
public class CopySpecs(public val all: Boolean) : SearchSpecs()

/**
 * Simple interface that returns a the keys to copy from a container.
 */
public interface Copy {
    /**
     * The keys to copy from the given tree, according to the rules defined in this object.
     *
     * @param tree The tree that contains bindings to copy.
     */
    public fun keySet(tree: DITree): Set<DI.Key<*, *, *>>

    /**
     * A [Copy] spec that copies all bindings.
     */
    public object All: Copy {
        override fun keySet(tree: DITree): Set<DI.Key<*, *, *>> = tree.bindings.keys
    }

    /**
     * A [Copy] spec that copies no bindings.
     */
    public object None: Copy {
        override fun keySet(tree: DITree): Set<DI.Key<*, *, *>> = emptySet<DI.Key<*, *, *>>()
    }

    /**
     * A [Copy] spec that copies only the bindings that retain no status / reference.
     */
    public object NonCached: Copy {
        override fun keySet(tree: DITree): Set<DI.Key<*, *, *>> = tree.bindings.filter { it.value.first().binding.copier == null } .keys
    }

    public companion object {
        /**
         * Creates copy specs where the DSL defines which bindings are to be [copied][BaseDSL.copy].
         */
        public operator fun invoke(f: DSL.() -> Unit): DSL = DSL().apply(f)

        /**
         * Creates copy specs where all bindings are copied by default, and the DSL defines which bindings are to be [ignored][BaseDSL.ignore].
         */
        public fun allBut(f: AllButDSL.() -> Unit): AllButDSL = AllButDSL().apply(f)

        internal fun specsToKeys(tree: DITree, it: CopySpecs): List<DI.Key<*, *, *>> {
            val list = tree.find(it)
            if (list.isEmpty()) {
                throw DI.NoResultException(it, "No binding found that match this search: $it")
            }
            if (!it.all && list.size > 1) {
                throw DI.NoResultException(it, "There were ${list.size} matches for this search: $it\n${list.associate { it.first to it.second }.description(false)}")
            }
            return list.map { it.first }
        }
    }

    /**
     * Base class for the Copy DSL.
     */
    public abstract class BaseDSL : SearchDSL(), Copy {
        internal val copySpecs = ArrayList<CopySpecs>()
        internal val ignoreSpecs = ArrayList<CopySpecs>()

        /**
         * Second half of the sentence `[copy|ignore] the` and `[copy|ignore] all`
         */
        public class Sentence(private val specs: MutableList<CopySpecs>) {
            /**
             * Defines that this spec will only correspond to **one** matching binding.
             *
             * @param binding The binding it should match.
             * @return The binding itself to configure it (e.g. `copy the binding<Whatever>() with context<MyContext>()`).
             */
            public infix fun the(binding: Binding): SearchSpecs {
                return CopySpecs(all = false).also { binding.apply(it) ; specs += it }
            }

            /**
             * Defines that this spec will only correspond to **all** matching binding.
             *
             * @param spec The specs of the search.
             * @return The binding itself to configure it (e.g. `copy all context<MyContext>() with tag("whatever")`).
             */
            public infix fun all(spec: Spec): SearchSpecs {
                return CopySpecs(all = true).also { spec.apply(it) ; specs += it }
            }
        }

        /**
         * Beginning of the following DSLs: `copy the ...` and `copy all ...`.
         */
        public val copy: Sentence = Sentence(copySpecs)

        /**
         * Beginning of the following DSLs: `ignore the ...` and `ignore all ...`.
         */
        public val ignore: Sentence = Sentence(ignoreSpecs)
    }

    /**
     * [Copy] DSL.
     */
    public class DSL : BaseDSL() {
        override fun keySet(tree: DITree): Set<DI.Key<*, *, *>> {
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
    public class AllButDSL : BaseDSL() {
        override fun keySet(tree: DITree): Set<DI.Key<*, *, *>> {
            val kept = copySpecs.flatMap { specsToKeys(tree, it) }
            val ignored = ignoreSpecs
                    .flatMap { specsToKeys(tree, it) }
                    .minus(kept)
            return tree.bindings.keys.minus(ignored)
        }
    }
}
