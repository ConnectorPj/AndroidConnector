package com.web.connector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by user on 2017-06-18.
 */

public class finClassActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finclass);

        ListView listView;
        Button writeButton ;
        ReviewListAdapter adapter;

        //Adapter 생성
        adapter = new ReviewListAdapter();
        writeButton = (Button)findViewById(R.id.button3);
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfo = new Intent(finClassActivity.this, writeReplyActivity.class );
                startActivity(appInfo);
            }
        });

        // 리스트뷰 참조 및 Adapter 달기
        listView = (ListView)findViewById(R.id.listview11);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.album1),
                "박덕환","2016-06-18","즐거웠습니다.");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.album2),
                "박덕환","2016-06-18","즐거웠습니다.");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.album3),
                "박덕환","2016-06-18","즐거웠습니다.");
    }
}
