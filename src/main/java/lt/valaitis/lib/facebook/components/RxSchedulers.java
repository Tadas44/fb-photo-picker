package lt.valaitis.lib.facebook.components;

import rx.Scheduler;

/**
 * @author Tadas Valaitis
 * @since 2017-03-01
 */
public class RxSchedulers {
    private final Scheduler ioScheduler;
    private final Scheduler uiScheduler;

    public RxSchedulers(Scheduler ioScheduler, Scheduler uiScheduler) {
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Scheduler getIoScheduler() {
        return ioScheduler;
    }

    public Scheduler getUiScheduler() {
        return uiScheduler;
    }
}
