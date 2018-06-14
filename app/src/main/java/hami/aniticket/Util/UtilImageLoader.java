package hami.aniticket.Util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

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
}
