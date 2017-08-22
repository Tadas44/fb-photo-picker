package lt.valaitis.lib.facebook.ui.presenter;


import android.util.Log;

import com.facebook.GraphRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lt.valaitis.lib.facebook.components.Bus;
import lt.valaitis.lib.facebook.components.GraphApiInteractor;
import lt.valaitis.lib.facebook.components.RxSchedulers;
import lt.valaitis.lib.facebook.components.UiComponent;
import lt.valaitis.lib.facebook.graph.FbPhoto;
import lt.valaitis.lib.facebook.graph.FbPhotoResponse;
import lt.valaitis.lib.facebook.ui.view.FacebookPhotoPickerActivityView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FacebookPhotoPickerActivityPresenterImpl implements FacebookPhotoPickerActivityPresenter {
    private final Bus bus;
    private final String albumId;
    private String TAG = getClass().getSimpleName();
    private final FacebookPhotoPickerActivityView view;
    private final GraphApiInteractor graphApi;
    private final RxSchedulers schedulers;
    private final List<FbPhoto> photos = new ArrayList<>();
    private GraphRequest nextPage;

    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public FacebookPhotoPickerActivityPresenterImpl(FacebookPhotoPickerActivityView view, UiComponent component, String albumId) {
        this.view = view;
        this.graphApi = component.getGraphApiInteractor();
        this.schedulers = component.getRxSchedulers();
        this.bus = component.getBus();
        this.albumId = albumId;
    }

    @Override
    public void onAttach() {
        if (photos.size() == 0) {
            initList();
        }

        add(view.nextPageObservable()
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(schedulers.getIoScheduler())
                .flatMap(new Func1<Object, Observable<FbPhotoResponse>>() {
                    @Override
                    public Observable<FbPhotoResponse> call(Object o) {
                        if (nextPage == null) {
                            return Observable.empty();
                        }

                        return graphApi.getPhotos(albumId, nextPage).toObservable().onErrorResumeNext(Observable.<FbPhotoResponse>empty());
                    }
                }).observeOn(schedulers.getUiScheduler())
                .subscribe(new FbPhotoSubscriber()));

    }

    private void initList() {
        add(graphApi.getPhotos(albumId, nextPage)
                .subscribeOn(schedulers.getIoScheduler())
                .observeOn(schedulers.getUiScheduler())
                .subscribe(new FbPhotoSubscriber()));
    }

    @Override
    public void onPhotoSelected(String id) {
        for (FbPhoto photo : photos) {
            if (photo.getId().equals(id)) {
                bus.post(photo);
                view.finish();
                return;
            }
        }
    }

    @Override
    public void onDetach() {
        subscriptions.clear();
    }

    private void add(Subscription subscriber) {
        subscriptions.add(subscriber);
    }


    private class FbPhotoSubscriber extends rx.Subscriber<FbPhotoResponse> implements rx.Observer<FbPhotoResponse> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error ", e);
        }

        @Override
        public void onNext(FbPhotoResponse response) {
            photos.addAll(response.getData());
            nextPage = response.getNextPage();
            view.addPhotos(response.getData());
        }
    }
}
