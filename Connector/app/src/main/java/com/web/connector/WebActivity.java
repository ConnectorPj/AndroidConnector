package com.web.connector;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by HongUi on 2017-06-15.
 */

public class WebActivity extends AppCompatActivity {
    private WebView mWebView;
    private WebActivity.JSInterface mJSInterface;
    private String mTokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Button btnWeb = (Button)findViewById(R.id.btnWeb);
        btnWeb.setOnClickListener(btnWebClick);

        mWebView = (WebView)findViewById(R.id.webview);
        // 자바스크립트 사용 셋팅
        mWebView.getSettings().setLoadWithOverviewMode(true); //웹뷰에서 페이지가 확대되는 문제해결
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setInitialScale(1); //기기별 화면사이트에 맞게 조절

        mWebView.getSettings().setJavaScriptEnabled(true);

        mJSInterface = new WebActivity.JSInterface();
        mWebView.addJavascriptInterface(mJSInterface, "mJSInterface"); // 안드로이드에서 호출하는 대상 그래서 이름.setSmile
        mWebView.setWebViewClient(new WebActivity.WebViewClientHandler());

        // assets 폴더에 있는 메인 페이지 로딩
//        mWebView.loadUrl("file:///android_asset/www/sample.html");
        // iptime5G 잡고 실험을 권장??? 이 ip를 알기 위해선 cmd > ipconfig >
        // 무선랜 어댑터 무선 네트워크 연결 부분의 IPv4주소임 114번 라인의 URL과 같은 주소여야함
        mWebView.loadUrl("http://192.168.0.124:8081/member/loginFormAjax.do");

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TEST", "Refreshed token: " + refreshedToken); // 앱에서 나온 토큰을 서버가 가지고 있어야 하니까 보내줘야 해
        if(refreshedToken != null && refreshedToken.length() > 0) {
            PrefUtil.setPreference(this, PrefUtil.KEY_PUSH_TOKEN, refreshedToken);
        }

    }

    private View.OnClickListener btnWebClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            // 자바스크립트 함수 호출
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:changeFace()");
                }
            });
        }
    };

    //자바스크립트 인터페이스 클래스
    class JSInterface {

        @JavascriptInterface
        public void setSmile(String msg){
            Toast.makeText(getApplicationContext(),"WebView에서 나를?",Toast.LENGTH_SHORT).show();
        }

        // 웹에서 호출되는 메소드
        @JavascriptInterface
        public void updateAndToken(String memberId){

            String token = PrefUtil.getPreference(WebActivity.this, PrefUtil.KEY_PUSH_TOKEN);
            //서버로 전송하는 스레드 시작
            new WebActivity.TokenSendTask(memberId, token).execute();
        }

    };
    private class TokenSendTask extends AsyncTask<String, Void, String> {

        private String memberId, token;

        public TokenSendTask(String memberId, String token){
            this.memberId = memberId;
            this.token = token;
        }

        @Override
        protected String doInBackground(String... params) {

            String sendUrl = "http://192.168.0.124:8081/updatePushToken.do";

            try{
                RestTemplate restT = new RestTemplate();
                restT.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restT.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map =
                        new LinkedMultiValueMap<String, Object>();
                map.add("memberId", memberId);
                map.add("token", token);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String, Object>> request =
                        new HttpEntity<>(map, headers);

                return restT.postForObject(sendUrl, request, String.class);
            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

    };

    class WebViewClientHandler extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Toast.makeText(WebActivity.this, "onPageStarted" + url , Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Toast.makeText(WebActivity.this, "onPageFinished" + url , Toast.LENGTH_SHORT).show();
        }
    };
}
