package com.muza.tugas;



public class Transaksi {
    private  String tipe_transaksi;
    private String keterangan;
    private String uid;
    private Long createdAt;
    private int jumlah;

    public Transaksi(String keterangan, String tipe_transaksi, int jumlah) {

    }


    public String getTipe_transaksi() {
        return tipe_transaksi;
    }

    public void setTipe_transaksi(String tipe_transaksi) {
        this.tipe_transaksi = tipe_transaksi;
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

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Transaksi(String Tipe_transaksi, String keterangan, String uid, int jumlah) {
        this.tipe_transaksi=tipe_transaksi;
        this.keterangan = keterangan;
        this.uid = uid;
        this.createdAt = createdAt;
        this.jumlah = jumlah;
    }


    public void setKey(String uid) {
    }
}

