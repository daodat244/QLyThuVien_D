package Model.Entity;

/**
 *
 * @author TUF
 */
public class ChiTietPhieuMuon {

    private int id;
    private int maphieu;
    private String masach;


    public ChiTietPhieuMuon(int maphieu, String masach) {
    }

    public ChiTietPhieuMuon(int id, int maphieu, String masach) {
        this.id = id;
        this.maphieu = maphieu;
        this.masach = masach;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaphieu() {
        return maphieu;
    }

    public void setMaphieu(int maphieu) {
        this.maphieu = maphieu;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

}
