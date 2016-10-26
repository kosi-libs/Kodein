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
import android.view.LayoutInflater
import android.view.WindowManager
import android.view.accessibility.AccessibilityManager
import android.view.inputmethod.InputMethodManager
import android.view.textservice.TextServicesManager
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.erasedFactory
import java.io.File

/**
 * A module that binds a lot of services for Android.
 *
 * With that, you can easily retrieve Android's services:
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

    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ACCOUNT_SERVICE) as AccountManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.DROPBOX_SERVICE) as DropBoxManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.NFC_SERVICE) as NfcManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.POWER_SERVICE) as PowerManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.SEARCH_SERVICE) as SearchManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.STORAGE_SERVICE) as StorageManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE) as TextServicesManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.USB_SERVICE) as UsbManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperService }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WIFI_SERVICE) as WifiManager }
    bind() from erasedFactory { ctx: Context -> ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

}
