package lt.valaitis.lib.facebook.components;

import lt.valaitis.lib.facebook.graph.FbPhoto;
import rx.Observable;

/**
 * @author Tadas Valaitis
 * @since 2017-03-03
 */
public interface Bus {
    Observable<FbPhoto> subscribe();

    void post(FbPhoto fbPhoto);
}
