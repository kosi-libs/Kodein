@file:Suppress("DEPRECATION")

package org.kodein.di.android

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
import android.content.pm.LauncherApps
import android.content.pm.ShortcutManager
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
import android.telecom.TelecomManager
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.CaptioningManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import org.kodein.di.*
import org.kodein.di.bindings.*
import java.io.File

val androidCoreContextTranslators = DI.Module(name = "\u2063androidCoreContextTranslators") {
    RegisterContextTranslator(SimpleContextTranslator<Fragment, Activity>(generic(), erased()) { it.activity })
    RegisterContextTranslator(SimpleContextTranslator<Dialog, Context>(generic(), erased()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<View, Context>(generic(), erased()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<Loader<*>, Context>(generic(), erased()) { it.context })
    RegisterContextTranslator(SimpleContextTranslator<AbstractThreadedSyncAdapter, Context>(generic(), erased()) { it.context })
}

/**
 * Android `DI.Module` that defines a lot of platform bindings.
 *
 * @param app The application object, used for context.
 * @return An Android `DI.Module` that defines a lot of platform bindings.
 */
@SuppressLint("NewApi")
fun androidCoreModule(app: Application) = DI.Module(name = "\u2063androidModule") {

    importOnce(androidCoreContextTranslators)

    val contextToken = erased<Context>()

    Bind() from Provider(TypeToken.Any, erased()) { app }

    Bind() from Provider(contextToken, erased()) { context.assets }
    Bind() from Provider(contextToken, erased()) { context.contentResolver }
    Bind() from Provider(contextToken, erased()) { context.applicationInfo }
    Bind() from Provider(contextToken, erased()) { context.mainLooper }
    Bind() from Provider(contextToken, erased()) { context.packageManager }
    Bind() from Provider(contextToken, erased()) { context.resources }
    Bind() from Provider(contextToken, erased()) { context.theme }

    Bind() from Provider(contextToken, erased()) { PreferenceManager.getDefaultSharedPreferences(context) }
    Bind() from Factory(contextToken, generic(), erased()) { name: String -> context.getSharedPreferences(name, Context.MODE_PRIVATE) }

    Bind<File>(generic(), tag = "cache") with Provider(contextToken, erased()) { context.cacheDir }
    Bind<File>(generic(), tag = "externalCache") with Provider(contextToken, erased()) { context.externalCacheDir }
    Bind<File>(generic(), tag = "files") with Provider(contextToken, erased()) { context.filesDir }
    Bind<File>(generic(), tag = "obb") with Provider(contextToken, erased()) { context.obbDir }

    Bind<String>(generic(), tag = "packageCodePath") with Provider(contextToken, erased()) { context.packageCodePath }
    Bind<String>(generic(), tag = "packageName") with Provider(contextToken, erased()) { context.packageName }
    Bind<String>(generic(), tag = "packageResourcePath") with Provider(contextToken, erased()) { context.packageResourcePath }

    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.NFC_SERVICE) as NfcManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.POWER_SERVICE) as PowerManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.USB_SERVICE) as UsbManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.WIFI_SERVICE) as WifiManager }
    Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    if (Build.VERSION.SDK_INT >= 16) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.INPUT_SERVICE) as InputManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.NSD_SERVICE) as NsdManager }
    }

    if (Build.VERSION.SDK_INT >= 17) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.USER_SERVICE) as UserManager }
    }

    if (Build.VERSION.SDK_INT >= 18) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    }

    if (Build.VERSION.SDK_INT >= 19) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.PRINT_SERVICE) as PrintManager }
    }

    if (Build.VERSION.SDK_INT >= 21) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
    }

    if (Build.VERSION.SDK_INT >= 22) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 23) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.MIDI_SERVICE) as MidiManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 24) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
    }

    if (Build.VERSION.SDK_INT >= 25) {
        Bind() from Provider(contextToken, erased()) { context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
    }
}
