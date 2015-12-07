package com.example.chloe.bceo.DBLayout;

import android.database.Cursor;

/**
 * Created by Chloe on 11/12/15.
 */
public class Read {

    // get a Cursor containing all information about the contact specified
    // by the given id
    public Cursor getOneUser(int userID, DatabaseConnector databaseConnector) {
        databaseConnector.open();
        return databaseConnector.getDatabase().query(
                "User", null, "uID=" + userID, null, null, null, null);
    } // end method getOnContact
    // return a Cursor with all contact information in the database

    public Cursor getAllUser(DatabaseConnector databaseConnector) {
        databaseConnector.open();
        return databaseConnector.getDatabase().query("User", null,
                null, null, null, null, null);
    } // end method getAllContacts

    // get a Cursor containing all information about the contact specified
    // by the given id
    public Cursor getOneProduct(int productID, DatabaseConnector databaseConnector) {
        databaseConnector.open();
        return databaseConnector.getDatabase().query(
                "Product2", null, "pID=" + productID, null, null, null, null);
    } // end method getOnContact
    // return a Cursor with all contact information in the database

    public Cursor getAllProduct(DatabaseConnector databaseConnector) {
        databaseConnector.open();
        return databaseConnector.getDatabase().query("Product2", null,
                null, null, null, null, null);
    } // end method getAllContacts
}
