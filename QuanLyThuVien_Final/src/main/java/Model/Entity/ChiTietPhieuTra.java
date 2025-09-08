
package Model.Entity;

public class ChiTietPhieuTra {

    private int id;
    private int maphieutra;
    private String masach;
    private double phiphat;
    private String tinhtrang;

    public ChiTietPhieuTra() {
    }

    public ChiTietPhieuTra(int id, int maphieutra, String masach, double phiphat, String tinhtrang) {
        this.id = id;
        this.maphieutra = maphieutra;
        this.masach = masach;
        this.phiphat = phiphat;
        this.tinhtrang = tinhtrang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaphieutra() {
        return maphieutra;
    }

    public void setMaphieutra(int maphieutra) {
        this.maphieutra = maphieutra;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public double getPhiphat() {
        return phiphat;
    }

    public void setPhiphat(double phiphat) {
        this.phiphat = phiphat;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public void setTinhtrang(String tinhtrang) {
        this.tinhtrang = tinhtrang;
    }

}
