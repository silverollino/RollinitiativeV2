package com.example.ollin.rollinitiativev2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import com.example.ollin.rollinitiativev2.helper.InputValidation;
import com.example.ollin.rollinitiativev2.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private int STORAGE_PERMISSION_CODE = 1;
    private final AppCompatActivity activity = LoginActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPass;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPass;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initViews() {
        nestedScrollView = findViewById(R.id.nestedScrollView);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPass = findViewById(R.id.textInputLayoutPass);
        textInputEditTextEmail = findViewById(R.id.textInputEmail);
        textInputEditTextPass = findViewById(R.id.textInputPass);
        appCompatButtonLogin = findViewById(R.id.buttonLogin);
        textViewLinkRegister = findViewById(R.id.linkRegister);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
               permission();
                verifyFromSQLite();
                break;
            case R.id.linkRegister:
                permission();
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isTextFielled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_msg_email))) {
            return;
        }
        if (!inputValidation.isInputEmailFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_msg_email))) {
            return;
        }
        if (!inputValidation.isTextFielled(textInputEditTextPass, textInputLayoutPass, getString(R.string.error_msg_pass))) {
            return;
        }

        if (databaseHelper.chkUser(textInputEditTextEmail.getText().toString(), textInputEditTextPass.getText().toString())) {
            Intent intentUser = new Intent(activity, UsersActivity.class);
            intentUser.putExtra("EMAIL", textInputEditTextEmail.getText().toString());
            emptyInputEditText();
            startActivity(intentUser);
        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_mail_pass), Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPass.setText(null);
    }

    private void permission() {
        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(LoginActivity.this,R.string.permissiongranted, Toast.LENGTH_LONG).show();
        }else{
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle(R.string.needspermision)
                    .setMessage(R.string.permisionmotive)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(LoginActivity.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,R.string.permissiongranted,Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,R.string.needspermision,Toast.LENGTH_LONG).show();
            }
        }
    }
}

