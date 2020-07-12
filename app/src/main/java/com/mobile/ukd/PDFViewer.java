package com.mobile.ukd;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.button.MaterialButton;

import java.io.File;

public class PDFViewer extends AppCompatActivity {
    MaterialButton btnDownload;
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_viewer);
        btnDownload = findViewById(R.id.btnDownloadPDF);
        pdfView = findViewById(R.id.pdfViewer);
        String trigger = getIntent().getStringExtra("trigger");
        String namaFileKtp = getIntent().getStringExtra("ktp");
        String namaFileNpwp = getIntent().getStringExtra("npwp");
        if(trigger.equals("ktp")) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download/" + namaFileKtp);
            pdfView.fromFile(file).load();
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadPDF(namaFileKtp);
                }
            });
        }
        if(trigger.equals("npwp")) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download/" + namaFileNpwp);
            pdfView.fromFile(file).load();
            btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadPDF(namaFileNpwp);
                }
            });
        }


    }

    private void downloadPDF(String pdfUrl) {
        Toast.makeText(this, pdfUrl + " Berhasil tersimpan di folder Download", Toast.LENGTH_SHORT).show();
    }
}
