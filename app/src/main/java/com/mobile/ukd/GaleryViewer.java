package com.mobile.ukd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.google.android.material.button.MaterialButton;
import com.mobile.ukd.admin.DetailCalonDebitur;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;
import com.veinhorn.scrollgalleryview.builder.GallerySettings;

import static ogbe.ozioma.com.glideimageloader.dsl.DSL.image;

public class GaleryViewer extends AppCompatActivity {
    private ScrollGalleryView galleryView;
    private MaterialButton btnDownloadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery_viewer);
        String urlImge = getIntent().getStringExtra("urlImage");
        String nameFile = getIntent().getStringExtra("nameFile");
        btnDownloadImage = findViewById(R.id.btn_download_image);
        galleryView = findViewById(R.id.scroll_gallery_view);
        galleryView = ScrollGalleryView
                .from((ScrollGalleryView) findViewById(R.id.scroll_gallery_view))
                .settings(
                        GallerySettings
                                .from(getSupportFragmentManager())
                                .thumbnailSize(100)
                                .enableZoom(true)
                                .build()
                )
                .add(image(urlImge))
                .build();

        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download_ktp(urlImge,nameFile);
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
                        Toast.makeText(GaleryViewer.this, "Foto berhasil di simpan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    }

