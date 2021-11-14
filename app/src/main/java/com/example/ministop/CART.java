package com.example.ministop;

import java.io.Serializable;

public class CART implements Serializable {
    String idsp;
    String hinhsp;
    String tensp;
    String giasp;
    int soluong;

    public CART(String idsp, String hinhsp, String tensp, String giasp, int soluong) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.hinhsp = hinhsp;
        this.giasp = giasp;
        this.soluong = soluong;
    }






    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }
}
