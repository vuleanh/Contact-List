package com.streeterstudio.democontactlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by leanh on 5/16/17.
 */

public class ContactListAdapter {


    class ContactViewHolder extends RecyclerView.ViewHolder {

        private ImageView imvAvatar;
        private TextView txtName;
        private StatusView status;

        public ContactViewHolder(View itemView) {
            super(itemView);
            imvAvatar = (ImageView) itemView.findViewById(R.id.imv_contact_avatar);
            txtName = (TextView) itemView.findViewById(R.id.txt_contact_name);
            status = (StatusView) itemView.findViewById(R.id.txt_contact_status);
        }
    }
}
