package com.web.connector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.web.connector.bean.ClassBean;
import com.web.connector.bean.ProfileBean;
import com.web.connector.utils.HttpClient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HongUi on 2017-06-09.
 * Search.jsp 부분을 웹뷰로 띄운다
 * 여기서 검색창 -> 수업 상세 내역 -> 결제창 까지 보여준다
 * 0616 -> 반응형 웹 완료(공통 헤더 빼고)
 */

public class SearchFragment extends Fragment {
    private LinearLayoutManager lLayout;
    // cafe24.com 사이트
    private static final String CONNECTOR_SITE = "http://jhu1993.cafe24.com";
    private ClassBean classBean;
    private Handler handler = new Handler();
    // URL을 연결 시켜줄 클래스
    public class NetworkTask extends AsyncTask<Map, Integer, String> {

        private CustomProgressDialog customProgressDialog = new CustomProgressDialog(getActivity());

        /**
         * doInBackground 실행되기 이전에 동작한다.
         */
        @Override
        protected void onPreExecute() {
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            customProgressDialog.show(); // Dialog 보여주기
            super.onPreExecute();
        }
        /**
         * 본 작업을 쓰레드로 처리해준다.
         * ... 은 가변 배열 또는 가변 파라미터라고 부른다.
         * a, b, c 이런식으로 보내도 되고 배열로 보내도 된다.
         *
         * @param maps
         * @return
         */
        @Override
        protected String doInBackground(Map... maps) {

            // HTTP 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST",
                    CONNECTOR_SITE + "/search.do");

            //파라미터를 전송한다.
            http.addAllParameters(maps[0]);

            // HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        /**
         * @param s : doInBackground에서 리턴한 body
         */
        protected void onPostExecute(String s) {
            Log.d("JSON_RESULT", s);

            Gson gson = new Gson();
            classBean = gson.fromJson(s, ClassBean.class);
            /*final String url = CONNECTOR_SITE + profileBean.getPhotoFileName();*/

            handler.post(new Runnable() {
                @Override
                public void run() {
                    customProgressDialog.dismiss(); // Dialog 없애기
                }
            }); //end of Handler
        }

    }
    // constructor
    public SearchFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
       /*NetworkTask networkTask = new NetworkTask();
        Map params = new HashMap();
        //로그인정보를 확인 후 그 아이디를 넘겨줘야한다.
        String studyId = "1993jhy@gmail.com";

        params.put("studyId",studyId);

        networkTask.execute(params);*/

        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(getActivity());

        RecyclerView rView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);
        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(getActivity(),rowListItem);
        rView.setAdapter(rcAdapter);


        return view;
    }

    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("아두이노", R.drawable.iotarduino));
        allItems.add(new ItemObject("IOS 초보", R.drawable.androidandios));
        allItems.add(new ItemObject("안드로이드", R.drawable.androidstart));
        allItems.add(new ItemObject("C언어", R.drawable.clanguage));
        allItems.add(new ItemObject("HTML", R.drawable.html));

        return allItems;
    }


}
