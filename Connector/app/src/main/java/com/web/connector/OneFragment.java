package com.web.connector;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by HongUi on 2017-06-09.
 */

public class OneFragment extends Fragment {
    //DATA Parsing 관련
    String url = "http://jhu1993.cafe24.com/loginProc.do";
    private static final String TAG_RESULTS="posts";
    private static final String TAG_WRITER = "writer";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DATE = "regist_day";
    private static final String TAG_CONTENT = "content";
    JSONArray posts = null;
    ArrayList<HashMap<String,String>> noticeList;
    DBhelper helper;
    // UI 관련
    private RecyclerView rv;
    private LinearLayoutManager mLinearLayoutManager;

    public OneFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_one, container, false);
        /*
        현재 오류로 보류중
        noticeList = new ArrayList<HashMap<String, String>>();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(mLinearLayoutManager);*/

      /*  // 폰 내 DB와 연결하여 uid 얻어와 보내기 - Fragment이기 때문에 context -> getActivity()
        helper = new DBhelper(getActivity(), "user2.db", null, DBhelper.DB_VER);
        url += "?uid=" + helper.getId(getActivity());
        *//*Log.e("getUid", helper.getId(getActivity()));*//*
        getData(url);
*/
        return view;
    }

    private void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                //JSON 받아온다.
                String uri = params[0];
                BufferedReader br = null;
                try{
                    URL url = new URL(uri);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String json;
                    while((json = br.readLine()) != null){
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();
                }catch (Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String myJSON) {
                makeList(myJSON);
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    /* JSON -> LIST 가공 메소드 **/
    public void makeList(String myJSON){
        try{
            JSONObject jsonobj = new JSONObject(myJSON);
            posts = jsonobj.getJSONArray(TAG_RESULTS);
            for (int i = 0; i< posts.length(); i++){
                // JSON에서 각각의 요소를 뽑아옴
                JSONObject c = posts.getJSONObject(i);
                String title = c.getString(TAG_TITLE);
                String writer = c.getString(TAG_WRITER);
                String date = c.getString(TAG_DATE);
                String content = c.getString(TAG_CONTENT);
                if(content.length() > 50){
                    content = content.substring(0, 50)+ "..."; // 50자 자르고 ... 붙이기
                }
                if(title.length() > 16){
                    title = title.substring(0, 16) + "..."; // 18자 자르고 ... 붙이기
                }
                // HashMap에 붙이기
                HashMap<String,String> posts = new HashMap<String,String>();
                posts.put(TAG_TITLE, title);
                posts.put(TAG_WRITER, writer);
                posts.put(TAG_DATE, date);
                posts.put(TAG_CONTENT, content);

                // ArrayList에 HashMap 붙이기
                noticeList.add(posts);
            }
            // 카드 리스뷰 어댑터에 연결
            NoticeAdapter adapter = new NoticeAdapter(getActivity(), noticeList);
            Log.e("onCreate[noticelist]", ""+ noticeList.size());
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch(JSONException e){
            e.printStackTrace();
        }
    }




}
