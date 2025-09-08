package Model.Entity;

import java.time.LocalDateTime;

/**
 *
 * @author TUF
 */
public class PhieuMuon {

    private int maphieu;
    private int madocgia;
    private int manv;
    private LocalDateTime ngaymuon;
    private LocalDateTime ngaytradukien;
    private String trangthai;

    // Constructor mặc định
    public PhieuMuon() {
    }

    // Constructor với tất cả các thuộc tính
    public PhieuMuon(int maphieu, int madocgia, int manv, LocalDateTime ngaymuon, LocalDateTime ngaytradukien, String trangthai) {
        this.maphieu = maphieu;
        this.madocgia = madocgia;
        this.manv = manv;
        this.ngaymuon = ngaymuon;
        this.ngaytradukien = ngaytradukien;
        this.trangthai = trangthai;
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

    public LocalDateTime getNgaymuon() {
        return ngaymuon;
    }

    public void setNgaymuon(LocalDateTime ngaymuon) {
        this.ngaymuon = ngaymuon;
    }

    public LocalDateTime getNgayTraDuKien() {
        return ngaytradukien;
    }

    public void setNgayTraDuKien(LocalDateTime ngaytradukien) {
        this.ngaytradukien = ngaytradukien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
