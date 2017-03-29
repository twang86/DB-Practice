package com.questdo.twang.grocerylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twang on 3/29/17.
 */

public class ShoppingSQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "SHOPPING_DB";
    public static final String SHOPPING_TABLE_NAME = "SHOPPING_LIST";

    public static final String COL_ID = "_id",
            COL_ITEM_NAME = "ITEM_NAME",
            COL_DESCRIPTION = "DESCRIPTION",
            COL_PRICE = "PRICE",
            COL_TYPE = "TYPE";

    public static final String CREATE_SHOPPING_TABLE =
            "CREATE TABLE " + SHOPPING_TABLE_NAME +
                    "(" +
                    COL_ID + " INTEGER PRIMARY KEY, " +
                    COL_ITEM_NAME + " TEXT, " +
                    COL_DESCRIPTION + " TEXT, " +
                    COL_PRICE + " TEXT, " +
                    COL_TYPE + " TEXT )";


    public static ShoppingSQLiteHelper sInstance;

    private ShoppingSQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ShoppingSQLiteHelper getInstance(Context context){
        if (sInstance == null){
            sInstance = new ShoppingSQLiteHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOPPING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHOPPING_TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getShoppingDb(){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(SHOPPING_TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public List<Grocery> getShoppingList(){
        Cursor cursor = getShoppingDb();
        List<Grocery> groceries = new ArrayList<>();
        if (cursor.moveToLast()){
            do{
                groceries.add(new Grocery(
                                cursor.getLong(cursor.getColumnIndex(COL_ID)),
                                cursor.getString(cursor.getColumnIndex(COL_ITEM_NAME)),
                                cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)),
                                cursor.getString(cursor.getColumnIndex(COL_PRICE)),
                                cursor.getString(cursor.getColumnIndex(COL_TYPE))
                        )
                );
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return groceries;
    }

    public int deleteItem(Grocery grocery){
        SQLiteDatabase db = getWritableDatabase();

        int rowsAffected = db.delete(SHOPPING_TABLE_NAME,
                COL_ID + "= ?",
                new String[]{String.valueOf(grocery.getID())});

        db.close();

        return rowsAffected;

    }

    public long addItem(String name, String description, String price, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ITEM_NAME, name);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_PRICE, price);
        values.put(COL_TYPE, type);

        long rowId = db.insert(SHOPPING_TABLE_NAME, null, values);
        db.close();

        return rowId;
    }

    public void editItem(Grocery edited){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ITEM_NAME, edited.getName());
        values.put(COL_DESCRIPTION, edited.getDescription());
        values.put(COL_PRICE, edited.getPrice());
        values.put(COL_TYPE, edited.getType());

        String[] selectionArgs = new String[] {String.valueOf(edited.getID())};

        db.update(SHOPPING_TABLE_NAME,
                values,
                COL_ID+" = ? ",
                new String[] {String.valueOf(edited.getID())}
        );

        db.close();

    }



}

