package com.mobile.ukd.api;

import com.mobile.ukd.model.Debitur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("get_calon_debitur_kabag.php")
    Call<List<Debitur>> getCalonDeiturKabag();

    @POST("get_calon_debitur.php")
    Call<List<Debitur>> getCalonDeiturAdmin();

    @POST("get_debitur.php")
    Call<List<Debitur>> getDeiturAktif();

    @POST("get_debitur_ditolak.php")
    Call<List<Debitur>> getDeiturDitolak();

    @POST("get_debitur.php")
    Call<List<Debitur>> getPembayaran();

}
