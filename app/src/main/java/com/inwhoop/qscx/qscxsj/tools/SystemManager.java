package com.inwhoop.qscx.qscxsj.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Lucky on 2016/9/28.
 */

public class SystemManager {

    /**
     * 使用外部浏览器打开URL
     */
    public static void openOutUrl(Context context, String outUrl) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(outUrl));
        context.startActivity(intent);
    }


    /**
     * 直接呼叫指定的号码(需要<uses-permission
     * android:name="android.permission.CALL_PHONE"/>权限)
     *
     * @param mContext    上下文Context
     * @param phoneNumber 需要呼叫的手机号码
     */
    public static void callPhone(Context mContext, String phoneNumber) {
        Uri uri = Uri.parse("tel:" + phoneNumber);
        // Intent call = new Intent(Intent.ACTION_CALL, uri);// 直接拨打
        Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + phoneNumber)); // 调出拨号键盘

        mContext.startActivity(call);

    }

    /**
     * 获取手mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress().replace(":", "");
                else
                    return macAddress;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }

    /**
     * 获取电话号码
     */
    public static String getNativePhoneNumber(Context context) {
        String nativePhoneNumber = "";
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        nativePhoneNumber = telephonyManager.getLine1Number();
        if (TextUtils.isEmpty(nativePhoneNumber)) {
            return "";
        } else {
            return nativePhoneNumber;
        }
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context) {

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            int versionCode = info.versionCode;
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取版本名
     *
     * @return 当前应用的版本名
     */
    public static String getVersionName(Context context) {

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String versionName = info.versionName;
            return versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * 获取设备硬件唯一id
     *
     * @return 当前设备唯一id
     */
    public static String getDeviceId(Context context) {

//        String deviceId;
//
//        try {
//
//            TelephonyManager manager = (TelephonyManager) ((Activity) context).getSystemService(Context.TELEPHONY_SERVICE);
//            String imei = manager.getDeviceId();
////            return imei;
//            deviceId = imei;
//        } catch (Exception e) {
//            e.printStackTrace();
////            return android.os.Build.SERIAL;
//            deviceId = android.os.Build.SERIAL;
//        }


        final TelephonyManager tm = (TelephonyManager) ((Activity) context).getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(((Activity) context).getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();


        uniqueId = uniqueId.replace("-", "");
        uniqueId = uniqueId.toUpperCase();

        return uniqueId;

    }


    /**
     * 判断应用是否正在运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : list) {
            String processName = appProcess.processName;
            if (processName != null && processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * MD5加密，32位
     */
    public static String getMD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    public static String updateWebViewContent(Activity activity, String content, boolean night, boolean flag) {

        try {
            InputStream inputStream = activity.getResources().getAssets().open(
                    "discover.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream), 16 * 1024);
            StringBuilder sBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            String modelHtml = sBuilder.toString();
            inputStream.close();
            reader.close();

            String contentNew = modelHtml.replace(
                    "<--@#$%discoverContent@#$%-->", content);
            if (night) {
                contentNew = contentNew.replace("<--@#$%colorfontsize2@#$%-->",
                        "color:#FFFFFF ;");
            } else {
                contentNew = contentNew.replace("<--@#$%colorfontsize2@#$%-->",
                        "color:#FFFFFF ;");
            }
            if (flag) {
                contentNew = contentNew.replace(
                        "<--@#$%colorbackground@#$%-->", "background:#0EFFFFFF");
            } else {
                contentNew = contentNew.replace(
                        "<--@#$%colorbackground@#$%-->", "background:#0EFFFFFF");
            }
            return contentNew;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 获取手机的SD卡存储根路径
     *
     * @return
     */
    public static String getSDPath() {
        String pathString;
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取根目录
            pathString = sdDir.toString();


            return pathString;
        } else {
            return "/mnt/sdcard";
        }

    }

    public static boolean installationAPK(Context context, String apkURLString) {
        try {
            Uri uri = Uri.fromFile(new File(apkURLString)); //这里是APK路径
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);

            return true;
        } catch (ActivityNotFoundException activityNotFoundException) {
            Log.e("installationAPK:",
                    "ActivityNotFoundException(未知的Actiivty未找到错误，可能与手机系统有关)");

            return false;
        } catch (Exception e) {

            Log.e("installationAPK:",
                    "安装失败");
            return false;
        }


    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
