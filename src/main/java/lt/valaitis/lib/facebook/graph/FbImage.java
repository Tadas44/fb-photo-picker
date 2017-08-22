package lt.valaitis.lib.facebook.graph;

import java.io.Serializable;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FbImage implements Serializable{
    private int height;
    private int width;
    private String source;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getSource() {
        return source;
    }
}
