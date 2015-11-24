// DatabaseConnector.java
// Provides easy connection and creation of UserContacts database.
package com.example.chloe.bceo.service.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

   // inserts a new contact in the database
    public void insertInfo() {
        ContentValues info = new ContentValues();

        open(); // open the database
        database.insert("information", null, info);
        close(); // close the database
    } // end method insertContact

    public void updateInfo(long id, ContentValues editContact) {
        open(); // open the database
        database.update("information", editContact, "_id=" + id, null);
        close(); // close the database
    } // end method updateContact

   // get a Cursor containing all information about the contact specified
   // by the given id
    public Cursor getOneInfo(long id) {
        return database.query(
               "information", null, "_id=" + id, null, null, null, null);
    } // end method getOnContact
    // return a Cursor with all contact information in the database

    public Cursor getAllInfo() {
        return database.query("information", null,
                null, null, null, null, null);
    } // end method getAllContacts

   // delete the contact specified by the given String name
   public void deleteInfo(long id) {
      open(); // open the database
      database.delete("information", "_id=" + id, null);
      close(); // close the database
   } // end method deleteContact

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
         String createQuery = "CREATE TABLE information" +
                 "(_id integer primary key autoincrement,sID integer, score1 integer, " +
                 "score2 integer, score3 integer, score4 integer, score5 integer);";

         db.execSQL(createQuery); // execute the query
      } // end method onCreate

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion,
          int newVersion) {
      } // end method onUpgrade
   } // end class DatabaseOpenHelper
} // end class DatabaseConnector
