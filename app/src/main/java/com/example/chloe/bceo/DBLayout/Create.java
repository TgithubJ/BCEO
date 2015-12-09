package com.example.chloe.bceo.DBLayout;

import android.content.ContentValues;

import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;

/**
 * Created by Chloe on 11/12/15.
 */
public class Create {

    // inserts a new contact in the database
    public void createBuyer(User user, DatabaseConnector databaseConnector) {
        ContentValues info = new ContentValues();
        ContentValues info2 = new ContentValues();

        info.put("uID", user.getUserID());
        info.put("uName", user.getUserName());
        info.put("Password", user.getPassword());
        info.put("Phone", user.getPhoneNum());

        info2.put("uID", user.getUserID());
        info2.put("gID", user.getGroupID());

        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().insert("User", null, info);
        databaseConnector.getDatabase().insert("UserGroup", null, info2);
        databaseConnector.close(); // close the database
    } // end method insertContact

    public void createProduct(User user, Product product,DatabaseConnector databaseConnector) {
        ContentValues info = new ContentValues();
        ContentValues info2 = new ContentValues();
//
//        info.put("pID", product.getpID());
//        info.put("pName", product.getpName());
//        info.put("Price", product.getpPrice());
//        info.put("Description", product.getpDescription());
//        info.put("Waiting", product.getpWaiting());
//        info.put("pImage", product.getpImage());
//
//        info2.put("uID", user.getUserID());
//        info2.put("pID", product.getpID());
//        info2.put("Priority", user.getPriority());

        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().insert("Product2", null, info);
        databaseConnector.getDatabase().insert("UserProduct", null, info2);
        databaseConnector.close(); // close the database
    } // end method insertContact
}
