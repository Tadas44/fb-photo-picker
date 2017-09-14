package lt.valaitis.lib.facebook;

import com.facebook.AccessToken;
import com.facebook.login.LoginResult;

import java.util.Arrays;
import java.util.List;

import lt.valaitis.lib.facebook.components.AppComponent;
import lt.valaitis.lib.facebook.components.AppComponentImpl;
import lt.valaitis.lib.facebook.components.LoginInteractor;
import lt.valaitis.lib.facebook.components.Permissions;
import lt.valaitis.lib.facebook.components.TargetUi;
import rx.Single;

/**
 * @author Tadas Valaitis
 * @since 2017-03-02
 */
class Login {

    private final LoginInteractor loginInteractor;
    private final Builder builder;

    Login(AppComponent appComponent, Builder builder) {
        this.loginInteractor = appComponent.getLoginInteractor();
        this.builder = builder;
    }

    Single<LoginResult> login() {
        return loginInteractor.login(builder.permissions);
    }

    Single<Boolean> logout() {
        return loginInteractor.logout();
    }

    Single<AccessToken> refreshAccessToken() {
        return loginInteractor.refreshAccessToken();
    }

    public static class Builder {
        private List<String> permissions = Arrays.asList(Permissions.PUBLIC_PROFILE);
        private final TargetUi targetUi;

        public Builder(TargetUi targetUi) {
            this.targetUi = targetUi;
        }

        public Builder setPermissions(String... permissions) {
            this.permissions = Arrays.asList(permissions);
            return this;
        }

        public Single<LoginResult> login() {
            return new Login(new AppComponentImpl(targetUi), this).login();
        }

        public Single<Boolean> logout() {
            return new Login(new AppComponentImpl(targetUi), this).logout();
        }

        public Single<AccessToken> refreshAccessToken() {
            return new Login(new AppComponentImpl(targetUi), this).refreshAccessToken();
        }

    }
}
