package com.web.connector.drawerItems;

import android.content.Intent;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.web.connector.R;

/**
 * Created by HongUi on 2017-06-20.
 */

public class DeveloperParallaxFragment extends Fragment {

    private DeveloperParallaxAdapter mCatsAdapter;
    private Intent intent;
    private Uri uri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_madeby, container, false);
        final ImageView image = (ImageView) v.findViewById(R.id.image);

        image.setImageResource(getArguments().getInt("image"));
        image.post(new Runnable() {
            @Override
            public void run() {
                Matrix matrix = new Matrix();
                matrix.reset();

                float wv = image.getWidth();
                float hv = image.getHeight();

                float wi = image.getDrawable().getIntrinsicWidth();
                float hi = image.getDrawable().getIntrinsicHeight();

                float width = wv;
                float height = hv;

                if (wi / wv > hi / hv) {
                    matrix.setScale(hv / hi, hv / hi);
                    width = wi * hv / hi;
                } else {
                    matrix.setScale(wv / wi, wv / wi);
                    height = hi * wv / wi;
                }

                matrix.preTranslate((wv - width) / 2, (hv - height) / 2);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setImageMatrix(matrix);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "길게 누를경우 \n해당 개발자에게 메일을 보낼 수 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String emailAddress = "mailto:" + getArguments().getString("email");
                uri = Uri.parse(emailAddress);
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                getActivity().startActivity(intent);
                return true;
            }
        });



        TextView text = (TextView)v.findViewById(R.id.name);
        text.setText(getArguments().getString("name"));
        return v;
    }




/*        TextView more = (TextView)v.findViewById(R.id.more);

        more.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mCatsAdapter != null) {
                    mCatsAdapter.remove(DeveloperParallaxFragment.this);
                    mCatsAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCatsAdapter != null) {
                    int select = (int) (Math.random() * 4);

                    int[] resD = {R.drawable.leebyongho, R.drawable.profile2, R.drawable.parkdockhwan, R.drawable.junghongui};
                    String[] resS = {"Lee", "UnKnown", "Park", "JUNG"};

                    DeveloperParallaxFragment newP = new DeveloperParallaxFragment();
                    Bundle b = new Bundle();
                    b.putInt("image", resD[select]);
                    b.putString("name", resS[select]);
                    newP.setArguments(b);
                    mCatsAdapter.add(newP);
                }
            }
        });
        return v;
    }*/

    public void setAdapter(DeveloperParallaxAdapter catsAdapter) {
        mCatsAdapter = catsAdapter;
    }
}