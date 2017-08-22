package lt.valaitis.lib.facebook.graph;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FbAlbum implements Serializable {

    public static final String EMPTY_ALBUM_COVER_PHOTO_URL = "https://www.facebook.com/images/photos/empty-album.png";

    private String createdTime;
    private String name;
    private String id;
    @Nullable
    private FbCoverPhoto coverPhoto;
    private int photoCount;

    public String getCreatedTime() {
        return createdTime;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public FbCoverPhoto getCoverPhoto() {
        return coverPhoto;
    }

    public int getPhotoCount() {
        return photoCount;
    }
}
