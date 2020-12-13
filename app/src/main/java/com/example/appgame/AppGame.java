package com.example.appgame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.learn2.MainRun;

public class AppGame extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        //指南针
        config.useCompass = false;
        //加速度
        config.useAccelerometer = false;
        config.useWakelock = true;
        config.numSamples = 5;
        initialize(new MainRun(), config);
    }
}
