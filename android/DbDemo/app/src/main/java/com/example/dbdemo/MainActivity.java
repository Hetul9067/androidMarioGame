package com.example.dbdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.dbdemo.adapter.RecyclerViewAdapter;
import com.example.dbdemo.data.MyDbHandler;
import com.example.dbdemo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Contact> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;
//    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyDbHandler db = new MyDbHandler(MainActivity.this);

//        //creating a contact object for the db;
        Contact hetul = new Contact();
        hetul.setPhoneNumber("9067070766");
        hetul.setName("Hetul");


        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);
        db.addContact(hetul);






//
//        //creating a contact object for the db;
//        Contact hardik = new Contact();
//        hardik.setPhoneNumber("7567713281");
//        hardik.setName("Hardik");
//        db.addContact(hardik);
//
//        hardik.setId(5);
//        hardik.setName("Changed Hardik");
//        hardik.setPhoneNumber("0000000000");
//        int afftectedRows = db.updateContact(hardik);
//
//
//        Log.d("dbhetul", "Id for hetul and hardik added" );
//
//        db.deleteContactById(1);
//        db.deleteContactById(3);
//        db.deleteContactById(8);


//        ArrayList<String> contacts = new ArrayList<>();
//        listView = findViewById(R.id.listView);

        contactArrayList = new ArrayList<>();
        //get all contacts
        List<Contact> contactList = db.getAllContact();
        for(Contact contact: contactList){

            Log.d("dbhetul", "Id: "+contact.getId() +"\n" +
                    "Name: " + contact.getName() + "\n"+
                    "Phone Number: "+ contact.getPhoneNumber()+ "\n");
            contactArrayList.add(contact);

        }

        //use your recycler view
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);


//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
//        listView.setAdapter(arrayAdapter);
//        Log.d("dbhetul", "Bro you have "+ db.getCount()+" contacts in your database.");

    }
}