package com.example.ignore_battery_optimizations

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class IgnoreBatteryOptimizationsPlugin : MethodCallHandler {
    companion object {
        private lateinit var reg: Registrar
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "ignore_battery_optimizations")
            channel.setMethodCallHandler(IgnoreBatteryOptimizationsPlugin())
            reg = registrar
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        if (call.method == "openBatteryOptimizations") {
            openBatteryOptimizations()
            result.success(Build.BRAND.toLowerCase())
        } else {
            result.notImplemented()
        }
    }

    private fun openBatteryOptimizations() {
        if (!isIgnoringBatteryOptimizations()) {
            requestIgnoreBatteryOptimizations()
        }
        if (isHuawei()) {
            goHuaweiSetting()
            return
        }
        if (isXiaomi()) {
            goXiaomiSetting()
            return
        }
        if (isLeTV()) {
            goLetvSetting()
            return
        }
        if (isMeizu()) {
            goMeizuSetting()
            return
        }
        if (isOPPO()) {
            goOPPOSetting()
            return
        }
        if (isSamsung()) {
            goSamsungSetting()
            return
        }
        if (isSmartisan()) {
            goSmartisanSetting()
            return
        }
        if (isVIVO()) {
            goVIVOSetting()
            return
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun isIgnoringBatteryOptimizations(): Boolean {
        var isIgnoring = false
        val powerManager = reg.context().getSystemService(Context.POWER_SERVICE) as PowerManager?
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(reg.context().packageName)
        }
        return isIgnoring
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestIgnoreBatteryOptimizations() {
        try {
            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.data = Uri.parse("package:" + reg.context().packageName)
            reg.context().startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private fun showActivity(packageName: String) {
        val intent: Intent? = reg.context().packageManager.getLaunchIntentForPackage(packageName)
        reg.context().startActivity(intent)
    }

    private fun showActivity(packageName: String, activityDir: String) {
        val intent = Intent()
        intent.component = ComponentName(packageName, activityDir)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        reg.context().startActivity(intent)
    }

    private fun isHuawei(): Boolean {
        return isBrand("huawei") || isBrand("honor")
    }

    private fun goHuaweiSetting() {
        try {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")
        } catch (e: Exception) {
            showActivity("com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity")
        }
    }

    private fun isBrand(brand: String): Boolean {
        return Build.BRAND != null && Build.BRAND.toLowerCase() == brand
    }

    private fun isXiaomi(): Boolean {
        return isBrand("xiaomi")
    }

    private fun goXiaomiSetting() {
        showActivity("com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity")
    }

    private fun isOPPO(): Boolean {
        return isBrand("oppo")
    }

    private fun goOPPOSetting() {
        try {
            showActivity("com.coloros.phonemanager")
        } catch (e1: Exception) {
            try {
                showActivity("com.oppo.safe")
            } catch (e2: Exception) {
                try {
                    showActivity("com.coloros.oppoguardelf")
                } catch (e3: Exception) {
                    showActivity("com.coloros.safecenter")
                }
            }
        }
    }

    private fun isVIVO(): Boolean {
        return isBrand("vivo")
    }

    private fun goVIVOSetting() {
        showActivity("com.iqoo.secure")
    }

    private fun isMeizu(): Boolean {
        return isBrand("meizu")
    }

    private fun goMeizuSetting() {
        showActivity("com.meizu.safe")
    }

    private fun isSamsung(): Boolean {
        return isBrand("samsung")
    }

    private fun goSamsungSetting() {
        try {
            showActivity("com.samsung.android.sm_cn")
        } catch (e: Exception) {
            showActivity("com.samsung.android.sm")
        }
    }

    private fun isLeTV(): Boolean {
        return isBrand("letv")
    }

    private fun goLetvSetting() {
        showActivity("com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity")
    }

    private fun isSmartisan(): Boolean {
        return isBrand("smartisan")
    }

    private fun goSmartisanSetting() {
        showActivity("com.smartisanos.security")
    }
}
