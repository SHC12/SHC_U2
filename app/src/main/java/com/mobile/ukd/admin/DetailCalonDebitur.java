package com.mobile.ukd.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.mobile.ukd.GaleryViewer;
import com.mobile.ukd.PDFViewer;
import com.mobile.ukd.R;
import com.mobile.ukd.model.Debitur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailCalonDebitur extends AppCompatActivity {

    public static final String DETAIL_DEBITUR = "detail_debitur";
    private String s_idUser, s_status, s_kode, s_tgl, s_nama, s_nik, s_scanKtp, s_npwp, s_scanNpwp, s_noHp, s_alamat, s_email, s_nominal, s_tenor, s_username, s_password;
    private String URL_UPDATE = "http://kristoforus.my.id/api_android/update_calon_debitur_admin.php";
    private TextView kodeDebitur, tglPengajuan, nama, nik, scanKtp, npwp, scanNpwp, noHp, alamat, email, nominal, tenor, username, password, status;
    private String URL_FILE_KTP = "http://kristoforus.my.id/scan_ktp/";
    private String URL_FILE_NPWP = "http://kristoforus.my.id/scan_npwp/";
    private String URL_LENGKAP_KTP;
    private String URL_LENGKAP_NPWP;
    private String extension;
    private String extensionNPWP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_calon_debitur);
        kodeDebitur = findViewById(R.id.tx_kode_debitur);
        tglPengajuan = findViewById(R.id.tanggalPengajuanDetailCalonDebitur);
        nama = findViewById(R.id.namaDetailCalonDebitur);
        nik = findViewById(R.id.nikDetailCalonDebitur);
        scanKtp = findViewById(R.id.fileKtpDetailCalonDebitur);
        npwp = findViewById(R.id.noNpwpDetailCalonDebitur);
        scanNpwp = findViewById(R.id.fileNpwpDetailCalonDebitur);
        noHp = findViewById(R.id.noHpDetailCalonDebitur);
        alamat = findViewById(R.id.alamarDetailCalonDebitur);
        email = findViewById(R.id.emailDetailCalonDebitur);
        nominal = findViewById(R.id.nominalDetailCalonDebitur);
        tenor = findViewById(R.id.tenorBulanDetailCalonDebitur);
        username = findViewById(R.id.usernameDetailCalonDebitur);
        password = findViewById(R.id.passwordDetailCalonDebitur);
        status = findViewById(R.id.statusDetailCalonDebitur);
        Debitur debitur = getIntent().getParcelableExtra(DETAIL_DEBITUR);
        s_idUser = debitur.getId_user();
        s_status = debitur.getStatus();
        s_kode = debitur.getKodeDebitur();
        s_tgl = debitur.getTanggalPengajuan();
        s_nama = debitur.getNamaDebitur();
        s_nik = debitur.getNik();
        s_scanKtp = debitur.getFileKtp();
        s_npwp = debitur.getNoNpwp();
        s_scanNpwp = debitur.getFileNpwp();
        s_noHp = debitur.getNoHp();
        s_alamat = debitur.getAlamat();
        s_email = debitur.getEmail();
        s_nominal = debitur.getNominal();
        s_tenor = debitur.getTenorBulan();
        s_username = debitur.getUsername();
        s_password = debitur.getPassword();
        if (s_status.equals("0")) {
            updateStatus(s_idUser);
            status.setText("Proses");
        } else if (s_status.equals("1")) {
            status.setText("Proses");
        } else if (s_status.equals("2")) {
            status.setText("Disetujui");
        }
        kodeDebitur.setText("Kode Debitur : " + s_kode);
        tglPengajuan.setText(s_tgl);
        nama.setText(s_nama);
        nik.setText(s_nik);
        scanKtp.setText(s_scanKtp);
        npwp.setText(s_npwp);
        scanNpwp.setText(s_scanNpwp);
        noHp.setText(s_noHp);
        alamat.setText(s_alamat);
        email.setText(s_email);
        nominal.setText(s_nominal);
        tenor.setText(s_tenor);
        username.setText(s_username);
        password.setText(s_password);

        URL_LENGKAP_KTP = URL_FILE_KTP + s_scanKtp;
        URL_LENGKAP_NPWP = URL_FILE_NPWP + s_scanNpwp;

        extension = s_scanKtp.substring(s_scanKtp.lastIndexOf("."));
        extensionNPWP = s_scanNpwp.substring(s_scanNpwp.lastIndexOf("."));


        scanKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extension.equals(".pdf")) {
                    download_ktp(URL_LENGKAP_KTP, s_scanKtp);
                }
                if (extension.equals(".jpg")) {
                    Intent intent = new Intent(DetailCalonDebitur.this, GaleryViewer.class);
                    intent.putExtra("urlImage", URL_LENGKAP_KTP);
                    intent.putExtra("nameFile", s_scanKtp);
                    startActivity(intent);
                }
                if (extension.equals(".png")) {
                    Intent intent = new Intent(DetailCalonDebitur.this, GaleryViewer.class);
                    intent.putExtra("urlImage", URL_LENGKAP_KTP);
                    intent.putExtra("nameFile", s_scanKtp);
                    startActivity(intent);
                }
            }
        });
        scanNpwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extensionNPWP.equals(".pdf")) {
                    download_npwp(URL_LENGKAP_NPWP, s_scanNpwp);
                }
                if (extensionNPWP.equals(".jpg")) {
                    Intent intent = new Intent(DetailCalonDebitur.this, GaleryViewer.class);
                    intent.putExtra("urlImage", URL_LENGKAP_NPWP);
                    intent.putExtra("nameFile", s_scanNpwp);
                    startActivity(intent);
                }
                if (extensionNPWP.equals(".png")) {
                    Intent intent = new Intent(DetailCalonDebitur.this, GaleryViewer.class);
                    intent.putExtra("urlImage", URL_LENGKAP_NPWP);
                    intent.putExtra("nameFile", s_scanNpwp);
                    startActivity(intent);
                }
            }
        });


    }

    public void download_ktp(String aUrl, String filename) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", filename)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {

                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Intent intent = new Intent(DetailCalonDebitur.this, PDFViewer.class);
                        intent.putExtra("urlKtp", URL_LENGKAP_KTP);
                        intent.putExtra("ktp", s_scanKtp);
                        intent.putExtra("trigger", "ktp");
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    public void download_npwp(String aUrl, String filename) {
        AndroidNetworking.download(aUrl, "/storage/emulated/0/Download/", filename)
                .setTag("downloadTest")
                .setPriority(Priority.MEDIUM)
                .addHeaders("Authorization", "Basic YnNyZTpzZWN1cmV0cmFuc2FjdGlvbnMhISE=")
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {

                        // do anything with progress
                    }
                })
                .startDownload(new DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        // do anything after completion
//                        progressDialog.dismiss();
                        Intent intent = new Intent(DetailCalonDebitur.this, PDFViewer.class);
                        intent.putExtra("urlNpwp", URL_LENGKAP_NPWP);
                        intent.putExtra("npwp", s_scanNpwp);
                        intent.putExtra("trigger", "npwp");
                        startActivity(intent);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    private void updateStatus(final String id_user) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("status");
                    if (success.equals("1")) {
                        Toast.makeText(DetailCalonDebitur.this, "Update Status Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailCalonDebitur.this, "Update Status Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
