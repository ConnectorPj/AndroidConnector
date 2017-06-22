package com.web.connector;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.web.connector.bean.Constants;
import com.web.connector.bean.CustomerBean;
import com.web.connector.bean.ProfileBean;
import com.web.connector.utils.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button singInBtn;

    // cafe24.com 사이트
    private static final String CONNECTOR_SITE = "http://jhu1993.cafe24.com";
    private ProfileBean profileBean;
    private Handler handler = new Handler();

    // URL을 연결 시켜줄 클래스
    public class NetworkTask extends AsyncTask<Map, Integer, String> {

        private CustomProgressDialog customProgressDialog = new CustomProgressDialog(SignInActivity.this);

        /**
         * doInBackground 실행되기 이전에 동작한다.
         */
        @Override
        protected void onPreExecute() {
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            customProgressDialog.show(); // Dialog 보여주기
            super.onPreExecute();
        }

        /**
         * 본 작업을 쓰레드로 처리해준다.
         * ... 은 가변 배열 또는 가변 파라미터라고 부른다.
         * a, b, c 이런식으로 보내도 되고 배열로 보내도 된다.
         *
         * @param maps
         * @return
         */
        @Override
        protected String doInBackground(Map... maps) {

            // HTTP 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST",
                    CONNECTOR_SITE + "/androidLogin.do");

            //파라미터를 전송한다.
            http.addAllParameters(maps[0]);

            // HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        /**
         * @param s : doInBackground에서 리턴한 body
         */
        protected void onPostExecute(String s) {
            Log.d("JSON_RESULT", s);

            Gson gson = new Gson();
            profileBean = gson.fromJson(s, ProfileBean.class);
            /*final String url = CONNECTOR_SITE + profileBean.getPhotoFileName();*/

            handler.post(new Runnable() {
                @Override
                public void run() {
                    customProgressDialog.dismiss(); // Dialog 없애기
                    // TODO : 일단은 인텐트로 보냄 여기서 로그인 처리
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("user", profileBean);
                    SignInActivity.this.startActivity(intent);
                }
            }); //end of Handler
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        singInBtn = (Button) findViewById(R.id.btn_signup);

        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        singInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 유효성 검사
                submitForm();
            }
        });
    }

    /**
     * 유효성검사
     */
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }
        NetworkTask networkTask = new NetworkTask();
        Map params = new HashMap();
        //로그인정보를 확인 후 그 아이디를 넘겨줘야한다.
        String userId = inputEmail.getText().toString();
        String userPw = inputPassword.getText().toString();

        params.put("userId", userId);
        params.put("userPw", userPw);

        networkTask.execute(params);
    }

    /**
     * 이메일 형식 확인
     **/
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * 비밀번호 빈칸 체크
     **/
    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    // 이메일 유효성 체크
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        /**
         * 텍스트가 바뀔 때마다 체크
         */
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }


}
