package com.inwhoop.qscx.qscxsj.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import com.amap.api.location.AMapLocation;

import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Locale;

public class Utils {


    /**
     * 设置价格的“¥”符号的文字大小
     *
     * @param str
     * @return
     */
    public static Spannable setRmbSmall(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "0";
        }
        if (!str.contains("¥")) {
            str = "¥" + str;
        }
        Spannable priceSpan = new SpannableString(str);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
        priceSpan.setSpan(sizeSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return priceSpan;
    }

    /**
     * 高德地图用 SHA1
     *
     * @param context
     * @return
     */
    public static String SHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuilder hexString = new StringBuilder();
            for (byte b : publicKey) {
                String appendString = Integer.toHexString(0xFF & b).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化一下 价格
     *
     * @param price 价格
     * @return 价格
     */
    public static Spannable getPriceWithUnit(String price) {
        String result = "";
        DecimalFormat df = new DecimalFormat("0");
        if (price == null || price.isEmpty()) {
            //价格为空字符串
            result = "¥0";
        } else {
            if (price.endsWith(".00") || price.endsWith(".0")) {
                //价格结尾为.00或.0
                result = "¥" + df.format(Double.parseDouble(price));
            } else {
                if (!price.contains(".")) {
                    //价格中不含有.表示整数
                    result = "¥" + Integer.parseInt(price);
                } else {
                    //价格中有小数点
                    if (price.endsWith("0")) {
                        DecimalFormat df2 = new DecimalFormat("0.0");
                        df2.setRoundingMode(RoundingMode.FLOOR);
                        result = "¥" + df2.format(Double.parseDouble(price));
                    } else {
                        DecimalFormat df2 = new DecimalFormat("0.00");
                        df2.setRoundingMode(RoundingMode.FLOOR);
                        result = "¥" + df2.format(Double.parseDouble(price));
                    }
                }
            }
        }
        return setRmbSmall(result);
    }

    /**
     * 获得价格
     *
     * @param price
     * @return
     */
    public static String getPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        return decimalFormat.format(price);
    }

    /**
     * 米----千米
     *
     * @param meter 米
     * @return 千米
     */
    public static String m2km(String meter) {
        try {
            int meterInt = Integer.parseInt(meter);
            return Math.round(meterInt / 100d) / 10d + "";
        } catch (Exception exception) {
            return "0";
        }
    }

    /**
     * 获取定位信息
     *
     * @param location
     * @return
     */
    public static String getLocationInfo(AMapLocation location) {
        StringBuilder sb = new StringBuilder();
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: ").append(location.getLocationType()).append("\n");
            sb.append("经    度    : ").append(location.getLongitude()).append("\n");
            sb.append("纬    度    : ").append(location.getLatitude()).append("\n");
            sb.append("精    度    : ").append(location.getAccuracy()).append("米").append("\n");
            sb.append("提供者    : ").append(location.getProvider()).append("\n");

            sb.append("速    度    : ").append(location.getSpeed()).append("米/秒").append("\n");
            sb.append("角    度    : ").append(location.getBearing()).append("\n");
            // 获取当前提供定位服务的卫星个数
            sb.append("星    数    : ").append(location.getSatellites()).append("\n");
            sb.append("国    家    : ").append(location.getCountry()).append("\n");
            sb.append("省            : ").append(location.getProvince()).append("\n");
            sb.append("市            : ").append(location.getCity()).append("\n");
            sb.append("城市编码 : ").append(location.getCityCode()).append("\n");
            sb.append("区            : ").append(location.getDistrict()).append("\n");
            sb.append("区域 码   : ").append(location.getAdCode()).append("\n");
            sb.append("地    址    : ").append(location.getAddress()).append("\n");
            sb.append("兴趣点    : ").append(location.getPoiName()).append("\n");
//            sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:").append(location.getErrorCode()).append("\n");
            sb.append("错误信息:").append(location.getErrorInfo()).append("\n");
            sb.append("错误描述:").append(location.getLocationDetail()).append("\n");
        }
        sb.append("***定位质量报告***").append("\n");
        sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
//       sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
        sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
        sb.append("* 网络类型：").append(location.getLocationQualityReport().getNetworkType()).append("\n");
        sb.append("* 网络耗时：").append(location.getLocationQualityReport().getNetUseTime()).append("\n");
        sb.append("****************").append("\n");
        return sb.toString();
    }
}
