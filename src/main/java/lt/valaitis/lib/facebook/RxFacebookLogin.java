package lt.valaitis.lib.facebook;

import android.app.Activity;
import android.support.v4.app.Fragment;

import lt.valaitis.lib.facebook.components.TargetUi;

/**
 * @author Tadas Valaitis
 * @since 2017-03-02
 */
public class RxFacebookLogin {

    public static Login.Builder using(Activity activity) {
        return using(new TargetUi(activity));
    }

    public static Login.Builder using(Fragment fragment) {
        return using(new TargetUi(fragment));
    }

    static Login.Builder using(TargetUi targetUi) {
        return new Login.Builder(targetUi);
    }

}
