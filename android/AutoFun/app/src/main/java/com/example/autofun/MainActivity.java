package com.example.autofun;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        String[] friends = {"Hetul", "Hardik", "Dilip", "Anjali","Harsh"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, friends);

        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);





    }
}