package com.web.connector;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LayoutFragmentThree extends Fragment {

    TextView textView;
    public LayoutFragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_layout_fragment_three, container, false);
        textView =(TextView)view.findViewById(R.id.textView);
        textView.setText("지도를 검색하여 \n \t 나랑 근접한 스터디 그룹을 \n찾아보세요!");
        return view;
    }

}
