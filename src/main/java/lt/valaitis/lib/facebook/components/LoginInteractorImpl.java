package lt.valaitis.lib.facebook.components;

import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Collection;

import lt.valaitis.lib.facebook.RxActivityResults;
import lt.valaitis.lib.facebook.exception.FacebookPermissionRequiredError;
import lt.valaitis.lib.facebook.exception.FbLoginCanceledError;
import lt.valaitis.lib.facebook.exception.FbLoginRequiredError;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.MainThreadSubscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * @author Tadas Valaitis
 * @since 2017-03-02
 */
class LoginInteractorImpl implements LoginInteractor {
    String TAG = getClass().getSimpleName();
    private final CallbackManager callbackManager = CallbackManager.Factory.create();
    private final RxFacebookCallback rxCallback = new RxFacebookCallback();
    private final TargetUi targetUi;
    private Subscription subscription;

    LoginInteractorImpl(TargetUi targetUi) {
        this.targetUi = targetUi;
        LoginManager.getInstance().registerCallback(callbackManager, rxCallback);
    }

    @Override
    public Single<Boolean> isAccessAvailable() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && accessToken.getUserId() != null && !accessToken.isExpired()) {
            return Single.just(true);
        }
        return Single.error(new FbLoginRequiredError());
    }

    @Override
    public Single<Boolean> isPermissionGranted(final String permission) {
        return isAccessAvailable().flatMap(new Func1<Boolean, Single<? extends Boolean>>() {
            @Override
            public Single<? extends Boolean> call(Boolean aBoolean) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken.getPermissions().contains(permission)) {
                    return Single.just(true);
                }
                return Single.error(new FacebookPermissionRequiredError());
            }
        });
    }

    @Override
    public Single<Boolean> isPermissionGrantedQuiet(final String permission) {
        return isPermissionGranted(permission).onErrorResumeNext(Single.just(false));
    }

    @Override
    public Single<LoginResult> login(final Collection<String> permissions) {
        return createLoginSingle(permissions)
                .flatMap(new Func1<Void, Single<LoginResult>>() {
                    @Override
                    public Single<LoginResult> call(Void o) {
                        return rxCallback.getObservable().take(1).toSingle();
                    }
                });
    }

    @NonNull
    private Single<Void> createLoginSingle(final Collection<String> permissions) {
        return Single.create(new Single.OnSubscribe<Void>() {
            @Override
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                subscribeForActivityResult();
                if (targetUi.getActivity() != null) {
                    LoginManager.getInstance().logInWithReadPermissions(targetUi.getActivity(), permissions);
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(targetUi.getFragment(), permissions);
                }

                singleSubscriber.add(new MainThreadSubscription() {
                    @Override
                    protected void onUnsubscribe() {
                        unsubscribeForActivityResult();
                    }
                });
                singleSubscriber.onSuccess(null);
            }
        });
    }

    private void unsubscribeForActivityResult() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    private void subscribeForActivityResult() {
        subscription = RxActivityResults.getInstance()
                .getResults()
                .subscribe(new Action1<ActivityResult>() {
                    @Override
                    public void call(ActivityResult activityResult) {
                        Log.i(TAG, "ActivityResult received");
                        callbackManager.onActivityResult(activityResult.getRequestCode(), activityResult.getResultCode(), activityResult.getData());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "Error receiving activity for results", throwable);
                    }
                });
    }

    private class RxFacebookCallback implements FacebookCallback<LoginResult> {
        private final PublishSubject<LoginResult> subject = PublishSubject.create();

        @Override
        public void onSuccess(LoginResult loginResult) {
            subject.onNext(loginResult);
        }

        @Override
        public void onCancel() {
            subject.onError(new FbLoginCanceledError());
        }

        @Override
        public void onError(FacebookException error) {
            subject.onError(error);
        }

        public Observable<LoginResult> getObservable() {
            return subject;
        }
    }
}
