package com.github.salomonbrys.kodein.android

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
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.erasedFactory
import com.github.salomonbrys.kodein.erasedInstanceOrNull
import com.github.salomonbrys.kodein.erasedProvider
import java.io.File

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
@SuppressLint("NewApi")
private fun defineAndroidModule() = Kodein.Module {

    bindErased<Application>() with erasedFactory { ctx: Context -> ctx.applicationContext as Application }

    bindErased<AssetManager>() with erasedFactory { ctx: Context -> ctx.assets }
    bindErased<ContentResolver>() with erasedFactory { ctx: Context -> ctx.contentResolver }
    bindErased<ApplicationInfo>() with erasedFactory { ctx: Context -> ctx.applicationInfo }
    bindErased<Looper>() with erasedFactory { ctx: Context -> ctx.mainLooper }
    bindErased<PackageManager>() with erasedFactory { ctx: Context -> ctx.packageManager }
    bindErased<Resources>() with erasedFactory { ctx: Context -> ctx.resources }
    bindErased<Resources.Theme>() with erasedFactory { ctx: Context -> ctx.theme }

    bindErased<SharedPreferences>() with erasedFactory { ctx: Context -> PreferenceManager.getDefaultSharedPreferences(ctx) }

    bindErased<File>("cache") with erasedFactory { ctx: Context -> ctx.cacheDir }
    bindErased<File>("externalCache") with erasedFactory { ctx: Context -> ctx.externalCacheDir }
    bindErased<File>("files") with erasedFactory { ctx: Context -> ctx.filesDir }
    bindErased<File>("obb") with erasedFactory { ctx: Context -> ctx.obbDir }

    bindErased<String>("packageCodePath") with erasedFactory { ctx: Context -> ctx.packageCodePath }
    bindErased<String>("packageName") with erasedFactory { ctx: Context -> ctx.packageName }
    bindErased<String>("packageResourcePath") with erasedFactory { ctx: Context -> ctx.packageResourcePath }

    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.NFC_SERVICE) as NfcManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.POWER_SERVICE) as PowerManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.USB_SERVICE) as UsbManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperService }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WIFI_SERVICE) as WifiManager }
    bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    if (Build.VERSION.SDK_INT >= 16) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.INPUT_SERVICE) as InputManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.NSD_SERVICE) as NsdManager }
    }

    if (Build.VERSION.SDK_INT >= 17) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.USER_SERVICE) as UserManager }
    }

    if (Build.VERSION.SDK_INT >= 18) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    }

    if (Build.VERSION.SDK_INT >= 19) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.PRINT_SERVICE) as PrintManager }
    }

    if (Build.VERSION.SDK_INT >= 21) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
    }

    if (Build.VERSION.SDK_INT >= 22) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 23) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.MIDI_SERVICE) as MidiManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 24) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
    }

    if (Build.VERSION.SDK_INT >= 25) {
        bindDirect() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
    }
}

val androidModule = defineAndroidModule()

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
    fun Kodein.ctx() = erasedInstanceOrNull<Context>() ?: app.applicationContext

    data class SharedPreferencesInfo(val ctx: Context, val name: String, val visibility: Int = Context.MODE_PRIVATE) {
        fun getSharedPreferences() = ctx.getSharedPreferences(name, visibility)
    }

    bindErased<Application>() with erasedProvider { app }

    bindErased<AssetManager>() with erasedProvider { ctx().assets }
    bindErased<ContentResolver>() with erasedProvider { ctx().contentResolver }
    bindErased<ApplicationInfo>() with erasedProvider { ctx().applicationInfo }
    bindErased<Looper>() with erasedProvider { ctx().mainLooper }
    bindErased<PackageManager>() with erasedProvider { ctx().packageManager }
    bindErased<Resources>() with erasedProvider { ctx().resources }
    bindErased<Resources.Theme>() with erasedProvider { ctx().theme }

    bindErased<SharedPreferences>() with erasedProvider { PreferenceManager.getDefaultSharedPreferences(ctx()) }
    bindErased<SharedPreferences>("named") with erasedFactory { info: SharedPreferencesInfo -> info.getSharedPreferences() }

    bindErased<File>("cache") with erasedProvider { ctx().cacheDir }
    bindErased<File>("externalCache") with erasedProvider { ctx().externalCacheDir }
    bindErased<File>("files") with erasedProvider { ctx().filesDir }
    bindErased<File>("obb") with erasedProvider { ctx().obbDir }

    bindErased<String>("packageCodePath") with erasedProvider { ctx().packageCodePath }
    bindErased<String>("packageName") with erasedProvider { ctx().packageName }
    bindErased<String>("packageResourcePath") with erasedProvider { ctx().packageResourcePath }

    bindDirect() from erasedProvider { ctx().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.NFC_SERVICE) as NfcManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.POWER_SERVICE) as PowerManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.USB_SERVICE) as UsbManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.WALLPAPER_SERVICE) as WallpaperService }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.WIFI_SERVICE) as WifiManager }
    bindDirect() from erasedProvider { ctx().getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.INPUT_SERVICE) as InputManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.NSD_SERVICE) as NsdManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.USER_SERVICE) as UserManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.PRINT_SERVICE) as PrintManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.MIDI_SERVICE) as MidiManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
        bindDirect() from erasedProvider { ctx().getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        bindDirect() from erasedProvider { ctx().getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
    }
}
