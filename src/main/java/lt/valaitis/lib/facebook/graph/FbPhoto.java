package lt.valaitis.lib.facebook.graph;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FbPhoto implements Serializable{
    String id;
    List<FbImage> images;
    String picture;
    String link;
    FbAlbum album;

    public String getId() {
        return id;
    }

    public List<FbImage> getImages() {
        return images;
    }

    public String getPicture() {
        return picture;
    }

    public String getLink() {
        return link;
    }

    public FbAlbum getAlbum() {
        return album;
    }
}
