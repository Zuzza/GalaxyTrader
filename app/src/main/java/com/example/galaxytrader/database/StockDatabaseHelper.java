package com.example.galaxytrader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.galaxytrader.models.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = StockDatabaseHelper.class.getName();

    private static StockDatabaseHelper mInstance = null;
    private Context context;

    //database constants
    private static final String DATABASE_NAME = "stock.db";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "stock";

    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_MAX_PRICE = "MAX_PRICE";
    private static final String COL_MIN_PRICE = "MIN_PRICE";
    private static final String COL_FILE_NAME = "FILE_NAME";

    private static final String CREATE_TABLE_ST = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT, " +
            COL_MAX_PRICE + " INTEGER, " +
            COL_MIN_PRICE + " INTEGER, " +
            COL_FILE_NAME + " TEXT )";

    private static final String DROP_TABLE_ST = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String GET_ALL_ST = "SELECT * FROM " + TABLE_NAME;
    private static final String GET_STOCK_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + "= ?";

    public static synchronized StockDatabaseHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new StockDatabaseHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    private StockDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_TABLE_ST);
        onCreate(sqLiteDatabase);
    }

    public Long insert(String name, Integer maxPrice, Integer minPrice, String fileName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_MAX_PRICE, maxPrice);
        contentValues.put(COL_MIN_PRICE, minPrice);
        contentValues.put(COL_FILE_NAME, fileName);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }
    public Long addStock(Stock stock) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, stock.getName());
        contentValues.put(COL_MAX_PRICE, stock.getMaxPrice());
        contentValues.put(COL_MIN_PRICE, stock.getMinPrice());
        contentValues.put(COL_FILE_NAME, stock.getImageFileName());

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result;
    }

    private Cursor getAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(GET_ALL_ST, null);
    }


    public boolean update(Long id, String name, Integer maxPrice, Integer minPrice, String fileName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_MAX_PRICE, maxPrice);
        contentValues.put(COL_MIN_PRICE, minPrice);
        contentValues.put(COL_FILE_NAME, fileName);

        int numOfRowsUpdated = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id.toString()});
        db.close();
        return numOfRowsUpdated == 1; //if your query is going to update more than 1 record (this is not the case) then the condition will be numRowsUpdated > 0
    }

    public boolean delete(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int numOfRowsDeleted = db.delete(TABLE_NAME, "ID = ?", new String[]{id.toString()});
        db.close();
        return numOfRowsDeleted == 1;
    }

    public List<Stock> getStocks() {
        List<Stock> stocks = new ArrayList<>();
        Cursor cursor = getAll();

        if(cursor.getCount() > 0) {
            Stock stock;
            while (cursor.moveToNext()) {
                Long id = cursor.getLong(0);
                String name = cursor.getString(1);
                Integer maxPrice = cursor.getInt(2);
                Integer minPrice = cursor.getInt(3);
                String imageFileName = cursor.getString(4);

                stock = new Stock(id, name, maxPrice, minPrice, imageFileName);
                stocks.add(stock);
            }
        }
        cursor.close();
        return stocks;
    }

    public Stock getStock(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Stock stock = null;
        Cursor cursor = db.rawQuery(GET_STOCK_BY_ID, new String[]{id.toString()});

        if(cursor.getCount() > 0 ){
            while (cursor.moveToNext()){
                String name = cursor.getString(1);
                Integer maxPrice = cursor.getInt(2);
                Integer minPrice = cursor.getInt(3);
                String imageFileName = cursor.getString(4);

                stock = new Stock(id, name, maxPrice, minPrice, imageFileName);
            }
        }
        cursor.close();
        return stock;
    }

    public Long getStockSourceCount(){
        return DatabaseUtils.queryNumEntries(this.getReadableDatabase(), TABLE_NAME);
    }

}
