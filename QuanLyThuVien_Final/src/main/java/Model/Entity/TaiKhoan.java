package Model.Entity;

/**
 *
 * @author PC
 */
public class TaiKhoan {
    private int matk;
    private int manv;
    private String tendangnhap;
    private String matkhau;
    private String role;

    public TaiKhoan() {
    }

    public TaiKhoan(int matk, int manv, String tendangnhap, String matkhau, String role) {
        this.matk = matk;
        this.manv = manv;
        this.tendangnhap = tendangnhap;
        this.matkhau = matkhau;
        this.role = role;
    }

    public int getMatk() {
        return matk;
    }

    public void setMatk(int matk) {
        this.matk = matk;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
