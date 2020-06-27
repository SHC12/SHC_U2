package com.mobile.ukd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountActivity extends AppCompatActivity {
    private String URL = "http://kristoforus.my.id/api_android/ganti_password.php";
    public static final String PREFS_NAME = "MyPrefsFile";
    private String mUsername,mPassword,nPassword,mId;
    private TextView username,password;
    private EditText newPassword;
    private MaterialButton btnGantiPassword;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mUsername = settings.getString("username","default");
        mId = settings.getString("id_user","default");
        mPassword = settings.getString("password","default");
        username = findViewById(R.id.akun_username);
        password = findViewById(R.id.akun_password);
        newPassword = findViewById(R.id.in_password_reset);
        username.setText(mUsername);
        password.setText(mPassword);
        progressDialog = new ProgressDialog(this);
        btnGantiPassword = findViewById(R.id.btnGantiPassword);
        btnGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                nPassword = newPassword.getText().toString().trim();
                if(nPassword.equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(AccountActivity.this, "Isi field untuk reset password", Toast.LENGTH_SHORT).show();
                } else {
                  reset_password(mId,nPassword);
                }
            }
        });
        
    }

    public void reset_password(final String id_user, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");

//                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    if (success.equals("1")) {
                        Intent intent = new Intent(AccountActivity.this, NotifikasiActivity.class);
                        intent.putExtra("flagNotif", "gantiPassword");
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Gagal, silahkan coba kembali ",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error  : " + e.toString(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
