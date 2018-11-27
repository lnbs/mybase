package com.example.administrator.mybasetest1.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.administrator.mybasetest1.widget.CircleTransform;

/**
 * Created by Administrator on 2018/11/17.
 */

public class ImageLoader {
    public static void load(Context context, String url,ImageView imageView){
        RequestOptions requestOptions=RequestOptions
                .skipMemoryCacheOf(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void load(Context context, int resId, ImageView iv) {
        Glide.with(context)
                .load(resId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv);
    }

    public static void load(Context context, String url, ImageView iv, int placeholder) {
        RequestOptions requestOptions=RequestOptions
                .skipMemoryCacheOf(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder);
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(iv);
    }

    /**
     * 需要在子线程执行
     *
     * @param context
     * @param url
     * @return
     */
    public static Drawable load(Context context, String url) {
        try {
            RequestOptions requestOptions=RequestOptions
                    .skipMemoryCacheOf(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            return Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadCircle(Context context, int resId, ImageView iv) {
        RequestOptions requestOptions=RequestOptions
                .skipMemoryCacheOf(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CircleTransform("RoundImage"));
        Glide.with(context)
                .load(resId)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(iv);
    }
}
