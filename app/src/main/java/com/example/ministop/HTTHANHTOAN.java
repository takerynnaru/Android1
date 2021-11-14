package com.example.ministop;

import java.io.Serializable;

public class HTTHANHTOAN implements Serializable {
    String idthanhtoan;
    String tenhinhthuc;
    String mota;
    String tinhtrang;

    public HTTHANHTOAN(String idthanhtoan, String tenhinhthuc, String mota, String tinhtrang) {
        this.idthanhtoan = idthanhtoan;
        this.tenhinhthuc = tenhinhthuc;
        this.mota = mota;
        this.tinhtrang = tinhtrang;
    }
    public String getIdthanhtoan() {
        return idthanhtoan;
    }

    public void setIdthanhtoan(String idthanhtoan) {
        this.idthanhtoan = idthanhtoan;
    }

    public String getTenhinhthuc() {
        return tenhinhthuc;
    }

    public void setTenhinhthuc(String tenhinhthuc) {
        this.tenhinhthuc = tenhinhthuc;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }






}
