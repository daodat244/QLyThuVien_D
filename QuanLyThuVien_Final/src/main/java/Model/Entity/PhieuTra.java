/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entity;

import java.time.LocalDateTime;

/**
 *
 * @author PC
 */
public class PhieuTra {

    private int maphieutra;
    private int maphieu;
    private int madocgia;
    private int manv;
    private String masach;
    private LocalDateTime ngaymuon;
    private LocalDateTime ngaytradukien;
    private LocalDateTime ngaytrathucte;
    private double tongphiphat;
    private String ghichu;
    private String tinhtrang;

    public PhieuTra() {
    }

    public PhieuTra(int maphieutra, int maphieu, int madocgia, int manv, String masach, LocalDateTime ngaymuon, LocalDateTime ngaytradukien, LocalDateTime ngaytrathucte, double tongphiphat, String ghichu, String tinhtrang) {
        this.maphieutra = maphieutra;
        this.maphieu = maphieu;
        this.madocgia = madocgia;
        this.manv = manv;
        this.masach = masach;
        this.ngaymuon = ngaymuon;
        this.ngaytradukien = ngaytradukien;
        this.ngaytrathucte = ngaytrathucte;
        this.tongphiphat = tongphiphat;
        this.ghichu = ghichu;
        this.tinhtrang = tinhtrang;
    }

    public int getMaphieutra() {
        return maphieutra;
    }

    public void setMaphieutra(int maphieutra) {
        this.maphieutra = maphieutra;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public int getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(int madocgia) {
        this.madocgia = madocgia;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public LocalDateTime getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(LocalDateTime ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public LocalDateTime getNgaytradukien() {
        return ngaytradukien;
    }

    public void setNgaytradukien(LocalDateTime ngaytradukien) {
        this.ngaytradukien = ngaytradukien;
    }

    public LocalDateTime getNgaytrathucte() {
        return ngaytrathucte;
    }

    public void setNgaytrathucte(LocalDateTime ngaytrathucte) {
        this.ngaytrathucte = ngaytrathucte;
    }

    public double getTongphiphat() {
        return tongphiphat;
    }

    public void setTongphiphat(double tongphiphat) {
        this.tongphiphat = tongphiphat;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

}
