package com.mobile.ukd.root;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.gson.JsonArray;
import com.mobile.ukd.InfoActivity;
import com.mobile.ukd.R;
import com.mobile.ukd.adapter.TableAdapterRiwayat;
import com.mobile.ukd.admin.DetailCalonDebitur;
import com.mobile.ukd.debitur.DataPinjamanDebitur;
import com.mobile.ukd.debitur.RiwayatPengajuanDebitur;
import com.mobile.ukd.model.Pembayaran;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditInfoAplikasi extends AppCompatActivity {
    private String URL_UPDATE_INFO = "http://kristoforus.my.id/api_android/update_info_root.php";
    private String URL_GET_NOTIF ="http://kristoforus.my.id/api_android/get_notifikasi.php?kategory=";
    private EditText info,nama,nomor;
    private String s_info,g_nama,g_nomor,s_nama,s_nomor,s_kategory,URL_LENGKAP,s_k;
    private MaterialButton btnSimpan;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info_aplikasi);


        info = findViewById(R.id.ed_info_app);
        nama = findViewById(R.id.info_nama_root);
        nomor = findViewById(R.id.info_no_root);
        btnSimpan = findViewById(R.id.btnSimpanInfo);
        s_k="1";
        URL_LENGKAP = URL_GET_NOTIF+s_k;
        progressDialog = new ProgressDialog(this);

        getNotif();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_info = info.getText().toString().trim();
                s_nama = nama.getText().toString().trim();
                s_nomor = nomor.getText().toString().trim();
                s_kategory = "1";
                progressDialog.show();
                progressDialog.setMessage("Loading..");
                if(s_info.equals("") || s_nama.equals("") || s_nomor.equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(EditInfoAplikasi.this, "Field harus di isi", Toast.LENGTH_SHORT).show();
                } else {

                    updateInfo(s_nama,s_info,s_nomor,s_kategory);

                }
            }
        });


    }

    private void updateInfo(String namaUser,String text,String nomorUser,String kategory) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");
                    if (success.equals("1")) {
                        Toast.makeText(EditInfoAplikasi.this, "Update Info Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditInfoAplikasi.this,MenuRoot.class));
                    } else {
                        Toast.makeText(EditInfoAplikasi.this, "Update Info Gagal", Toast.LENGTH_SHORT).show();
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
                params.put("nama",namaUser);
                params.put("nomor",nomorUser);
                params.put("text_notifikasi",text);
                params.put("kategory",kategory);
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
                            info.setText(s_notif);
                            nama.setText(jsonObject.getString("nama"));
                            nomor.setText(jsonObject.getString("nik"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(EditInfoAplikasi.this, "Response Kosong",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditInfoAplikasi.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

}
