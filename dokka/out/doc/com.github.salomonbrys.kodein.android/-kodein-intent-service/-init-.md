[com.github.salomonbrys.kodein.android](../index.md) / [KodeinIntentService](index.md) / [&lt;init&gt;](.)

# &lt;init&gt;

`KodeinIntentService()`

Default constructor that sets the service name to "KodeinIntentService".

`KodeinIntentService(name: String)`

A base class that manages an [IntentServiceInjector](../-intent-service-injector/index.md) for easy bootstrapping of Kodein.
Injections will be available after `super.onCreate` and will be destroyed after `super.onDestroy`.

### Parameters

`name` - The name of the service