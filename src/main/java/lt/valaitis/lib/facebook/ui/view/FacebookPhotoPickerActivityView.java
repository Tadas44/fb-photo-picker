package lt.valaitis.lib.facebook.ui.view;

import java.util.List;

import lt.valaitis.lib.facebook.graph.FbPhoto;
import rx.Observable;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface FacebookPhotoPickerActivityView {

    Observable<Object> nextPageObservable();

    void addPhotos(List<FbPhoto> photos);

    void finish();
}
