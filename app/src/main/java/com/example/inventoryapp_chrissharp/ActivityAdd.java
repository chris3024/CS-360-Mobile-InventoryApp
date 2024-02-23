package com.example.inventoryapp_chrissharp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class ActivityAdd extends AppCompatActivity {

    EditText itemName, itemQuantity;
    Button addButton;



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
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDB = new DatabaseHelper(ActivityAdd.this);
                myDB.addInventory(itemName.getText().toString().trim(),
                        Integer.parseInt(itemQuantity.getText().toString().trim()));
            }
        });

    }

    // Setting up the navigation button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    TextWatcher LoginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String user = itemName.getText().toString().trim();
            String password = itemQuantity.getText().toString().trim();
            addButton.setEnabled(!user.isEmpty() && !password.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };




}