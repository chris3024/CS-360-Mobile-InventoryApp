package com.example.inventoryapp_chrissharp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityUpdate extends AppCompatActivity {

    EditText itemNameInput, itemQuantityInput;
    Button updateButton, deleteButton;

    String  itemName, itemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemNameInput = findViewById(R.id.editItemNameTwo);
        itemQuantityInput = findViewById(R.id.editItemAmountTwo);
        updateButton = findViewById(R.id.buttonUpdate);
        deleteButton = findViewById(R.id.buttonDelete);

        getAndSetIntentData();

        updateButton.setOnClickListener(v -> {
            DatabaseHelper myDB = new DatabaseHelper(ActivityUpdate.this);
            itemName = itemNameInput.getText().toString().trim();
            itemQuantity = itemQuantityInput.getText().toString().trim();
            myDB.updateData(itemName, itemName, Integer.valueOf(itemQuantity));

        });

        deleteButton.setOnClickListener(v -> confirmDialog());

    }

    // Method to get the data from the recyclerView and bring it forward into the new Activity
    void getAndSetIntentData() {
        if (getIntent().hasExtra("Item Name") && getIntent().hasExtra("Item Quantity")) {

            //Getting data from the passed Intent
            itemName = getIntent().getStringExtra("Item Name");
            itemQuantity = getIntent().getStringExtra("Item Quantity");

            // Setting the Data from the passed Intent
            itemNameInput.setText(itemName);
            itemQuantityInput.setText(itemQuantity);

        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    // Setting up the Back navigation button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + itemName + " ?");
        builder.setMessage(" Are you sure you want to delete " + itemName + " ?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            DatabaseHelper myDB = new DatabaseHelper(ActivityUpdate.this);
            myDB.deleteOneRow(itemName);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }
}