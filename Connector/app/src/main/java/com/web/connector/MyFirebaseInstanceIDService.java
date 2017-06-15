package com.web.connector;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by 정홍의 on 2017-05-24.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        //단말기의 고유아이디 는 토큰이라 한다
        //여기서는 콜백으로 고유아이디를 발급받는거다!!!
        String refreshedToken = FirebaseInstanceId.getInstance().getToken(); //토큰을 발급받는다
        Log.d("TEST",refreshedToken);

        // 토큰값 저장
        if(refreshedToken != null && refreshedToken.length() > 0) {
            PrefUtil.setPreference(this, PrefUtil.KEY_PUSH_TOKEN, refreshedToken);
        }

    }
}

