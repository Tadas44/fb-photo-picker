package lt.valaitis.lib.facebook.components;

import android.support.annotation.Nullable;

import com.facebook.GraphRequest;

import lt.valaitis.lib.facebook.graph.FbAlbumsResponse;
import lt.valaitis.lib.facebook.graph.FbPhotoResponse;
import rx.Single;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface GraphApiInteractor {

    Single<FbPhotoResponse> getPhotos(@Nullable String albumId,@Nullable GraphRequest request);

    Single<FbAlbumsResponse> getAlbums(@Nullable GraphRequest request);
}
