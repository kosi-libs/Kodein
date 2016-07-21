[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [mutable](.)

# mutable

`var mutable: Boolean?`

Whether this ConfigurableKodein can be mutated.

`null` = not set yet. `true` = can be mutated. `false` = cannot be mutated.

Note that if not set, this field will be set to false on `first` Kodein retrieval.

