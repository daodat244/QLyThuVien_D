package Model.Entity;

import java.util.Date;

/**
 *
 * @author PC
 */
public class NhanVien {
    private int manv;
    private String tennv;
    private String sdt;
    private Date ngaysinh;    
    private String quequan;
    private String gioitinh;
    
    public NhanVien() {
    }

    public NhanVien(int manv, String tennv, String sdt, Date ngaysinh, String quequan, String gioitinh) {
        this.manv = manv;
        this.tennv = tennv;
        this.sdt = sdt;
        this.ngaysinh = ngaysinh;
        this.quequan = quequan;
        this.gioitinh = gioitinh;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    
}