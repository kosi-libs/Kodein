package com.github.salomonbrys.kodein.android

import android.accounts.AccountManager
import android.app.*
import android.app.admin.DevicePolicyManager
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.hardware.SensorManager
import android.hardware.usb.UsbManager
import android.location.LocationManager
import android.media.AudioManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pManager
import android.nfc.NfcManager
import android.os.DropBoxManager
import android.os.Looper
import android.os.PowerManager
import android.os.Vibrator
import android.os.storage.StorageManager
import android.preference.PreferenceManager
import android.service.wallpaper.WallpaperService
import android.telephony.TelephonyManager
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
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
val androidModule = Kodein.Module {

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

}
