package Model.Entity;

/**
 *
 * @author PC
 */
public class DocGia {
    private int madocgia;
    private String tendocgia;
    private String sdt;
    private String email;
    private String diachi;
    private String gioitinh;

    public DocGia() {
    }

    public DocGia(int madocgia, String tendocgia, String sdt, String email, String diachi, String gioitinh) {
        this.madocgia = madocgia;
        this.tendocgia = tendocgia;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
    }

    public int getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(int madocgia) {
        this.madocgia = madocgia;
    }

    public String getTendocgia() {
        return tendocgia;
    }

    public void setTendocgia(String tendocgia) {
        this.tendocgia = tendocgia;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }
     
}
