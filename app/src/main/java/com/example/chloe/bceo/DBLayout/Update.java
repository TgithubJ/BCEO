package com.example.chloe.bceo.DBLayout;

import android.content.ContentValues;

import com.example.chloe.bceo.model.Product;
import com.example.chloe.bceo.model.User;

/**
 * Created by Chloe on 11/12/15.
 */
public class Update {

    public void updateUser(User user, ContentValues newInfo ,DatabaseConnector databaseConnector) {
        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().update("User", newInfo, "uID=" + user.getUserID(), null);
        databaseConnector.close(); // close the database
    } // end method updateContact

    public void updateProduct(Product product, ContentValues newInfo ,DatabaseConnector databaseConnector) {
        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().update("User", newInfo, "pID=" + product.getpID(), null);
        databaseConnector.close(); // close the database
    } // end method updateContact
}
