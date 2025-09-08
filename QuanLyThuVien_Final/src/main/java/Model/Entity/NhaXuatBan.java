package Model.Entity;

/**
 *
 * @author PC
 */
public class NhaXuatBan {
    private int manxb;
    private String tennxb;
    private String sdt;
    private String email;    
    private String diachi;
    private String quocgia;

    public NhaXuatBan() {
    }

    public NhaXuatBan(int manxb, String tennxb, String sdt, String email, String diachi, String quocgia) {
        this.manxb = manxb;
        this.tennxb = tennxb;
        this.sdt = sdt;
        this.email = email;
        this.diachi = diachi;
        this.quocgia = quocgia;
    }

    public int getManxb() {
        return manxb;
    }

    public void setManxb(int manxb) {
        this.manxb = manxb;
    }

    public String getTennxb() {
        return tennxb;
    }

    public void setTennxb(String tennxb) {
        this.tennxb = tennxb;
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

    public String getQuocgia() {
        return quocgia;
    }

    public void setQuocgia(String quocgia) {
        this.quocgia = quocgia;
    }
    
    @Override
    public String toString() {
        return tennxb; // Hiển thị trong JComboBox
    }
}
