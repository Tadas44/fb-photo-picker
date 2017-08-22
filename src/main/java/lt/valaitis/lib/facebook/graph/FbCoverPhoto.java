package lt.valaitis.lib.facebook.graph;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tadas Valaitis
 * @since 2017-03-03
 */
public class FbCoverPhoto implements Serializable {
    String id;
    List<FbImage> images;

    public String getId() {
        return id;
    }

    public List<FbImage> getImages() {
        return images;
    }
}
