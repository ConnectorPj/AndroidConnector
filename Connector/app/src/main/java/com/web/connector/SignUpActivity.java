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

import com.google.gson.Gson;
import com.web.connector.bean.ProfileBean;
import com.web.connector.utils.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputName, inputEmail, inputPassword, inputPasswordCheck;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutPasswordCheck;
    private Button singUpBtn;


    // cafe24.com 사이트
    private static final String CONNECTOR_SITE = "http://jhu1993.cafe24.com";

    private ProfileBean profileBean;
    private Handler handler = new Handler();


    // URL을 연결 시켜줄 클래스
    public class NetworkTask extends AsyncTask<Map, Integer, String> {

        private CustomProgressDialog customProgressDialog = new CustomProgressDialog(SignUpActivity.this);

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
                    CONNECTOR_SITE + "/androidInsert.do");

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

            handler.post(new Runnable() {
                @Override
                public void run() {
                    customProgressDialog.dismiss(); // Dialog 없애기
                    // 받아오기
                    // 회원가입 과 동시에 로그인 하게 됨.
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    intent.putExtra("user", profileBean);
                    SignUpActivity.this.startActivity(intent);
                }
            }); //end of Handler
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutPasswordCheck = (TextInputLayout)findViewById(R.id.input_layout_password_check);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputPasswordCheck = (EditText)findViewById(R.id.input_password_check);
        singUpBtn = (Button) findViewById(R.id.btn_signup);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputPasswordCheck.addTextChangedListener(new MyTextWatcher(inputLayoutPasswordCheck));

        singUpBtn.setOnClickListener(new View.OnClickListener() {
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
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if( !validatePasswordCheck() ){
            return;
        }
        // 모든 조건 만족 시 -> 이름, 이메일, 비밀번호, 비밀번호 확인 조건 만족 시
        SignUpActivity.NetworkTask networkTask = new SignUpActivity.NetworkTask();
        Map params = new HashMap();
        //로그인정보를 확인 후 그 아이디를 넘겨줘야한다.
        String userId = inputEmail.getText().toString();
        String userPw = inputPassword.getText().toString();
        String userName = inputName.getText().toString();

        params.put("userId", userId);
        params.put("userPw", userPw);
        params.put("userName", userName);

        networkTask.execute(params);
    }


    /** 이름 확인 **/
    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }
    /** 이메일 형식 확인 **/
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
    /** 비밀번호 빈칸 체크 **/
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
    /** 비밀번호 확인 체크 -> 위와 동일 및 빈칸 체크**/
    private boolean validatePasswordCheck() {
        // 빈칸인 경우
        if (inputPasswordCheck.getText().toString().trim().isEmpty()) {
            inputLayoutPasswordCheck.setError(getString(R.string.err_msg_password));
            requestFocus(inputPasswordCheck);
            return false;
        }
        // 비밀번호가 맞지 않는 경우
        else if(!(inputPasswordCheck.getText().toString()).equals(inputPassword.getText().toString())){
            inputLayoutPasswordCheck.setError(getString(R.string.err_msg_password_check));
            requestFocus(inputPasswordCheck);
            return false;
        } else{
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
        /** 텍스트가 바뀔 때마다 체크 */
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_layout_password_check:
                    validatePasswordCheck();
                    break;
            }
        }
    }
}
