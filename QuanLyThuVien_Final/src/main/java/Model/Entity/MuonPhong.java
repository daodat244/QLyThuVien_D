package Model.Entity;


import java.time.LocalDateTime;

public class MuonPhong {
    private int maphieump;
    private int maphong;
    private int madocgia;
    private LocalDateTime tgianmuon;
    private LocalDateTime tgiantra;
    private String ghichu;
    private String trangthai;

    public MuonPhong() {
    }
    
    public MuonPhong(int maphieump, int maphong, int madocgia, LocalDateTime tgianmuon, LocalDateTime tgiantra, String ghichu, String trangthai) {
        this.maphieump = maphieump;
        this.maphong = maphong;
        this.madocgia = madocgia;
        this.tgianmuon = tgianmuon;
        this.tgiantra = tgiantra;
        this.ghichu = ghichu;
        this.trangthai = trangthai;
    }
    
    
    public int getMaphieump() {
        return maphieump;
    }
    
    public void setMaphieump(int maphieump) {
        this.maphieump = maphieump;
    }
    
    public int getMaphong() {
        return maphong;
    }

    public void setMaphong(int maphong) {
        this.maphong = maphong;
    }

    public int getMadocgia() {
        return madocgia;
    }

    public void setMadocgia(int madocgia) {
        this.madocgia = madocgia;
    }

    public LocalDateTime getTgianmuon() {
        return tgianmuon;
    }

    public void setTgianmuon(LocalDateTime tgianmuon) {
        this.tgianmuon = tgianmuon;
    }
    
    public LocalDateTime getTgiantra() {
        return tgiantra;
    }

    public void setTgiantra(LocalDateTime tgiantra) {
        this.tgiantra = tgiantra;
    }
    
    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
    
}
