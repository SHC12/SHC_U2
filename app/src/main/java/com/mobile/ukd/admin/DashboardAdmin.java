package com.mobile.ukd.admin;

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

public class DashboardAdmin extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    String mUsername;
    private boolean doubleBackToExitPressedOnce = false;
    private String nama;
    private TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mUsername = settings.getString("username", "default");
        nama = settings.getString("nama", "default");
        String[] first = nama.split(" ", 2);
        mName = findViewById(R.id.usernameAdmin);
        mName.setText(first[0]);
    }


    public void toDataCalonDebiturAdmin(View view) {
        Intent i = new Intent(DashboardAdmin.this, DataCalonDebitur.class);
        i.putExtra("flag", "admin");
        startActivity(i);
    }

    public void toDataPembayaranAdmin(View view) {
        startActivity(new Intent(DashboardAdmin.this, DataPembayaranDebitur.class));
    }

    public void toInputPembayaran(View view) {
        Intent i = new Intent(DashboardAdmin.this, DataCalonDebitur.class);
        i.putExtra("flag", "inputPembayaran");
        startActivity(i);
    }

    public void toAccountAdmin(View view) {
        startActivity(new Intent(DashboardAdmin.this, AccountActivity.class));
    }

    public void toLogoutAdmin(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(DashboardAdmin.this, MenuLoginActivity.class));
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
