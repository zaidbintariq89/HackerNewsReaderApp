package com.android.hackernewsreaderapp.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Html;
import android.util.AttributeSet;

/**
 * Created by zaidbintariq on 17/06/2017.
 */

public class HTMLTextView extends AppCompatTextView {

    public HTMLTextView(Context context) {
        super(context);
        init();
    }

    public HTMLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HTMLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            setText(Html.fromHtml(getText().toString(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            setText(Html.fromHtml(getText().toString()));
        }
    }
}
