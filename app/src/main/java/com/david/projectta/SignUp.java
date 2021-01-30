package com.david.projectta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextnamalengkap, textInputEditTextemail, textInputEditTextusername,  textInputEditTextpassword;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextemail = findViewById(R.id.email);
        textInputEditTextnamalengkap = findViewById(R.id.namalengkap);
        textInputEditTextpassword = findViewById(R.id.password);
        textInputEditTextusername = findViewById(R.id.username);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
        buttonSignUp.setOnClickListener(v -> {
            String namalengkap, email, username, password;
            namalengkap = String.valueOf(textInputEditTextnamalengkap.getText());
            email = String.valueOf(textInputEditTextemail.getText());
            username = String.valueOf(textInputEditTextusername.getText());
            password = String.valueOf(textInputEditTextpassword.getText());

            if(!namalengkap.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler();
                handler.post(() -> {

                    String[] field = new String[4];
                    field[0] = "namalengkap";
                    field[1] = "email";
                    field[2] = "username";
                    field[3] = "password";

                    String[] data = new String[4];
                    data[0] = namalengkap;
                    data[1] = email;
                    data[2] = username;
                    data[3] = password;
                    PutData putData = new PutData("http://192.168.1.12/LoginRegister/signup.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            if(result.equals("Sign Up Success")) {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Pastikan semua data telah terisi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}