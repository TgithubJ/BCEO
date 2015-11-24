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
   private static final String DATABASE_NAME = "StudentInformation";
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
         String createUser = "CREATE TABLE User" +
                 "(uID text,  uName text, Password text, Phone integer, Message text);";
         db.execSQL(createUser); // execute the query

          // query to create a new table named contacts
          String createGroup = "CREATE TABLE Group" +
                  "(uID text, gID text, gName text);";
          db.execSQL(createGroup); // execute the query

          // query to create a new table named contacts
          String createProduct = "CREATE TABLE Product" +
                  "(pID text, pName text, Price float, Description text, Waiting integer);";
          db.execSQL(createProduct); // execute the query

          // query to create a new table named contacts
          String createUserProduct = "CREATE TABLE UserProduct" +
                  "(uID text, pID text, Priority integer);";
          db.execSQL(createUserProduct); // execute the query


      } // end method onCreate

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion,
          int newVersion) {
      } // end method onUpgrade
   } // end class DatabaseOpenHelper
} // end class DatabaseConnector
