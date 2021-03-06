package com.web.connector;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.web.connector.fragmentItems.NoticeItem;

import java.util.ArrayList;

/**
 * Created by HongUi on 2017-06-18.
 */

public class NotiFragmentAdapter extends BaseAdapter {
    // adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<NoticeItem> noticeItems = new ArrayList<NoticeItem>();
    // NotiFragmentAdapter 기본 생성자
    public NotiFragmentAdapter(){

    }
    // Adapter에 사용되는 데이터의 개수를 리턴 : 필수!
    @Override
    public int getCount() {
        return noticeItems.size();
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴 : 필수!


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "notilist_item.xml"에 있는 layout을  inflate하여 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.notilist_item, parent, false);
        }
        // 화면에 표시할 View(Layout)으로 부터 위젯에 대한 참조 획득
        FrameLayout imgHeader = (FrameLayout) convertView.findViewById(R.id.imgHeader);
        TextView titleTextView = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView contentTextView = (TextView) convertView.findViewById(R.id.txtContent);

        // Data set에서 position에 위치한 데이터 참조 획득
        NoticeItem noticeItem = noticeItems.get(position);

        // 각각 위젯에 데이터 반영
        imgHeader.setBackground(noticeItem.getImgDrawable());
        titleTextView.setText(noticeItem.getTitleStr());
        contentTextView.setText(noticeItem.getContentStr());


        return convertView;
    }

    // 지정한 위치(position)에 잇는 데이터와 관계된 아이템(row)의 ID를 리턴 : 필수!

    @Override
    public long getItemId(int position) {
        return position;
    }
    // 지정한 위치의 데이터를 리턴 : 필수!
    @Override
    public Object getItem(int position) {
        return noticeItems.get(position);
    }

    public void addItem(Drawable img, String title, String content){
        NoticeItem noticeItem = new NoticeItem();

        noticeItem.setImgDrawable(img);
        noticeItem.setTitleStr(title);
        noticeItem.setContentStr(content);

        noticeItems.add(noticeItem);
    }
}
