package Model.Entity;

/**
 *
 * @author PC
 */
public class TacGia {
    private int matacgia;
    private String tentacgia;
    private int namsinh;
    private String quequan;    
    private String mota;
    private String sdt;
    private String email;
    private String quocgia;

    public TacGia() {
    }

    public TacGia(int matacgia, String tentacgia, int namsinh, String quequan, String mota, String sdt, String email, String quocgia) {
        this.matacgia = matacgia;
        this.tentacgia = tentacgia;
        this.namsinh = namsinh;
        this.quequan = quequan;
        this.mota = mota;
        this.sdt = sdt;
        this.email = email;
        this.quocgia = quocgia;
    }

    public int getMatacgia() {
        return matacgia;
    }

    public void setMatacgia(int matacgia) {
        this.matacgia = matacgia;
    }

    public String getTentacgia() {
        return tentacgia;
    }

    public void setTentacgia(String tentacgia) {
        this.tentacgia = tentacgia;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
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

    public String getQuocgia() {
        return quocgia;
    }

    public void setQuocgia(String quocgia) {
        this.quocgia = quocgia;
    }
    
    @Override
    public String toString() {
        return tentacgia; 
    }
}
