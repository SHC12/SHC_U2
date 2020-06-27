package com.mobile.ukd;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.mobile.ukd.admin.InsertPembayaran;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class AjukanPeminjamanActivity extends AppCompatActivity implements View.OnClickListener{
    EditText scan_ktp, scan_npwp;
    String path_ktp, path_npwp;
    CurrencyEditText nominal;
    EditText nama_lengkap, nik, no_npwp, no_hp, alamat, email, tgl_pengajuan,tenor, username, password;
    String s_nama_lengkap, s_nik, s_no_npwp, s_no_hp, s_alamat, s_email, s_tgl_pengajuan, s_nominal, s_tenor, s_username, s_password;
    File ktp, npwp;
    Button btnAjukan;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    ProgressDialog progressDialog;

    private String url  = "http://kristoforus.my.id/api_android/daftar_pengajuan.php";

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
        setEdt(nominal);
        tenor = findViewById(R.id.in_tenor);
        username = findViewById(R.id.in_username_pengajuan);
        password = findViewById(R.id.in_password_pengajuan);

        scan_ktp = findViewById(R.id.in_ktp);
        scan_ktp.setOnClickListener(this);

        scan_npwp = findViewById(R.id.in_unpwp);
        scan_npwp.setOnClickListener(this);

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
                s_nominal = ""+nominal.getCleanIntValue();
                s_tenor = tenor.getText().toString();
                s_username = username.getText().toString();
                s_password = password.getText().toString();

                if(s_nama_lengkap.equals("") ||
                        s_nik.equals("") ||
                        s_no_npwp.equals("") ||
                        s_no_hp.equals("") ||
                        s_alamat.equals("") ||
                        s_email.equals("") ||
                        s_tgl_pengajuan.equals("") ||
                        s_nominal.equals("") ||
                        s_tenor.equals("") ||
                        s_username.equals("") ||
                        s_password.equals("") ||
                        scan_ktp.equals("") ||
                        scan_npwp.equals("")
                ){
                    Toast.makeText(AjukanPeminjamanActivity.this, "Silahkan lengkapi data", Toast.LENGTH_SHORT).show();
                }else{
                    upload_data(s_nama_lengkap,s_nik,s_no_npwp,s_no_hp,s_alamat,s_email,s_tgl_pengajuan,s_nominal,s_tenor,s_username,s_password,s_password);
                }
            }
        });
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
