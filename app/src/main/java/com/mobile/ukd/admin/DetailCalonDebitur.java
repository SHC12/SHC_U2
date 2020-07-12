package com.mobile.ukd.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.google.android.material.button.MaterialButton;
import com.mobile.ukd.DataCalonDebitur;
import com.mobile.ukd.GaleryViewer;
import com.mobile.ukd.PDFViewer;
import com.mobile.ukd.R;
import com.mobile.ukd.api.ApiClient;
import com.mobile.ukd.api.ApiInterface;
import com.mobile.ukd.model.Debitur;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCalonDebitur extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String DETAIL_DEBITUR = "detail_debitur";
    private String s_idUser, s_status, s_kode, s_tgl, s_nama, s_nik, s_scanKtp, s_npwp, s_scanNpwp, s_noHp, s_alamat, s_email, s_nominal, s_tenor, s_username, s_password, s_tangguangan, s_pKotor, s_pKeluarga, s_pBersih, s_usaha, s_pBersihBulan;
    private String URL_UPDATE = "http://kristoforus.my.id/api_android/update_calon_debitur_admin.php";
    private TextView kodeDebitur, tglPengajuan, nama, nik, scanKtp, npwp, scanNpwp, noHp, alamat, email, nominal, tenor, username, password, status, tanggungan, kotor, keluarga, bersihPerhari, usaha, bersihPerbulan;
    private String URL_FILE_KTP = "http://kristoforus.my.id/scan_ktp/";
    private String URL_FILE_NPWP = "http://kristoforus.my.id/scan_npwp/";
    private String URL_LENGKAP_KTP;
    private String URL_LENGKAP_NPWP;
    private String extension;
    private String extensionNPWP;
    private MaterialButton btnUpdate, btnHapus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_calon_debitur);
        kodeDebitur = findViewById(R.id.tx_kode_debitur);
        tglPengajuan = findViewById(R.id.tanggalPengajuanDetailCalonDebitur);
        btnHapus = findViewById(R.id.btnHapusDebitur);
        btnUpdate = findViewById(R.id.btnUpdateDetailDebitur);
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
        tanggungan = findViewById(R.id.tanggunanDetailCalonDebitur);
        kotor = findViewById(R.id.pendapatanKotorDetailCalonDebitur);
        keluarga = findViewById(R.id.pengeluaranKeluargaDetailCalonDebitur);
        bersihPerhari = findViewById(R.id.pendapatanBersihPerhariDetailCalonDebitur);
        usaha = findViewById(R.id.jumlahHariUsahaDetailCalonDebitur);
        bersihPerbulan = findViewById(R.id.pendapatanBersihPerbulanDetailCalonDebitur);
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
        s_tangguangan = debitur.getJumlah_tanggunan();
        s_pKotor = debitur.getPendapatanKotorPerhari();
        s_pKeluarga = debitur.getPengeluaranKeluargaPerhari();
        s_pBersih = debitur.getPendapatanBersihPerhari();
        s_usaha = debitur.getJumlahHariUsahaSebulan();
        s_pBersihBulan = debitur.getJumlahPendapatanBersihPerbulan();
        if (s_status.equals("0")) {
            status.setText("Tidak Layak");
        } else if (s_status.equals("1")) {
            status.setText("Layak");
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
        tanggungan.setText(s_tangguangan);
        kotor.setText(s_pKotor);
        keluarga.setText(s_pKeluarga);
        bersihPerhari.setText(s_pBersih);
        usaha.setText(s_usaha);
        bersihPerbulan.setText(s_pBersihBulan);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String mLevel = settings.getString("level", "default");
        if (mLevel.equals("3")) {
            btnHapus.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
        }
        URL_LENGKAP_KTP = URL_FILE_KTP + s_scanKtp;
        URL_LENGKAP_NPWP = URL_FILE_NPWP + s_scanNpwp;

        extension = s_scanKtp.substring(s_scanKtp.lastIndexOf("."));
        extensionNPWP = s_scanNpwp.substring(s_scanNpwp.lastIndexOf("."));
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailCalonDebitur.this);
                builder.setCancelable(true);
                builder.setTitle("Hapus Debitur");
                builder.setMessage("Anda yakin ingin menghapus debitur ini ?");
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusUser(s_idUser);
                    }
                });
                builder.show();

            }

        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailCalonDebitur.this, UpdateDebitur.class);
                i.putExtra("id", s_idUser);
                i.putExtra("nama", s_nama);
                i.putExtra("nik", s_nik);
                i.putExtra("noNpwp", s_npwp);
                i.putExtra("noHp", s_noHp);
                i.putExtra("alamat", s_alamat);
                i.putExtra("email", s_email);
                i.putExtra("username", s_username);
                i.putExtra("password", s_password);
                startActivity(i);

            }
        });

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

    private void hapusUser(String s_idUser) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> updateDebitur = apiInterface.hapusDebitur(s_idUser);
        updateDebitur.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject o = new JSONObject(response.body().string());
                    if (o.getString("status").equals("1")) {
                        Toast.makeText(DetailCalonDebitur.this, "Debitur berhasil di hapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailCalonDebitur.this, DataCalonDebitur.class).putExtra("flag", "admin"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailCalonDebitur.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

}
