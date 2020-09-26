package com.example.myapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.VideoView;

public class FreeTutorialVid extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog progressDialog;
    VideoView videoView;
    ImageButton btnPlayPause;
    String videoUrl="https://firebasestorage.googleapis.com/v0/b/myapp-71c42.appspot.com/o/Day%201%2030%20Minute%20at%20Home%20Strength%20Workout%20Clutch%20Life_%20Ashley%20Conrad's%2024%207%20Fitness%20Trainer.mp4?alt=media&token=edf9590e-8623-4c73-9479-4bb35170319c";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_tutorial1);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Free tutorial");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        videoView = (VideoView)  findViewById(R.id.videoview);
        btnPlayPause = (ImageButton) findViewById(R.id.button_play_pause);
        // btnPlayPause.setOnClickListener(this);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog= new ProgressDialog(FreeTutorialVid.this);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                try {
                    if(videoView.isPlaying()){

                        Uri uri = Uri.parse(videoUrl);
                        videoView.setVideoURI(uri);
                        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                btnPlayPause.setImageResource(R.drawable.ic_play);
                            }
                        });
                    }

                    else {

                        videoView.pause();
                        btnPlayPause.setImageResource(R.drawable.ic_play);

                    }

                }
                catch (Exception e){}

                try {
                    Uri uri = Uri.parse(videoUrl);
                    videoView.setVideoURI(uri);
                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            btnPlayPause.setImageResource(R.drawable.ic_play);
                        }
                    });

                }
                catch (Exception ex){

                }

                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        progressDialog.dismiss();
                        mp.setLooping(true);
                        videoView.start();
                        btnPlayPause.setImageResource(R.drawable.ic_pause);
                    }
                });

            }
        });


        ;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }



}


