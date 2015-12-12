// DatabaseConnector.java
// Provides easy connection and creation of UserContacts database.
package com.example.chloe.bceo.DBLayout;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseConnector {
   // database name
   private static final String DATABASE_NAME = "Market";
   private SQLiteDatabase database; // database object
   private DatabaseOpenHelper databaseOpenHelper; // database helper

   // public constructor for DatabaseConnector
   public DatabaseConnector(Context context) {
      // create a new DatabaseOpenHelper
       databaseOpenHelper =
         new DatabaseOpenHelper(context, DATABASE_NAME, null, 1);
   } // end DatabaseConnector constructor

   // open the database connection
   public void open() throws SQLException {
      // create or open a database for reading/writing
      database = databaseOpenHelper.getWritableDatabase();
   } // end method open

   // close the database connection
   public void close() {
       if (database != null)
           database.close(); // close the database connection
   } // end method close

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

   private class DatabaseOpenHelper extends SQLiteOpenHelper
   {
      // public constructor
      public DatabaseOpenHelper(Context context, String name,
         CursorFactory factory, int version) {
         super(context, name, factory, version);
      } // end DatabaseOpenHelper constructor

      // creates the information table when the database is created
      @Override
      public void onCreate(SQLiteDatabase db) {

          // query to create a new table named contacts
          String createProduct = "CREATE TABLE Product" +
                  "(pID integer, pName text, price float, description text, waiting integer, status text, imageID integer, groupID integer, category text);";
          db.execSQL(createProduct); // execute the query

          // query to create a new table named contacts
          String createImage = "CREATE TABLE Image" +
                  "(imageID integer, content text);";
          db.execSQL(createImage); // execute the query

          // query to create a new table named contacts
          String createUser = "CREATE TABLE User" +
                  "(uID integer, uEmail text ,uName text, password text, phone integer, message text);";
          db.execSQL(createUser); // execute the query

          // query to create a new table named contacts
          String createUserProduct = "CREATE TABLE BuyerProduct" +
                  "(uID integer, pID integer, Priority integer);";
          db.execSQL(createUserProduct); // execute the query

          // query to create a new table named contacts
          String createGroup = "CREATE TABLE SellerProduct" +
                  "(uID integer, gID text, gName text);";
          db.execSQL(createGroup); // execute the query

      } // end method onCreate

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion,
          int newVersion) {
      } // end method onUpgrade
   } // end class DatabaseOpenHelper
} // end class DatabaseConnector
