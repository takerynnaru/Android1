package com.example.ministop;

import java.io.Serializable;

public class Products implements Serializable {


    String ma;
    String ten;
    String gia;
    String hinh;
    String mota;
    String madm;

    public Products(String ma, String ten, String gia, String hinh, String mota, String madm) {
        this.ma = ma;
        this.ten = ten;
        this.gia = gia;
        this.hinh = hinh;
        this.mota = mota;
        this.madm = madm;
    }






    public String getMadm() {
        return madm;
    }

    public void setMadm(String madm) {
        this.madm = madm;
    }





    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
