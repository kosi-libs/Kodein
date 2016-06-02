
#### 2.7.1 (02-06-2016)

 * Overriding exceptions now print the affected key
 * New syntax for binding without specifying the type: `bind() from scope`

#### 2.7.0 (23-05-2016)

 * Kotlin `1.0.2`.
 * Overriding restrictions and policies (overrides must now be explicit).
 * Scoped singleton system.
 * Android Activity scope binding syntax.
 *`kodein.with` that makes it easier to deal with factory bindings (better than `toProvider` and `toInstance`).
 * Factories arguments can be nullable.


### 2.6.0 (19-02-2016)

 * Kotlin `1.0.0`.


### 2.5.0 (04-02-2016)

 * Kotlin `1.0.0-rc-1036`.


### 2.4.1 (11-01-2016)

 * Kotlin `1.0.0-beta-4584`
 * `KodeinInjector` can inject the `Kodein` object.
 * `KodeinInjector` has a `onInjected` callback.
 * Android: `lazyKodeinFromApp` and `appKodein` support for `View`, `AbstractThreadedSyncAdapter` and `Loader`.


### 2.3.1 (06-11-2015)

 * Container extension: allows to `import` a kodein object's binding into another kodein object.


### 2.3.0 (02-11-2015)

 * Kotlin `1.0.0-beta-1038`
