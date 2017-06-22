package com.web.connector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by HongUi on 2017-06-13.
 */

public class MainPageActivity extends AppCompatActivity {
    Button signUpBtn, singInBtn;
    Context mContext;
    //네이버  로그인 관련
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_mainpage);

        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        singInBtn = (Button) findViewById(R.id.signInBtn);

        // Inner Class
        MyClick myClick = new MyClick();


        signUpBtn.setOnClickListener(myClick);
        singInBtn.setOnClickListener(myClick);
    }
    /** Onclick 이벤트 처리 */
    class MyClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.signInBtn:
                    Intent singUpIntent = new Intent(MainPageActivity.this, SignInActivity.class);
                    MainPageActivity.this.startActivity(singUpIntent);
                    break;
                case R.id.signUpBtn:
                    Intent signInIntent = new Intent(MainPageActivity.this, SignUpActivity.class);
                    MainPageActivity.this.startActivity(signInIntent);
                    break;
            }
        }
    }


}
