package com.web.connector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

/**
 * Created by HongUi on 2017-06-13.
 */

public class MainPageActivity extends AppCompatActivity {
    Button signUpBtn, singInBtn, googleBtn;
    OAuthLoginButton mOAuthLoginButton;
    OAuthLogin mOAuthLoginModule;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_mainpage);

        signUpBtn = (Button) findViewById(R.id.signUpBtn);
        singInBtn = (Button) findViewById(R.id.signInBtn);
        googleBtn = (Button) findViewById(R.id.googleBtn);
        // 네이버 로그인
   /*     mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                OAuthSampleActivity.this
                ,OAUTH_CLIENT_ID
                ,OAUTH_CLIENT_SECRET
                ,OAUTH_CLIENT_NAME*//*
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );*/

        mOAuthLoginButton = (OAuthLoginButton)findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setBgResourceId(R.drawable.img_loginbtn_usercustom);
        // Inner Class
        MyClick myClick = new MyClick();


        signUpBtn.setOnClickListener(myClick);
        singInBtn.setOnClickListener(myClick);
        googleBtn.setOnClickListener(myClick);
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
                case R.id.googleBtn:
                    Toast.makeText(getApplicationContext(), "구글로그인 해!", Toast.LENGTH_SHORT);
                    break;
            }
        }
    }

    /*** OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
    객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.*/
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                String tokenType = mOAuthLoginModule.getTokenType(mContext);
               /* mOauthAT.setText(accessToken);
                mOauthRT.setText(refreshToken);
                mOauthExpires.setText(String.valueOf(expiresAt));
                mOauthTokenType.setText(tokenType);
                mOAuthState.setText(mOAuthLoginModule.getState(mContext).toString());*/
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                Toast.makeText(mContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };

/*        mOAuthLoginModule.startOauthLoginActivity(mContext, mOAuthLoginHandler);*/
}
