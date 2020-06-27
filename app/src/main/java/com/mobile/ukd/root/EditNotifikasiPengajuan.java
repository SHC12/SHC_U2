package com.mobile.ukd.root;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.mobile.ukd.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditNotifikasiPengajuan extends AppCompatActivity {
    private String URL_UPDATE_INFO = "http://kristoforus.my.id/api_android/update_info_root.php";
    private String URL_GET_NOTIF ="http://kristoforus.my.id/api_android/get_notifikasi.php?kategory=";
    private EditText notif;
    private MaterialButton btn;
    private String s_notif,s_kategory,URL_LENGKAP,s_k,s_nama,s_nik;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notifikasi_pengajuan);
        notif = findViewById(R.id.ed_notif_pengajuan);
        btn = findViewById(R.id.btnSimpanPengajuan);
        s_k = "2";
        URL_LENGKAP = URL_GET_NOTIF+s_k;
        getNotif();
        progressDialog = new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_notif = notif.getText().toString().trim();
                s_kategory = "2";
                progressDialog.show();
                if(s_notif.equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(EditNotifikasiPengajuan.this, "Field tidak boleh Kosong", Toast.LENGTH_SHORT).show();
                } else {
                    updateNotifPengajuan(s_notif,s_kategory,s_nama,s_nik);
                }

            }
        });
    }

    private void updateNotifPengajuan(String s_notif,String s_kategory,String s_nama,String s_nomor) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");
                    if (success.equals("1")) {
                        Toast.makeText(EditNotifikasiPengajuan.this, "Update Info Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditNotifikasiPengajuan.this, MenuRoot.class));
                    } else {
                        Toast.makeText(EditNotifikasiPengajuan.this, "Update Info Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                Map<String ,String> params = new HashMap<>();
                params.put("text_notifikasi",s_notif);
                params.put("nama",s_nama);
                params.put("nomor",s_nomor);
                params.put("kategory",s_kategory);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                            notif.setText(s_notif);
                            s_nama=jsonObject.getString("nama");
                            s_nik=jsonObject.getString("nik");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(EditNotifikasiPengajuan.this, "Response Kosong",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditNotifikasiPengajuan.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
