package com.example.petsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;

import com.example.petsapp.data.PetDbHelper;
import com.example.petsapp.data.PetContract.PetEntry;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new PetDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void insertPet(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        values.put(PetEntry.COLUMN_PET_WEIGHT, 7);
        long returnedValue = db.insert(PetEntry.TABLE_NAME, null, values);
        if (returnedValue == -1){
            Toast.makeText(this, "Error with rod id "+ returnedValue, Toast.LENGTH_SHORT);
        }
        else {
            Toast.makeText(this, "Pet Saved with row id: "+ returnedValue, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            case R.id.delete_all_entries:
                return true;
        }
        return true;
    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        PetDbHelper mDbHelper = new PetDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM pets"
        // to get a Cursor that contains all rows from the pets table.
        // Cursor is the a bunch of rows returned from selecting some data in the db
        String [] projection = {PetEntry._ID, PetEntry.COLUMN_PET_NAME, PetEntry.COLUMN_PET_BREED,
        PetEntry.COLUMN_PET_GENDER, PetEntry.COLUMN_PET_WEIGHT};
        Cursor cursor = db.query(PetEntry.TABLE_NAME, projection,null,null,null, null, null);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            // Cursor Methods: (to get certain row)
            // moveToFirst(), moveToLast()
            // moveToPosition(3) --> will go to the 4th row
            // Cursor Methods: (for columns)
            // int nameColumnIndex = cursor.getColummnIndex(PetEntry.COLUMN_PET_NAME)
            // String name = cursor.getString(nameColumnIndex)
            TextView displayView = (TextView) findViewById(R.id.text_view_pet);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount());
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
