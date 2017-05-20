package com.streeterstudio.democontactlist.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.streeterstudio.democontactlist.AddContactListener;
import com.streeterstudio.democontactlist.ContactListAdapter;
import com.streeterstudio.democontactlist.R;
import com.streeterstudio.democontactlist.model.Contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactListActivity extends AppCompatActivity implements AddContactListener {

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ContactListAdapter adapter;
    private LinearLayout selectedlayout;

    private HashMap<String, Contact> selectedContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_contact_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showContacts();

        selectedlayout = (LinearLayout) findViewById(R.id.layout_selected_contact);
    }


    private void addContactView(Contact contact) {
        ImageView imageView = new ImageView(this);
        imageView.setTag(contact.getId());
        if (contact.getImageUri() != null) {
            imageView.setImageURI(Uri.parse(contact.getImageUri()));
        } else {
            imageView.setImageResource(R.drawable.ic_person);
        }
        selectedlayout.addView(imageView);
    }


    private void initToolbar() {
        toolbar.setTitle("Select Contacts");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contact_list, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            adapter = new ContactListAdapter(this, fetchContacts(), this);
            recyclerView.setAdapter(adapter);
        }
    }

    private Cursor cursor;
    private int counter;

    private List<Contact> fetchContacts() {

        List<Contact> contacts = new ArrayList<>();

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String IMAGE_URI = ContactsContract.Contacts.PHOTO_URI;


        ContentResolver contentResolver = getContentResolver();

        cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            counter = 0;
            while (cursor.moveToNext()) {
                Contact contact = new Contact();
                contact.setId(cursor.getString(cursor.getColumnIndex(_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(DISLAY_NAME)));
                contact.setImageUri(cursor.getString(cursor.getColumnIndex(IMAGE_URI)));
                contacts.add(contact);
            }

        }
        return contacts;
    }

    @Override
    public void addContact(Contact contact) {
        if (selectedContacts == null) {
            selectedContacts = new HashMap<>();
        }
        if (!selectedContacts.containsKey(contact.getId())) {
            selectedContacts.put(contact.getId(), contact);
            addContactView(contact);
        }
    }

    @Override
    public void removeContact(Contact contact) {
        if (selectedContacts.containsKey(contact.getId())) {
            selectedlayout.removeView(selectedlayout.findViewWithTag(contact.getId()));
            selectedContacts.remove(contact.getId());
        }
    }
}
