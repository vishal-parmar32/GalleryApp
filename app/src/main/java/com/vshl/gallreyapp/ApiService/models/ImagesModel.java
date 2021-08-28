package com.vshl.gallreyapp.ApiService.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagesModel  implements Parcelable {



    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("realname")
    @Expose
    private String realname;
    @SerializedName("team")
    @Expose
    private String team;
    @SerializedName("firstappearance")
    @Expose
    private String firstappearance;
    @SerializedName("createdby")
    @Expose
    private String createdby;
    @SerializedName("publisher")
    @Expose
    private String publisher;


    @SerializedName("imageurl")
    @Expose
    private String imageurl;
    @SerializedName("bio")
    @Expose
    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getFirstappearance() {
        return firstappearance;
    }

    public void setFirstappearance(String firstappearance) {
        this.firstappearance = firstappearance;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }


    protected ImagesModel(Parcel in) {
        imageurl = in.readString();

    }



    public static final Creator<ImagesModel> CREATOR = new Creator<ImagesModel>() {
        @Override
        public ImagesModel createFromParcel(Parcel in) {
            return new ImagesModel(in);
        }

        @Override
        public ImagesModel[] newArray(int size) {
            return new ImagesModel[size];
        }
    };


    public static Creator<ImagesModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageurl);
    }
}
