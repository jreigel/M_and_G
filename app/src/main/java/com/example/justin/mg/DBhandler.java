package com.example.justin.mg;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 1/24/2016.
 */
public class DBhandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "userDB.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PURCHASES = "purchases";
    private static final String TABLE_TRANSACTIONS = "transactions";

    private static final String ASURITE_ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
//    TODO: verify that balance and purchase history should be strings here for values.put()
    private static final String BALANCE = "balance";

    //    TODO: Verify that email and password should be in this db.
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static final String PURCHASES_ID = "pid";
    public static final String PURCHASES_BUSINESS = "purchasesBusiness";
    public static final String PURCHASES_DATE = "purchasesDate";
    public static final String PURCHASES_AMOUNT = "purchasesAmount";
    public static final String TRANSACTION_NAME = "transactionName";
    public static final String TRANSACTION_AMOUNT = "transactionAmount";
    public static final String TRANSACTION_DECISION = "transactionDecision";

    private SQLiteDatabase db;

    public DBhandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + ASURITE_ID + " INTEGER PRIMARY KEY,"
                + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"
                + BALANCE + " REAL,"
                + EMAIL + " TEXT,"
                + PASSWORD + " TEXT" + ");";
        String CREATE_PURCHASES_TABLE = "CREATE TABLE " + TABLE_PURCHASES + "("
                + PURCHASES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ASURITE_ID + " INTEGER,"
                + PURCHASES_BUSINESS + " TEXT,"
                + PURCHASES_AMOUNT + " REAL,"
                + PURCHASES_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP" + ");";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_PURCHASES_TABLE);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_USERS;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + TABLE_PURCHASES;
        db.execSQL(query);
        onCreate(db);
    }

//    TODO: change purchase history to 2d array!
//    TODO: Or make a different db for the purchase history?

    public void insertUser(User u) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ASURITE_ID, u.getId());
        values.put(FIRST_NAME, u.getFirst_name());
        values.put(LAST_NAME, u.getLast_name());
        values.put(BALANCE, u.getBalance());
        values.put(EMAIL, u.getEmail());
        values.put(PASSWORD, u.getPassword());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    public void insertPurchase(Purchase p){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ASURITE_ID, p.getId());
        values.put(PURCHASES_BUSINESS, p.getBusiness());
        values.put(PURCHASES_AMOUNT, p.getAmount());
        values.put(PURCHASES_DATE, p.getDate());
        db.insert(TABLE_PURCHASES, null, values);
        db.close();
    }

    public List<List<String>> getPurchases(int student_id){
//        TODO: Change to list? or move code in purchaseList
        db = this.getReadableDatabase();
        List<List<String>> listOfLists = new ArrayList<>();
        System.out.println("Current id: " + student_id);
        String query = "select * from " + TABLE_PURCHASES + " where id = " + student_id;
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ArrayList<String> list = new ArrayList<>();
//            Primary key and Student id are the first 2 cols
//            TODO: REFACTOR, This way feels extremely sloppy.
            for(int i = 2; i < cursor.getColumnCount(); i++){
                list.add(cursor.getString(i));
            }
            listOfLists.add(list);
            cursor.moveToNext();
        }
        cursor.close();
        System.out.println("purchases: " + listOfLists);
        return listOfLists;
    }

    public String searchPass(String mEmail) {
        db = this.getReadableDatabase();
        String query = "select email, password from " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    System.out.println(cursor.getString(i));
                }

                if(a.equals(mEmail)){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return b;
    }
    public void deductBalance(int currentId, float deducted_amount){
        db = this.getReadableDatabase();
        String query = "select balance from " + TABLE_USERS + " where id = " + currentId;
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        float current_amount = cursor.getFloat(0);
        current_amount -= deducted_amount;
        cursor.close();
        query = "update " + TABLE_USERS + " set " + BALANCE + "=" + current_amount + " where id = " + currentId;
        db.execSQL(query);
        db.close();
    }
    public int getUserId(String currentEmail){
        db = this.getReadableDatabase();
//        todo: find a way to use query to find where email = currentEmail.
//        Current query using where email=currentEmail will give syntax error due to @ symbol
        String query = "select email, id from " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);

        String a;
        int b;
        b = 0;
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0);

                if(a.equals(currentEmail)){
                    b = cursor.getInt(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return b;
    }
    public String getUserEmail(int currentId){
        db = this.getReadableDatabase();
        String query = "select id, email from " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);

        int a;
        String b = null;
        if(cursor.moveToFirst()){
            do{
                a = cursor.getInt(0);

                if(a == currentId){
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return b;
    }
    public String[] userDetails(int currentId){
        db = this.getReadableDatabase();
        System.out.println("Current id: " + currentId);
        String query = "select firstName, lastName, balance from " + TABLE_USERS + " where id = " + currentId;
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        String[] a = {cursor.getString(0), cursor.getString(1), cursor.getString(2)};
        cursor.close();
        return a;
    }
}
