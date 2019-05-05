package com.example.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    TextView company, proName, proID;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        company = findViewById(R.id.company);
        proID = findViewById(R.id.proID);
        proName = findViewById(R.id.proName);

    }

    public void submit1(View view){
        try {
            SQLiteDatabase db = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS product ( company VARCHAR, productName VARCHAR, ProID INTEGER PRIMARY KEY)");
            ContentValues contentValues = new ContentValues();
            contentValues.put("company", company.getText().toString());
            contentValues.put("productName", proName.getText().toString());
            contentValues.put("ProID", Integer.parseInt(proID.getText().toString()));
            db.insert("product", null, contentValues);
            Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
            db.close();
            finish();
        }catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


}
