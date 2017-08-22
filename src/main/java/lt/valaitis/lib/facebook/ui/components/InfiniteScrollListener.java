package lt.valaitis.lib.facebook.ui.components;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rx.subjects.PublishSubject;

/**
 * @author Tadas Valaitis
 * @since 2017-03-01
 */
public class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    private static final int BUFFER = 25;
    private final LinearLayoutManager layoutManager;
    private final PublishSubject<Object> subject;
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisibleItems;

    public InfiniteScrollListener(LinearLayoutManager layoutManager, PublishSubject<Object> subject) {
        this.layoutManager = layoutManager;
        this.subject = subject;
    }

    public InfiniteScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        this.subject = PublishSubject.create();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

        if ((visibleItemCount + pastVisibleItems)+ BUFFER >= totalItemCount) {
            subject.onNext(new Object());
        }
    }

    public PublishSubject<Object> getSubject() {
        return subject;
    }
}
