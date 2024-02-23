package com.example.inventoryapp_chrissharp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ActivityLogin extends AppCompatActivity {

    EditText userName, userPassword;

    Button buttonLogin;

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.textUserName);
        userPassword = findViewById(R.id.textPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setEnabled(false);
        userName.addTextChangedListener(LoginWatcher);
        userPassword.addTextChangedListener(LoginWatcher);

        buttonLogin.setOnClickListener(v -> {

            String user = userName.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            if (myDB.checkUserLogin(user,password)) {
                Toast.makeText(this, "Successful Login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityLogin.this, ActivityDatabase.class);
                startActivity(intent);
            }else {
                registerNewUser();
            }
        });
        
        myDB = new DatabaseHelper(ActivityLogin.this);
        

    }

    // Method to build AlertBox to add new user
    void registerNewUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create New User?");
        builder.setMessage("Do you want to create a new user login?");
        builder.setPositiveButton("Yes", (dialog, which) -> {
            String nameUser = userName.getText().toString().trim();
            String passwordUser = userPassword.getText().toString().trim();

            DatabaseHelper myDB = new DatabaseHelper(ActivityLogin.this);
            myDB.addUser(nameUser, passwordUser);

            Intent intent = new Intent(ActivityLogin.this, ActivityLogin.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialog, which) -> {

        });
        builder.create().show();
    }

    TextWatcher LoginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String user = userName.getText().toString().trim();
            String password = userPassword.getText().toString().trim();
            buttonLogin.setEnabled(!user.isEmpty() && !password.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };






}