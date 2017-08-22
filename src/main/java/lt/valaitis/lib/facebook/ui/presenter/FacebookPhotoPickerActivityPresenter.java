package lt.valaitis.lib.facebook.ui.presenter;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface FacebookPhotoPickerActivityPresenter {

    void onAttach();

    void onPhotoSelected(String id);

    void onDetach();
}
