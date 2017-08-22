package lt.valaitis.lib.facebook.ui.view;

import java.util.List;

import lt.valaitis.lib.facebook.graph.FbAlbum;
import rx.Observable;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface FacebookAlbumPickerActivityView {

    Observable<Object> nextPageObservable();

    void addAlbums(List<FbAlbum> albums);

    void finish();
}
