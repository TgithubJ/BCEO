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

    public void createProduct(Product product,DatabaseConnector databaseConnector) {
        ContentValues info = new ContentValues();

        info.put("pID", product.getpID());
        info.put("pName", product.getpName());
        info.put("price", product.getpPrice());
        info.put("description", product.getpDescription());
        info.put("waiting", product.getpWaiting());
        info.put("status", product.getStatus());
        info.put("imageID", product.getImageId());
        info.put("groupID", product.getGroupId());
        info.put("category", product.getCategory());

        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().insert("Product", null, info);
        databaseConnector.close(); // close the database
    } // end method insertContact

    public void createImage(int pID, String content, DatabaseConnector databaseConnector) {
        ContentValues info = new ContentValues();

        info.put("imageID", pID);
        info.put("content", content);

        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().insert("Image", null, info);
        databaseConnector.close(); // close the database
    } // end method insertContact
}
