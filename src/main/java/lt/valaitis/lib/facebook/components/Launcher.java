package lt.valaitis.lib.facebook.components;

import lt.valaitis.lib.facebook.graph.FbPhoto;
import rx.Single;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface Launcher {
    Single<FbPhoto> launchPhotoPicker(String albumId);

    Single<FbPhoto> launchPhotoPicker();

    Single<FbPhoto> launchAlbumPicker();
}
