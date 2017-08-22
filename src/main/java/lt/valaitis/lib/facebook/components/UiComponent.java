package lt.valaitis.lib.facebook.components;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface UiComponent {
    Serializer getSerializer();

    GraphApiInteractor getGraphApiInteractor();

    RxSchedulers getRxSchedulers();

    Bus getBus();

    ImageLoader getImageLoader();

    LoginInteractor getLoginInteractor();

    Launcher getLauncher();
}
