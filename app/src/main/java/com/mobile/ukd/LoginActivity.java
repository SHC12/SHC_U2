package com.mobile.ukd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.common.Method;
import com.google.android.material.button.MaterialButton;
import com.mobile.ukd.admin.DashboardAdmin;
import com.mobile.ukd.debitur.DashboardDebitur;
import com.mobile.ukd.kabag.DashboardKabag;
import com.mobile.ukd.root.MenuRoot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static okhttp3.Request.*;

public class LoginActivity extends AppCompatActivity {
    private String URL_LOGIN = "http://kristoforus.my.id/api_android/model_login.php";
    public static final String PREFS_NAME = "MyPrefsFile";
    private EditText eUsername,ePassword;
    private MaterialButton btnLogin;
    private String username,password;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(LoginActivity.this);
        eUsername = findViewById(R.id.in_username);
        ePassword = findViewById(R.id.in_password);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = eUsername.getText().toString().trim();
                password = ePassword.getText().toString().trim();

                progressDialog.setMessage("Mohon Tunggu....");
                progressDialog.show();
                if (username.isEmpty()) {
                    eUsername.setError("Username tidak boleh kosong !");
                } else if (password.isEmpty()) {
                    ePassword.setError("Password tidak boleh kosong !");
                } else {

                    auth_user(username, password);

                }
            }
        });



    }

    public void auth_user(final String usernamee, final String passwordd) {
        StringRequest stringRequest = new StringRequest(Method.POST, URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");

//                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    if (success.equals("1")) {
                        for (int i = 0; i < 11; i++) {
//                            JSONObject jsonObject1 = jsonObject.getJSONObject();


                            String id_user = jsonObject.getString("id_user").trim();
                            String id_debitur_user = jsonObject.getString("id_debitur_user").trim();
                            String username = jsonObject.getString("username").trim();
                            String level = jsonObject.getString("level").trim();
                            String status = jsonObject.getString("statusakun").trim();
                            String password = jsonObject.getString("password").trim();
                            String nama = jsonObject.getString("nama").trim();
                            String nik = jsonObject.getString("nik").trim();


                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("id_user", id_user);
                            editor.putString("id_debitur_user", id_debitur_user);
                            editor.putString("username", username);
                            editor.putString("level", level);
                            editor.putString("status", status);
                            editor.putString("password", password);
                            editor.putString("nama", nama);
                            editor.putString("nik", nik);


                            editor.commit();


                            if (level.equals("1") && status.equals("2")) {
                                Intent intent = new Intent(getApplicationContext(), DashboardDebitur.class);
                                intent.putExtra("username", username);
                                intent.putExtra("id_user", id_user);


                                startActivity(intent);
                            } else if (level.equals("1") && status.equals("0")) {
                                Toast.makeText(LoginActivity.this, "Akun anda belum aktif", Toast.LENGTH_SHORT).show();
                            } else if (level.equals("1") && status.equals("1")) {
                                Toast.makeText(LoginActivity.this, "Akun dan pengajuan anda sedang di tinjau", Toast.LENGTH_SHORT).show();
                            } else if (level.equals("1") && status.equals("3")) {
                                Toast.makeText(LoginActivity.this, "Akun dan pengajuan ditolak", Toast.LENGTH_SHORT).show();
                            } else if (level.equals("2")) {
                                Intent intent = new Intent(getApplicationContext(), DashboardAdmin.class);
                                intent.putExtra("username", username);
                                intent.putExtra("id_user", id_user);
                                startActivity(intent);
                            }
                            else if (level.equals("3")) {
                                Intent intent = new Intent(getApplicationContext(), DashboardKabag.class);
                                intent.putExtra("username", username);
                                intent.putExtra("id_user", id_user);
                                startActivity(intent);
                            }
                            else if (level.equals("4")) {
                                Intent intent = new Intent(getApplicationContext(), MenuRoot.class);
                                intent.putExtra("username", username);
                                intent.putExtra("id_user", id_user);
                                startActivity(intent);
                            }
                        }
                    } else {
                        Toast.makeText(LoginActivity.this,
                                "ID. User dan Password tidak ditemukan! ",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,
                            "Error login : " + e.toString(),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernamee);
                params.put("password", passwordd);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}
