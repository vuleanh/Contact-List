package com.streeterstudio.democontactlist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.streeterstudio.democontactlist.R;
import com.streeterstudio.democontactlist.activity.ContactListActivity;

public class MainActivity extends AppCompatActivity {

    public static final int GET_CONTACT_REQUEST_CODE = 101;

    private Button btnOpen;
    private TextView txtContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpen = (Button) findViewById(R.id.btn_open_contact_list);
        txtContact = (TextView) findViewById(R.id.txt_selected_contact);

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactList();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_CONTACT_REQUEST_CODE) {

        }
    }

    private void openContactList() {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivityForResult(intent, GET_CONTACT_REQUEST_CODE);
    }

}
