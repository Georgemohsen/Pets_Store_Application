package com.example.petsapp.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.petsapp.data.PetContract.PetEntry;

import com.example.petsapp.data.PetContract;

public class PetDbHelper extends SQLiteOpenHelper {

    // Database Name and version as constants
    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1 ;

    // Constructor
    public PetDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    // Called when database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE "+ PetEntry.TABLE_NAME + "(" + PetEntry._ID + " INTEGER PRIMARY KEY, "
                                        + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                                        + PetEntry.COLUMN_PET_BREED + " TEXT, "
                                        + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                                        + PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
