package com.web.connector;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.web.connector.reviewList.ReviewListItem;

import java.util.ArrayList;

/**
 * Created by user on 2017-06-18.
 */

public class ReviewListAdapter extends BaseAdapter {

    private ArrayList<ReviewListItem> reviewList = new ArrayList<>();


    //생성자
    public ReviewListAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴 : 필수 구현


    @Override
    public int getCount() {
        return reviewList.size();
    }


    // Position에 위치한 데이터를 화면에 출력하는데 사용될 view를 리턴 : 필수

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final  int pos = position;
        final Context context = parent.getContext();

        // listView 아이템
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_reviewlist, parent , false);
        }

        // 화면에 표시될 View
        ImageView profile = (ImageView) convertView.findViewById(R.id.imageView5);
        TextView name = (TextView)convertView.findViewById(R.id.textView11);
        TextView time = (TextView)convertView.findViewById(R.id.textView9);
        TextView detail = (TextView)convertView.findViewById(R.id.textView12);

        ReviewListItem reviewListItem = reviewList.get(position);

        profile.setImageDrawable(reviewListItem.getProfile());
        name.setText(reviewListItem.getName());
        time.setText(reviewListItem.getDate());
        detail.setText(reviewListItem.getDetail());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    public void addItem(Drawable profile, String name, String time, String detail){
        ReviewListItem item = new  ReviewListItem();

        item.setProfile(profile);
        item.setName(name);
        item.setDate(time);
        item.setDetail(detail);

        reviewList.add(item);
    }


}
