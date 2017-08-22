package lt.valaitis.lib.facebook.components;

import android.widget.ImageView;

import java.util.List;

import lt.valaitis.lib.facebook.graph.FbImage;

/**
 * @author Tadas Valaitis
 * @since 2017-03-01
 */
public interface ImageLoader {
    void loadImage(String url, ImageView imageView);

    void loadImage(List<FbImage> images, ImageView imageView, int width, int height);
}
