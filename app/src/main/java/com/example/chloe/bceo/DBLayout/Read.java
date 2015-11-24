package com.example.chloe.bceo.DBLayout;

import android.database.Cursor;

import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;

/**
 * Created by Chloe on 11/12/15.
 */
public class Read {

    // get a Cursor containing all information about the contact specified
    // by the given id
    public Cursor getOneUser(User user, DatabaseConnector databaseConnector) {
        return databaseConnector.getDatabase().query(
                "User", null, "uID" + user.getUserID(), null, null, null, null);
    } // end method getOnContact
    // return a Cursor with all contact information in the database

    public Cursor getAllUser(DatabaseConnector databaseConnector) {
        return databaseConnector.getDatabase().query("User", null,
                null, null, null, null, null);
    } // end method getAllContacts

    // get a Cursor containing all information about the contact specified
    // by the given id
    public Cursor getOneProduct(Product product, DatabaseConnector databaseConnector) {
        return databaseConnector.getDatabase().query(
                "Product", null, "pID" + product.getpID(), null, null, null, null);
    } // end method getOnContact
    // return a Cursor with all contact information in the database

    public Cursor getAllProduct(DatabaseConnector databaseConnector) {
        return databaseConnector.getDatabase().query("Product", null,
                null, null, null, null, null);
    } // end method getAllContacts
}
