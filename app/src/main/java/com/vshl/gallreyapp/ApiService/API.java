package com.vshl.gallreyapp.ApiService;

import com.vshl.gallreyapp.ApiService.models.ImagesModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    String BASE_URL = "https://simplifiedcoding.net/demos/";




    @GET("marvel")
    Call<ArrayList<ImagesModel>> getList();


}
