package com.streeterstudio.democontactlist;

import com.streeterstudio.democontactlist.model.Contact;

/**
 * Created by vuanh on 5/19/17.
 */

public interface AddContactListener {
    void addContact(Contact contact);
    void removeContact(Contact contact);
}
