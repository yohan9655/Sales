package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    TextView name, address, phone, ID;
    Spinner sp1;
    ArrayList<String> spinnerItems;
    ArrayAdapter<String> adapter;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        name = findViewById(R.id.Name);
        address = findViewById(R.id.Address);
        phone = findViewById(R.id.Phone);
        ID = findViewById(R.id.ID);
        sp1 = findViewById(R.id.spinner);
        b1 = findViewById(R.id.button);

        spinnerItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems);
        sp1.setAdapter(adapter);

        try{
            SQLiteDatabase db = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            String query = "SELECT productName, proID FROM product";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    spinnerItems.add(cursor.getString(0) + "  " + cursor.getString(1));
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerItems);
                    sp1.setAdapter(adapter);
                }
            } else {
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
            }
            db.close();
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.i("error", e.toString());
        }


    }

    public void submit2(View view) {
        try {
            SQLiteDatabase db = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS users ( name VARCHAR, ID INTEGER PRIMARY KEY, Address VARCHAR, phone INTEGER, proID INTEGER, FOREIGN KEY(proID) REFERENCES product(proID))");
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", name.getText().toString());
            contentValues.put("ID", Integer.parseInt(ID.getText().toString()));
            contentValues.put("Address", address.getText().toString());
            contentValues.put("phone", Integer.parseInt(phone.getText().toString()));
            db.insert("users", null, contentValues);

            int k = sp1.getSelectedItem().toString().indexOf(" ");
            String s = sp1.getSelectedItem().toString().substring(k+1);
            String query = "DELETE FROM product WHERE proID =" + s;
            db.execSQL(query);
            spinnerItems.clear();
            adapter.notifyDataSetChanged();

            db.close();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            Log.i("error", e.toString());
        }


    }

}
