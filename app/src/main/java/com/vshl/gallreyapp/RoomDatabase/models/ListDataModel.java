package com.vshl.gallreyapp.RoomDatabase.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ListDataModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "imageurl")
    private String img;

    public ListDataModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    public ListDataModel(Parcel in) {
        img = in.readString();

    }



    public static final Creator<ListDataModel> CREATOR = new Creator<ListDataModel>() {
        @Override
        public ListDataModel createFromParcel(Parcel in) {
            return new ListDataModel(in);
        }

        @Override
        public ListDataModel[] newArray(int size) {
            return new ListDataModel[size];
        }
    };


    public static Creator<ListDataModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(img);
    }
}
