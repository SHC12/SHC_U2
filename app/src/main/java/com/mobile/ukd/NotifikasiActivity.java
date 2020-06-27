package com.mobile.ukd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.mobile.ukd.admin.DashboardAdmin;
import com.mobile.ukd.admin.DetailDataPembayaranDebitur;
import com.mobile.ukd.kabag.DashboardKabag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotifikasiActivity extends AppCompatActivity {
    private String URL_GET_NOTIF ="http://kristoforus.my.id/api_android/get_notifikasi.php?kategory=";
    private String URL_LENGKAP;
    private String kategory;
    private TextView title,desc;
    private MaterialButton btnLoginUlang;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);
        title = findViewById(R.id.notif_title);
        desc = findViewById(R.id.notif_desc);
        btnLoginUlang = findViewById(R.id.btnLoginUlang);
        String getIntent  = getIntent().getStringExtra("flagNotif");
        if(getIntent.equals("insertPembayaran")){
            title.setText("Pembayaran Berhasil");
            desc.setText("Pembayaran Berhasil Di Inputkan");
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(NotifikasiActivity.this, DashboardAdmin.class));
                    }
                }
            };
            thread.start();
        }
        if(getIntent.equals("ajukanPengajuan")){
            kategory = "2";
            URL_LENGKAP = URL_GET_NOTIF+kategory;
            title.setText("Pengajuan Berhasil");
            getNotif();
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(NotifikasiActivity.this, LoginActivity.class));
                    }
                }
            };
            thread.start();
        }
        if(getIntent.equals("gantiPassword")){
            kategory = "3";
            URL_LENGKAP = URL_GET_NOTIF+kategory;
            title.setText("Password Berhasil Di Ganti");
            getNotif();
            btnLoginUlang.setVisibility(View.VISIBLE);
           btnLoginUlang.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPrefs.edit();
                   editor.clear();
                   editor.commit();
                   startActivity(new Intent(NotifikasiActivity.this, MenuLoginActivity.class));
               }
           });
        }

    }
    public void getNotif(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LENGKAP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.e("api detail", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String s_notif = jsonObject.getString("ket_notif");
                            desc.setText(s_notif);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(NotifikasiActivity.this, "Response Kosong",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NotifikasiActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
