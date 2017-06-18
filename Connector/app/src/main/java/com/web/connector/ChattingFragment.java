package com.web.connector;

import android.content.Intent;
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

public class ChattingFragment extends Fragment {

    ListView listView;
    ChattingFragmentAdapter adapter;
    boolean init = false;

    // 여기서는 중복제거를 위한 String을 배열로 선언(내용이 똑같을 때)
    String[] nameStr = {"List1", "List2", "List3", "List4"};
    String[] numberStr = {"4", "5", "8", "12"};
    String[] contentStr = {"One", "Two", "Three", "Four"};


    public ChattingFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ChattingFragmentAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatting, container, false);
        listView = (ListView)view.findViewById(R.id.listChat);
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
                // 현재는 그냥 채팅 ui 로 이동.
                Intent chattingIntent = new Intent(getActivity(), ChatActivity.class);
                getActivity().startActivity(chattingIntent);

            }
        });
        return view;
    }
    // 중복 제거
    public void initItems(){
        // 먼저 멤버변수 init을 true로 만들어 준다. -> 초기만 가능하도록
        init = true;
        // 여기서 필요한 정보를 집어넣는다.
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.minions),"Minions","Banana!", "2");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.album6),"Eminem","Love the way you are", "3");
        adapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.album2),"Michel","it is real?", "8");
    }


}
