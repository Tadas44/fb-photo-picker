package lt.valaitis.lib.facebook.components;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import lt.valaitis.lib.facebook.graph.FbImage;

/**
 * @author Tadas Valaitis
 * @since 2017-03-01
 */
class ImageLoaderImpl implements ImageLoader {

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .into(imageView);
    }

    @Override
    public void loadImage(List<FbImage> images, ImageView imageView, int width, int height) {
        loadImage(getUrl(images, width, height), imageView);
    }

    private String getUrl(List<FbImage> images, int width, int height) {
        FbImage fbImage = getLargestImage(images, width, height);
        return fbImage.getSource();
    }

    private FbImage getLargestImage(List<FbImage> images, int width, int height) {
        FbImage possibleImage = images.get(0);
        for (FbImage fbImage : images) {

            if (fbImage.getWidth() < width) {
                return possibleImage;
            }
            possibleImage = fbImage;
        }
        return possibleImage;
    }
}
