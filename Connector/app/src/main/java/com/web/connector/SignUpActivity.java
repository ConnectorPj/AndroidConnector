package com.web.connector;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputName, inputEmail, inputPassword, inputPasswordCheck;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutPasswordCheck;
    private Button singUpBtn;


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
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
        // TODO 일단은 인텐트로 MainActivity로 보냄
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        SignUpActivity.this.finish();
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
