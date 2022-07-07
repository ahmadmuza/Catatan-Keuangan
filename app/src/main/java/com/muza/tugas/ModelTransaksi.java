package com.muza.tugas;

public class ModelTransaksi {
    private String keterangan;
    private String uid;
    private Long createdAt;
    private String jumlah;
    private String tipe_transaksi;
    public ModelTransaksi(){}
    public  ModelTransaksi(String keterangan, String uid, Long createdAt, String jumlah, String tipe_transaksi) {
        this.keterangan = keterangan;
        this.uid = uid;
        this.createdAt = createdAt;
        this.jumlah = jumlah;
        this.tipe_transaksi=tipe_transaksi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTipe_transaksi() {
        return tipe_transaksi;
    }

    public void setTipe_transaksi(String tipe_transaksi) {
        this.tipe_transaksi = tipe_transaksi;
    }



}
