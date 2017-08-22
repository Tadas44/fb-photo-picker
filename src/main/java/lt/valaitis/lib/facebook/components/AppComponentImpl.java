package lt.valaitis.lib.facebook.components;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class AppComponentImpl implements AppComponent {
    private final Serializer serializer;
    private final GraphApiInteractor graphApiInteractor;
    private final Launcher launcher;
    private final TargetUi targetUi;
    private final RxSchedulers rxSchedulers;
    private final ImageLoader imageLoader;
    private final LoginInteractor loginInteractor;
    private final Bus bus;

    public AppComponentImpl(TargetUi targetUi) {
        this.bus = new RxBus();
        this.serializer = new GsonSerializer(GsonProvider.getGson());
        this.loginInteractor = new LoginInteractorImpl(targetUi);
        this.graphApiInteractor = new GraphApiInteractorImpl(serializer, loginInteractor);
        this.launcher = new LauncherImpl(targetUi,bus);
        this.targetUi = targetUi;
        this.rxSchedulers = new RxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread());
        this.imageLoader = new ImageLoaderImpl();
    }

    @Override
    public Serializer getSerializer() {
        return serializer;
    }

    @Override
    public GraphApiInteractor getGraphApiInteractor() {
        return graphApiInteractor;
    }

    public Launcher getLauncher() {
        return launcher;
    }

    @Override
    public TargetUi getTargetUi() {
        return targetUi;
    }

    @Override
    public RxSchedulers getRxSchedulers() {
        return rxSchedulers;
    }


    @Override
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public LoginInteractor getLoginInteractor() {
        return loginInteractor;
    }

    @Override
    public Bus getBus() {
        return bus;
    }
}
