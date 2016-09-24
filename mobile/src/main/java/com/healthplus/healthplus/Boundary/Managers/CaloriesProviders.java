package com.healthplus.healthplus.Boundary.Managers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.HashMap;

/**
 * Created by VSRK on 9/24/2016.
 */

public class CaloriesProviders extends ContentProvider {

    public static final String PROVIDER_NAME = "com.healthplus.healthplus.plus";
    public static final String URL = "content://" + PROVIDER_NAME + "/diets";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String CALORIES = "calories";
    public static final String TIME = "time";
    public static final String _ID = "_id";

    private static HashMap<String, String> REVIEWS_PROJECTION_MAP;

    static final int DIETS = 1;
    static final int DIETS_ID = 2;

    static final UriMatcher uriMatcher;

    static{

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "diets", DIETS);
        uriMatcher.addURI(PROVIDER_NAME, "diets/#", DIETS_ID);
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "Diets";
    static final String DIETS_TABLE_NAME = "diets";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + DIETS_TABLE_NAME +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "calories TEXT NOT NULL, " +
                    "time TEXT NOT NULL);";



    private class DataBaseHelper extends SQLiteOpenHelper
    {

        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  DIETS_TABLE_NAME);
            onCreate(db);
        }



    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        CaloriesProviders.DataBaseHelper dbHelper = new CaloriesProviders.DataBaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DIETS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {

            case DIETS:
                qb.setProjectionMap(REVIEWS_PROJECTION_MAP);
                break;

            case DIETS_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){

            sortOrder = _ID;
        }

        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){

            case DIETS:
                return "vnd.android.cursor.dir/vnd.example.students1";


            case DIETS_ID:
                return "vnd.android.cursor.item/vnd.example.students2";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(	DIETS_TABLE_NAME, "", values);
        Uri _uri = null;
        /**
         * If record is added successfully
         */

        if (rowID > 0) {
            _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);

        }

        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}