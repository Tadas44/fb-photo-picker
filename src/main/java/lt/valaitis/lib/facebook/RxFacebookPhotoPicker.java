package lt.valaitis.lib.facebook;

import android.app.Activity;
import android.support.v4.app.Fragment;

import lt.valaitis.lib.facebook.components.TargetUi;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class RxFacebookPhotoPicker {

    public static PhotoPicker.Builder using(Activity activity) {
        return new PhotoPicker.Builder(new TargetUi(activity));
    }


    public static PhotoPicker.Builder using(Fragment fragment) {
        return new PhotoPicker.Builder(new TargetUi(fragment));
    }

}
