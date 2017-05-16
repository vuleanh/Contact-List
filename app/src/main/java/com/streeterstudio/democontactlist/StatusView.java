package com.streeterstudio.democontactlist;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by leanh on 5/16/17.
 */

public class StatusView extends TextView {

    public StatusView(Context context) {
        super(context);
    }

    public void setStatus(boolean isOnline) {
        if (isOnline) {
            this.setText("Online");
        } else {
            this.setText("Offline");
        }
    }


}
