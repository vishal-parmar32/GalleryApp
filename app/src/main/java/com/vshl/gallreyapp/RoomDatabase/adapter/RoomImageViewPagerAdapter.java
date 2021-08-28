package com.vshl.gallreyapp.RoomDatabase.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vshl.gallreyapp.R;
import com.vshl.gallreyapp.RoomDatabase.models.ListDataModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class RoomImageViewPagerAdapter extends FragmentPagerAdapter {

    public ArrayList<ListDataModel> arrayList = new ArrayList<>();
    public static Activity activity;

    public RoomImageViewPagerAdapter(@NonNull @NotNull FragmentManager fm, ArrayList<ListDataModel> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position, arrayList.get(position).getImg());
    }




    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }



    public static class PlaceholderFragment extends Fragment {
        int click = 0;
        String path;
        int pos;
        boolean imgsave = false;
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_IMG_URL = "image_url";

        @Override
        public void setArguments(Bundle args) {
            super.setArguments(args);
            this.pos = args.getInt(ARG_SECTION_NUMBER);
            this.path = args.getString(ARG_IMG_URL);
        }

        public static PlaceholderFragment newInstance(int sectionNumber, String url) {

            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_IMG_URL, url);
            fragment.setArguments(args);


            return fragment;
        }

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.view_image, container, false);
            final PhotoView imageView = (PhotoView) rootView.findViewById(R.id.photo);

            ImageView imgBack = (ImageView) rootView.findViewById(R.id.imgBack);
            ImageView imgSetWall = (ImageView) rootView.findViewById(R.id.imgSetWall);
            RelativeLayout header = (RelativeLayout) rootView.findViewById(R.id.header);

            click = 0;

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    header.setVisibility(View.GONE);
                }
            }, 1000);

            imageView.setOnClickListener(v -> {
                if (click == 0) {
                    header.setVisibility(View.VISIBLE);
                    click = 1;
                } else {
                    header.setVisibility(View.GONE);
                    click = 0;
                }
            });


            Picasso.with(getActivity()).load(path).into(imageView);

            imgSetWall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAsLockOrWall();

                    try {
                        if (imgsave) {


                            File sdCard = Environment.getExternalStorageDirectory();
                            File directory = new File(sdCard.getAbsolutePath() + "/GalleryAppWallpaper");
                            File f = new File(directory, "Wallpaper" + ".jpg"); //or any other format supported


                            Intent intent;
                            Uri uriForFile;
                            if (Build.VERSION.SDK_INT >= 24) {
                                uriForFile = FileProvider.getUriForFile(getActivity(), new StringBuffer().append(getActivity().getPackageName()).append(".provider").toString(), f);
                                intent = new Intent(Intent.ACTION_ATTACH_DATA);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setDataAndType(uriForFile, "image/*");
                                intent.putExtra("mimeType", "image/*");
                                //intent.addFlags(Intent.EXTRA_DOCK_STATE_CAR);
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(Intent.createChooser(intent, "Set as: "));

                                return;
                            }
                            uriForFile = Uri.parse(new StringBuffer().append("file://").append(f.getAbsolutePath()).toString());
                            intent = new Intent(Intent.ACTION_ATTACH_DATA);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setDataAndType(uriForFile, "image/*");
                            intent.putExtra("mimeType", "image/*");
                            //intent.addFlags(Intent.EXTRA_DOCK_STATE_DESK);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(Intent.createChooser(intent, "Set as: "));

                            imgsave = false;

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().onBackPressed();
                }
            });

            File f = new File(path);


            return rootView;
        }


        @SuppressLint("WrongConstant")
        private void setAsLockOrWall() {



            Picasso.with(activity).load(path).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    SaveImage(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });

        }


        private void SaveImage(Bitmap finalBitmap) {

            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            File myDir = new File(root + "/GalleryAppWallpaper");
            myDir.mkdirs();

            String fname = "Wallpaper" + ".jpg";
            File file = new File(myDir, fname);
            if (file.exists()) file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


            imgsave = true;

        }


    }
}
