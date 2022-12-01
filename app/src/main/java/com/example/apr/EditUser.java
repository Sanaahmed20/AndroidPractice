package com.example.apr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class EditUser extends AppCompatActivity {

    EditText txt_FirstName, txt_LastName, txt_Email, txt_Username, txt_Password, txt_ConfirmPassword, txt_UserType;
    TextView error_firstName, error_lastName, error_email, error_username, error_password, error_confirmPassword, error_userType;
    Button btnSaveChanges;
    DatabaseConfiguration databaseConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        txt_FirstName = (EditText) findViewById(R.id.txt_FirstName);
        txt_LastName = (EditText) findViewById(R.id.txt_LastName);
        txt_Email = (EditText) findViewById(R.id.txt_Email);
        txt_Username = (EditText) findViewById(R.id.txt_Username);
        txt_Password = (EditText) findViewById(R.id.txt_Password);
        txt_ConfirmPassword = (EditText) findViewById(R.id.txt_ConfirmPassword);
        txt_UserType = (EditText) findViewById(R.id.txt_UserType);

        error_firstName = (TextView) findViewById(R.id.error_firstName);
        error_lastName = (TextView) findViewById(R.id.error_lastName);
        error_email = (TextView) findViewById(R.id.error_email);
        error_username = (TextView) findViewById(R.id.error_username);
        error_password = (TextView) findViewById(R.id.error_password);
        error_confirmPassword = (TextView) findViewById(R.id.error_confirmPassword);
        error_userType = (TextView) findViewById(R.id.error_userType);

        btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);

        databaseConfiguration = new DatabaseConfiguration(this);

        int userId = getIntent().getIntExtra("UserEditId", 0);

        if (userId == 0){
            // Explicit
            Intent obj = new Intent(getApplicationContext(), ActUserList.class);
            startActivity(obj);
        }
        else {
            User user = databaseConfiguration.GetUserById(userId);
            if (user == null){
                // Explicit
                Intent obj = new Intent(getApplicationContext(), ActUserList.class);
                startActivity(obj);
            }
            else {
                txt_FirstName.setText(user.FirstName);
                txt_LastName.setText(user.LastName);
                txt_Email.setText(user.Email);
                txt_Username.setText(user.Username);
                txt_Password.setText(user.Password);
                txt_ConfirmPassword.setText(user.Password);
                txt_UserType.setText(Integer.toString(user.UserType));
            }
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameTxt = txt_FirstName.getText().toString();
                String lastNameTxt = txt_LastName.getText().toString();
                String emailTxt = txt_Email.getText().toString();
                String usernameTxt = txt_Username.getText().toString();
                String passwordTxt = txt_Password.getText().toString();
                String confirmPasswordTxt = txt_ConfirmPassword.getText().toString();
                int userType = Integer.parseInt(txt_UserType.getText().toString());

                if (firstNameTxt.equals("") || firstNameTxt == null){
                    error_firstName.setText("First Name is required!");
                }
                else {
                    error_firstName.setText("");
                }
                if (lastNameTxt.equals("") || lastNameTxt == null){
                    error_lastName.setText("Last Name is required!");
                }
                else {
                    error_lastName.setText("");
                }
                if (emailTxt.equals("") || emailTxt == null){
                    error_email.setText("Email is required!");
                }
                else {
                    error_email.setText("");
                }
                if (usernameTxt.equals("") || usernameTxt == null){
                    error_username.setText("Username is required!");
                }
                else {
                    error_username.setText("");
                }
                if (passwordTxt.equals("") || passwordTxt == null){
                    error_password.setText("Password is required!");
                }
                else {
                    error_password.setText("");
                }
                if (confirmPasswordTxt.equals("") || confirmPasswordTxt == null){
                    error_confirmPassword.setText("Confirm Passowrd is required!");
                }
                else {
                    error_confirmPassword.setText("");
                }
                if (!passwordTxt.equals(confirmPasswordTxt)){
                    error_confirmPassword.setText("Passowrd does not match!");
                }
                else {
                    error_confirmPassword.setText("");
                }
                if (userType < 1 && userType > 2){
                    error_userType.setText("Invalid User Type!");
                }
                else {
                    error_userType.setText("");
                }

                if (
                        firstNameTxt.equals("") || firstNameTxt == null ||
                                lastNameTxt.equals("") || lastNameTxt == null ||
                                emailTxt.equals("") || emailTxt == null ||
                                usernameTxt.equals("") || usernameTxt == null ||
                                passwordTxt.equals("") || passwordTxt == null ||
                                confirmPasswordTxt.equals("") || confirmPasswordTxt == null ||
                                !passwordTxt.equals(confirmPasswordTxt) ||
                                (userType < 1 && userType > 2)
                ) {

                }
                else {
                    User user = new User();
                    user.Id = userId;
                    user.FirstName = firstNameTxt;
                    user.LastName = lastNameTxt;
                    user.Email = emailTxt;
                    user.Username = usernameTxt;
                    user.Password = passwordTxt;
                    //Date date = new Date();
                    //user.CreatedOn = date;
                    user.UserType = userType;

                    if (databaseConfiguration.Update(user)){
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}