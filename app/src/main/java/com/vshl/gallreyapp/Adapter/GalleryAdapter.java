package com.vshl.gallreyapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vshl.gallreyapp.ApiService.models.ImagesModel;
import com.vshl.gallreyapp.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    Context context;
    private List<ImagesModel> arrayListMain;

    private GalleryAdapter.onItemClickListner clickListner;

    public interface onItemClickListner {
        void onItemClick(int position);
    }

    public void setOnItemClick(GalleryAdapter.onItemClickListner listner) {
        clickListner = listner;
    }

    public GalleryAdapter(Context context, List<ImagesModel> arrayListMain) {
        this.context = context;
        this.arrayListMain = arrayListMain;
    }

    private position positions;

    public interface position {
        void onPostion(int position);
    }

    public void setPosition(GalleryAdapter.position position){
        positions = position;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull GalleryAdapter.ViewHolder holder, int position) {


        Picasso.with(context)
                .load(arrayListMain.get(position).getImageurl())
                .into(holder.image);



        positions.onPostion(position);


    }

    @Override
    public int getItemCount() {
        return arrayListMain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
