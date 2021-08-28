package com.vshl.gallreyapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vshl.gallreyapp.Adapter.GalleryAdapter;
import com.vshl.gallreyapp.Adapter.ImageViewPagerAdapter;
import com.vshl.gallreyapp.ApiService.APIManager;
import com.vshl.gallreyapp.ApiService.models.ImagesModel;
import com.vshl.gallreyapp.Key;
import com.vshl.gallreyapp.Preferences;
import com.vshl.gallreyapp.R;
import com.vshl.gallreyapp.Utils;
import com.vshl.gallreyapp.RoomDatabase.DatabaseClient;
import com.vshl.gallreyapp.RoomDatabase.models.ListDataModel;
import com.vshl.gallreyapp.RoomDatabase.adapter.RoomAdapter;
import com.vshl.gallreyapp.RoomDatabase.adapter.RoomImageViewPagerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GalleryAdapter.onItemClickListner,
        RoomAdapter.onRoomItemClickListner,
        GalleryAdapter.position {

    Context context;
    boolean isViewPager = false;
    boolean isInsertData = false;
    boolean isDeleted = false;
    private Utils utils;

    ImageView user;
    ViewPager viewPager;
    LinearLayout llViewPager;
    RelativeLayout header;

    GalleryAdapter adapter;
    ArrayList<ImagesModel> listData = new ArrayList<>();
    RecyclerView recyclerView;


    ArrayList<ListDataModel> roomDataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    private void initView() {
        context = this;
        utils = new Utils(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        user = (ImageView) findViewById(R.id.user);
        llViewPager = (LinearLayout) findViewById(R.id.llViewPager);
        header = (RelativeLayout) findViewById(R.id.header);


        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });


        boolean isConnected = utils.isConnected();
        if (!isConnected) {

            getDataBaseList();

        } else {

            getList();
        }


    }


    private void getList() {
        APIManager.getInstance().showProgressDialog(this, false, "Loading...");
        APIManager.getInstance().getcity(new Callback<ArrayList<ImagesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ImagesModel>> call, Response<ArrayList<ImagesModel>> response) {
                APIManager.getInstance().dismissProgressDialog();
                if (response.isSuccessful()) {

                    listData = response.body();
                    adapter = new GalleryAdapter(context, listData);
                    adapter.setOnItemClick(MainActivity.this);
                    adapter.setPosition(MainActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ImagesModel>> call, Throwable t) {
                APIManager.getInstance().dismissProgressDialog();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getDataBaseList() {


        class GetTasks extends AsyncTask<Void, Void, ArrayList<ListDataModel>> {

            @Override
            protected ArrayList<ListDataModel> doInBackground(Void... voids) {
                ArrayList<ListDataModel> taskList = (ArrayList<ListDataModel>) DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .imageDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(ArrayList<ListDataModel> tasks) {
                super.onPostExecute(tasks);
                roomDataList.addAll(tasks);
                RoomAdapter adapter = new RoomAdapter(MainActivity.this, tasks);
                adapter.setOnRoomItemClick(MainActivity.this);
                recyclerView.setAdapter(adapter);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();


    }


    @Override
    public void onItemClick(int position) {
        isViewPager = true;
        llViewPager.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        final ImageViewPagerAdapter fullScreenImageAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), listData);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(fullScreenImageAdapter);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onRoomItemClick(int position) {
        isViewPager = true;
        llViewPager.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        header.setVisibility(View.GONE);
        final RoomImageViewPagerAdapter fullScreenImageAdapter = new RoomImageViewPagerAdapter(getSupportFragmentManager(), roomDataList);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(fullScreenImageAdapter);
        viewPager.setCurrentItem(position);
    }


    @Override
    public void onPostion(int position) {

        isInsertData = Preferences.getBoolean(context, Key.isInsertData);

        if (!isInsertData) {
            insertData(position);
        }


    }

    private void insertData(int position) {
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                ListDataModel imagesModel = new ListDataModel();

                imagesModel.setImg(listData.get(position).getImageurl());

                //adding to database
                DatabaseClient.getInstance(context).getAppDatabase()
                        .imageDao()
                        .insert(imagesModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

        }


        SaveTask st = new SaveTask();
        st.execute();

    }

    @Override
    public void onBackPressed() {

        if (isViewPager) {
            llViewPager.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            header.setVisibility(View.VISIBLE);
            isViewPager = false;
        } else {
            super.onBackPressed();
        }

    }
}