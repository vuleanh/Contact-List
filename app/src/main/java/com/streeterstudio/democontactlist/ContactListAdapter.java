package com.streeterstudio.democontactlist;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.streeterstudio.democontactlist.model.Contact;

import java.util.List;

import static android.R.attr.x;

/**
 * Created by leanh on 5/16/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private AddContactListener addContactListener;

    private List<Contact> data;
    private Context context;

    public ContactListAdapter(Context context, List<Contact> data, AddContactListener addContactListener) {
        this.context = context;
        this.data = data;
        this.addContactListener = addContactListener;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contact contact = data.get(position);
        holder.txtName.setText(contact.getName());
        if (contact.getImageUri() != null){
            holder.imvAvatar.setImageURI(Uri.parse(contact.getImageUri()));
        }else{
            holder.imvAvatar.setImageResource(R.drawable.ic_person);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addContactListener.addContact(contact);
                }else {
                    addContactListener.removeContact(contact);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private ImageView imvAvatar;
        private TextView txtName;
        private StatusView status;

        public ContactViewHolder(View itemView) {
            super(itemView);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox_contact);
            imvAvatar = (ImageView) itemView.findViewById(R.id.imv_contact_avatar);
            txtName = (TextView) itemView.findViewById(R.id.txt_contact_name);
            //status = (StatusView) itemView.findViewById(R.id.txt_contact_status);
        }
    }
}
