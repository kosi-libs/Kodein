package org.kodein.android

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.*
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.bluetooth.BluetoothManager
import android.content.*
import android.content.pm.ApplicationInfo
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.content.pm.ShortcutManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.hardware.ConsumerIrManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.display.DisplayManager
import android.hardware.fingerprint.FingerprintManager
import android.hardware.input.InputManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.MediaRouter
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.nsd.NsdManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.*
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.preference.PreferenceManager
import android.print.PrintManager
import android.service.wallpaper.WallpaperService
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import org.kodein.*
import org.kodein.bindings.FactoryBinding
import org.kodein.bindings.ProviderBinding
import java.io.File

/**
 * Defines the [androidModule]
 */
@SuppressLint("NewApi")
private fun defineAndroidModule() = Kodein.Module {

    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.applicationContext as Application }

    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.assets }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.contentResolver }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.applicationInfo }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.mainLooper }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.packageManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.resources }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.theme }

    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> PreferenceManager.getDefaultSharedPreferences(ctx) }

    Bind<File>(erased(), tag = "cache") with FactoryBinding(erased(), erased()) { ctx: Context -> ctx.cacheDir }
    Bind<File>(erased(), tag = "externalCache") with FactoryBinding(erased(), erased()) { ctx: Context -> ctx.externalCacheDir }
    Bind<File>(erased(), tag = "files") with FactoryBinding(erased(), erased()) { ctx: Context -> ctx.filesDir }
    Bind<File>(erased(), tag = "obb") with FactoryBinding(erased(), erased()) { ctx: Context -> ctx.obbDir }

    Bind<String>(erased(), tag = "packageCodePath") with FactoryBinding(erased(), erased()) { ctx: Context -> ctx.packageCodePath }
    Bind<String>(erased(), tag = "packageName") with FactoryBinding(erased(), erased()) { ctx: Context -> ctx.packageName }
    Bind<String>(erased(), tag = "packageResourcePath") with FactoryBinding(erased(), erased()) { ctx: Context -> ctx.packageResourcePath }

    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.NFC_SERVICE) as NfcManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.POWER_SERVICE) as PowerManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.USB_SERVICE) as UsbManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperService }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.WIFI_SERVICE) as WifiManager }
    bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    if (Build.VERSION.SDK_INT >= 16) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.INPUT_SERVICE) as InputManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.NSD_SERVICE) as NsdManager }
    }

    if (Build.VERSION.SDK_INT >= 17) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.USER_SERVICE) as UserManager }
    }

    if (Build.VERSION.SDK_INT >= 18) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    }

    if (Build.VERSION.SDK_INT >= 19) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.PRINT_SERVICE) as PrintManager }
    }

    if (Build.VERSION.SDK_INT >= 21) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
    }

    if (Build.VERSION.SDK_INT >= 22) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 23) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.MIDI_SERVICE) as MidiManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 24) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
    }

    if (Build.VERSION.SDK_INT >= 25) {
        bind() from FactoryBinding(erased(), erased()) { ctx: Context -> ctx.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
    }
}

/**
 * A module that binds a lot of Android framework classes:
 *
 * ```kotlin
 * class MyActivity : Activity(), KodeinInjected {
 *   override val injector = KodeinInjector()
 *
 *   override val inflator: LayoutInflator by withContext(this).instance()
 * }
 * ```
 */
val androidModule = defineAndroidModule()

/**
 * A helper class for binding a named SharedPreferences
 *
 * @property ctx A context
 * @property name The name of the shared preferences.
 * @property visibility The visibility of the shared preferences, when creating it.
 */
data class KodeinSharedPreferencesInfo(val ctx: Context, val name: String, val visibility: Int = Context.MODE_PRIVATE) {
    /**
     * @return The shared preferemces given the classes parameters.
     */
    fun getSharedPreferences(): SharedPreferences = ctx.getSharedPreferences(name, visibility)
}

/**
 * A module that binds a lot of Android framework classes:
 *
 * ```kotlin
 * class MyActivity : Activity(), KodeinInjected {
 *   override val injector = KodeinInjector()
 *
 *   override val inflator: LayoutInflator by instance()
 * }
 * ```
 */
@SuppressLint("NewApi")
fun autoAndroidModule(app: Application) = Kodein.Module {
    fun Kodein.ctx() = InstanceOrNull<Context>(erased()) ?: app.applicationContext

    bind() from ProviderBinding(erased()) { app }

    bind() from ProviderBinding(erased()) { ctx().assets }
    bind() from ProviderBinding(erased()) { ctx().contentResolver }
    bind() from ProviderBinding(erased()) { ctx().applicationInfo }
    bind() from ProviderBinding(erased()) { ctx().mainLooper }
    bind() from ProviderBinding(erased()) { ctx().packageManager }
    bind() from ProviderBinding(erased()) { ctx().resources }
    bind() from ProviderBinding(erased()) { ctx().theme }

    bind() from ProviderBinding(erased()) { PreferenceManager.getDefaultSharedPreferences(ctx()) }
    bind(tag = "named") from FactoryBinding(erased(), erased()) { info: KodeinSharedPreferencesInfo -> info.getSharedPreferences() }

    Bind<File>(erased(), tag = "cache") with ProviderBinding(erased()) { ctx().cacheDir }
    Bind<File>(erased(), tag = "externalCache") with ProviderBinding(erased()) { ctx().externalCacheDir }
    Bind<File>(erased(), tag = "files") with ProviderBinding(erased()) { ctx().filesDir }
    Bind<File>(erased(), tag = "obb") with ProviderBinding(erased()) { ctx().obbDir }

    Bind<String>(erased(), tag = "packageCodePath") with ProviderBinding(erased()) { ctx().packageCodePath }
    Bind<String>(erased(), tag = "packageName") with ProviderBinding(erased()) { ctx().packageName }
    Bind<String>(erased(), tag = "packageResourcePath") with ProviderBinding(erased()) { ctx().packageResourcePath }

    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.NFC_SERVICE) as NfcManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.POWER_SERVICE) as PowerManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.USB_SERVICE) as UsbManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.WALLPAPER_SERVICE) as WallpaperService }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.WIFI_SERVICE) as WifiManager }
    bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.INPUT_SERVICE) as InputManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.NSD_SERVICE) as NsdManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.USER_SERVICE) as UserManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.PRINT_SERVICE) as PrintManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.MIDI_SERVICE) as MidiManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        bind() from ProviderBinding(erased()) { ctx().getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
    }
}
