package com.mobile.ukd;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PDFViewer extends AppCompatActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_viewer);

        pdfView = findViewById(R.id.pdfViewer);
        String trigger = getIntent().getStringExtra("trigger");
        String namaFileKtp = getIntent().getStringExtra("ktp");
        String namaFileNpwp = getIntent().getStringExtra("npwp");
        if(trigger.equals("ktp")){
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download/"+namaFileKtp);
            pdfView.fromFile(file).load();
        }
        if(trigger.equals("npwp")){
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Download/"+namaFileNpwp);
            pdfView.fromFile(file).load();
        }



    }
}
