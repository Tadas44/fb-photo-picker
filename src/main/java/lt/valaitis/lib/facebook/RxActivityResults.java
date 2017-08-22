package lt.valaitis.lib.facebook;

import android.content.Intent;

import lt.valaitis.lib.facebook.components.ActivityResult;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * @author Tadas Valaitis
 * @since 2017-03-02
 */
public class RxActivityResults {
    private static RxActivityResults instance;
    private PublishSubject<ActivityResult> subject = PublishSubject.create();

    private RxActivityResults() {
    }

    public static RxActivityResults getInstance() {
        if (instance == null) {
            instance = new RxActivityResults();
        }
        return instance;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ActivityResult result = new ActivityResult(requestCode, resultCode, data);
        subject.onNext(result);
    }

    public Observable<ActivityResult> getResults() {
        return subject;
    }

    public Observable<ActivityResult> getResults(final int requestCode) {
        return subject.filter(new Func1<ActivityResult, Boolean>() {
            @Override
            public Boolean call(ActivityResult activityResult) {
                return activityResult.getRequestCode() == requestCode;
            }
        });
    }
}
