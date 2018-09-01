package com.hami.common.Util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

public class UtilImageLoader {
    public static void loadImage(Context context, ImageView imageView, String url, int errorImage) {
        if (context != null && imageView == null) return;
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso p, Uri u, Exception e) {
                e.printStackTrace();
            }
        });
        Picasso pic = builder.build();
        pic.load(url.trim())
                .error(errorImage)
                .placeholder(errorImage).into(imageView);
    }
    //-----------------------------------------------
    public static void loadImageWithCacheGlid(RequestManager request, String url, ImageView imageView, int errorImage) {
        request.load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(errorImage)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

}
