package com.example.inventoryapp_chrissharp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;


public class ActivityAdd extends AppCompatActivity {

    EditText itemName, itemQuantity;
    Button addButton;

    DatabaseHelper myDB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemName = findViewById(R.id.editItemName);
        itemQuantity = findViewById(R.id.editItemAmount);
        addButton = findViewById(R.id.buttonConfirm);

        addButton.setEnabled(false);
        itemName.addTextChangedListener(LoginWatcher);
        itemQuantity.addTextChangedListener(LoginWatcher);

        // Adding the data to both the RecyclerView and the Database
        addButton.setOnClickListener(v -> {
            String item = itemName.getText().toString().toUpperCase(Locale.ROOT).trim();

            if (myDB.checkInventory(item)) {
                Toast.makeText(this, "Item Already in Inventory", Toast.LENGTH_SHORT).show();
            }else {
                confirmDialog();
            }
        });
        myDB = new DatabaseHelper(ActivityAdd.this);

    }

    // Alertbox for adding item to inventory
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Add Item To Inventory?");
        builder.setMessage(" Are you sure you want to add item to the inventory?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            String item = itemName.getText().toString().toUpperCase(Locale.ROOT).trim();
            Integer quantity = Integer.valueOf(itemQuantity.getText().toString().trim());
            myDB.addInventory(item, quantity);



        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }

    // Setting up the navigation button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    // Textwatcher for button
    TextWatcher LoginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String item = itemName.getText().toString().trim();
            String quantity = itemQuantity.getText().toString().trim();
            addButton.setEnabled(!item.isEmpty() && !quantity.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };




}