package com.findriver.docubaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.findriver.docubaseapp.Hooks.VolleySingleton;
import com.findriver.docubaseapp.Utils.InputValidation;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText inputEmail, inputPassword;
    private TextInputLayout layEmail, layPassword;
    private NestedScrollView nestedScrollView;

    private RequestQueue queue;
    private InputValidation inputValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initView();
        this.initObject();
        this.initListener();
    }

    private void initView() {
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        layEmail = (TextInputLayout) findViewById(R.id.emailError);
        layPassword = (TextInputLayout) findViewById(R.id.passwordError);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
    }

    private void initObject() {
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        inputValidation = new InputValidation(this);
    }

    private void initListener() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                checkLogin(email, password);
            }
        });
    }

    private void checkLogin(String email, String password) {
        if (!inputValidation.isInputEditTextFilled(inputEmail, layEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(inputEmail, layEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(inputPassword, layPassword, getString(R.string.error_message_password))) {
            return;
        }
        login(email, password);
    }

    private void login(final String email, final String password) {
        String url = "http://51.210.107.146:5000/api/users/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String user = jsonResponse.getString("user");
                    JSONObject userJson = new JSONObject(user);
                    String id = userJson.getString("id");
                    String firstname = userJson.getString("firstname");
                    String lastname = userJson.getString("lastname");
                    String email = userJson.getString("email");
                    String role = userJson.getString("role");

                    Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("email", email);
                    intent.putExtra("firstname", firstname);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            }
        }) {
            String data = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
            public String getBodyContentType() { return "application/json; charset=utf-8"; }
            public byte[] getBody() throws AuthFailureError {
                try {
                    return data.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };

        queue.add(stringRequest);
    }
}
