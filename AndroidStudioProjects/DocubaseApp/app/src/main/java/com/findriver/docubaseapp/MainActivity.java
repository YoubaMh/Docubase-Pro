package com.findriver.docubaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText inputEmail, inputPassword;
    private TextInputLayout layEmail, layPassword;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initView();
        this.initListener();
        this.initObject();
    }

    private void initView() {
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        layEmail = (TextInputLayout) findViewById(R.id.emailError);
        layPassword = (TextInputLayout) findViewById(R.id.passwordError);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
    }

    private void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();


            }
        });
    }

    private void initObject() {

    }
}
