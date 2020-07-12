package com.mobile.ukd.api;

import com.mobile.ukd.model.Debitur;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("get_calon_debitur.php")
    Call<List<Debitur>> getCalonDeiturAdmin();

    @POST("get_debitur.php")
    Call<List<Debitur>> getDeiturAktif();

    @POST("get_debitur_ditolak.php")
    Call<List<Debitur>> getDeiturDitolak();

    @POST("get_debitur.php")
    Call<List<Debitur>> getPembayaran();

    @FormUrlEncoded
    @POST("hapus_debitur.php")
    Call<ResponseBody> hapusDebitur(
            @Field("id") String id);

    @FormUrlEncoded
    @POST("update_debitur.php")
    Call<ResponseBody> updateDebitur(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("nik") String nik,
            @Field("npwp") String npwp,
            @Field("nope") String nope,
            @Field("alamat") String alamat,
            @Field("email") String email,
            @Field("username") String username,
            @Field("password") String password
    );

}
