package Model.Entity;

import java.time.LocalDateTime;

public class SuKien {
    private int masukien;
    private String tensukien;
    private int manxb;
    private LocalDateTime tgiantochuc;
    private LocalDateTime tgianketthuc;
    private String mota;

    public SuKien() {
    }

    public SuKien(int masukien, String tensukien, int manxb, LocalDateTime tgiantochuc, LocalDateTime tgianketthuc, String mota) {
        this.masukien = masukien;
        this.tensukien = tensukien;
        this.manxb = manxb;
        this.tgiantochuc = tgiantochuc;
        this.tgianketthuc = tgianketthuc;
        this.mota = mota;
    }

    public int getMasukien() {
        return masukien;
    }

    public void setMasukien(int masukien) {
        this.masukien = masukien;
    }

    public String getTensukien() {
        return tensukien;
    }

    public void setTensukien(String tensukien) {
        this.tensukien = tensukien;
    }

    public int getManxb() {
        return manxb;
    }

    public void setManxb(int manxb) {
        this.manxb = manxb;
    }

    public LocalDateTime getTgiantochuc() {
        return tgiantochuc;
    }

    public void setTgiantochuc(LocalDateTime tgiantochuc) {
        this.tgiantochuc = tgiantochuc;
    }

    public LocalDateTime getTgianketthuc() {
        return tgianketthuc;
    }

    public void setTgianketthuc(LocalDateTime tgianketthuc) {
        this.tgianketthuc = tgianketthuc;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }


}
