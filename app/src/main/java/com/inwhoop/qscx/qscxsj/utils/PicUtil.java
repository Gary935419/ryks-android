package com.inwhoop.qscx.qscxsj.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.inwhoop.qscx.qscxsj.R;
import com.inwhoop.qscx.qscxsj.constants.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * 图片工具类
 *
 * @author duweibin
 * @version 创建时间:2013年9月6日 图片工具类
 */
public class PicUtil {

    public static DisplayImageOptions options = new DisplayImageOptions.Builder()
//			 .showImageOnLoading(R.drawable.default_ic)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisk(true).considerExifParams(true)
            // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角,弧度为多少
            // .displayer(new FadeInBitmapDisplayer(100))// 设置图片渐显的时间
            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 图片的缩放方式缩放类型mageScaleType:
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    /**
     * 保存图片
     *
     * @param bmp
     * @return 保存的文件的路径
     */
    public static String cacheToPhone(Bitmap bmp) {
        Bitmap bitmap = bmp;
        String path = Environment.getExternalStorageDirectory().toString()
                + "/zzjaforcoach";
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                return "";
            }
        }
        File target = new File(file, UUID.randomUUID() + ".png");

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(target);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);

            return target.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return "";

    }

    /**
     * 保存图片
     *
     * @param imageView
     * @param path      保存目录
     * @param picName   图片名字
     * @return 保存的文件对象;保存失败返回null
     */
    public static File savePic(ImageView imageView, String path, String picName) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView
                .getDrawable();
        if (bitmapDrawable == null) {
            return null;
        }
        return saveBitmap(bitmapDrawable.getBitmap(), path, picName);
    }

    public static File saveBitmap(Bitmap bitmap, String path, String picName) {
        File dir = new File(path);
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, picName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }

    /**
     * 使用默认方法展示图片
     *
     * @param uri       图片的uri.可接收的uri如下: String imageUri =
     *                  "http://site.com/image.png"; // 从网络加载 String imageUri =
     *                  "/mnt/sdcard/image.png"; // 从本地sd卡加载 String imageUri =
     *                  "content://media/external/audio/albumart/13"; // 从content
     *                  provider中加载 String imageUri = "assets://image.png"; //
     *                  从assets中加载
     * @param imageView
     */
    public static void displayImage(String uri, ImageView imageView) {
        displayImage(uri, imageView, new SimpleImageLoadingListener());
    }

    public static void displayImage(String uri, ImageView image,
                                    DisplayImageOptions options) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(uri, image, options);
    }

    /**
     * 带回调方法的图片展示方法
     *
     * @param uri
     * @param imageView
     * @param imageLoadingListener 回调接口
     */
    public static void displayImage(String uri, ImageView imageView,
                                    ImageLoadingListener imageLoadingListener) {
        // displayImage(uri, imageView, imageLoadingListener, false);
        displayImage(uri, imageView, imageLoadingListener, true);
    }

    /**
     * 带回调方法的图片展示方法
     *
     * @param uri
     * @param imageView
     * @param imageLoadingListener 回调接口
     * @param isFadeIn             展示图片时是否使用动画，默认不使用
     */
    public static void displayImage(String uri, ImageView imageView,
                                    ImageLoadingListener imageLoadingListener, boolean isFadeIn) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!TextUtils.isEmpty(uri)
                && (uri.startsWith("/mnt/sdcard") || uri.startsWith("/storage"))) {
            uri = "file://".concat(uri);
        }
        imageLoader.displayImage(uri, imageView, options, imageLoadingListener);
    }

    public static void displayImage(String uri, ImageView imageView,
                                    boolean isFadeIn) {
        displayImage(uri, imageView, new SimpleImageLoadingListener() {
        }, isFadeIn);
    }

    /**
     * 根据图片的uri得到图片在sd卡中的路径。
     *
     * @param uri 图片的uri
     * @return 以文件对象返回
     */
    public static File getPicFile(String uri) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        return imageLoader.getDiskCache().get(uri);
    }

    /**
     * 解析文件对象得到位图
     *
     * @param file 文件对象
     * @return 位图
     */
    public static Bitmap parseBitmap(File file) {
        Bitmap d;
        try {
            d = BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Throwable e) {
            System.err.println("PicUtil   内存溢出");
            try {
                d = decodeScaledBitmap(file.getAbsolutePath(), 4);
            } catch (Throwable e1) {
                try {
                    d = decodeScaledBitmap(file.getAbsolutePath(), 8);
                } catch (Throwable e2) {
                    System.err.println("PicUtil 内存溢出2");
                    d = null;
                }
            }
        }
        return d;
    }

    /**
     * 得到图片的位图
     *
     * @param filename 文件名
     * @param scale    缩小值.2表示长和宽各缩小1/2
     * @return Bitmap
     */
    public static Bitmap decodeScaledBitmap(String filename, int scale) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
    }

    public static File zipImage(String srcPath, int kbSize) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        if (bitmap == null) {
            srcPath = srcPath.substring(7);
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > kbSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            bitmap.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 3;// 每次都减少3
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
        return savePic(bis, Constants.FILE_CACHE, System.currentTimeMillis()
                + ".jpg");// 把压缩后的数据baos存放到ByteArrayInputStream中
    }

    /**
     * 压缩算法，按480*800压缩，图片大小不大于200Kb.压缩后文件会保存到缓存目录
     *
     * @param srcPath
     * @return 压缩后的文件路径,
     */
    public static File zipImage(String srcPath) {
        return zipImage(srcPath, 20000);
    }

    /**
     * 保存图片
     *
     * @param inputStream 输入流
     * @param path        保存目录
     * @param picName     图片名字
     * @return 保存的文件对象;保存失败返回null
     */
    public static File savePic(InputStream inputStream, String path,
                               String picName) {
        if (inputStream == null) {
            return null;
        }
        File dir = new File(path);
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, picName);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            byte[] bs = new byte[1024 * 4];
            int len = -1;
            while ((len = inputStream.read(bs)) != -1) {
                outputStream.write(bs, 0, len);
            }
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                inputStream = null;
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 得到图片的Sring类型的路径
     *
     * @param uri
     * @return
     */
    public static String doGetAvatarURL(Context context, Uri uri) {

        if (uri.toString().contains("file:///")) {
            return uri.toString();
        }

        String avatarURL = "";
        Bitmap avatarBitmap = null;
        try {
            String[] pojo = {MediaStore.Images.Media.DATA};
            // Cursor cursor = ((Activity) context).managedQuery(uri, pojo,
            // null, null, null);
            Cursor cursor = ((Activity) context).getContentResolver().query(
                    uri, pojo, null, null, null);
            if (cursor != null) {
                ContentResolver cr = context.getContentResolver();
                int colunm_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(colunm_index);

                cursor.close();
                cursor = null;

                /**
                 *
                 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
                 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                 * */
                if (path.endsWith(".jpg") || path.endsWith(".png")
                        || path.endsWith(".jpeg") || path.endsWith(".JPG")
                        || path.endsWith(".PNG") || path.endsWith(".JPEG")) {
                    avatarURL = path;

                    // avatarBitmap =
                    // BitmapFactory.decodeStream(cr.openInputStream(uri));

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 10; // width，hight设为原来的十分一
                    avatarBitmap = BitmapFactory.decodeStream(
                            cr.openInputStream(uri), null, options);

//					avatarBitmap=revitionImageSize(avatarURL);

                    if (avatarBitmap == null) {
                        avatarURL = "";
                        alert(context);
                    }

                } else {
                    avatarURL = "";
                    if (path.endsWith(".bmp") || path.endsWith(".gif")
                            || path.endsWith(".BMP") || path.endsWith(".GIF")) {
                        alertBmpOrGif(context);
                    } else {
                        alert(context);
                    }

                }
            } else {
                avatarURL = "";
                alert(context);
            }
        } catch (Exception e) {
        } finally {

            if (avatarBitmap != null) {
                avatarBitmap.recycle();
                avatarBitmap = null;
            }

        }

        return avatarURL;

    }


    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }


    private static void alert(Context context) {
        Dialog dialog = new AlertDialog.Builder(context).setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        dialog.show();
    }

    private static void alertBmpOrGif(Context context) {
        Dialog dialog = new AlertDialog.Builder(context).setTitle("提示")
                .setMessage("不支持.bmp和.gif格式图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        dialog.show();
    }

    /**
     * 得到图片的base64格式
     *
     * @param iconPath
     * @return
     */
    public static String getBase64(String iconPath) {

        String iconBase64 = "";

        File file;
        try {
            try {
                file = PicUtil.zipImage(iconPath);
            } catch (NullPointerException nullPointerException) {
                file = PicUtil.zipImage("file:///" + iconPath);
            }

            FileInputStream stream = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = stream.read(buff)) != -1) {
                baos.write(buff, 0, len);
            }
            iconBase64 = Base64.encodeToString(baos.toByteArray(),
                    Base64.DEFAULT);
            baos.flush();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return iconBase64;
    }

    /**
     * 得到图片的base64格式
     *
     * @param bitmap
     * @return
     */
    private static String getBase64(Bitmap bitmap) {

        return "";
    }

    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return 图片的uri
     */
    public static Uri createImagePathUri(Context context) {
        Uri imageFilePath = null;
        String status = Environment.getExternalStorageState();
        SimpleDateFormat timeFormatter = new SimpleDateFormat(
                "yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));
        // ContentValues是我们希望这条记录被创建时包含的数据信息
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
        values.put(MediaStore.Images.Media.DATE_TAKEN, time);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            imageFilePath = context.getContentResolver().insert(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
        }
        Log.i("", "生成的照片输出路径：" + imageFilePath.toString());
        return imageFilePath;
    }

    /**
     * TODO uri转真实路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String uriToPath(Context context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = ((Activity) context).managedQuery(uri, proj,
                null, null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        return actualimagecursor.getString(actual_image_column_index);
    }

    /**
     * TODO 真实路径转uri。。大概是本地
     *
     * @param picPath
     * @return
     */
    public static Uri pathToUri(Context context, String picPath) {
        Uri mUri = Uri.parse("content://media/external/images/media");
        Uri mImageUri = null;
        Cursor cursor = ((Activity) context).managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String data = cursor.getString(cursor
                    .getColumnIndex(MediaStore.MediaColumns.DATA));
            if (picPath.equals(data)) {
                int ringtoneID = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID);
                return mImageUri;
            }
            cursor.moveToNext();
        }
        return mImageUri;
    }

    /**
     * 递归删除文件及文件夹
     *
     * @param file
     */
    public static void deleteAll(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            // if ((!file.getName().equals("cache") )||(
            // !file.getName().equals("file")))
            // {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteAll(childFiles[i]);
            }
            file.delete();
            // } else
            // {
            //
            // }

        }
    }

}
