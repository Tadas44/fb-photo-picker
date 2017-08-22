package lt.valaitis.lib.facebook.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lt.valaitis.lib.facebook.R;
import lt.valaitis.lib.facebook.components.ImageLoader;
import lt.valaitis.lib.facebook.components.UiComponent;
import lt.valaitis.lib.facebook.graph.FbAlbum;
import lt.valaitis.lib.facebook.graph.FbCoverPhoto;
import lt.valaitis.lib.facebook.ui.presenter.FacebookAlbumPickerActivityPresenter;

import static lt.valaitis.lib.facebook.graph.FbAlbum.EMPTY_ALBUM_COVER_PHOTO_URL;

/**
 * @author Tadas Valaitis
 * @since 2017-03-01
 */
public class FbAlbumsAdapter extends RecyclerView.Adapter<FbAlbumsAdapter.FbAlbumVH> {
    final List<FbAlbum> albums = new ArrayList<>();
    private final ImageLoader imageLoader;
    private final View.OnClickListener clickListener;

    public FbAlbumsAdapter(UiComponent component, final FacebookAlbumPickerActivityPresenter presenter) {
        this.imageLoader = component.getImageLoader();
        this.clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String) v.getTag(R.id.photo_id);
                presenter.onAlbumSelected(id);
            }
        };
    }

    @Override
    public FbAlbumVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return FbAlbumVH.create(parent);
    }

    @Override
    public void onBindViewHolder(FbAlbumVH holder, int position) {
        FbAlbum album = albums.get(position);
        FbCoverPhoto coverPhoto = album.getCoverPhoto();
        if (coverPhoto != null) {
            imageLoader.loadImage(coverPhoto.getImages(), holder.photo, holder.size, holder.size);
        } else {
            imageLoader.loadImage(EMPTY_ALBUM_COVER_PHOTO_URL, holder.photo);
        }
        holder.itemView.setTag(R.id.photo_id, album.getId());
        holder.itemView.setOnClickListener(clickListener);
        holder.name.setText(album.getName());
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void addAlbums(List<FbAlbum> albums) {
        int prevSize = this.albums.size();
        this.albums.addAll(albums);
        notifyItemRangeInserted(prevSize, albums.size());
    }

    static class FbAlbumVH extends RecyclerView.ViewHolder {

        private final int size;
        private final ImageView photo;
        private final TextView name;

        public FbAlbumVH(View itemView, int size) {
            super(itemView);
            this.photo = (ImageView) itemView.findViewById(R.id.photo);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.size = size;
        }

        public static FbAlbumVH create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_album, parent, false);
            int height = view.getResources().getDisplayMetrics().widthPixels;
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            return new FbAlbumVH(view, height);
        }
    }
}
