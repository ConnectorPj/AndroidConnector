package com.web.connector;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

/**
 * Created by BAN on 2017-06-19.
 */

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

    }
}
