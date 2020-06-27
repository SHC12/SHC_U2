package com.mobile.ukd.debitur;

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
import com.mobile.ukd.R;
import com.mobile.ukd.admin.DetailDataPembayaranDebitur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataPinjamanDebitur extends AppCompatActivity {
    private String URL_DETAIL_PEMBAYARAN = "http://kristoforus.my.id/api_android/detail_data_pembayaran_debitur.php?id_user=";
    private TextView t_pinjaman,t_total,t_sisa;
    private String idUser;
    private String URL_LENGKAP;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pinjaman_debitur);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        idUser = settings.getString("id_user", "default");
        URL_LENGKAP = URL_DETAIL_PEMBAYARAN+idUser;
        t_pinjaman = findViewById(R.id.total_pinjaman_debitur);
        t_total = findViewById(R.id.total_terbayar_debitur);
        t_sisa = findViewById(R.id.sisa_hutang_debitur);
        getDataDetail();
    }

    public void getDataDetail(){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LENGKAP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.e("api detail", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            String s_pinjaman = jsonObject.getString("pinjaman");
                            String s_total_bayar = jsonObject.getString("total_bayar");
                            String s_sisa_hutang = jsonObject.getString("sisa_hutang");
                            t_pinjaman.setText(s_pinjaman);
                            t_total.setText(s_total_bayar);
                            t_sisa.setText(s_sisa_hutang);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(DataPinjamanDebitur.this, "Response Kosong",Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DataPinjamanDebitur.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
