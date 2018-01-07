package cyb.xandroid.album.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cyb.xandroid.album.R;
import cyb.xandroid.album.SelectParam;
import cyb.xandroid.album.model.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyangbo
 */
public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;

    private Context context;
    private SelectParam mSelectParam;
    //
    private List<LocalMedia> mImageList = new ArrayList<LocalMedia>();
    //
    private List<LocalMedia> mSelectImages;

    private OnImageSelectChangedListener imageSelectChangedListener;

    public ImageListAdapter(Context context, SelectParam selectParam) {
        this.context = context;
        this.mSelectParam = selectParam;
        bindSelectImages(selectParam.getSelectedList());
    }

    public void bindImages(List<LocalMedia> images) {
        this.mImageList = images;
        notifyDataSetChanged();
    }

    public void bindSelectImages(List<LocalMedia> images) {
        this.mSelectImages = images;
        notifyDataSetChanged();
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(mSelectImages);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSelectParam.takePhotoEnable && position == 0) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CAMERA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.headerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageSelectChangedListener != null) {
                        imageSelectChangedListener.onTakePhoto();
                    }
                }
            });
        } else {
            final ViewHolder contentHolder = (ViewHolder) holder;
            final LocalMedia image = mImageList.get(mSelectParam.takePhotoEnable ? position - 1 : position);
            //load image
            Glide.with(context)
                    .load(new File(image.getPath()))
                    .centerCrop()
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .dontAnimate()
                    .into(contentHolder.picture);

            //show checkbox
            if (mSelectParam.selectMode == SelectParam.MODE_SINGLE) {
                contentHolder.check.setVisibility(View.GONE);
            } else {
                contentHolder.check.setVisibility(View.VISIBLE);
            }

            selectImage(contentHolder, isSelected(image));

            if (mSelectParam.previewEnable) {
                contentHolder.check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeCheckboxState(contentHolder, image);
                    }
                });
            }

            contentHolder.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((mSelectParam.selectMode == SelectParam.MODE_SINGLE
                            || mSelectParam.previewEnable) && imageSelectChangedListener != null) {
                        //
                        imageSelectChangedListener.onPictureClick(
                                image, mSelectParam.takePhotoEnable ? position - 1 : position);
                    } else {
                        changeCheckboxState(contentHolder, image);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mSelectParam.takePhotoEnable ? mImageList.size() + 1 : mImageList.size();
    }

    public int getSelectCount() {
        return mSelectParam.getSelectedList().size();
    }

    /**
     *
     *
     **/
    private void changeCheckboxState(ViewHolder contentHolder, LocalMedia image) {
        boolean isChecked = contentHolder.check.isSelected();
        if (mSelectImages.size() >= mSelectParam.maxSelectNum && !isChecked) {
            Toast.makeText(context, context.getString(R.string.message_max_num, mSelectParam.maxSelectNum),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (isChecked) {
            for (LocalMedia media : mSelectImages) {
                if (media.getPath().equals(image.getPath())) {
                    mSelectImages.remove(media);
                    break;
                }
            }
        } else {
            mSelectImages.add(image);
        }
        selectImage(contentHolder, !isChecked);
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(mSelectImages);
        }
    }

    public List<LocalMedia> getSelectedImages() {
        return mSelectImages;
    }

    public List<LocalMedia> getmImageList() {
        return mImageList;
    }

    public boolean isSelected(LocalMedia image) {
        for (LocalMedia media : mSelectImages) {
            if (media.getPath().equals(image.getPath())) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     **/
    public void selectImage(ViewHolder holder, boolean isChecked) {
        holder.check.setSelected(isChecked);
        if (isChecked) {
            holder.picture.setColorFilter(
                    context.getResources().getColor(R.color.image_overlay2),
                    PorterDuff.Mode.SRC_ATOP);
        } else {
            holder.picture.setColorFilter(
                    context.getResources().getColor(R.color.image_overlay),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        View headerView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerView = itemView;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        ImageView check;

        View contentView;

        public ViewHolder(View itemView) {
            super(itemView);
            contentView = itemView;
            picture = (ImageView) itemView.findViewById(R.id.picture);
            check = (ImageView) itemView.findViewById(R.id.check);
        }

    }

    public interface OnImageSelectChangedListener {
        void onChange(List<LocalMedia> selectImages);

        void onTakePhoto();

        void onPictureClick(LocalMedia media, int position);
    }

    public void setOnImageSelectChangedListener(OnImageSelectChangedListener imageSelectChangedListener) {
        this.imageSelectChangedListener = imageSelectChangedListener;
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener.onChange(mSelectImages);
        }
    }
}
