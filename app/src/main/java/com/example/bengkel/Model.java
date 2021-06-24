package com.example.bengkel;

public class Model {

    String id, tanggal, plat, mj, pemilik, keluhan;

    public Model(){

    }

    public Model(String id, String tanggal, String plat, String mj, String pemilik, String keluhan) {
        this.id = id;
        this.tanggal = tanggal;
        this.plat = plat;
        this.mj = mj;
        this.pemilik = pemilik;
        this.keluhan = keluhan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getMj() {
        return mj;
    }

    public void setMj(String mj) {
        this.mj = mj;
    }

    public String getPemilik() {
        return pemilik;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }
}
