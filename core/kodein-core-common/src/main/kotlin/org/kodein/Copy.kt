package org.kodein

class CopySpecs(val all: Boolean) : SearchSpecs()

interface Copy {
    fun keySet(tree: KodeinTree): Set<Kodein.Key<*, *, *>>

    object All: Copy {
        override fun keySet(tree: KodeinTree) = tree.bindings.keys
    }

    object None: Copy {
        override fun keySet(tree: KodeinTree) = emptySet<Kodein.Key<*, *, *>>()
    }

    object NonCached: Copy {
        override fun keySet(tree: KodeinTree) = tree.bindings.filter { it.value.first().binding.copier == null } .keys
    }

    companion object {
        operator fun invoke(f: DSL.() -> Unit) = DSL().apply(f)

        fun allBut(f: DSL.() -> Unit) = AllButDSL().apply(f)
    }

    open class DSL : SearchDSL(), Copy {
        internal val specs = ArrayList<CopySpecs>()

        inner class Copy {
            infix fun the(binding: SearchDSL.Binding): SearchSpecs {
                return CopySpecs(false).also { binding.apply(it) ; specs += it }
            }
            infix fun all(spec: SearchDSL.Spec): SearchSpecs {
                return CopySpecs(true).also { spec.apply(it) ; specs += it }
            }
        }

        val copy = Copy()

        override fun keySet(tree: KodeinTree) = specs
                .flatMap {
                    val list = tree.find(it)
                    if (list.isEmpty()) {
                        throw Kodein.NoResultException(it, "No binding found that match this search: $it")
                    }
                    if (!it.all && list.size > 1) {
                        throw Kodein.NoResultException(it, "There were ${list.size} matches for this search: $it\n${list.toMap().description(false)}")
                    }
                    list.map { it.first }
                }
                .toSet()
    }

    class AllButDSL : DSL() {
        override fun keySet(tree: KodeinTree): Set<Kodein.Key<*, *, *>> = tree.bindings.keys.minus(super.keySet(tree))
    }
}
