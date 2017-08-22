package lt.valaitis.lib.facebook.components;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public interface AppComponent {
    Serializer getSerializer();

    GraphApiInteractor getGraphApiInteractor();

    Launcher getLauncher();

    TargetUi getTargetUi();

    RxSchedulers getRxSchedulers();

    ImageLoader getImageLoader();

    LoginInteractor getLoginInteractor();

    Bus getBus();
}
