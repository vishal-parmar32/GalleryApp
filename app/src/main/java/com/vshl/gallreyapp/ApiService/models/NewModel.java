package com.vshl.gallreyapp.ApiService.models;

import android.os.Parcel;
import android.os.Parcelable;

public class NewModel implements Parcelable {

    private String path;


    public NewModel(String path) {
        this.path = path;
    }

    protected NewModel(Parcel in) {
        path = in.readString();

    }



    public static final Creator<NewModel> CREATOR = new Creator<NewModel>() {
        @Override
        public NewModel createFromParcel(Parcel in) {
            return new NewModel(in);
        }

        @Override
        public NewModel[] newArray(int size) {
            return new NewModel[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Creator<NewModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
    }

}
