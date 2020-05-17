package ru.ilyabaykalov.testproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;

import java.util.List;

public class GalleryViewAdapter extends RecyclerView.Adapter<GalleryViewAdapter.ViewHolder> {

    List<PhotoResponse.Photo> photos;
    OnPhotoClickListener onPhotoClickListener;

    public GalleryViewAdapter(List<PhotoResponse.Photo> photos, OnPhotoClickListener onPhotoClickListener) {
        this.photos = photos;
        this.onPhotoClickListener = onPhotoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_adapter_gallery, parent, false);

        return new ViewHolder(view, onPhotoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(getPhotoLink(position))
                .into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    private String getPhotoLink(int position) {
        return photos.get(position).getLink();
    }

    interface OnPhotoClickListener {
        void onPhotoClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.photoView)
        ImageView photoView;

        OnPhotoClickListener onPhotoClickListener;

        public ViewHolder(@NonNull View view, OnPhotoClickListener onPhotoClickListener) {
            super(view);
            ButterKnife.bind(this, view);

            this.onPhotoClickListener = onPhotoClickListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPhotoClickListener.onPhotoClick(getAdapterPosition());
        }
    }
}
