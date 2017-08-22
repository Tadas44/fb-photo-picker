package lt.valaitis.lib.facebook.components;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class TargetUi {
    private final Activity activity;
    private final Fragment fragment;

    public TargetUi(Activity activity) {
        this.activity = activity;
        this.fragment = null;
        if (activity == null)
            throw new IllegalStateException("Activity cannot be null");
    }

    public TargetUi(Fragment fragment) {
        this.fragment = fragment;
        this.activity = null;
        if (fragment == null)
            throw new IllegalStateException("Fragment cannot be null");
    }

    public Activity getActivity() {
        return activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public Context getContext() {
        if (activity != null) {
            return activity;
        } else if (fragment != null) {
            return fragment.getActivity();
        } else {
            throw new IllegalStateException("Fragment or Activity must not be null");
        }
    }
}
