package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Locale;

public class StopwatchActivity extends Activity {
    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

//    // this is called when the activity is about to become visible
//    // after it has run, the activity content is visible
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (this.wasRunning) {
//            this.running = true;
//        }
//    }

    // this is called when the activity has focus
    // in this case it doesn't matter if the activity resumes or start, as it will do the same
    // thing, onResume is implemented because it runs when the activity is started or resumed
    @Override
    protected void onResume() {
        super.onResume();
        if (this.wasRunning) {
            this.running = true;
        }
    }

    // this is called when the Activity object has been instantiated
    // so basically it set up the activity, all initialisation code should be here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            this.seconds = savedInstanceState.getInt("seconds");
            this.running = savedInstanceState.getBoolean("running");
            this.wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

//    // this is called when the activity stops being visible
//    // and before onSaveInstanceState and onDestroy
//    @Override
//    protected void onStop() {
//        super.onStop();
//        this.wasRunning = this.running;
//        this.running = false;
//    }

    // this is called when the activity loses focus
    // in this case it will do the same thing irrespective of whether the activity is stopped
    // or paused so only onPause is implemented. This method runs if the activity is paused or stopped
    @Override
    protected void onPause() {
        super.onPause();
        this.wasRunning = this.running;
        this.running = false;
    }

    // this is called before onDestroy (and onDestroy just before the activity gets destroyed)
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", this.seconds);
        savedInstanceState.putBoolean("running", this.running);
        savedInstanceState.putBoolean("wasRunning", this.wasRunning);
    }

    public void startHandle(View view) {
        this.running = true;
    }

    public void stopHandle(View view) {
        this.running = false;
    }

    public void resetHandle(View view) {
        this.running = false;
        this.seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.timer);
        final Handler handler = new Handler();

        // like an async thing running in the Android main thread
        // only the main thread can update the user interface
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}