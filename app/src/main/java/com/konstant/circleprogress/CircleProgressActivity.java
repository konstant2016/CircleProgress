package com.konstant.circleprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import cn.konstant.circleprogress.CircleProgress;

public class CircleProgressActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    CircleProgress mCircleProgress;
    SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBaseViews();
    }

    private void initBaseViews() {
        mCircleProgress = (CircleProgress) findViewById(R.id.progress);
        mSeekBar = (SeekBar) findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        mCircleProgress.setValue(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
