package com.example.inventoryapp_chrissharp;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ActivityDatabase extends AppCompatActivity {


    RecyclerView recyclerView;
    CustomAdapter adapter;

    DatabaseHelper myDB;
    ArrayList<String> itemId, itemName, itemQuantity;

    private final int SMS_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



        recyclerView = findViewById(R.id.recyclerView);

        myDB = new DatabaseHelper(ActivityDatabase.this);

        itemName = new ArrayList<>();
        itemQuantity = new ArrayList<>();
        itemId = new ArrayList<>();


        storeDataInArrays();

        adapter = new CustomAdapter(ActivityDatabase.this, this, itemId, itemName,
                itemQuantity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityDatabase.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()) {
                itemId.add(cursor.getString(0));
                itemName.add(cursor.getString(1));
                itemQuantity.add(cursor.getString(2));
            }
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.inventory_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_item){
            Intent intent = new Intent(this, ActivityAdd.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_exit) {
            super.finish();
            return true;
        } else if (item.getItemId() == R.id.action_sms) {
            if (ContextCompat.checkSelfPermission(ActivityDatabase.this, android.Manifest
                    .permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(ActivityDatabase.this, "You have already granted permission!",
                    Toast.LENGTH_SHORT).show();
            }else {
                requestSMSPermission();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void requestSMSPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            new AlertDialog.Builder(this).setTitle("Permission Requested")
                    .setMessage("Permission is needed to allow for the application to " +
                            " send SMS messages" +
                            " when the item inventory is running low.").setPositiveButton("Yes",
                            (dialog, which) -> ActivityCompat.requestPermissions(
                                    ActivityDatabase.this,
                                    new String[] {Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

