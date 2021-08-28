package com.vshl.gallreyapp.RoomDatabase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vshl.gallreyapp.R;
import com.vshl.gallreyapp.RoomDatabase.models.ListDataModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ListDataModel> listDataModels;



    public RoomAdapter(Context context, ArrayList<ListDataModel> listDataModels) {
        this.context = context;
        this.listDataModels = listDataModels;
    }

    private onRoomItemClickListner clickListner;

    public interface onRoomItemClickListner {
        void onRoomItemClick(int position);
    }

    public void setOnRoomItemClick(onRoomItemClickListner listner) {
        clickListner = listner;
    }

    @NonNull
    @NotNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RoomAdapter.ViewHolder holder, int position) {


        Picasso.with(context)
                .load(listDataModels.get(position).getImg())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return listDataModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListner != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListner.onRoomItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
