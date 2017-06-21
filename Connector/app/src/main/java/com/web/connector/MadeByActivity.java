package com.web.connector;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.web.connector.drawerItems.DeveloperParallaxAdapter;
import com.web.connector.drawerItems.DeveloperParallaxFragment;
import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;

public class MadeByActivity extends AppCompatActivity {

    ViewPager mPager;
    DeveloperParallaxAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_made_by);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setBackgroundColor(0xFF000000);

        ParallaxPagerTransformer pt = new ParallaxPagerTransformer((R.id.image));
        pt.setBorder(20);
        //pt.setSpeed(0.2f);
        mPager.setPageTransformer(false, pt);

        mAdapter = new DeveloperParallaxAdapter(getSupportFragmentManager());
        mAdapter.setPager(mPager); //only for this transformer

        Bundle deokhwhan = new Bundle();
        deokhwhan.putInt("image", R.drawable.parkdockhwan);
        deokhwhan.putString("name", "박덕환");
        deokhwhan.putString("email","pdh8788@naver.com");
        DeveloperParallaxFragment fDeokhwan = new DeveloperParallaxFragment();
        fDeokhwan.setArguments(deokhwhan);


        Bundle yoonsik = new Bundle();
        yoonsik.putInt("image", R.drawable.jiyoonsik);
        yoonsik.putString("name", "지윤식");
        yoonsik.putString("email","njyssr@hanmail.net");
        DeveloperParallaxFragment fYoonsik = new DeveloperParallaxFragment();
        fYoonsik.setArguments(yoonsik);

        Bundle byongho = new Bundle();
        byongho.putInt("image", R.drawable.leebyongho);
        byongho.putString("name", "이병호");
        byongho.putString("email","qudgh999@naver.com");
        DeveloperParallaxFragment fByongho = new DeveloperParallaxFragment();
        fByongho.setArguments(byongho);


        Bundle boram = new Bundle();
        boram.putInt("image", R.drawable.joboram);
        boram.putString("name", "조보람");
        boram.putString("email","boram7062@naver.com");
        DeveloperParallaxFragment fBooram = new DeveloperParallaxFragment();
        fBooram.setArguments(boram);

        Bundle hongui = new Bundle();
        hongui.putInt("image", R.drawable.junghongui);
        hongui.putString("name", "정홍의");
        hongui.putString("email", "1993jhy@mgmail.com");
        DeveloperParallaxFragment fHongui = new DeveloperParallaxFragment();
        fHongui.setArguments(hongui);

        Bundle joohee = new Bundle();
        joohee.putInt("image", R.drawable.yoonjuhee);
        joohee.putString("name", "윤주희");
        joohee.putString("email", "jh54311@hanmail.net");
        DeveloperParallaxFragment fJoohee = new DeveloperParallaxFragment();
        fJoohee.setArguments(joohee);

        Bundle sangsu = new Bundle();
        sangsu.putInt("image", R.drawable.bansangui);
        sangsu.putString("name", "반상수");
        sangsu.putString("email", "asdd3525@nate.com");
        DeveloperParallaxFragment fSangsu = new DeveloperParallaxFragment();
        fSangsu.setArguments(sangsu);

        mAdapter.add(fDeokhwan);
        mAdapter.add(fYoonsik);
        mAdapter.add(fByongho);
        mAdapter.add(fBooram);
        mAdapter.add(fHongui);
        mAdapter.add(fJoohee);
        mAdapter.add(fSangsu);
        mPager.setAdapter(mAdapter);

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

}
