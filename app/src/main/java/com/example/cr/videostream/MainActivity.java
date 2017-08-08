package com.example.cr.videostream;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity
{
    //Video URL
    private String VideoURL = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4" +
            "";

    private ProgressDialog progressDialog;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (VideoView) findViewById(R.id.video_view);

        new StreamVideo().execute();

    }

    private class StreamVideo extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            //create progressbar
            progressDialog  = new ProgressDialog(MainActivity.this);

            //set progress title
            progressDialog.setTitle("Android Stream Video Tutorial");

            //set progress message
            progressDialog.setMessage("Buffering.......");

            progressDialog.setIndeterminate(false);

            //show progress
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
           try
           {
               MediaController mediaController = new MediaController(MainActivity.this);

               mediaController.setAnchorView(videoView);

               //get the URL
               Uri video = Uri.parse(VideoURL);
               videoView.setMediaController(mediaController);
               videoView.setVideoURI(video);

               videoView.requestFocus();
               videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
               {
                   @Override
                   public void onPrepared(MediaPlayer mp)
                   {
                       progressDialog.dismiss();
                       videoView.start();
                   }
               });
           }
           catch (Exception e)
           {
               e.printStackTrace();
           }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
