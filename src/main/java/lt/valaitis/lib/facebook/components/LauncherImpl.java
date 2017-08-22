package lt.valaitis.lib.facebook.components;

import android.content.Intent;

import lt.valaitis.lib.facebook.graph.FbPhoto;
import lt.valaitis.lib.facebook.ui.activity.FacebookAlbumPickerActivity;
import lt.valaitis.lib.facebook.ui.activity.FacebookPhotoPickerActivity;
import rx.Single;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
class LauncherImpl implements Launcher {

    private static final int REQUEST_CODE_PHOTO = 6565;
    private static final int REQUEST_CODE_ALBUM = 6566;
    private final TargetUi target;
    private final Bus bus;

    LauncherImpl(TargetUi target, Bus bus) {
        this.target = target;
        this.bus = bus;
    }

    @Override
    public Single<FbPhoto> launchPhotoPicker(String albumId) {
        final Intent intent = FacebookPhotoPickerActivity.createIntent(target.getContext(), albumId);
        return launchIntent(intent);
    }

    @Override
    public Single<FbPhoto> launchPhotoPicker() {
        final Intent intent = FacebookPhotoPickerActivity.createIntent(target.getContext(), null);
        return launchIntent(intent);
    }

    @Override
    public Single<FbPhoto> launchAlbumPicker() {
        final Intent intent = FacebookAlbumPickerActivity.createIntent(target.getContext());
        return launchIntent(intent);
    }

    private Single<FbPhoto> launchIntent(Intent intent) {
        if (target.getActivity() != null) {
            target.getActivity().startActivityForResult(intent, REQUEST_CODE_PHOTO);
        } else {
            target.getFragment().startActivityForResult(intent, REQUEST_CODE_PHOTO);
        }
        return bus.subscribe().take(1).toSingle();
    }
}
