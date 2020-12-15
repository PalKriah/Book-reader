package com.learn.bookreader;

import android.Manifest;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

public class AudioBookActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_book_sound_layout);

        String path = getIntent().getStringExtra("audioBookPath");
        String perms = Manifest.permission.READ_EXTERNAL_STORAGE;

        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_to_read),
                    123, perms);
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(path.substring(path.indexOf(':') + 1));
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.i("Bad", e.toString());
            return;
        }

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        SeekBar volumeControl = findViewById(R.id.volumeSeekBar);
        final SeekBar progressControl = findViewById(R.id.progressSeekBar);
        configureSoundSeekBar(volumeControl);
        configureProgressSeekBar(progressControl);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressControl.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 100);

        wireMenu();
    }

    public void playSound(View view) {
        mediaPlayer.start();
    }

    public void pauseSound(View view) {
        mediaPlayer.pause();
    }

    private void configureSoundSeekBar(SeekBar volumeControl) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void configureProgressSeekBar(SeekBar progressControl) {
        progressControl.setMax(mediaPlayer.getDuration());

        progressControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });
    }

    public void wireMenu() {
        Button addBtn = findViewById(R.id.backBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}
