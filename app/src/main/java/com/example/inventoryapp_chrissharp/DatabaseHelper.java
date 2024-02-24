package com.example.inventoryapp_chrissharp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


// Helpful information was provided by https://www.youtube.com/playlist?list=PLSrm9z4zp4mGK0g_0_jxYGgg3os9tqRUQ

// The main class that handles the database information
public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Inventory.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_INVENTORY = "Inventory";

    public static final String TABLE_NAME_USER = "user_information";
    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_QUANTITY = "item_quantity";

    // Constructor for the database class
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    // Creating the database
    // Contains two tables (Inventoried items and User information)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryInventory = "CREATE TABLE " + TABLE_NAME_INVENTORY +
                " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " + COLUMN_QUANTITY + " INTEGER);";

        String queryUser = "CREATE TABLE " + TABLE_NAME_USER +
                " (" +  COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " + COLUMN_PASSWORD + " TEXT);";

        db.execSQL(queryInventory);
        db.execSQL(queryUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INVENTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(db);

    }

    // Method to add the items to the database, returns a message on fail/pass
    void addInventory(String itemName, Integer quantity) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, itemName);
        cv.put(COLUMN_QUANTITY, quantity);

        long result = db.insert(TABLE_NAME_INVENTORY, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to create user and add to the database
    void addUser(String userName, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_NAME, userName);
        cv.put(COLUMN_PASSWORD, userPassword);

        long result = db.insert(TABLE_NAME_USER, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Added Successfully!", Toast.LENGTH_SHORT).show();
        }


    }

    // reading all the data from the database
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME_INVENTORY;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }

    // Method to update the data in the database
    void updateData(String row_id, String itemName, Integer itemQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ITEM_NAME, itemName);
        cv.put(COLUMN_QUANTITY, itemQuantity);

        long result = db.update(TABLE_NAME_INVENTORY, cv, "_id = ?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to Update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Changed", Toast.LENGTH_SHORT).show();
        }

    }

    // deleting the data from the database
    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_INVENTORY,"_id=?", new String[]{ row_id } );

        if (result == -1){
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    // checking the user login information against what is present in the database
    public boolean checkUserLogin(String userName, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "user_name= ? AND password = ?";
        String[] whereArgs = new String[]{userName, password};

        Cursor cursor = db.query(TABLE_NAME_USER, new String[]{"user_name", "password"},
                whereClause, whereArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkInventory(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "item_name = ?";
        String[] whereArgs = new String[]{itemName};

        Cursor cursor = db.query(TABLE_NAME_INVENTORY, new String[]{"item_name"}, whereClause,
                whereArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
