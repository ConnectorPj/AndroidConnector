package com.web.connector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 2017-06-18.
 */

public class writeReplyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Button writeBtn =  (Button)findViewById(R.id.button4);
        Button cancelBtn =  (Button)findViewById(R.id.button2);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메시지 전송 완료
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 취소 하기  finClassActivity
                Intent appInfo = new Intent(writeReplyActivity.this, finClassActivity.class );
                startActivity(appInfo);
            }
        });


    }
}
