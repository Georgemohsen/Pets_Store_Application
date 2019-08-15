package com.example.petsapp;
import com.example.petsapp.data.PetContract.PetEntry;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petsapp.data.PetContract;
import com.example.petsapp.data.PetDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mEditName;
    private EditText mEditBreed;
    private EditText mWeight;
    private int mGender;
    private PetDbHelper mDbHelper = new PetDbHelper(this);
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor);
        spinner = (Spinner)findViewById(R.id.spin);
        // We can Design a spinner layout instead of the default one
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mWeight = (EditText)findViewById(R.id.pet_weight);
        mEditBreed = (EditText)findViewById(R.id.pet_breed);
        mEditName = (EditText)findViewById(R.id.pet_name);

        Button saveButton = (Button)findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertPet();
                finish();
            }
        });
    }

    private void insertPet(){
        String name = mEditName.getText().toString().trim();
        String breed = mEditBreed.getText().toString().trim();
        int weight = Integer.parseInt(mWeight.getText().toString().trim());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_NAME, name);
        values.put(PetEntry.COLUMN_PET_BREED, breed);
        values.put(PetEntry.COLUMN_PET_GENDER, mGender);
        values.put(PetEntry.COLUMN_PET_WEIGHT, weight);

        long returnedValue = db.insert(PetEntry.TABLE_NAME, null, values);
        if (returnedValue == -1){
            Toast.makeText(this, "Error with rod id "+ returnedValue, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Pet Saved with row id: "+ returnedValue, Toast.LENGTH_SHORT).show();
        }
    }
}
