package lt.valaitis.lib.facebook;

import android.support.annotation.NonNull;

import lt.valaitis.lib.facebook.components.AppComponent;
import lt.valaitis.lib.facebook.components.AppComponentImpl;
import lt.valaitis.lib.facebook.components.Launcher;
import lt.valaitis.lib.facebook.components.LoginInteractor;
import lt.valaitis.lib.facebook.components.Permissions;
import lt.valaitis.lib.facebook.components.TargetUi;
import lt.valaitis.lib.facebook.graph.FbPhoto;
import rx.Single;
import rx.functions.Func1;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
class PhotoPicker {

    private final Launcher launcher;
    private final Builder builder;
    private final LoginInteractor loginInteractor;

    PhotoPicker(AppComponent component, Builder builder) {
        this.launcher = component.getLauncher();
        this.loginInteractor = component.getLoginInteractor();
        this.builder = builder;
    }

    private Single<FbPhoto> pick() {
        return loginInteractor
                .isPermissionGranted(Permissions.USER_PHOTOS)
                .flatMap(launchPicker(builder.showAlbums));
    }

    private Single<FbPhoto> pickWithLogin() {
        return loginInteractor
                .isPermissionGrantedQuiet(Permissions.USER_PHOTOS)
                .flatMap(new Func1<Boolean, Single<?>>() {
                    @Override
                    public Single<?> call(Boolean granted) {
                        if (granted) {
                            return Single.just(true);
                        }
                        return RxFacebookLogin.using(builder.targetUi)
                                .setPermissions(Permissions.USER_PHOTOS)
                                .login();
                    }
                })
                .flatMap(launchPicker(builder.showAlbums));

    }

    @NonNull
    private <T> Func1<T, Single<? extends FbPhoto>> launchPicker(final boolean showAlbums) {
        return new Func1<T, Single<? extends FbPhoto>>() {
            @Override
            public Single<? extends FbPhoto> call(T result) {
                if (showAlbums) {
                    return launcher.launchAlbumPicker();
                }
                return launcher.launchPhotoPicker();
            }
        };
    }

    public static class Builder {
        private boolean autologin = false;
        private boolean showAlbums = false;

        private final TargetUi targetUi;

        public Builder(TargetUi targetUi) {
            this.targetUi = targetUi;
        }

        public Builder setAutologin(boolean autologin) {
            this.autologin = autologin;
            return this;
        }

        public Builder setShowAlbums(boolean showAlbums) {
            this.showAlbums = showAlbums;
            return this;
        }

        public Single<FbPhoto> pickPhoto() {
            PhotoPicker picker = new PhotoPicker(new AppComponentImpl(targetUi), this);
            if (autologin) {
                return picker.pickWithLogin();
            } else {
                return picker.pick();
            }
        }

    }
}
