package lt.valaitis.lib.facebook.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import lt.valaitis.lib.facebook.R;
import lt.valaitis.lib.facebook.components.BundleHolder;
import lt.valaitis.lib.facebook.components.UiComponentImpl;
import lt.valaitis.lib.facebook.graph.FbPhoto;
import lt.valaitis.lib.facebook.ui.components.FbPhotosAdapter;
import lt.valaitis.lib.facebook.ui.components.InfiniteScrollListener;
import lt.valaitis.lib.facebook.ui.presenter.FacebookPhotoPickerActivityPresenter;
import lt.valaitis.lib.facebook.ui.presenter.FacebookPhotoPickerActivityPresenterImpl;
import lt.valaitis.lib.facebook.ui.view.FacebookPhotoPickerActivityView;
import rx.Observable;

/**
 * @author Tadas Valaitis
 * @since 2017-02-28
 */
public class FacebookPhotoPickerActivity extends AppCompatActivity implements FacebookPhotoPickerActivityView {

    private RecyclerView recycler;
    private Toolbar toolbar;
    private FacebookPhotoPickerActivityPresenter presenter;
    private FbPhotosAdapter fbPhotosAdapter;
    private InfiniteScrollListener infiniteScrollListener;

    public static Intent createIntent(Context context, @Nullable String albumId) {
        Intent intent = new Intent(context, FacebookPhotoPickerActivity.class);

        BundleHolder bundleHolder = new BundleHolder();
        bundleHolder.setAlbumId(albumId);

        intent.putExtras(bundleHolder.getBundle());

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_photo_picker);

        BundleHolder bundleHolder = new BundleHolder(getIntent().getExtras());

        UiComponentImpl component = new UiComponentImpl(this);
        presenter = new FacebookPhotoPickerActivityPresenterImpl(this, component, bundleHolder.getAlbumId());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(getTitle());

        fbPhotosAdapter = new FbPhotosAdapter(component, presenter);
        recycler.setAdapter(fbPhotosAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(layoutManager);
        infiniteScrollListener = new InfiniteScrollListener(layoutManager);
        recycler.addOnScrollListener(infiniteScrollListener);

    }

    @Override
    public Observable<Object> nextPageObservable() {
        return infiniteScrollListener.getSubject();
    }

    @Override
    public void addPhotos(List<FbPhoto> photos) {
        fbPhotosAdapter.addPhotos(photos);
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
