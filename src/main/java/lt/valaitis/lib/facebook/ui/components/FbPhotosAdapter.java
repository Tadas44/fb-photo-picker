package lt.valaitis.lib.facebook.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import lt.valaitis.lib.facebook.R;
import lt.valaitis.lib.facebook.components.ImageLoader;
import lt.valaitis.lib.facebook.components.UiComponent;
import lt.valaitis.lib.facebook.graph.FbPhoto;
import lt.valaitis.lib.facebook.ui.presenter.FacebookPhotoPickerActivityPresenter;

/**
 * @author Tadas Valaitis
 * @since 2017-03-01
 */
public class FbPhotosAdapter extends RecyclerView.Adapter<FbPhotosAdapter.FbPhotosVH> {
    final List<FbPhoto> photos = new ArrayList<>();
    private final ImageLoader imageLoader;
    private final View.OnClickListener clickListener;

    public FbPhotosAdapter(UiComponent component, final FacebookPhotoPickerActivityPresenter presenter) {
        this.imageLoader = component.getImageLoader();
        this.clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = (String) v.getTag(R.id.photo_id);
                presenter.onPhotoSelected(id);
            }
        };
    }

    @Override
    public FbPhotosVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return FbPhotosVH.create(parent);
    }

    @Override
    public void onBindViewHolder(FbPhotosVH holder, int position) {
        FbPhoto photo = photos.get(position);
        imageLoader.loadImage(photo.getImages(), holder.photo, holder.size, holder.size);
        holder.itemView.setTag(R.id.photo_id, photo.getId());
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void addPhotos(List<FbPhoto> photos) {
        int prevSize = this.photos.size();
        this.photos.addAll(photos);
        notifyItemRangeInserted(prevSize, photos.size());
    }

    static class FbPhotosVH extends RecyclerView.ViewHolder {

        private final int size;
        private final ImageView photo;

        public FbPhotosVH(View itemView, int size) {
            super(itemView);
            this.photo = (ImageView) itemView.findViewById(R.id.photo);
            this.size = size;
        }

        public static FbPhotosVH create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo, parent, false);
            int size = view.getResources().getDisplayMetrics().widthPixels / 2;
            view.setLayoutParams(new ViewGroup.LayoutParams(size, size));
            return new FbPhotosVH(view, size);
        }
    }
}
