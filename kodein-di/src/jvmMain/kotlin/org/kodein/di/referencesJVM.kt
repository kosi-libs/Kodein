package org.kodein.di

import org.kodein.di.bindings.*


// Since 7.1
@Deprecated("Changed name: StrongReference", ReplaceWith("Strong", "org.kodein.di.bindings.Strong"))
public val SingletonReference : Reference.Maker = Strong

// Since 7.1
@Deprecated("Changed package", replaceWith = ReplaceWith("ThreadLocal", "org.kodein.di.bindings.ThreadLocal"))
public val threadLocal: Reference.Maker = ThreadLocal

// Since 7.1
@Deprecated("Changed package", replaceWith = ReplaceWith("Soft", "org.kodein.di.bindings.Soft"))
public val softReference: Reference.Maker = Soft

// Since 7.1
@Deprecated("Changed package", replaceWith = ReplaceWith("Weak", "org.kodein.di.bindings.Weak"))
public val weakReference : Reference.Maker = Weak
