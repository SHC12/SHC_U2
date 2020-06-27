package com.mobile.ukd.admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.mobile.ukd.NotifikasiActivity;
import com.mobile.ukd.R;
import com.mobile.ukd.model.Debitur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class InsertPembayaran extends AppCompatActivity implements View.OnClickListener {
    public static final String INSERT_PEMBAYARAN = "insert_pembayaran";
    String URL_LENGKAP_PEMBAYARAN;
    ProgressDialog progressDialog;
    private String URL_RIWAYAT_PEMBAYARAN = "http://kristoforus.my.id/api_android/rincian_pembayaran.php?id_debitur=";
    private EditText kode_debitur, nama_debitur, tgl_pembayaran, pembayaran_ke;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;
    private CurrencyEditText nominal;
    private MaterialButton btnInsert;
    private String URL_INPUT_PEMBAYARAN = "http://kristoforus.my.id/api_android/input_pembayaran_debitur.php";
    private String URL_GET_LAST_PEMBAYARAN = "http://kristoforus.my.id/api_android/get_last_pembayaran.php?id_debitur=";
    private String URL_LENGKAP_LAST_PEMBAYARAN;
    private String idDebitur, idUser, s_tglPembayaran, s_pembayaranKe, s_nominalPembayaran, f_pembayaranKe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_pembayaran);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Debitur debitur = getIntent().getParcelableExtra(INSERT_PEMBAYARAN);
        kode_debitur = findViewById(R.id.in_kode_debitur);
        tgl_pembayaran = findViewById(R.id.in_tanggal_pembayaran_insert);
        tgl_pembayaran.setOnClickListener(this);
        pembayaran_ke = findViewById(R.id.in_pembayaran_ke);
        nominal = findViewById(R.id.in_nominal);
        setEdt(nominal);
        nama_debitur = findViewById(R.id.in_nama_debitur);
        kode_debitur.setText(debitur.getKodeDebitur());
        nama_debitur.setText(debitur.getNamaDebitur());
        progressDialog = new ProgressDialog(InsertPembayaran.this);
        btnInsert = findViewById(R.id.btnInput);
        btnInsert.setOnClickListener(this);
        idDebitur = debitur.getIdDebitur();
        idUser = debitur.getId_user();
        s_tglPembayaran = tgl_pembayaran.getText().toString();
        s_nominalPembayaran = "" + nominal.getCleanIntValue();
        URL_LENGKAP_PEMBAYARAN = URL_RIWAYAT_PEMBAYARAN + idDebitur;
        URL_LENGKAP_LAST_PEMBAYARAN = URL_GET_LAST_PEMBAYARAN + idDebitur;
        getLastPembayaran();


    }

    public void getLastPembayaran() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_LENGKAP_LAST_PEMBAYARAN, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    Log.e("api detail", response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            f_pembayaranKe = jsonObject.getString("sekarang");
                            pembayaran_ke.setText(f_pembayaranKe);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(InsertPembayaran.this, "Anda belum memiliki riwayat pembayaran", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InsertPembayaran.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void inputPembayaran(final String idDebiturr, final String idUserr, final String tglPembayarann, final String pembayaranKee, final String nominall) {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INPUT_PEMBAYARAN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");
                    if (success.equals("1")) {
                        Toast.makeText(InsertPembayaran.this, "Input Pembayaran Berhasil", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(InsertPembayaran.this, NotifikasiActivity.class);
                        i.putExtra("flagNotif", "insertPembayaran");
                        startActivity(i);
                    } else {
                        Toast.makeText(InsertPembayaran.this, "Input Pembayaran Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(InsertPembayaran.this, "Error Input Pembayaran", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_debitur", idDebiturr);
                params.put("id_user", idUserr);
                params.put("tgl_pembayaran", tglPembayarann);
                params.put("pembayaran_ke", pembayaranKee);
                params.put("nominal_pembayaran", nominall);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showDateDialog(final EditText edt_target) {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edt_target.setText(simpleDateFormat.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void setEdt(CurrencyEditText target) {
        target.setCurrency(CurrencySymbols.INDONESIA);
        target.setDecimals(false);
        target.setSeparator(".");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.in_tanggal_pembayaran_insert:
                showDateDialog(tgl_pembayaran);
                break;
            case R.id.btnInput:
                inputPembayaran(idDebitur, idUser, tgl_pembayaran.getText().toString(), pembayaran_ke.getText().toString(), "" + nominal.getCleanIntValue());
                break;
        }
    }
}
