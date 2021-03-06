package com.aAronQInk.Walls.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aAronQInk.Walls.ItemDecorator;
import com.aAronQInk.Walls.POJO.Photo;
import com.aAronQInk.Walls.R;
import com.aAronQInk.Walls.Walls;
import com.aAronQInk.Walls.activities.PhotoPreviewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> mPhotos;
    private ItemDecorator mDecorator;

    public PhotoAdapter(List<Photo> photos, ItemDecorator decorator) {
        mPhotos = photos;
        mDecorator = decorator;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhotoViewHolder holder, final int position) {
        holder.mAuthorTextView.setText(mDecorator.getAuthor(mPhotos.get(position).getUser()));
        holder.mPhotoImageView.setMinimumHeight(mDecorator.getFinalHeight(mPhotos.get(position).getWidth(), mPhotos.get(position).getHeight()));

        Picasso.get()
                .load(mDecorator.getPhotoUrl(mPhotos.get(position).getUrls()))
                .placeholder(mDecorator.getPlaceholder(mPhotos.get(position).getColor()))
                .error(mDecorator.getPlaceholder(mPhotos.get(position).getColor()))
                .centerCrop()
                .resize(holder.mPhotoImageView.getMeasuredWidth(), mDecorator.getFinalHeight(mPhotos.get(position).getWidth(), mPhotos.get(position).getHeight()))
                .into(holder.mPhotoImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PhotoPreviewActivity.newIntent(v.getContext());
                Walls.sDefaultPhoto = mPhotos.get(position);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photo_image_view) ImageView mPhotoImageView;
        @BindView(R.id.author_text_view) TextView mAuthorTextView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
