@file:Suppress("DEPRECATION")

package org.kodein.di.android

import android.accounts.AccountManager
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.Application
import android.app.Dialog
import android.app.DownloadManager
import android.app.Fragment
import android.app.KeyguardManager
import android.app.NotificationManager
import android.app.SearchManager
import android.app.UiModeManager
import android.app.WallpaperManager
import android.app.admin.DevicePolicyManager
import android.app.job.JobScheduler
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStatsManager
import android.appwidget.AppWidgetManager
import android.content.AbstractThreadedSyncAdapter
import android.content.ClipboardManager
import android.content.Context
import android.content.Loader
import android.content.RestrictionsManager
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.hardware.fingerprint.FingerprintManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.media.midi.MidiManager
import android.media.projection.MediaProjectionManager
import android.media.session.MediaSessionManager
import android.media.tv.TvInputManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.BatteryManager
import android.os.Build
import android.os.DropBoxManager
import android.os.HardwarePropertiesManager
import android.os.PowerManager
import android.os.Vibrator
import android.os.health.SystemHealthManager
import android.os.storage.StorageManager
import android.preference.PreferenceManager
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.bindings.Factory
import org.kodein.di.bindings.Provider
import org.kodein.di.bindings.SimpleContextTranslator
import org.kodein.type.TypeToken
import org.kodein.type.generic

public val androidCoreContextTranslators: DI.Module = DI.Module(name = "\u2063androidCoreContextTranslators") {
    RegisterContextTranslator(SimpleContextTranslator<Fragment, Activity>(generic(), generic()) { it.activity })
    RegisterContextTranslator(SimpleContextTranslator<Dialog, Context>(generic(), generic()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<View, Context>(generic(), generic()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<Loader<*>, Context>(generic(), generic()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<AbstractThreadedSyncAdapter, Context>(generic(), generic()) { it.context })
}

/**
 * Android `DI.Module` that defines a lot of platform bindings.
 *
 * @param app The application object, used for context.
 * @return An Android `DI.Module` that defines a lot of platform bindings.
 */
@SuppressLint("NewApi")
public fun androidCoreModule(app: Application): DI.Module = DI.Module(name = "\u2063androidModule") {

    importOnce(androidCoreContextTranslators)

    val contextToken = generic<Context>()

    bind { Provider(TypeToken.Any, generic()) { app } }

    bind { Provider(contextToken, generic()) { context.assets } }
    bind { Provider(contextToken, generic()) { context.contentResolver } }
    bind { Provider(contextToken, generic()) { context.applicationInfo } }
    bind { Provider(contextToken, generic()) { context.mainLooper } }
    bind { Provider(contextToken, generic()) { context.packageManager } }
    bind { Provider(contextToken, generic()) { context.resources } }
    bind { Provider(contextToken, generic()) { context.theme } }

    bind { Provider(contextToken, generic()) { PreferenceManager.getDefaultSharedPreferences(context) } }
    bind {
        Factory(contextToken, generic(), generic()) { name: String ->
            context.getSharedPreferences(
                name,
                Context.MODE_PRIVATE
            )
        }
    }

    bind(tag = "cache") { Provider(contextToken, generic()) { context.cacheDir } }
    // Bind<File>(generic(), tag = "externalCache") with Provider(contextToken, generic()) { context.externalCacheDir } TODO: re-enable once we found how to bind nullables
    bind(tag = "files") { Provider(contextToken, generic()) { context.filesDir } }
    bind(tag = "obb") { Provider(contextToken, generic()) { context.obbDir } }

    bind(tag = "packageCodePath") { Provider(contextToken, generic()) { context.packageCodePath } }
    bind(tag = "packageName") { Provider(contextToken, generic()) { context.packageName } }
    bind(tag = "packageResourcePath") { Provider(contextToken, generic()) { context.packageResourcePath } }

    bind { Provider(contextToken, generic()) { context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.ALARM_SERVICE) as AlarmManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.NFC_SERVICE) as NfcManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.POWER_SERVICE) as PowerManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.SEARCH_SERVICE) as SearchManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.STORAGE_SERVICE) as StorageManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.USB_SERVICE) as UsbManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.WIFI_SERVICE) as WifiManager } }
    bind { Provider(contextToken, generic()) { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager } }

    if (Build.VERSION.SDK_INT >= 21) {
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.CAMERA_SERVICE) as CameraManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager } }
    }

    if (Build.VERSION.SDK_INT >= 22) {
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager } }
    }

    if (Build.VERSION.SDK_INT >= 23) {
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.MIDI_SERVICE) as MidiManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager } }
    }

    if (Build.VERSION.SDK_INT >= 24) {
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager } }
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager } }
    }

    if (Build.VERSION.SDK_INT >= 25) {
        bind { Provider(contextToken, generic()) { context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager } }
    }
}
