package com.inwhoop.qscx.qscxsj.myinterface;

import android.app.Activity;
import android.widget.ImageView;

import com.inwhoop.qscx.qscxsj.R;
import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

;

/**
 * Created by liyou on 2016-10-7.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Picasso.with(activity)//
                .load(new File(path))//
                .placeholder(R.mipmap.ic_launcher)//default_image
                .error(R.mipmap.ic_launcher)//default_image
                .resize(width, height)//
                .centerInside()//
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
                .into(imageView);


    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {

    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}
