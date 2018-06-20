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

interface AndroidContextGetter {
    fun get(from: Any): Context?

    companion object {
        operator fun invoke(block: (Any) -> Context?) = object : AndroidContextGetter {
            override fun get(from: Any) = block(from)
        }
    }
}

/**
 * Android `Kodein.Module` that defines a lot of platform bindings.
 *
 * @param app The application object, used for context.
 * @return An Android `Kodein.Module` that defines a lot of platform bindings.
 */
@SuppressLint("NewApi")
fun androidModule(app: Application) = Kodein.Module(name = "\u2063androidModule") {

    Bind() from SetBinding<Any?, AndroidContextGetter>(AnyToken, erased(), erasedSet())

    Bind<AndroidContextGetter>(erased()).InSet(erasedSet()) with InstanceBinding(erased(), AndroidContextGetter {
        when(it) {
            is Fragment -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) it.context else it.activity
            is Dialog -> it.context
            is View -> it.context
            is Loader<*> -> it.context
            is AbstractThreadedSyncAdapter -> it.context
            else -> null
        }
    })

    lateinit var contextGetters: Set<AndroidContextGetter>

    onReady {
        contextGetters = Instance(erasedSet())
    }

    fun Any.anyAndroidContext(): Context? {
        (this as? Context)?.let { return it }

        contextGetters.forEach {
            it.get(this)?.let { return it }
        }

        return null
    }

    fun <T> T.androidContext(): Context where T: WithReceiver, T: WithContext<*> =
            receiver?.anyAndroidContext() ?: context?.anyAndroidContext() ?: app

    Bind() from Provider(AnyToken, erased()) { app }

    Bind() from Provider(AnyToken, erased()) { androidContext().assets }
    Bind() from Provider(AnyToken, erased()) { androidContext().contentResolver }
    Bind() from Provider(AnyToken, erased()) { androidContext().applicationInfo }
    Bind() from Provider(AnyToken, erased()) { androidContext().mainLooper }
    Bind() from Provider(AnyToken, erased()) { androidContext().packageManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().resources }
    Bind() from Provider(AnyToken, erased()) { androidContext().theme }

    Bind() from Provider(AnyToken, erased()) { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    Bind() from Factory(AnyToken, erased(), erased()) { name: String -> androidContext().getSharedPreferences(name, Context.MODE_PRIVATE) }

    Bind<File>(erased(), tag = "cache") with Provider(AnyToken, erased()) { androidContext().cacheDir }
    Bind<File>(erased(), tag = "externalCache") with Provider(AnyToken, erased()) { androidContext().externalCacheDir }
    Bind<File>(erased(), tag = "files") with Provider(AnyToken, erased()) { androidContext().filesDir }
    Bind<File>(erased(), tag = "obb") with Provider(AnyToken, erased()) { androidContext().obbDir }

    Bind<String>(erased(), tag = "packageCodePath") with Provider(AnyToken, erased()) { androidContext().packageCodePath }
    Bind<String>(erased(), tag = "packageName") with Provider(AnyToken, erased()) { androidContext().packageName }
    Bind<String>(erased(), tag = "packageResourcePath") with Provider(AnyToken, erased()) { androidContext().packageResourcePath }

    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NFC_SERVICE) as NfcManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.POWER_SERVICE) as PowerManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.USB_SERVICE) as UsbManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WIFI_SERVICE) as WifiManager }
    Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    if (Build.VERSION.SDK_INT >= 16) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.INPUT_SERVICE) as InputManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MEDIA_ROUTER_SERVICE) as MediaRouter }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NSD_SERVICE) as NsdManager }
    }

    if (Build.VERSION.SDK_INT >= 17) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.USER_SERVICE) as UserManager }
    }

    if (Build.VERSION.SDK_INT >= 18) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager }
    }

    if (Build.VERSION.SDK_INT >= 19) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CAPTIONING_SERVICE) as CaptioningManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.PRINT_SERVICE) as PrintManager }
    }

    if (Build.VERSION.SDK_INT >= 21) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.APPWIDGET_SERVICE) as AppWidgetManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CAMERA_SERVICE) as CameraManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.RESTRICTIONS_SERVICE) as RestrictionsManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TELECOM_SERVICE) as TelecomManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TV_INPUT_SERVICE) as TvInputManager }
    }

    if (Build.VERSION.SDK_INT >= 22) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 23) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.MIDI_SERVICE) as MidiManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager }
    }

    if (Build.VERSION.SDK_INT >= 24) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager }
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager }
    }

    if (Build.VERSION.SDK_INT >= 25) {
        Bind() from Provider(AnyToken, erased()) { androidContext().getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager }
    }
}
