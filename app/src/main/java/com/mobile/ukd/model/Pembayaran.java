package com.mobile.ukd.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pembayaran implements Parcelable {
    private String pembayaranKe;

    public String getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(String totalBayar) {
        this.totalBayar = totalBayar;
    }

    private String totalBayar;

    protected Pembayaran(Parcel in) {
        pembayaranKe = in.readString();
        tanggalPembayaran = in.readString();
        nominalPembayaran = in.readString();
        totalBayar = in.readString();
    }

    public Pembayaran(String pembayaranKe, String tanggalPembayaran, String nominalPembayaran,String totalBayar) {
        this.pembayaranKe = pembayaranKe;
        this.tanggalPembayaran = tanggalPembayaran;
        this.nominalPembayaran = nominalPembayaran;
        this.totalBayar = totalBayar;
    }

    public Pembayaran() {
    }

    public static final Creator<Pembayaran> CREATOR = new Creator<Pembayaran>() {
        @Override
        public Pembayaran createFromParcel(Parcel in) {
            return new Pembayaran(in);
        }

        @Override
        public Pembayaran[] newArray(int size) {
            return new Pembayaran[size];
        }
    };

    public String getPembayaranKe() {
        return pembayaranKe;
    }

    public void setPembayaranKe(String pembayaranKe) {
        this.pembayaranKe = pembayaranKe;
    }

    public String getTanggalPembayaran() {
        return tanggalPembayaran;
    }

    public void setTanggalPembayaran(String tanggalPembayaran) {
        this.tanggalPembayaran = tanggalPembayaran;
    }

    public String getNominalPembayaran() {
        return nominalPembayaran;
    }

    public void setNominalPembayaran(String nominalPembayaran) {
        this.nominalPembayaran = nominalPembayaran;
    }

    private String tanggalPembayaran;
    private String nominalPembayaran;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pembayaranKe);
        dest.writeString(tanggalPembayaran);
        dest.writeString(nominalPembayaran);
        dest.writeString(totalBayar);
    }
}
