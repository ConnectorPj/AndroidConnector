package com.web.connector;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.web.connector.bean.ProfileBean;
import com.web.connector.utils.HttpClient;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private static final String CONNECTOR_SITE = "http://jhu1993.cafe24.com";

    private int pickMenu = 0;

    private Uri mImageCaptureUri;
    private ImageView userProfilePhoto;
    private ImageView backgroundImage;

    private String absoultePath;
    private ProfileBean profileBean;

    private Handler handler = new Handler();

    private TextView userProfileName;
    private TextView userProfileContent;
    private TextView userProfileGender;
    private TextView userProfileCellphone;

    /**
     * Parameter type of doInBackground :Map
     * Progress : Integer
     * return type of doInBackground : String
     */
    public class NetworkTask extends AsyncTask<Map,Integer,String> {

        private CustomProgressDialog customProgressDialog = new CustomProgressDialog(ProfileActivity.this);

        /**
         * doInBackground 실행되기 이전에 동작한다.
         */
        @Override protected void onPreExecute() {
            customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            customProgressDialog.show(); // Dialog 보여주기
            super.onPreExecute();
        }

        /**
         * 본 작업을 쓰레드로 처리해준다.
         * ... 은 가변 배열 또는 가변 파라미터라고 부른다.
         * a, b, c 이런식으로 보내도 되고 배열로 보내도 된다.
         * @param maps
         * @return
         */
        @Override
        protected String doInBackground(Map... maps) {

            // HTTP 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("POST",
                    CONNECTOR_SITE + "/androidProfile.do");

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
            profileBean = gson.fromJson(s, ProfileBean.class);
            final String url = CONNECTOR_SITE + profileBean.getPhotoFileName();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    userProfileName.setText(profileBean.getUserName());
                    userProfileContent.setText(profileBean.getUserProfile());
                    userProfileGender.setText(profileBean.getUserGender());
                    userProfileCellphone.setText(profileBean.getUserCellphone());

                    Glide.with(ProfileActivity.this).load(url).into(userProfilePhoto);

                    customProgressDialog.dismiss(); // Dialog 없애기

                }
            }); //end of Handler
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkTask networkTask = new NetworkTask();
        Map params = new HashMap();
        //로그인정보를 확인 후 그 아이디를 넘겨줘야한다.
        String userId = "dlqudgh@naver.com";

        params.put("userId",userId);

        networkTask.execute(params);

        setContentView(R.layout.activity_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userProfileName = (TextView)findViewById(R.id.user_profile_name);
        userProfileContent = (TextView)findViewById(R.id.user_profile_content);
        userProfileGender = (TextView)findViewById(R.id.user_profile_gender);
        userProfileCellphone = (TextView)findViewById(R.id.user_profile_cellphone);

        userProfilePhoto = (ImageView)findViewById(R.id.user_profile_photo);
        backgroundImage = (ImageView)findViewById(R.id.background_image);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myMenuInflater = getMenuInflater();
        myMenuInflater.inflate(R.menu.profile_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user:
                pickMenu = R.id.menu_user;
                pictureSelected();
                return true;
//            case R.id.menu_background:
//                pickMenu = R.id.menu_background;
//                pictureSelected();
//                return true;
            case R.id.menu_name:

                return true;
            case R.id.menu_status:

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void pictureSelected() {
        DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };

        DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(this)
                .setTitle("이미지 선택")
                .setNeutralButton("취소", cancelListener)
                .setNegativeButton("사진촬영", cameraListener)
                .setPositiveButton("앨범선택", albumListener)
                .show();
    }

    /**
     * 카메라에서 사진 촬영
     */
    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }


    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel",mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_IMAGE:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel/"+System.currentTimeMillis()+".jpg";

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP

                    switch (pickMenu) {
                        case R.id.menu_user:
                            userProfilePhoto.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                            break;
//                        case R.id.menu_background:
//                            backgroundImage.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
//                            break;
                    }

                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;

                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
            }
        }

    }



    /*
    * Bitmap을 저장하는 부분
    */
    private void storeCropImage(Bitmap bitmap, String filePath) {
        // SmartWheel 폴더를 생성하여 이미지를 저장하는 방식이다.
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel = new File(dirPath);

        if(!directory_SmartWheel.exists()) // SmartWheel 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
            directory_SmartWheel.mkdirs();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
