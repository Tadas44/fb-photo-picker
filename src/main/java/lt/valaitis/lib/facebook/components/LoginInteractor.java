package lt.valaitis.lib.facebook.components;

import com.facebook.login.LoginResult;

import java.util.Collection;

import rx.Single;

/**
 * @author Tadas Valaitis
 * @since 2017-03-02
 */
public interface LoginInteractor {
    Single<Boolean> isAccessAvailable();

    Single<Boolean> isPermissionGranted(String permission);

    Single<Boolean> isPermissionGrantedQuiet(String permission);

    Single<LoginResult> login(Collection<String> permissions);

}
