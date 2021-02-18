package com.inwhoop.qscx.qscxsj.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.inwhoop.qscx.qscxsj.constants.Constants;

import java.io.File;

/**
 * 打开相册、系统相机的方法（兼容Android7.0）
 */
public class FileUtils {

    // 项目文件根目录
    public static final String FILE_DIR = "/RuYouDriver";
    //文件夹名称
    public static final String FILE_IMAGE = "/images";     //图片存放文件夹
    public static final String FILE_AUTHORIZATION = "/authorization";     //图片存放文件夹
    public static final String FILE_DOWNLOAD = "/download";     //下载文件夹

    public static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + FILE_DIR + FILE_IMAGE;
    public static final String AUTHORIZATION_PATH = Environment.getExternalStorageDirectory() + FILE_DIR + FILE_AUTHORIZATION;
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + FILE_DIR + FILE_DOWNLOAD;

    //文件名称
    public static final String FILENAME_GOODS = "file_goods-";     //头像文件名
    public static final String FILENAME_FRONT = "file_front.jpg";     //身份证正面
    public static final String FILENAME_BEHIND = "file_behind.jpg";     //身份证反面

    public static final int PICK_FROM_CAMERA = 8889;   //从相机获取图片
    public static final int PICK_FROM_ALBUM = 8888;   //从相册获取图片

    /**
     * 检查sd卡是否可用
     * getExternalStorageState 获取状态
     * Environment.MEDIA_MOUNTED 直译  环境媒体登上  表示，当前sd可用
     */
    public static boolean checkSdCard() {
        //sd卡可用    //当前sd卡不可用
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sd卡的文件路径
     * getExternalStorageDirectory 获取路径
     */
    public static String getSdPath() {
        return Environment.getExternalStorageDirectory() + "/";
    }

    /**
     * 创建一个文件夹
     */
    public static void createFileDir(String fileDir) {
        String path = getSdPath() + fileDir;
        File path1 = new File(path);
        if (!path1.exists()) {
            path1.mkdirs();
        }
    }

    /**
     * 创建APP相关的文件夹
     *
     * @return 是否创建成功
     */
    public static boolean createAppFiles() {
        if (FileUtils.checkSdCard()) {
            FileUtils.createFileDir(FileUtils.FILE_DIR);
            FileUtils.createFileDir(FileUtils.FILE_DIR + FileUtils.FILE_IMAGE);
            FileUtils.createFileDir(FileUtils.FILE_DIR + FileUtils.FILE_AUTHORIZATION);
            FileUtils.createFileDir(FileUtils.FILE_DIR + FileUtils.FILE_DOWNLOAD);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 打开文件
     * 兼容7.0
     *
     * @param context     activity
     * @param file        File
     * @param contentType 文件类型如：文本（text/html）
     *                    当手机中没有一个app可以打开file时会抛ActivityNotFoundException
     */
    public static void startFile(Context context, File file, String contentType) throws ActivityNotFoundException {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getUriFromFile(context, file), contentType);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 打开文件
     * 兼容7.0
     *
     * @param fragment    activity
     * @param file        File
     * @param contentType 文件类型如：文本（text/html）
     *                    当手机中没有一个app可以打开file时会抛ActivityNotFoundException
     */
    public static void startFile(Fragment fragment, File file, String contentType) throws ActivityNotFoundException {
        if (fragment == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//增加读写权限
        intent.setDataAndType(getUriFromFile(fragment.getActivity(), file), contentType);
        fragment.startActivity(intent);
    }

    /**
     * 打开相册 activity
     *
     * @param fragment Activity
     */
    public static void startAlbum(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        fragment.startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    /**
     * 打开相册 activity
     *
     * @param activity Activity
     */
    public static void startAlbum(Activity activity) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    /**
     * 打开相机 fragment
     * 兼容7.0
     *
     * @param fragment Fragment
     * @param file     File
     */
    public static void startCamera(Fragment fragment, File file) {
        if (fragment == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriFromFile(fragment.getActivity(), file));
        fragment.startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 打开相机 activity
     * 兼容7.0
     *
     * @param activity Activity
     * @param file     File
     */
    public static void startCamera(Activity activity, File file) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriFromFile(activity, file));
        activity.startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    /**
     * 获取文件的uri路径
     *
     * @param context 上下文
     * @param file    文件
     * @return 文件的uri
     */
    public static Uri getUriFromFile(Context context, File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, Constants.FILE_PROVIDER, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件的路径path
     */
    public static void deleteFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            return;
        }
        dirFile.delete();
    }

    /**
     * File转成Uri
     *
     * @param context   上下文
     * @param imageFile 文件的file格式
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        @SuppressLint("Recycle") Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * Path转成Uri
     *
     * @param path 图片path路径
     * @return
     */
    public static Uri getUriFromPath(String path) {
        return Uri.parse(path);
    }

    /**
     * Path转成File
     *
     * @param path 图片path路径
     * @return
     */
    public static File getFileFromPath(String path) {
        return new File(path);
    }

    /**
     * Uri转成Path
     *
     * @param context 上下文
     * @param uri     文件Uri
     * @return
     */
    public static String getPathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String path = null;
        if (scheme == null)
            path = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        path = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return path;
    }

    public static boolean clearDirectory(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                clearDirectory(f);
            }
        } else if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
