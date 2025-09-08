/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Entity;

/**
 *
 * @author PC
 */
public class TheLoai {
    private int matheloai;
    private String tentheloai;
    private String mota;

    public TheLoai() {
    }

    public TheLoai(int matheloai, String tentheloai, String mota) {
        this.matheloai = matheloai;
        this.tentheloai = tentheloai;
        this.mota = mota;
    }

    public int getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(int matheloai) {
        this.matheloai = matheloai;
    }

    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
    
    @Override
    public String toString() {
        return tentheloai; // Hiển thị trong JComboBox
    }
}
