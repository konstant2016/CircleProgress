package com.konstant.circleprogress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import cn.konstant.circleprogress.ScaleProgress;

public class ScaleProgressActivity extends AppCompatActivity {
    private ScaleProgress mScaleCircleProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_progress);
        mScaleCircleProgress =(ScaleProgress) findViewById(R.id.scale_progress);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) (Math.random() * 100);
                Log.d("test", "刻度圆的值：" + i);
                mScaleCircleProgress.setProgress(i);
            }
        });
    }
}
