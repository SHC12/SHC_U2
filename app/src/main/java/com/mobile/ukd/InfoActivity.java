package com.mobile.ukd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InfoActivity extends AppCompatActivity {
    private String URL_GET_NOTIF ="http://kristoforus.my.id/api_android/get_notifikasi.php?kategory=";
    private String URL_LENGKAP;
    private String mName,mNomor,mKategory;
    private TextView nomor,desc,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        mKategory = "1";
        URL_LENGKAP = URL_GET_NOTIF+mKategory;
        name = findViewById(R.id.infoName);
        desc = findViewById(R.id.infoDesc);
        nomor = findViewById(R.id.infoBp);
        getNotif();
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
                            String s_nama = jsonObject.getString("nama");
                            String s_nik = jsonObject.getString("nik");
                            desc.setText(s_notif);
                            name.setText(s_nama);
                            nomor.setText(s_nik);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(InfoActivity.this, "Response Kosong",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InfoActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
