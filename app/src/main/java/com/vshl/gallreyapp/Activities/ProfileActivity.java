package com.vshl.gallreyapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;
import com.vshl.gallreyapp.Key;
import com.vshl.gallreyapp.Preferences;
import com.vshl.gallreyapp.R;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    ImageView profile_img,back;
    TextView name,email,id;
    Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();
    }

    private void initView() {

        gso  =new  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this::onConnectionFailed)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        name = (TextView) findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        id = (TextView)findViewById(R.id.id);
        profile_img = (ImageView) findViewById(R.id.profile_img);
        signout = (Button) findViewById(R.id.signout);
        back = (ImageView)findViewById(R.id.back);


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull @NotNull Status status) {
                        if (status.isSuccess()){
                            gotoLogin();
                        }else {

                            Toast.makeText(ProfileActivity.this,"Log Out Failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }


    private void gotoLogin() {
        Preferences.setBoolean(ProfileActivity.this, Key.IS_LOGGED_IN,false);
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        finish();
    }

    private void handleSingInResult(GoogleSignInResult result){
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            name.setText(account.getDisplayName());
            email.setText(account.getEmail());
            id.setText(account.getId());
            Picasso.with(ProfileActivity.this).load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher_round).into(profile_img);
        }else {
            gotoLogin();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSingInResult(result);
        }else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull @NotNull GoogleSignInResult result) {
                    handleSingInResult(result);
                }
            });
        }
    }
}