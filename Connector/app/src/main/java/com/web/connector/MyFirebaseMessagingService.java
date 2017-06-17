package com.web.connector;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by 정홍의 on 2017-05-24.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();

        Log.e("notitest", "메세지서비스");
        if(data != null && data.size() > 0){
            // json 파싱 처리 한다
            Gson gson = new Gson();
            // data class 를 --> JSON 문자열 변환
            String pushSendStr = gson.toJson(data);

            Log.d("TEST", pushSendStr);

            // JSON --> Bean 변환
            PushMsgBean.Data pushMsgBean = gson.fromJson(pushSendStr, PushMsgBean.Data.class);

            noti(pushMsgBean.getTitle(), pushMsgBean.getMessage());

            return;
        }
//        noti("noTitle", "noContent");

        Log.e("TEST", data.toString());
        //푸시 메시지가 서버로 부터 왔을때 콜백 받는 메서드
        //서버에서 보내준 json형식을 다시 파싱한후 setTicker, setContentText 부분으로 넣어줘~

    };

    public void noti(String title, String content){

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        NotificationManager notiMng = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notiMng.notify((int)System.currentTimeMillis(), notiBuilder.build());
    }
}

