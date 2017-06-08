[com.github.salomonbrys.kodein.conf](../index.md) / [ConfigurableKodein](index.md) / [clear](.)

# clear

`fun clear(): Unit`

Clear all the bindings of the Kodein instance. Needs [mutable](mutable.md) to be true.

### Exceptions

`IllegalStateException` - if [mutable](mutable.md) is not `true`.