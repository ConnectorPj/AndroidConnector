package com.web.connector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by HongUi on 2017-06-13.
 */

/** Intro 화면*/
public class IntroActivity extends AppCompatActivity{
    /** 로딩화면이 떠있는 시간(ms) **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*setContentView(R.layout.activity_intro);*/
        // 인텐트로 Tutorial로 보냄
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(IntroActivity.this, TutorialActivity.class);
                IntroActivity.this.startActivity(intent);
                IntroActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }

}
