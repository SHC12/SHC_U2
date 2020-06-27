package com.mobile.ukd.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Debitur implements Parcelable {
    public static final Creator<Debitur> CREATOR = new Creator<Debitur>() {
        @Override
        public Debitur createFromParcel(Parcel in) {
            return new Debitur(in);
        }

        @Override
        public Debitur[] newArray(int size) {
            return new Debitur[size];
        }
    };
    @SerializedName("no")
    private String no;
    @SerializedName("id_format_debitur")
    private String kodeDebitur;
    @SerializedName("id_debitur")
    private String idDebitur;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("tanggal_pengajuan")
    private String tanggalPengajuan;
    @SerializedName("nama_lengkap")
    private String namaDebitur;
    @SerializedName("nik")
    private String nik;
    @SerializedName("scan_ktp")
    private String fileKtp;
    @SerializedName("npwp")
    private String noNpwp;
    @SerializedName("scan_npwp")
    private String fileNpwp;
    @SerializedName("no_hp")
    private String noHp;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("email")
    private String email;
    @SerializedName("nominal_debitur")
    private String nominal;
    @SerializedName("tenor")
    private String tenorBulan;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("status")
    private String status;
    public Debitur(String no, String kodeDebitur, String idDebitur, String id_user, String tanggalPengajuan, String namaDebitur, String nik, String fileKtp, String noNpwp, String fileNpwp, String noHp, String alamat, String email, String nominal, String tenorBulan, String username, String password, String status) {
        this.no = no;
        this.kodeDebitur = kodeDebitur;
        this.idDebitur = idDebitur;
        this.id_user = id_user;
        this.tanggalPengajuan = tanggalPengajuan;
        this.namaDebitur = namaDebitur;
        this.nik = nik;
        this.fileKtp = fileKtp;
        this.noNpwp = noNpwp;
        this.fileNpwp = fileNpwp;
        this.noHp = noHp;
        this.alamat = alamat;
        this.email = email;
        this.nominal = nominal;
        this.tenorBulan = tenorBulan;
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public Debitur() {
    }

    protected Debitur(Parcel in) {
        no = in.readString();
        kodeDebitur = in.readString();
        idDebitur = in.readString();
        id_user = in.readString();
        tanggalPengajuan = in.readString();
        namaDebitur = in.readString();
        nik = in.readString();
        fileKtp = in.readString();
        noNpwp = in.readString();
        fileNpwp = in.readString();
        noHp = in.readString();
        alamat = in.readString();
        email = in.readString();
        nominal = in.readString();
        tenorBulan = in.readString();
        username = in.readString();
        password = in.readString();
        status = in.readString();
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getIdDebitur() {
        return idDebitur;
    }

    public void setIdDebitur(String idDebitur) {
        this.idDebitur = idDebitur;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getKodeDebitur() {
        return kodeDebitur;
    }

    public void setKodeDebitur(String kodeDebitur) {
        this.kodeDebitur = kodeDebitur;
    }

    public String getTanggalPengajuan() {
        return tanggalPengajuan;
    }

    public void setTanggalPengajuan(String tanggalPengajuan) {
        this.tanggalPengajuan = tanggalPengajuan;
    }

    public String getNamaDebitur() {
        return namaDebitur;
    }

    public void setNamaDebitur(String namaDebitur) {
        this.namaDebitur = namaDebitur;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getFileKtp() {
        return fileKtp;
    }

    public void setFileKtp(String fileKtp) {
        this.fileKtp = fileKtp;
    }

    public String getNoNpwp() {
        return noNpwp;
    }

    public void setNoNpwp(String noNpwp) {
        this.noNpwp = noNpwp;
    }

    public String getFileNpwp() {
        return fileNpwp;
    }

    public void setFileNpwp(String fileNpwp) {
        this.fileNpwp = fileNpwp;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTenorBulan() {
        return tenorBulan;
    }

    public void setTenorBulan(String tenorBulan) {
        this.tenorBulan = tenorBulan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(no);
        dest.writeString(kodeDebitur);
        dest.writeString(idDebitur);
        dest.writeString(id_user);
        dest.writeString(tanggalPengajuan);
        dest.writeString(namaDebitur);
        dest.writeString(nik);
        dest.writeString(fileKtp);
        dest.writeString(noNpwp);
        dest.writeString(fileNpwp);
        dest.writeString(noHp);
        dest.writeString(alamat);
        dest.writeString(email);
        dest.writeString(nominal);
        dest.writeString(tenorBulan);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(status);
    }
}
