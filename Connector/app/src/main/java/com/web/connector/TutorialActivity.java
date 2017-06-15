package com.web.connector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by HongUi on 2017-06-13.
 */

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener{
    Button buttonSkip,buttonNext;
    ImageView imageViewFilled,imageViewUnfilled1,imageViewUnfilled2;
    int count=1;
    LayoutFramentOne layoutFramentOne;
    LayoutFragmentTwo layoutFragmentTwo;
    LayoutFragmentThree layoutFragmentThree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);
        layoutFramentOne = new LayoutFramentOne();
        layoutFragmentTwo = new LayoutFragmentTwo();
        layoutFragmentThree = new LayoutFragmentThree();
        buttonSkip = (Button)findViewById(R.id.buttonSkip);
        buttonNext =(Button)findViewById(R.id.buttonNext);
        imageViewFilled =(ImageView)findViewById(R.id.imageView1);
        imageViewUnfilled1 =(ImageView)findViewById(R.id.imageView2);
        imageViewUnfilled2 =(ImageView)findViewById(R.id.imageView3);
        buttonSkip.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        try {
            getSupportFragmentManager().beginTransaction().add(R.id.linearLayoutFragment, layoutFramentOne).commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSkip:
                startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
                finish();
                break;
            case R.id.buttonNext:
                count =count+1;
                if(count==2){
                    try {
                        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutFragment, layoutFragmentTwo).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    imageViewFilled.setImageResource(R.drawable.ic_circleshape);
                    imageViewUnfilled1.setImageResource(R.drawable.ic_circular);
                }else if(count ==3){
                    try {
                        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutFragment, layoutFragmentThree).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    imageViewUnfilled1.setImageResource(R.drawable.ic_circleshape);
                    imageViewUnfilled2.setImageResource(R.drawable.ic_circular);
                    buttonNext.setText("DONE");
                    buttonSkip.setClickable(false);
                    buttonSkip.setTextColor(Color.GRAY);
                }else if (count ==4){
                    startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
                    finish();
                }
                break;
        }
    }
}