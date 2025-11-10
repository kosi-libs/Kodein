# AGENTS.md

Kodein-DI is a Kotlin Multiplatform dependency injection framework.

## Core Architecture

**Main interfaces:** `kodein-di/src/commonMain/kotlin/org/kodein/di/`
- `DI.kt` - Main DI interface, Key, exceptions (NotFoundException, DependencyLoopException, OverridingException)
- `DIContainer.kt` - Internal container interface for binding storage and resolution
- `DIBuilder.kt` - Extension functions for `bind()` DSL syntax
- `DIAware.kt` - Interface for classes that hold a DI instance
- `DirectDI.kt` - Direct (non-delegated) DI access without property delegation
- `Retrieving.kt` - Core retrieval functions: `instance()`, `provider()`, `factory()`

**Implementations:** `kodein-di/src/commonMain/kotlin/org/kodein/di/internal/`
- `DIImpl.kt`, `DIContainerImpl.kt`, `DIBuilderImpl.kt`, `DIContainerBuilderImpl.kt`
- `DirectDIImpl.kt`, `DITreeImpl.kt`
- `concurrent.kt` - Threading/synchronization primitives
- `collections.kt` - Internal collection helpers

## Binding System

**DSL files (one per binding type):**
- `bindProvider.kt` - `provider { }` - new instance each time
- `bindSingleton.kt` - `singleton { }` - single shared instance
- `bindFactory.kt` - `factory { arg -> }` - new instance per call with arguments
- `bindMultiton.kt` - `multiton { arg -> }` - cached instance per unique argument value
- `bindInstance.kt` - `instance(obj)` - bind direct instance

**Binding implementations:** `bindings/standardBindings.kt`
- `Factory<C, A, T>` - Creates new instance on each call
- `Provider<C, T>` - Factory with Unit argument
- `Singleton<C, T>` - Single instance with scope support
- `Multiton<C, A, T>` - Cached instances per argument
- `EagerSingleton<T>` - Initialized at DI creation
- `InstanceBinding<T>` - Wraps existing instance

**Binding infrastructure:** `kodein-di/src/commonMain/kotlin/org/kodein/di/bindings/`
- `DIBinding.kt` - Base interface for all bindings
- `BindingDI.kt` - Context passed to binding creators
- `scopes.kt` - Scope interface, ScopeRegistry, NoScope, SingleItemScope, multiItem scopes
- `references.kt` - RefMaker, SingletonReference, SoftReference, WeakReference
- `ExternalSource.kt` - Fallback for unregistered bindings
- `set.kt` - SetBinding, ArgSetBinding, InSet for collection bindings

## Advanced Features

**Tagging & Organization:**
- `Named.kt` - Property delegation that uses property name as tag
- `Typed.kt` - Type token helpers

**Multi-argument support:**
- `curry.kt` - Currying for multi-argument factories

**Instance creation:**
- `New.kt` - `new(::Constructor)` for direct constructor invocation
- `ParameterizedNew.kt` - Constructor invocation with arguments

**Querying:**
- `hasFactory.kt` - Check if binding exists for given key
- `Search.kt` - Search for bindings by criteria (SearchSpecs, SearchDSL)

**Collections:**
- `SetBindings.kt` - `bindSet<T>()`, `addInSet()` for binding multiple values to set

**Modularity:**
- `subs.kt` - Sub-DI creation with inherited bindings
- `Copy.kt` - Copy DI containers with modifications

**Property delegation:**
- `properties.kt` - DIProperty helpers
- `lateinit.kt` - Late-initialized DI properties

**Type handling:**
- `erasedComp.kt` - Type erasure utilities
- `BindingsMap.kt` - Internal binding storage

**Tree structure:**
- `DITree.kt` - DI hierarchy representation

## Platform-Specific Code

**Source sets:**
- `commonMain/` - Shared across all platforms
- `jvmMain/` - JVM-only features
- `jsBasedMain/` - JS/Wasm shared
- `nativeMain/` - Native shared

**JVM-specific:** `jvmMain/kotlin/org/kodein/di/`
- `SubTypes.kt` - Type hierarchy support (subclass binding)
- `jvmReferences.kt` - Weak/soft references
- `bindings/standardScopes.kt` - Thread-local scope
- `bindings/subTypes.kt` - Subtype binding implementations
- `RetrievingJVM.kt`, `DirectDIJVM.kt`, `DIAwareJVM.kt` - JVM-specific retrieval
- `internal/collections.jvm.kt`, `internal/DirectDIJVMImpl.kt` - JVM internals

## Testing

**Structure:**
- Common: `kodein-di/src/commonTest/kotlin/org/kodein/di/Tests_XX_FeatureName.kt` (numbered 00-99)
- JVM: `jvmTest/kotlin/org/kodein/di/GenericJvmTests_*.kt`, `ErasedJvmTests_*.kt`

**Conventions:**
- `@FixMethodOrder(MethodSorters.NAME_ASCENDING)`
- Methods: `test_00_DescriptiveName`, `test_01_NextTest`
- Use `assertFailsWith<ExceptionType> { }` for error cases

**Test utils:** `test-utils/src/commonMain/kotlin/org/kodein/di/test/`
- `assert.kt` - `assertAllNull`, `assertAllNotNull`, `assertAllEqual`
- `person.kt` - Fixtures: `Person`, `IPerson`, `Name`, `FullName`, `FullInfos`, `A`, `B`, `C`, `D`, `E`, `F`
- `annotations.kt` - Test ordering annotations (platform-specific)

## Type Parameters Convention

Throughout codebase:
- `C` = Context type (scope context, defaults to `Any`)
- `A` = Argument type (for factories, defaults to `Unit`)
- `T` = Created type (the actual dependency/instance)

## Framework Integration Pattern

**Available framework modules:**
- `framework/android/kodein-di-framework-android-core`
- `framework/android/kodein-di-framework-android-support`
- `framework/android/kodein-di-framework-android-x`
- `framework/android/kodein-di-framework-android-x-viewmodel`
- `framework/android/kodein-di-framework-android-x-viewmodel-savedstate`
- `framework/compose/kodein-di-framework-android-x-compose`
- `framework/compose/kodein-di-framework-compose`
- `framework/compose/kodein-di-framework-compose-runtime`
- `framework/ktor/kodein-di-framework-ktor-server-jvm`
- `framework/ktor/kodein-di-framework-ktor-server-controller-jvm`
- `framework/tornadofx/kodein-di-framework-tornadofx-jvm`

**Standard pattern:** Framework modules provide:
1. `closest()` - Find DI in component hierarchy
2. Scope implementations for framework lifecycle
3. Framework-specific binding extensions

## Key Interfaces to Know

When extending Kodein-DI:
- `DIAware` - For classes that hold a DI instance
- `DIBinding<C, A, T>` - Custom binding implementations
- `Scope<C>` - Custom scopes with registry
- `ExternalSource` - Fallback dependency sources for unregistered bindings
- `ContextTranslator<C, S>` - Translate between scope contexts
