package lt.valaitis.lib.facebook.components;

import android.app.Activity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class UiComponentImpl implements UiComponent {
    private final Serializer serializer;
    private final GraphApiInteractor graphApiInteractor;
    private final RxSchedulers rxSchedulers;
    private final Bus bus;
    private final ImageLoader imageLoader;
    private final LoginInteractor loginInteractor;
    private final Launcher launcher;

    public UiComponentImpl(Activity activity) {
        this.serializer = new GsonSerializer(GsonProvider.getGson());
        this.loginInteractor = new LoginInteractorImpl(new TargetUi(activity));
        this.graphApiInteractor = new GraphApiInteractorImpl(serializer, loginInteractor);
        this.rxSchedulers = new RxSchedulers(Schedulers.io(), AndroidSchedulers.mainThread());
        this.bus = new RxBus();
        this.imageLoader = new ImageLoaderImpl();
        this.launcher = new LauncherImpl(new TargetUi(activity), bus);
    }

    @Override
    public Serializer getSerializer() {
        return serializer;
    }

    @Override
    public GraphApiInteractor getGraphApiInteractor() {
        return graphApiInteractor;
    }

    @Override
    public RxSchedulers getRxSchedulers() {
        return rxSchedulers;
    }

    public Bus getBus() {
        return bus;
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
    public Launcher getLauncher() {
        return launcher;
    }
}
