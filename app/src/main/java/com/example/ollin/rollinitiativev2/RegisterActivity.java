package com.example.ollin.rollinitiativev2;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.ollin.rollinitiativev2.helper.InputValidation;
import com.example.ollin.rollinitiativev2.model.User;
import com.example.ollin.rollinitiativev2.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPass);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputConfirmPass);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputPass);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputTxtConfrimPass);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.buttonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.linkLogin);
    }

    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonRegister:
                postDataToSQLite();
                break;
            case R.id.linkLogin:
                finish();
                break;
        }
    }

    private void postDataToSQLite(){
        if (!inputValidation.isTextFielled(textInputEditTextName, textInputLayoutName, getString(R.string.error_msg_name))) {
            return;
        }
        if (!inputValidation.isTextFielled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_msg_email))) {
            return;
        }
        if (!inputValidation.isInputEmailFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_msg_email))) {
            return;
        }
        if (!inputValidation.isTextFielled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_msg_pass))) {
            return;
        }
        if (!inputValidation.isPassMatch(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_pass_match))) {
            return;
        }

        if (!databaseHelper.chkUser(textInputEditTextEmail.getText().toString().trim(),textInputEditTextPassword.getText().toString().trim())){

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_msg), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
            finish();
        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_mail_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
