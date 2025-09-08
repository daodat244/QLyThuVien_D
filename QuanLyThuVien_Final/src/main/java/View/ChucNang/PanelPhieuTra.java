/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package View.ChucNang;

import Control.PhieuTraController;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author TUF
 */
public class PanelPhieuTra extends javax.swing.JPanel {

    private PhieuTraController controller;

    public PanelPhieuTra() {
        initComponents();
        this.controller = new PhieuTraController(this);

        String[] trangThaiSach = {"Nguyên vẹn", "Hư hỏng/Mất"};
        cbsach1.setModel(new DefaultComboBoxModel<>(trangThaiSach));
        cbsach2.setModel(new DefaultComboBoxModel<>(trangThaiSach));
        cbsach3.setModel(new DefaultComboBoxModel<>(trangThaiSach));
        cbsach4.setModel(new DefaultComboBoxModel<>(trangThaiSach));
        cbsach5.setModel(new DefaultComboBoxModel<>(trangThaiSach));

        // Ẩn tất cả các JLabel và JComboBox ban đầu
        controller.clearBookFields();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablePhieuTra = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblghichu = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtMaphieu = new javax.swing.JTextField();
        tblMaphieu1 = new javax.swing.JLabel();
        tbltendocgia = new javax.swing.JLabel();
        txttendocgia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        dpngaymuon = new com.github.lgooddatepicker.components.DateTimePicker();
        dpngayhentra = new com.github.lgooddatepicker.components.DateTimePicker();
        jLabel2 = new javax.swing.JLabel();
        date = new com.github.lgooddatepicker.components.DateTimePicker();
        btntrasach = new javax.swing.JButton();
        btnxuatdulieu = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        tblsach1 = new javax.swing.JLabel();
        tblsach2 = new javax.swing.JLabel();
        tblsach3 = new javax.swing.JLabel();
        tblsach4 = new javax.swing.JLabel();
        tblsach5 = new javax.swing.JLabel();
        cbsach1 = new javax.swing.JComboBox<>();
        cbsach2 = new javax.swing.JComboBox<>();
        cbsach3 = new javax.swing.JComboBox<>();
        cbsach4 = new javax.swing.JComboBox<>();
        cbsach5 = new javax.swing.JComboBox<>();
        tbldanhsachphieumuon = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listphieumuon = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(1120, 666));
        setMinimumSize(new java.awt.Dimension(1120, 666));
        setPreferredSize(new java.awt.Dimension(1125, 675));

        tablePhieuTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu trả", "Mã phiếu", "Tên độc giả", "Tên nhân viên", "Mã sách", "Ngày mượn", "Ngày hẹn trả", "Ngày trả", "Tổng phí phạt", "Ghi chú"
            }
        ));
        jScrollPane1.setViewportView(tablePhieuTra);

        tblghichu.setColumns(20);
        tblghichu.setRows(5);
        jScrollPane2.setViewportView(tblghichu);

        jLabel4.setText("Ghi chú :");

        txtMaphieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaphieuActionPerformed(evt);
            }
        });

        tblMaphieu1.setText("Mã phiếu :");

        tbltendocgia.setText("Tên độc giả :");

        jLabel5.setText("Ngày mượn : ");

        jLabel6.setText("Ngày hẹn trả :");

        jLabel2.setText("Ngày trả : ");

        btntrasach.setText("Trả sách");
        btntrasach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntrasachActionPerformed(evt);
            }
        });

        btnxuatdulieu.setText("Xuất dữ liệu");
        btnxuatdulieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxuatdulieuActionPerformed(evt);
            }
        });

        jLabel1.setText("Sách Mượn");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tblsach1.setText("Sách 1 :");

        tblsach2.setText("Sách 2 :");

        tblsach3.setText("Sách 3 :");

        tblsach4.setText("Sách 4 :");

        tblsach5.setText("Sách 5 :");

        tbldanhsachphieumuon.setText("Danh Sách Phiếu Mượn");

        jScrollPane3.setViewportView(listphieumuon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tbltendocgia)
                    .addComponent(tblMaphieu1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaphieu)
                    .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dpngayhentra, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(dpngaymuon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txttendocgia)
                    .addComponent(jScrollPane2))
                .addGap(102, 102, 102)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tblsach1)
                            .addComponent(tblsach2)
                            .addComponent(tblsach3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbsach3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbsach2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbsach1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(btntrasach, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tblsach5)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbsach5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(btnxuatdulieu))
                                .addGap(110, 110, 110))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tblsach4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbsach4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tbldanhsachphieumuon, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbsach1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tblsach1))
                                .addGap(18, 18, 18)
                                .addComponent(cbsach2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbsach3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tblsach3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tblsach4)
                                    .addComponent(cbsach4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txttendocgia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tbltendocgia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tblMaphieu1)
                                            .addComponent(txtMaphieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel1))
                                        .addGap(44, 44, 44)
                                        .addComponent(tblsach2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dpngaymuon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(17, 17, 17)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tblsach5)
                                    .addComponent(cbsach5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnxuatdulieu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btntrasach, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dpngayhentra, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tbldanhsachphieumuon)
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane3)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 644, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 389, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 185, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btntrasachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntrasachActionPerformed

    }//GEN-LAST:event_btntrasachActionPerformed

    private void txtMaphieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaphieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaphieuActionPerformed

    private void btnxuatdulieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxuatdulieuActionPerformed

    }//GEN-LAST:event_btnxuatdulieuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btntrasach;
    private javax.swing.JButton btnxuatdulieu;
    private javax.swing.JComboBox<String> cbsach1;
    private javax.swing.JComboBox<String> cbsach2;
    private javax.swing.JComboBox<String> cbsach3;
    private javax.swing.JComboBox<String> cbsach4;
    private javax.swing.JComboBox<String> cbsach5;
    private com.github.lgooddatepicker.components.DateTimePicker date;
    private com.github.lgooddatepicker.components.DateTimePicker dpngayhentra;
    private com.github.lgooddatepicker.components.DateTimePicker dpngaymuon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listphieumuon;
    private javax.swing.JTable tablePhieuTra;
    private javax.swing.JLabel tblMaphieu1;
    private javax.swing.JLabel tbldanhsachphieumuon;
    private javax.swing.JTextArea tblghichu;
    private javax.swing.JLabel tblsach1;
    private javax.swing.JLabel tblsach2;
    private javax.swing.JLabel tblsach3;
    private javax.swing.JLabel tblsach4;
    private javax.swing.JLabel tblsach5;
    private javax.swing.JLabel tbltendocgia;
    private javax.swing.JTextField txtMaphieu;
    private javax.swing.JTextField txttendocgia;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JButton getBtnTraSach() {
        return btntrasach;
    }

    public javax.swing.JButton getBtnXuatDuLieu() {
        return btnxuatdulieu;
    }

    public javax.swing.JList<String> getListPhieuMuon() {
        return listphieumuon;
    }

    public javax.swing.JTextField getTxtMaPhieu() {
        return txtMaphieu;
    }

    public javax.swing.JTextField getTxtTenDocGia() {
        return txttendocgia;
    }

    public com.github.lgooddatepicker.components.DateTimePicker getDpNgayMuon() {
        return dpngaymuon;
    }

    public com.github.lgooddatepicker.components.DateTimePicker getDpNgayHenTra() {
        return dpngayhentra;
    }

    public com.github.lgooddatepicker.components.DateTimePicker getDate() {
        return date;
    }

    public javax.swing.JTextArea getTblGhiChu() {
        return tblghichu;
    }

    public javax.swing.JTable getTablePhieuTra() {
        return tablePhieuTra;
    }

    public javax.swing.JLabel getTblSach1() {
        return tblsach1;
    }

    public javax.swing.JLabel getTblSach2() {
        return tblsach2;
    }

    public javax.swing.JLabel getTblSach3() {
        return tblsach3;
    }

    public javax.swing.JLabel getTblSach4() {
        return tblsach4;
    }

    public javax.swing.JLabel getTblSach5() {
        return tblsach5;
    }

    public javax.swing.JComboBox<String> getCbSach1() {
        return cbsach1;
    }

    public javax.swing.JComboBox<String> getCbSach2() {
        return cbsach2;
    }

    public javax.swing.JComboBox<String> getCbSach3() {
        return cbsach3;
    }

    public javax.swing.JComboBox<String> getCbSach4() {
        return cbsach4;
    }

    public javax.swing.JComboBox<String> getCbSach5() {
        return cbsach5;
    }
}
