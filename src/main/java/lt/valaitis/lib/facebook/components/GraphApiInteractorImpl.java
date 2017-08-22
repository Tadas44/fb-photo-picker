package lt.valaitis.lib.facebook.components;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import lt.valaitis.lib.facebook.graph.FbAlbumsResponse;
import lt.valaitis.lib.facebook.graph.FbDataResponse;
import lt.valaitis.lib.facebook.graph.FbPhotoResponse;
import lt.valaitis.lib.facebook.exception.FbLoginRecoverableError;
import lt.valaitis.lib.facebook.exception.FbOtherFacebookError;
import lt.valaitis.lib.facebook.exception.FbTransientError;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.MainThreadSubscription;
import rx.functions.Func1;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
class GraphApiInteractorImpl implements GraphApiInteractor {

    final String LOG_TAG = getClass().getSimpleName();

    private static final String GRAPH_PATH_PHOTOS = "/photos";
    private static final String GRAPH_PATH_MY_PHOTOS = "/me/photos";
    private static final String GRAPH_PATH_MY_ALBUMS = "/me/albums";

    private static final String PARAMETER_NAME_FIELDS = "fields";
    private static final String PARAMETER_PHOTOS_VALUE = "id,link,picture,images,album";
    private static final String PARAMETER_ALBUMS_VALUE = "cover_photo{images,id},created_time,photo_count,name";

    private static final String PARAMETER_NAME_LIMIT = "limit";
    private static final String PARAMETER_VALUE_LIMIT = "100";


    private final Serializer serializer;
    private final LoginInteractor loginInteractor;


    GraphApiInteractorImpl(Serializer serializer, LoginInteractor loginInteractor) {
        this.serializer = serializer;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public Single<FbPhotoResponse> getPhotos(@Nullable String albumId, @Nullable GraphRequest request) {
        if (request == null) {
            request = new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    getPhotosPath(albumId),
                    getPhotosParameters(),
                    HttpMethod.GET,
                    null);
        }
        return checkPermissionAndProcess(request, FbPhotoResponse.class);
    }

    private String getPhotosPath(@Nullable String albumId) {
        if (albumId != null) {
            return "/" + albumId + GRAPH_PATH_PHOTOS;
        }
        return GRAPH_PATH_MY_PHOTOS;
    }

    @Override
    public Single<FbAlbumsResponse> getAlbums(@Nullable GraphRequest request) {
        if (request == null) {
            request = new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    GRAPH_PATH_MY_ALBUMS,
                    getMyAlbumParameters(),
                    HttpMethod.GET,
                    null);
        }
        return checkPermissionAndProcess(request, FbAlbumsResponse.class);
    }


    private <T> Single<T> checkPermissionAndProcess(@NonNull final GraphRequest request, final Class<T> clazz) {
        return loginInteractor
                .isPermissionGranted(Permissions.USER_PHOTOS)
                .flatMap(new Func1<Boolean, Single<? extends T>>() {
                    @Override
                    public Single<? extends T> call(Boolean aBoolean) {
                        return processRequest(request, clazz);
                    }
                });
    }

    @NonNull
    private <T> Single<T> processRequest(@NonNull final GraphRequest request, final Class<T> clazz) {
        return Single.create(new Single.OnSubscribe<T>() {
            @Override
            public void call(final SingleSubscriber<? super T> subscriber) {
                GraphRequest.Callback photosGraphRequestCallback = new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(final GraphResponse response) {
                        if (subscriber.isUnsubscribed()) return;

                        if (response.getError() != null) {
                            processError(response, subscriber);
                        } else {
                            JSONObject json = response.getJSONObject();
                            if (json != null) {
                                Log.d(LOG_TAG, "Response object: " + json.toString());

                                T data = serializer.deserialize(json.toString(), clazz);
                                if (data instanceof FbDataResponse) {
                                    ((FbDataResponse) data).setNextPage(response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT));
                                }
                                subscriber.onSuccess(data);
                            } else {
                                subscriber.onError(new IllegalStateException("Json object is null"));
                            }
                        }
                    }
                };

                subscriber.add(new MainThreadSubscription() {
                    @Override
                    protected void onUnsubscribe() {
                        //TODO stop request
                    }
                });

                request.setCallback(photosGraphRequestCallback);
                request.executeAndWait();
            }
        });
    }

    private void processError(GraphResponse response, SingleSubscriber<?> subscriber) {
        Log.d(LOG_TAG, "Response error: " + response.getError());

        switch (response.getError().getCategory()) {
            case LOGIN_RECOVERABLE:
                subscriber.onError(new FbLoginRecoverableError());
            case OTHER:
                subscriber.onError(new FbOtherFacebookError(response.getError()));
            case TRANSIENT:
                subscriber.onError(new FbTransientError());
        }
    }

    private Bundle getPhotosParameters() {
        Bundle parameters = new Bundle();
        parameters.putString(PARAMETER_NAME_FIELDS, PARAMETER_PHOTOS_VALUE);
        parameters.putString(PARAMETER_NAME_LIMIT, PARAMETER_VALUE_LIMIT);
        return parameters;
    }

    private Bundle getMyAlbumParameters() {
        Bundle parameters = new Bundle();
        parameters.putString(PARAMETER_NAME_FIELDS, PARAMETER_ALBUMS_VALUE);
        parameters.putString(PARAMETER_NAME_LIMIT, PARAMETER_VALUE_LIMIT);
        return parameters;
    }
}
