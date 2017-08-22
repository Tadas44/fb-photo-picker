package lt.valaitis.lib.facebook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import lt.valaitis.lib.facebook.R;
import lt.valaitis.lib.facebook.components.UiComponentImpl;
import lt.valaitis.lib.facebook.graph.FbAlbum;
import lt.valaitis.lib.facebook.ui.components.FbAlbumsAdapter;
import lt.valaitis.lib.facebook.ui.components.InfiniteScrollListener;
import lt.valaitis.lib.facebook.ui.presenter.FacebookAlbumPickerActivityPresenter;
import lt.valaitis.lib.facebook.ui.presenter.FacebookAlbumPickerActivityPresenterImpl;
import lt.valaitis.lib.facebook.ui.view.FacebookAlbumPickerActivityView;
import rx.Observable;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FacebookAlbumPickerActivity extends AppCompatActivity implements FacebookAlbumPickerActivityView {

    private RecyclerView recycler;
    private Toolbar toolbar;
    private FacebookAlbumPickerActivityPresenter presenter;
    private FbAlbumsAdapter fbAlbums;
    private InfiniteScrollListener infiniteScrollListener;

    public static Intent createIntent(Context context) {
        return new Intent(context, FacebookAlbumPickerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_album_picker);

        UiComponentImpl component = new UiComponentImpl(this);
        presenter = new FacebookAlbumPickerActivityPresenterImpl(this, component);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(getTitle());

        fbAlbums = new FbAlbumsAdapter(component, presenter);
        recycler.setAdapter(fbAlbums);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(layoutManager);
        infiniteScrollListener = new InfiniteScrollListener(layoutManager);
        recycler.addOnScrollListener(infiniteScrollListener);

    }

    @Override
    public Observable<Object> nextPageObservable() {
        return infiniteScrollListener.getSubject();
    }

    @Override
    public void addAlbums(List<FbAlbum> albums) {
        fbAlbums.addAlbums(albums);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onAttach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onDetach();
    }

}
