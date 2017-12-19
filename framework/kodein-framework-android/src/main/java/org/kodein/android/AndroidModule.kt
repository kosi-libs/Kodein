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
import android.service.wallpaper.WallpaperService
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
import org.kodein.*
import org.kodein.bindings.*
import java.io.File

/**
 * Defines the [androidModule]
 */
@SuppressLint("NewApi")
fun androidModule(app: Application) = Kodein.Module(name = "\u2063androidModule") {

    fun Any.anyAndroidContext() = when(this) {
            is Context -> this
            is android.app.Fragment -> this.context
            is android.support.v4.app.Fragment -> this.context
            is Dialog -> this.context
            is View -> this.context
            is android.content.Loader<*> -> this.context
            is android.support.v4.content.Loader<*> -> this.context
            is AbstractThreadedSyncAdapter -> this.context
            else -> null
    }

    fun <T> T.androidContext(): Context where T: WithReceiver, T: WithContext<*> =
            receiver?.anyAndroidContext() ?: context?.anyAndroidContext() ?: app

    bind() from Provider(AnyToken, erased()) { app }

    bind() from Provider(AnyToken, erased()) { androidContext().assets }
    bind() from Provider(AnyToken, erased()) { androidContext().contentResolver }
    bind() from Provider(AnyToken, erased()) { androidContext().applicationInfo }
    bind() from Provider(AnyToken, erased()) { androidContext().mainLooper }
    bind() from Provider(AnyToken, erased()) { androidContext().packageManager }
    bind() from Provider(AnyToken, erased()) { androidContext().resources }
    bind() from Provider(AnyToken, erased()) { androidContext().theme }

    bind() from Provider(AnyToken, erased()) { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    bind() from Factory(AnyToken, erased(), erased()) { name: String -> androidContext().getSharedPreferences(name, Context.MODE_PRIVATE) }

    Bind<File>(erased(), tag = "cache") with Provider(AnyToken, erased()) { androidContext().cacheDir }
    Bind<File>(erased(), tag = "externalCache") with Provider(AnyToken, erased()) { androidContext().externalCacheDir }
    Bind<File>(erased(), tag = "files") with Provider(AnyToken, erased()) { androidContext().filesDir }
    Bind<File>(erased(), tag = "obb") with Provider(AnyToken, erased()) { androidContext().obbDir }

    Bind<String>(erased(), tag = "packageCodePath") with Provider(AnyToken, erased()) { androidContext().packageCodePath }
    Bind<String>(erased(), tag = "packageName") with Provider(AnyToken, erased()) { androidContext().packageName }
    Bind<String>(erased(), tag = "packageResourcePath") with Provider(AnyToken, erased()) { androidContext().packageResourcePath }

    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NFC_SERVICE) as NfcManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.POWER_SERVICE) as PowerManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.USB_SERVICE) as UsbManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WALLPAPER_SERVICE) as WallpaperService }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WIFI_SERVICE) as WifiManager }
    bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    if (Build.VERSION.SDK_INT >= 16) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.INPUT_SERVICE) as InputManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NSD_SERVICE) as NsdManager }
    }

    if (Build.VERSION.SDK_INT >= 17) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.USER_SERVICE) as UserManager }
    }

    if (Build.VERSION.SDK_INT >= 18) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    }

    if (Build.VERSION.SDK_INT >= 19) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.PRINT_SERVICE) as PrintManager }
    }

    if (Build.VERSION.SDK_INT >= 21) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
    }

    if (Build.VERSION.SDK_INT >= 22) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 23) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MIDI_SERVICE) as MidiManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 24) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
    }

    if (Build.VERSION.SDK_INT >= 25) {
        bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
    }
}
