package View.ChucNang;

import Model.DAO.TaiKhoanDAO;
import Model.DAO.NhanVienDAO;
import Model.Entity.TaiKhoan;
import Model.Entity.NhanVien;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class PanelTaiKhoan extends javax.swing.JPanel {

    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    
    
    public PanelTaiKhoan() {
        initComponents();
        loadComboBoxes();
        loadTableData();
            txtTimKiem.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { searchTK(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { searchTK(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { searchTK(); }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelInfor = new javax.swing.JPanel();
        TenDangNhap = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        MatKhau = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        Role = new javax.swing.JLabel();
        cbNhanVien = new javax.swing.JComboBox<>();
        NhanVien = new javax.swing.JLabel();
        radQuanLy = new javax.swing.JRadioButton();
        radNhanVien = new javax.swing.JRadioButton();
        panelButton = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        TimKiem = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        cbTimKiem = new javax.swing.JComboBox<>();
        panelTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTaiKhoan = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNhapDuLieu = new javax.swing.JButton();
        btnXuatDuLieu = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1120, 666));
        setMinimumSize(new java.awt.Dimension(1120, 666));
        setPreferredSize(new java.awt.Dimension(1120, 666));

        panelInfor.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        TenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        TenDangNhap.setText("Tên đăng nhập");

        txtTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenDangNhap.setPreferredSize(new java.awt.Dimension(64, 25));
        txtTenDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDangNhapActionPerformed(evt);
            }
        });

        MatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        MatKhau.setText("Mật khẩu");

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMatKhau.setPreferredSize(new java.awt.Dimension(64, 25));

        Role.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        Role.setText("Role");

        cbNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cbNhanVien.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                cbNhanVienAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        cbNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNhanVienActionPerformed(evt);
            }
        });

        NhanVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        NhanVien.setText("Nhân viên");

        buttonGroup1.add(radQuanLy);
        radQuanLy.setSelected(true);
        radQuanLy.setText("Quản lý");

        buttonGroup1.add(radNhanVien);
        radNhanVien.setText("Nhân viên");

        javax.swing.GroupLayout panelInforLayout = new javax.swing.GroupLayout(panelInfor);
        panelInfor.setLayout(panelInforLayout);
        panelInforLayout.setHorizontalGroup(
            panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInforLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TenDangNhap)
                    .addComponent(MatKhau))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NhanVien)
                    .addComponent(Role))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelInforLayout.createSequentialGroup()
                        .addComponent(radQuanLy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radNhanVien)))
                .addGap(83, 83, 83))
        );
        panelInforLayout.setVerticalGroup(
            panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInforLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TenDangNhap)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Role)
                    .addComponent(radQuanLy)
                    .addComponent(radNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(MatKhau)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelInforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(NhanVien)
                        .addComponent(cbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        panelButton.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        panelButton.setPreferredSize(new java.awt.Dimension(1120, 86));

        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        TimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TimKiem.setText("Tìm Kiếm:");

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        cbTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbTimKiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tên đăng nhập", "Tên nhân viên", "Role" }));

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(TimKiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbTimKiem, 0, 159, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TimKiem)
                    .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        tableTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã tài khoản", "Tên nhân viên", "Tên đăng nhập", "Mật khẩu", "Role"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableTaiKhoan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableTaiKhoan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTaiKhoanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableTaiKhoan);
        if (tableTaiKhoan.getColumnModel().getColumnCount() > 0) {
            tableTaiKhoan.getColumnModel().getColumn(0).setResizable(false);
            tableTaiKhoan.getColumnModel().getColumn(0).setPreferredWidth(10);
            tableTaiKhoan.getColumnModel().getColumn(1).setResizable(false);
            tableTaiKhoan.getColumnModel().getColumn(1).setPreferredWidth(160);
            tableTaiKhoan.getColumnModel().getColumn(2).setResizable(false);
            tableTaiKhoan.getColumnModel().getColumn(2).setPreferredWidth(80);
            tableTaiKhoan.getColumnModel().getColumn(3).setResizable(false);
            tableTaiKhoan.getColumnModel().getColumn(4).setResizable(false);
            tableTaiKhoan.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        javax.swing.GroupLayout panelTableLayout = new javax.swing.GroupLayout(panelTable);
        panelTable.setLayout(panelTableLayout);
        panelTableLayout.setHorizontalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        panelTableLayout.setVerticalGroup(
            panelTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTableLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnNhapDuLieu.setText("Nhập Dữ liệu");
        btnNhapDuLieu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnNhapDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapDuLieuActionPerformed(evt);
            }
        });

        btnXuatDuLieu.setText("Xuất Dữ liệu");
        btnXuatDuLieu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXuatDuLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatDuLieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNhapDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnNhapDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXuatDuLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelInfor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 1133, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInfor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNhanVienActionPerformed

    private void tableTaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTaiKhoanMouseClicked
    int row = tableTaiKhoan.getSelectedRow();
            if (row >= 0) {
                txtTenDangNhap.setText(tableTaiKhoan.getValueAt(row, 2).toString());
                txtTenDangNhap.setEditable(false);
                txtMatKhau.setText(tableTaiKhoan.getValueAt(row, 3).toString());
                String role = tableTaiKhoan.getValueAt(row, 4).toString();
                radQuanLy.setSelected(role.equals("Quản lý"));
                radNhanVien.setSelected(role.equals("Nhân viên"));

                int manv = Integer.parseInt(tableTaiKhoan.getValueAt(row, 1).toString().split(" - ")[0]);

                for (int i = 0; i < cbNhanVien.getItemCount(); i++) {
                    if (cbNhanVien.getItemAt(i).getManv()== manv) {
                        cbNhanVien.setSelectedIndex(i);
                        break;
                    }
                }
            }
    }//GEN-LAST:event_tableTaiKhoanMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        addTK();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        updateTK();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        deleteTK();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        searchTK();
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void cbNhanVienAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_cbNhanVienAncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_cbNhanVienAncestorMoved

    private void btnNhapDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapDuLieuActionPerformed
     JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu tài khoản");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx");
            }
            @Override
            public String getDescription() {
                return "Excel Files (*.xlsx)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(selectedFile); Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                int successCount = 0;
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    try {
                        TaiKhoan tk = new TaiKhoan();
                        // Không đặt matk vì có thể là khóa tự động tăng
                        Cell manvCell = row.getCell(1);
                        if (manvCell != null && manvCell.getCellType() == CellType.NUMERIC) {
                            tk.setManv((int) manvCell.getNumericCellValue());
                        } else {
                            throw new IllegalArgumentException("Mã nhân viên không hợp lệ tại dòng " + (i + 1));
                        }
                        Cell tendangnhapCell = row.getCell(2);
                        if (tendangnhapCell != null && tendangnhapCell.getCellType() == CellType.STRING) {
                            tk.setTendangnhap(tendangnhapCell.getStringCellValue());
                        } else {
                            throw new IllegalArgumentException("Tên đăng nhập không hợp lệ tại dòng " + (i + 1));
                        }
                        Cell matkhauCell = row.getCell(3);
                        if (matkhauCell != null && matkhauCell.getCellType() == CellType.STRING) {
                            tk.setMatkhau(matkhauCell.getStringCellValue());
                        } else {
                            throw new IllegalArgumentException("Mật khẩu không hợp lệ tại dòng " + (i + 1));
                        }
                        Cell roleCell = row.getCell(4);
                        if (roleCell != null && roleCell.getCellType() == CellType.STRING) {
                            tk.setRole(roleCell.getStringCellValue());
                        } else {
                            throw new IllegalArgumentException("Role không hợp lệ tại dòng " + (i + 1));
                        }

                        if (taiKhoanDAO.addTK(tk)) {
                            successCount++;
                        } else {
                            JOptionPane.showMessageDialog(this, "Không thể thêm tài khoản tại dòng " + (i + 1) + ": Nhân viên đã có tài khoản!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi dữ liệu tại dòng " + (i + 1) + ": " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi cơ sở dữ liệu tại dòng " + (i + 1) + ": " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }

                loadTableData();
                JOptionPane.showMessageDialog(this, "Đã nhập thành công " + successCount + " tài khoản!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnNhapDuLieuActionPerformed

    private void btnXuatDuLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatDuLieuActionPerformed
    JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("danh_sach_tai_khoan.xlsx"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".xlsx");
            }
            @Override
            public String getDescription() {
                return "Excel Files (*.xlsx)";
            }
        });

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
                selectedFile = new File(filePath);
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("DanhSachTaiKhoan");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Mã tài khoản", "Tên nhân viên", "Tên đăng nhập", "Mật khẩu", "Role"};
                for (int i = 0; i < headers.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(headers[i]);
                }

                List<TaiKhoan> tkList = taiKhoanDAO.getAllTaiKhoan();
                int rowNum = 1;
                for (TaiKhoan tk : tkList) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(tk.getManv());
                    // Lấy tên nhân viên từ cơ sở dữ liệu
                    String tenNhanVien = "";
                    for (NhanVien nv : nhanVienDAO.getAllNhanVien()) {
                        if (nv.getManv()== tk.getManv()) {
                            tenNhanVien = nv.getTennv();
                            break;
                        }
                    }
                    row.createCell(1).setCellValue(tenNhanVien);
                    row.createCell(2).setCellValue(tk.getTendangnhap());
                    row.createCell(3).setCellValue(tk.getMatkhau());
                    row.createCell(4).setCellValue(tk.getRole());
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(selectedFile)) {
                    workbook.write(fileOut);
                }

                JOptionPane.showMessageDialog(this, "Xuất dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnXuatDuLieuActionPerformed

    private void txtTenDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDangNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDangNhapActionPerformed
    
    private void loadComboBoxes() {
        try {
            // Tải tài khoản
            List<NhanVien> nvList = nhanVienDAO.getAllNhanVien();
            for (NhanVien nv : nvList) {
                cbNhanVien.addItem(nv);
            }
            cbNhanVien.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof NhanVien) {
                        NhanVien nv = (NhanVien) value;
                        setText(nv.getManv()+ " - " +nv.getTennv());
                    }
                    return this;
                }
            });

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadTableData() {
        try {
            List<TaiKhoan> tkList = taiKhoanDAO.getAllTaiKhoan();
            DefaultTableModel model = (DefaultTableModel) tableTaiKhoan.getModel();
            model.setRowCount(0);
            for (TaiKhoan tk : tkList) {
                String tenNhanVien = "";
                

                for (int i = 0; i < cbNhanVien.getItemCount(); i++) {
                    if (cbNhanVien.getItemAt(i).getManv()== tk.getManv()) {
                        tenNhanVien = cbNhanVien.getItemAt(i).getTennv();
                        break;
                    }
                }

                model.addRow(new Object[]{
                    tk.getMatk(),
                    tenNhanVien,
                    tk.getTendangnhap(),
                    tk.getMatkhau(),
                    tk.getRole()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addTK() {
        
        String tendangnhap = txtTenDangNhap.getText().trim();
        String matkhau = txtMatKhau.getText().trim();

        if (tendangnhap.isEmpty()) {
            showError("Tên đăng nhập không được để trống!");
            txtTenDangNhap.requestFocus();
            return;
        }
        if (!isValidUsername(tendangnhap)) {
            showError("Tên đăng nhập chỉ chứa chữ cái, số, độ dài 4-20 ký tự!");
            txtTenDangNhap.requestFocus();
            return;
        }
        if (matkhau.isEmpty()) {
            showError("Mật khẩu không được để trống!");
            txtMatKhau.requestFocus();
            return;
        }
        if (!isValidPassword(matkhau)) {
            showError("Mật khẩu phải có ít nhất 6 ký tự!");
            txtMatKhau.requestFocus();
            return;
        }
        
        try {
            
            if (taiKhoanDAO.isUsernameExists(tendangnhap, null)) {
                showError("Tên đăng nhập đã tồn tại!");
                txtTenDangNhap.requestFocus();
                return;
            }
            
            TaiKhoan tk = new TaiKhoan();
            tk.setTendangnhap(txtTenDangNhap.getText());
            tk.setManv(((NhanVien) cbNhanVien.getSelectedItem()).getManv());
            tk.setMatkhau(txtMatKhau.getText());
            tk.setRole(radQuanLy.isSelected() ? "Quản lý" : "Nhân viên");
            
            

            if (taiKhoanDAO.addTK(tk)) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
                loadTableData();
                clearFields();
                txtTenDangNhap.setEditable(true);
                cbNhanVien.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Nhân viên này đã có tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTK() {
        int row = tableTaiKhoan.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String matkhau = txtMatKhau.getText().trim();

        if (matkhau.isEmpty()) {
            showError("Mật khẩu không được để trống!");
            txtMatKhau.requestFocus();
            return;
        }
        if (!isValidPassword(matkhau)) {
            showError("Mật khẩu phải có ít nhất 6 ký tự!");
            txtMatKhau.requestFocus();
            return;
        }

        try {
            TaiKhoan tk = new TaiKhoan();
            tk.setMatk(Integer.parseInt(tableTaiKhoan.getValueAt(row, 0).toString()));
            tk.setTendangnhap(txtTenDangNhap.getText());
            tk.setManv(((NhanVien) cbNhanVien.getSelectedItem()).getManv());
            tk.setMatkhau(txtMatKhau.getText());
            tk.setRole(radQuanLy.isSelected() ? "Quản lý" : "Nhân viên");

            if (taiKhoanDAO.updateTK(tk)) {
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thành công!");
                loadTableData();
                clearFields();
                txtTenDangNhap.setEditable(true);
                cbNhanVien.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Không được trùng nhân viên");
        }
    }

    private void deleteTK() {
        int row = tableTaiKhoan.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int matk = Integer.parseInt(tableTaiKhoan.getValueAt(row, 0).toString());
                if (taiKhoanDAO.deleteTK(matk)) {
                    JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
                    loadTableData();
                    clearFields();
                    txtTenDangNhap.setEditable(true);
                    cbNhanVien.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchTK() {
    try {
        String searchText = txtTimKiem.getText().trim();
        String searchCriteria = (String) cbTimKiem.getSelectedItem();

        List<TaiKhoan> tkList = taiKhoanDAO.getAllTaiKhoan();
        DefaultTableModel model = (DefaultTableModel) tableTaiKhoan.getModel();
        model.setRowCount(0);

        for (TaiKhoan tk : tkList) {
            boolean match = false;
            String tenNhanVien = "";

            // Lấy tên từ các combo box để hiển thị
            for (int i = 0; i < cbNhanVien.getItemCount(); i++) {
                if (cbNhanVien.getItemAt(i).getManv()== tk.getManv()) {
                    tenNhanVien = cbNhanVien.getItemAt(i).getTennv();
                    break;
                }
            }

            // Kiểm tra tiêu chí tìm kiếm
            if (searchCriteria.equals("Tên đăng nhập") && !searchText.isEmpty()) {
                match = String.valueOf(tk.getTendangnhap()).contains(searchText);
            } else if (searchCriteria.equals("Role") && !searchText.isEmpty()) {
                match = tk.getRole().toLowerCase().contains(searchText.toLowerCase());
            } else if (searchText.isEmpty()) {
                match = true; // Hiển thị tất cả nếu không có từ khóa
            }

            if (match) {
                model.addRow(new Object[]{
                    tk.getMatk(),
                    tenNhanVien,
                    tk.getTendangnhap(),
                    tk.getMatkhau(),
                    tk.getRole()
                });
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm tài khoản: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
    
    private void clearFields() {
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        cbNhanVien.setSelectedIndex(-1);
        txtTenDangNhap.setEditable(true);

    }
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    private void logError(String message, Exception ex) {
        try (FileWriter fw = new FileWriter("error.log", true)) {
            fw.write(LocalDateTime.now() + ": " + message + " - " + ex.getMessage() + "\n");
        } catch (IOException e) {
        }
    }
    
    private boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) return false;
        // Chỉ chữ cái, số, độ dài 4-20
        return Pattern.matches("^[a-zA-Z0-9]{4,20}$", username);
    }
    
    private boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) return false;
        // Tối thiểu 6 ký tự
        return password.length() >= 6;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel MatKhau;
    private javax.swing.JLabel NhanVien;
    private javax.swing.JLabel Role;
    private javax.swing.JLabel TenDangNhap;
    private javax.swing.JLabel TimKiem;
    private javax.swing.JButton btnNhapDuLieu;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatDuLieu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<NhanVien> cbNhanVien;
    private javax.swing.JComboBox<String> cbTimKiem;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelInfor;
    private javax.swing.JPanel panelTable;
    private javax.swing.JRadioButton radNhanVien;
    private javax.swing.JRadioButton radQuanLy;
    private javax.swing.JTable tableTaiKhoan;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
