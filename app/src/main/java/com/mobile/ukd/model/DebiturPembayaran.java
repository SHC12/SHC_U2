package com.mobile.ukd.model;

public class DebiturPembayaran {
    private String no;
    private String id_format_debitur;
    private String id__debitur;
    private String nama_lengkap;
    private String pinjaman;
    private String total_bayar;
    private String sisa_hutang;

    public DebiturPembayaran(String no, String id_format_debitur, String id__debitur, String nama_lengkap, String pinjaman, String total_bayar, String sisa_hutang) {
        this.no = no;
        this.id_format_debitur = id_format_debitur;
        this.id__debitur = id__debitur;
        this.nama_lengkap = nama_lengkap;
        this.pinjaman = pinjaman;
        this.total_bayar = total_bayar;
        this.sisa_hutang = sisa_hutang;
    }

    public DebiturPembayaran() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId_format_debitur() {
        return id_format_debitur;
    }

    public void setId_format_debitur(String id_format_debitur) {
        this.id_format_debitur = id_format_debitur;
    }

    public String getId__debitur() {
        return id__debitur;
    }

    public void setId__debitur(String id__debitur) {
        this.id__debitur = id__debitur;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getPinjaman() {
        return pinjaman;
    }

    public void setPinjaman(String pinjaman) {
        this.pinjaman = pinjaman;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getSisa_hutang() {
        return sisa_hutang;
    }

    public void setSisa_hutang(String sisa_hutang) {
        this.sisa_hutang = sisa_hutang;
    }
}
