package lt.valaitis.lib.facebook.ui.presenter;


import android.util.Log;

import com.facebook.GraphRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lt.valaitis.lib.facebook.components.GraphApiInteractor;
import lt.valaitis.lib.facebook.components.Launcher;
import lt.valaitis.lib.facebook.components.RxSchedulers;
import lt.valaitis.lib.facebook.components.UiComponent;
import lt.valaitis.lib.facebook.graph.FbAlbum;
import lt.valaitis.lib.facebook.graph.FbAlbumsResponse;
import lt.valaitis.lib.facebook.ui.view.FacebookAlbumPickerActivityView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FacebookAlbumPickerActivityPresenterImpl implements FacebookAlbumPickerActivityPresenter {
    private final Launcher launcher;
    private String TAG = getClass().getSimpleName();
    private final FacebookAlbumPickerActivityView view;
    private final GraphApiInteractor graphApi;
    private final RxSchedulers schedulers;
    private final List<FbAlbum> albums = new ArrayList<>();
    private GraphRequest nextPage;

    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public FacebookAlbumPickerActivityPresenterImpl(FacebookAlbumPickerActivityView view, UiComponent component) {
        this.view = view;
        this.graphApi = component.getGraphApiInteractor();
        this.schedulers = component.getRxSchedulers();
        this.launcher = component.getLauncher();
    }

    @Override
    public void onAttach() {
        if (albums.size() == 0) {
            initList();
        }

        add(view.nextPageObservable()
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(schedulers.getIoScheduler())
                .flatMap(new Func1<Object, Observable<FbAlbumsResponse>>() {
                    @Override
                    public Observable<FbAlbumsResponse> call(Object o) {
                        if (nextPage == null) {
                            return Observable.empty();
                        }
                        return graphApi.getAlbums(nextPage).toObservable().onErrorResumeNext(Observable.<FbAlbumsResponse>empty());
                    }
                }).observeOn(schedulers.getUiScheduler())
                .subscribe(new FbAlbumSubscriber()));

    }

    private void initList() {
        add(graphApi.getAlbums(nextPage)
                .subscribeOn(schedulers.getIoScheduler())
                .observeOn(schedulers.getUiScheduler())
                .subscribe(new FbAlbumSubscriber()));
    }

    @Override
    public void onAlbumSelected(String id) {
        launcher.launchPhotoPicker(id);
        view.finish();
    }

    @Override
    public void onDetach() {
        subscriptions.clear();
    }

    private void add(Subscription subscriber) {
        subscriptions.add(subscriber);
    }


    private class FbAlbumSubscriber extends rx.Subscriber<FbAlbumsResponse> implements rx.Observer<FbAlbumsResponse> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "Error ", e);
        }

        @Override
        public void onNext(FbAlbumsResponse response) {
            albums.addAll(response.getData());
            nextPage = response.getNextPage();
            view.addAlbums(response.getData());
        }
    }
}
