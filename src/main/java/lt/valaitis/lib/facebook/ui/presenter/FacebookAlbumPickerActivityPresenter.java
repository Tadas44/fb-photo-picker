package lt.valaitis.lib.facebook.ui.presenter;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface FacebookAlbumPickerActivityPresenter {

    void onAttach();

    void onAlbumSelected(String id);

    void onDetach();
}
