package com.mobile.ukd.root;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.mobile.ukd.MenuLoginActivity;
import com.mobile.ukd.R;
import com.mobile.ukd.debitur.DashboardDebitur;

public class MenuRoot extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_root);
    }

    public void toLogoutRoot(View view) {
        SharedPreferences sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(MenuRoot.this, MenuLoginActivity.class));
    }

    public void toEditInfo(View view) {
        startActivity(new Intent(MenuRoot.this,EditInfoAplikasi.class));
    }

    public void toEditNotifPengajuan(View view) {
        startActivity(new Intent(MenuRoot.this,EditNotifikasiPengajuan.class));
    }
}
