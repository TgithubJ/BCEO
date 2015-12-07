package com.example.chloe.bceo.DBLayout;

import com.example.chloe.bceo.model.User;
import com.example.chloe.bceo.model.Product;

/**
 * Created by Chloe on 11/12/15.
 */
public class Delete {

    // delete the contact specified by the given String name
    public void deleteUser(User user, DatabaseConnector databaseConnector) {
        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().delete("User", "uID" + user.getUserID(), null);
        databaseConnector.getDatabase().delete("UserGroup", "uID=" + user.getUserID(), null);
        databaseConnector.close(); // close the database
    } // end method deleteContact

    public void deleteProduct(Product product, DatabaseConnector databaseConnector) {
        databaseConnector.open(); // open the database
        databaseConnector.getDatabase().delete("Product", "pID" + product.getpID(), null);
        databaseConnector.getDatabase().delete("UserProduct", "pID=" + product.getpID(), null);
        databaseConnector.close(); // close the database
    } // end method deleteContact
}
