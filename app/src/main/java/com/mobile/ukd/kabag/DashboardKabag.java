package com.mobile.ukd.kabag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobile.ukd.AccountActivity;
import com.mobile.ukd.DataCalonDebitur;
import com.mobile.ukd.MenuLoginActivity;
import com.mobile.ukd.R;

public class DashboardKabag extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    private boolean doubleBackToExitPressedOnce = false;
    private String nama;
    private TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_kabag);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        nama = settings.getString("nama", "default");
        mName = findViewById(R.id.usernameKabag);
        mName.setText(nama);
    }
    

    public void toPersetujuanDebitur(View view) {
        Intent i = new Intent(DashboardKabag.this,DataCalonDebitur.class);
        i.putExtra("flag","kabag");
        startActivity(i);
    }

    public void toDataDebiturAktif(View view) {
        Intent i = new Intent(DashboardKabag.this,DataCalonDebitur.class);
        i.putExtra("flag","debiturAktif");
        startActivity(i);
    }

    public void toDataDebiturDitolak(View view) {
        Intent i = new Intent(DashboardKabag.this,DataCalonDebitur.class);
        i.putExtra("flag","debiturDitolak");
        startActivity(i);
    }

    public void toAccountKabag(View view) {
        startActivity(new Intent(DashboardKabag.this, AccountActivity.class));
    }

    public void toLogoutKabag(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(DashboardKabag.this, MenuLoginActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar dari UKD", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
