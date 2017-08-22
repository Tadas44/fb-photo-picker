package lt.valaitis.lib.facebook.components;

import lt.valaitis.lib.facebook.graph.FbPhoto;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * @author Tadas Valaitis
 * @since 2017-03-03
 */
class RxBus implements Bus {
    private static final PublishSubject<FbPhoto> photoBus = PublishSubject.create();

    @Override
    public Observable<FbPhoto> subscribe() {
        return photoBus;
    }

    @Override
    public void post(FbPhoto fbPhoto) {
        photoBus.onNext(fbPhoto);
    }
}
