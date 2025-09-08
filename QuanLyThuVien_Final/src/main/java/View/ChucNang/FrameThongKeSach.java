
package View.ChucNang;

import Control.ThongKeController;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FrameThongKeSach extends javax.swing.JFrame {
    private final ThongKeController controller;
    public FrameThongKeSach() {
        initComponents();
        this.controller = new ThongKeController(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblDauSach = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblSoLuongSach = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblSachConLai = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblSachDangMuon = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblMostBorrow = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Tổng số lượng đầu sách:");

        lblDauSach.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblDauSach.setText("jLabel3");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Tổng số lượng sách:");

        lblSoLuongSach.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSoLuongSach.setText("jLabel4");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Sách được mượn nhiều nhất:");

        lblSachConLai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSachConLai.setText("jLabel4");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Tổng số lượng sách Đang được mượn:");

        lblSachDangMuon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblSachDangMuon.setText("jLabel4");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Tổng số lượng sách còn trong kho:");

        lblMostBorrow.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMostBorrow.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDauSach))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSachConLai))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoLuongSach))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSachDangMuon))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMostBorrow)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblDauSach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblSoLuongSach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblSachDangMuon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSachConLai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblMostBorrow))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  
    public JLabel getLblDauSach(){
        return lblDauSach;
    }
    public JLabel getLblSoLuongSach(){
        return lblSoLuongSach;
    }
    public JLabel getLblDangMuon(){
        return lblSachDangMuon;
    }
    public JLabel getLblSachConLai(){
        return lblSachConLai;
    }
    public JLabel getLblMostBorrow(){
        return lblMostBorrow;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblDauSach;
    private javax.swing.JLabel lblMostBorrow;
    private javax.swing.JLabel lblSachConLai;
    private javax.swing.JLabel lblSachDangMuon;
    private javax.swing.JLabel lblSoLuongSach;
    // End of variables declaration//GEN-END:variables
}
