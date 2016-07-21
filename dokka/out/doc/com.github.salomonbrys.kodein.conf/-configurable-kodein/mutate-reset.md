[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [mutateReset](.)

# mutateReset

`fun mutateReset(keep: Boolean = false): Unit`

Reset the Kodein instance, allowing it to be configured again. Needs [mutable](mutable.md) to be true.

By default, it will reset it with no configured bindings.
If [mutable](mutable.md) is true, all bindings from the old kodein will be transferred into the new kodein.

### Parameters

`keep` - Whether or not to keep the old bindings into the new Kodein instance.

### Exceptions

`IllegalStateException` - if [mutable](mutable.md) is not `true`.