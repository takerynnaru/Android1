package com.example.ministop;

import java.io.Serializable;

public class NGUOIDUNG implements Serializable {
    String manv;
    String tennhanvien;
    String sdtnhanvien;
    String gioitinh;
    String ngaysinh;
    String email;
    String diachi;
    String macv;
    String tendangnhap;
    String matkhau;
    String trangthai;
    String motacongviec;
    String hinhanh;

    public NGUOIDUNG(String manv, String tennhanvien, String sdtnhanvien, String gioitinh, String ngaysinh, String email, String diachi, String macv, String tendangnhap, String matkhau, String trangthai, String motacongviec, String hinhanh) {
        this.manv = manv;
        this.tennhanvien = tennhanvien;
        this.sdtnhanvien = sdtnhanvien;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.email = email;
        this.diachi = diachi;
        this.macv = macv;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.trangthai = trangthai;
        this.motacongviec = motacongviec;
        this.hinhanh = hinhanh;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getTennhanvien() {
        return tennhanvien;
    }

    public void setTennhanvien(String tennhanvien) {
        this.tennhanvien = tennhanvien;
    }

    public String getSdtnhanvien() {
        return sdtnhanvien;
    }

    public void setSdtnhanvien(String sdtnhanvien) {
        this.sdtnhanvien = sdtnhanvien;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMacv() {
        return macv;
    }

    public void setMacv(String macv) {
        this.macv = macv;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getMotacongviec() {
        return motacongviec;
    }

    public void setMotacongviec(String motacongviec) {
        this.motacongviec = motacongviec;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
