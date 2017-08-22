package lt.valaitis.lib.facebook.components;

import android.os.Bundle;

import lt.valaitis.lib.facebook.graph.FbPhoto;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class BundleHolder {

    private static final String FB_PHOTO = "fb_photo";
    private static final String ERROR = "error";
    private final Bundle bundle;
    private static final String ALBUM_ID = "album_id";

    public BundleHolder(Bundle bundle) {
        this.bundle = bundle;
    }

    public BundleHolder() {
        this.bundle = new Bundle();
    }

    public FbPhoto getFbPhoto() {
        if (bundle.containsKey(FB_PHOTO)) {
            return (FbPhoto) bundle.getSerializable(FB_PHOTO);
        }

        return null;
    }

    public void putFbPhoto(FbPhoto fbPhoto) {
        bundle.putSerializable(FB_PHOTO, fbPhoto);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void putError(Throwable e) {
        bundle.putSerializable(ERROR, e);
    }

    public Throwable getError() {
        if (bundle.containsKey(ERROR)) {
            return (Throwable) bundle.getSerializable(ERROR);
        }
        return null;
    }

    public void setAlbumId(String albumId) {
        bundle.putString(ALBUM_ID, albumId);
    }

    public String getAlbumId() {
        return bundle.getString(ALBUM_ID);
    }
}
