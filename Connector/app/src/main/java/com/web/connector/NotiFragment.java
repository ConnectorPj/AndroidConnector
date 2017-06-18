package com.web.connector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * Created by HongUi on 2017-06-09.
 */

public class NotiFragment extends Fragment {
    ListView listView;
    NotiFragmentAdapter adapter;
    boolean init = false;

    // 여기서는 중복제거를 위한 String을 배열로 선언(내용이 똑같을 때)
    String[] nameStr = {"List1", "List2", "List3", "List4"};
    String[] contentStr = {"One", "Two", "Three", "Four"};

    public NotiFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            adapter = new NotiFragmentAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_noti, container, false);
         listView = (ListView)view.findViewById(R.id.listNoti);
         listView.setAdapter(adapter);
        // init 변수가 false 라면 초기 아이템을 집어 넣고 아니면 안함.
        if(!init) {
            initItems();
        }
        // listView 리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), position +"번째" , Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    // 중복 제거
    public void initItems(){
        // 먼저 멤버변수 init을 true로 만들어 준다. -> 초기만 가능하도록
        init = true;
        // 여기서 필요한 정보를 집어넣는다.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profile3),"List1","Number One");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profile4),"List2","Number Two");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.profile5),"List3","Number Three");


    }

}
