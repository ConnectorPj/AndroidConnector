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
public class LayoutFragmentTwo extends Fragment {

TextView textView;
    public LayoutFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_layout_fragment_two, container, false);
        textView =(TextView)view.findViewById(R.id.textView);
        textView.setText("실시간 대화가 가능합니다!");
        return view;
    }

}
