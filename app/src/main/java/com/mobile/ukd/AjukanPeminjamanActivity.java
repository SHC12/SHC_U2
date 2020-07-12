package com.mobile.ukd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class AjukanPeminjamanActivity extends AppCompatActivity implements View.OnClickListener {
    EditText scan_ktp, scan_npwp;
    String path_ktp, path_npwp;
    CurrencyEditText nominal, pendapatan_kotor, pengeluaran_keluarga, pendapatan_bersih_hari, pendapatan_bersih_bulan;
    EditText nama_lengkap, nik, no_npwp, no_hp, alamat, email, tgl_pengajuan, tenor, username, password, tanggungan, hari_kerja;
    String s_nama_lengkap, s_nik, s_no_npwp, s_no_hp, s_alamat, s_email, s_tgl_pengajuan, s_nominal, s_tenor, s_username, s_password, s_tanggungan, s_pendapatan_kotor, s_pengeluaran_keluarga, s_pendapatan_bersih_hari, s_hari_kerja, s_pendapatan_bersih_bulan;
    File ktp, npwp;
    Button btnAjukan;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    ProgressDialog progressDialog;

    String a, b, c, d, e, status_layak;
    int i1, i2, i3, i4, i5;

    private String url = "http://kristoforus.my.id/api_android/daftar_pengajuan.php";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajukan_peminjaman);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        nama_lengkap = findViewById(R.id.in_name_register);
        nik = findViewById(R.id.in_nik);
        no_npwp = findViewById(R.id.in_npwp);
        no_hp = findViewById(R.id.in_nohp);
        alamat = findViewById(R.id.in_alamat);
        email = findViewById(R.id.in_email);
        tgl_pengajuan = findViewById(R.id.in_tgl_pengajuan);
        tgl_pengajuan.setOnClickListener(this);
        nominal = findViewById(R.id.in_nominal);

        tanggungan = findViewById(R.id.in_tanggungan);
        tenor = findViewById(R.id.in_tenor);
        username = findViewById(R.id.in_username_pengajuan);
        password = findViewById(R.id.in_password_pengajuan);

        scan_ktp = findViewById(R.id.in_ktp);
        scan_ktp.setOnClickListener(this);

        scan_npwp = findViewById(R.id.in_unpwp);
        scan_npwp.setOnClickListener(this);

        pendapatan_kotor = findViewById(R.id.in_pendapatan_kotor);
        pengeluaran_keluarga = findViewById(R.id.in_pengeluaran_keluarga);
        pengeluaran_keluarga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                a = "" + pendapatan_kotor.getCleanIntValue();
                b = "" + pengeluaran_keluarga.getCleanIntValue();
                i1 = Integer.parseInt(a);
                i2 = Integer.parseInt(b);

                i3 = i1 - i2;

                c = String.valueOf(i3);

                pendapatan_bersih_hari.setText(c.toString());

            }
        });
        pendapatan_bersih_hari = findViewById(R.id.in_pendapatan_bersih_hari);
        hari_kerja = findViewById(R.id.in_hari_kerja);
        hari_kerja.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                d = hari_kerja.getText().toString();

                i4 = Integer.parseInt(d);

                i5 = i3 * i4;

                e = String.valueOf(i5);

                pendapatan_bersih_bulan.setText(e.toString());
            }
        });
        pendapatan_bersih_bulan = findViewById(R.id.in_pendapatan_bersih_bulan);

        btnAjukan = findViewById(R.id.btnAjukan);
        btnAjukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_nama_lengkap = nama_lengkap.getText().toString();
                s_nik = nik.getText().toString();
                s_no_npwp = no_npwp.getText().toString();
                s_no_hp = no_hp.getText().toString();
                s_alamat = alamat.getText().toString();
                s_email = email.getText().toString();
                s_tgl_pengajuan = tgl_pengajuan.getText().toString();
                s_nominal = "" + nominal.getCleanIntValue();
                s_tenor = tenor.getText().toString();
                s_username = username.getText().toString();
                s_password = password.getText().toString();

                s_tanggungan = tanggungan.getText().toString();
                s_pendapatan_kotor = "" + pendapatan_kotor.getCleanIntValue();
                s_pengeluaran_keluarga = "" + pengeluaran_keluarga.getCleanIntValue();
                s_pendapatan_bersih_hari = "" + pendapatan_bersih_hari.getCleanIntValue();
                s_pendapatan_bersih_bulan = "" + pendapatan_bersih_bulan.getCleanIntValue();
                s_hari_kerja = hari_kerja.getText().toString();

                if (i5 < 800000) {
                    status_layak = "0";
                } else {
                    status_layak = "1";
                }

                if (s_nama_lengkap.equals("") ||
                        s_nik.equals("") ||
                        s_no_npwp.equals("") ||
                        s_no_hp.equals("") ||
                        s_alamat.equals("") ||
                        s_email.equals("") ||
                        s_tanggungan.equals("") ||
                        s_pendapatan_kotor.equals("") ||
                        s_pendapatan_bersih_hari.equals("") ||
                        s_pendapatan_bersih_bulan.equals("") ||
                        s_hari_kerja.equals("") ||
                        s_pengeluaran_keluarga.equals("") ||
                        s_tgl_pengajuan.equals("") ||
                        s_nominal.equals("") ||
                        s_tenor.equals("") ||
                        s_username.equals("") ||
                        s_password.equals("") ||
                        scan_ktp.equals("") ||
                        scan_npwp.equals("")
                ) {
                    Toast.makeText(AjukanPeminjamanActivity.this, "Silahkan lengkapi data", Toast.LENGTH_SHORT).show();
                } else {

                    upload_data(s_nama_lengkap, s_nik, s_no_npwp, s_no_hp, s_alamat, s_email, s_tanggungan, s_pendapatan_kotor, s_pengeluaran_keluarga, s_pendapatan_bersih_hari, s_hari_kerja, s_pendapatan_bersih_bulan, status_layak, s_tgl_pengajuan, s_nominal, s_tenor, s_username, s_password, s_password);
                }
            }
        });


        setEdt(pendapatan_kotor);
        setEdt(pengeluaran_keluarga);
        setEdt(pendapatan_bersih_hari);
        setEdt(nominal);
        setEdt(pendapatan_bersih_bulan);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().getPath();
                    scan_ktp.setText(path);
                }

                break;
            case 20:
                if (resultCode == RESULT_OK) {
                    String path = data.getData().getPath();
                    scan_npwp.setText(path);
                }

                break;
            case 42:
                if (requestCode == 42 && resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
//                      path_ktp = getRealPathFromUri(uri, AjukanPeminjamanActivity.this);
                    path_ktp = FilePath.getFilePath(AjukanPeminjamanActivity.this, uri);
                    Log.e("path ktp : ", path_ktp);
                    ktp = new File(path_ktp);
                    scan_ktp.setText(ktp.getName());


                }
                break;
            case 43:
                if (requestCode == 43 && resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
//                      path_ktp = getRealPathFromUri(uri, AjukanPeminjamanActivity.this);
                    path_npwp = FilePath.getFilePath(AjukanPeminjamanActivity.this, uri);
                    Log.e("path npwp : ", path_npwp);
                    npwp = new File(path_npwp);
                    scan_npwp.setText(npwp.getName());


                }
                break;
        }


    }

    public void upload_data(String nl,
                            String nik,
                            String no_npwp,
                            String hp,
                            String alamat,
                            String email,
                            String tanggungan,
                            String pendapatan_kotor,
                            String pendapatan_bersih_hari,
                            String pengeluaran_keluarga,
                            String hari_kerja,
                            String pendapatan_bersih_bulan,
                            String status,
                            String tgl_pengajuan,
                            String nominal,
                            String tenor,
                            String username,
                            String password,
                            String text_password
    ) {
        progressDialog = new ProgressDialog(AjukanPeminjamanActivity.this);
        progressDialog.setMessage("Mohon Tunggu....");
        progressDialog.show();

        AndroidNetworking.upload(url)
                .addMultipartParameter("nama_lengkap", nl)
                .addMultipartParameter("nik", nik)
                .addMultipartParameter("npwp", no_npwp)
                .addMultipartParameter("no_hp", hp)
                .addMultipartParameter("alamat", alamat)
                .addMultipartParameter("email", email)
                .addMultipartParameter("tanggungan", tanggungan)
                .addMultipartParameter("pendapatan_kotor", pendapatan_kotor)
                .addMultipartParameter("pengeluaran_keluarga", pengeluaran_keluarga)
                .addMultipartParameter("pendapatan_bersih_perhari", pendapatan_bersih_hari)
                .addMultipartParameter("hari_kerja", hari_kerja)
                .addMultipartParameter("pendapatan_bersih_perbulan", pendapatan_bersih_bulan)
                .addMultipartParameter("status", status)
                .addMultipartParameter("tgl_pengajuan", tgl_pengajuan)
                .addMultipartParameter("nominal", nominal)
                .addMultipartParameter("tenor", tenor)
                .addMultipartParameter("username", username)
                .addMultipartParameter("password", password)
                .addMultipartParameter("text_password", text_password)
                .addMultipartFile("scan_ktp", ktp)
                .addMultipartFile("scan_npwp", npwp)
                .setTag("Upload Ajuan")
                .setPriority(Priority.HIGH)

                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                          String status = response.get("status").toString();

                          if(status.equals("1")){
                              progressDialog.dismiss();
                              Intent i = new Intent(AjukanPeminjamanActivity.this, NotifikasiActivity.class);
                              i.putExtra("flagNotif","ajukanPengajuan");
                              startActivity(i);
                          }else{
                              progressDialog.dismiss();
                              Toast.makeText(AjukanPeminjamanActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                          }
                        }catch (JSONException e){

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                        //    Toast.makeText(DaftarTTD.this, "ERROR : " + anError.getErrorBody(), Toast.LENGTH_SHORT).show();
                        Log.e("ERROR  : ", ""+anError.getErrorDetail());
                    }
                });
    }

    private void setEdt(CurrencyEditText target) {
        target.setCurrency(CurrencySymbols.INDONESIA);
        target.setDecimals(false);
        target.setSeparator(".");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.in_tgl_pengajuan:
                showDateDialog(tgl_pengajuan);
                break;
            case R.id.in_ktp:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                startActivityForResult(intent, 42);
                break;
            case R.id.in_unpwp:
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.setType("*/*");
                startActivityForResult(i, 43);
                break;

        }

    }
}
