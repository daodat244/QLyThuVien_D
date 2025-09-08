package Model.Entity;

public class Sach {

    private String masach;
    private String tensach;
    private int matacgia;
    private int manxb;
    private int matheloai;
    private int namxb;
    private int sotrang;
    private int soluong;
    private byte[] anh;

    public Sach() {
    }

    public Sach(String masach, String tensach, int matacgia, int manxb, int matheloai, int namxb, int sotrang, int soluong, byte[] anh) {
        this.masach = masach;
        this.tensach = tensach;
        this.matacgia = matacgia;
        this.manxb = manxb;
        this.matheloai = matheloai;
        this.namxb = namxb;
        this.sotrang = sotrang;
        this.soluong = soluong;
        this.anh = anh;
    }

    public String getMasach() {
        return masach;
    }

    public void setMasach(String masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getMatacgia() {
        return matacgia;
    }

    public void setMatacgia(int matacgia) {
        this.matacgia = matacgia;
    }

    public int getManxb() {
        return manxb;
    }

    public void setManxb(int manxb) {
        this.manxb = manxb;
    }

    public int getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(int matheloai) {
        this.matheloai = matheloai;
    }

    public int getNamxb() {
        return namxb;
    }

    public void setNamxb(int namxb) {
        this.namxb = namxb;
    }

    public int getSotrang() {
        return sotrang;
    }

    public void setSotrang(int sotrang) {
        this.sotrang = sotrang;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

}
