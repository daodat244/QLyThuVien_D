package Control;

import Model.DAO.DocGiaDAO;
import Model.DAO.NhanVienDAO;
import Model.DAO.PhieuMuonDAO;
import Model.DAO.PhieuTraDAO;
import Model.DAO.SachDAO;
import Model.DAO.TaiKhoanDAO;
import Model.Entity.ChiTietPhieuTra;
import Model.Entity.PhieuMuon;
import Model.Entity.PhieuTra;
import Model.Entity.Sach;
import View.ChucNang.PanelPhieuTra;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Utils.ConnectToSQLServer;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

public class PhieuTraController {

    private static final Logger LOGGER = Logger.getLogger(PhieuTraController.class.getName());
    private PanelPhieuTra view;
    private PhieuTraDAO phieuTraDAO;
    private DocGiaDAO docGiaDAO;
    private NhanVienDAO nhanVienDAO;
    private PhieuMuonDAO phieuMuonDAO;
    private SachDAO sachDAO;
    private TaiKhoanDAO taiKhoanDAO;
    private int maNhanVien;
    private PhieuMuon selectedPhieuMuon;

    public PhieuTraController(PanelPhieuTra view) {
        this.view = view;
        this.phieuTraDAO = new PhieuTraDAO();
        this.docGiaDAO = new DocGiaDAO();
        this.nhanVienDAO = new NhanVienDAO();
        this.phieuMuonDAO = new PhieuMuonDAO();
        this.sachDAO = new SachDAO();
        this.taiKhoanDAO = new TaiKhoanDAO();
        setupTableRenderers();
        loadPhieuMuonList();
        loadSelectedPhieuMuon();
    }

    public void setupEventListeners() {
        for (ActionListener al : view.getBtnTraSach().getActionListeners()) {
            view.getBtnTraSach().removeActionListener(al);
        }
        view.getBtnTraSach().addActionListener(this::btnTraSachActionPerformed);

        for (ActionListener al : view.getBtnXuatDuLieu().getActionListeners()) {
            view.getBtnXuatDuLieu().removeActionListener(al);
        }
        view.getBtnXuatDuLieu().addActionListener(this::btnXuatDuLieuActionPerformed);

        for (ListSelectionListener lsl : view.getListPhieuMuon().getListSelectionListeners()) {
            view.getListPhieuMuon().removeListSelectionListener(lsl);
        }
        view.getListPhieuMuon().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedPhieuMuon();
                }
            }
        });

        // Thêm ListSelectionListener cho tablePhieuTra
        view.getTablePhieuTra().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedPhieuTra();
                }
            }
        });
    }

    private void setupTableRenderers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        view.getTablePhieuTra().getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof LocalDateTime) {
                    setText(((LocalDateTime) value).format(formatter));
                } else {
                    super.setValue(value);
                }
            }
        });
        view.getTablePhieuTra().getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof LocalDateTime) {
                    setText(((LocalDateTime) value).format(formatter));
                } else {
                    super.setValue(value);
                }
            }
        });
        view.getTablePhieuTra().getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof LocalDateTime) {
                    setText(((LocalDateTime) value).format(formatter));
                } else {
                    super.setValue(value);
                }
            }
        });
    }

    public void loadTableData() {
        try {
            DefaultTableModel model = (DefaultTableModel) view.getTablePhieuTra().getModel();
            model.setRowCount(0);

            List<PhieuTra> phieuTraList = phieuTraDAO.getAllPhieuTra();

            for (PhieuTra pt : phieuTraList) {
                String tendocgia = getTenDocGia(pt.getMadocgia());
                String tennv = getTenNhanVien(pt.getManv());
                String masachList = phieuTraDAO.getMasachListByPhieuTra(pt.getMaphieutra());
                Object[] row = new Object[]{
                    pt.getMaphieutra(),
                    pt.getMaphieu(),
                    tendocgia != null ? tendocgia : "Không xác định",
                    tennv != null ? tennv : "Không xác định",
                    masachList,
                    pt.getNgaymuon(),
                    pt.getNgaytradukien(),
                    pt.getNgaytrathucte(),
                    pt.getTongphiphat(),
                    pt.getGhichu()
                };
                model.addRow(row);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải dữ liệu phiếu trả", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu phiếu trả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadPhieuMuonList() {
        try {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            List<PhieuMuon> phieuMuonList = phieuMuonDAO.getAllPhieuMuonWithDetails();

            for (PhieuMuon pm : phieuMuonList) {
                String trangThai = pm.getTrangthai().trim().replaceAll("\\s+", " ");
                if (trangThai.equalsIgnoreCase("Chưa trả")) {
                    String tenNhanVien = getTenNhanVien(pm.getManv());
                    String displayText = pm.getMaphieu() + " - "
                            + (tenNhanVien != null ? tenNhanVien : "Nhân viên không xác định")
                            + " - " + trangThai;
                    listModel.addElement(displayText);
                }
            }

            view.getListPhieuMuon().setModel(listModel);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải danh sách phiếu mượn", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi tải danh sách phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedPhieuTra() {
        int selectedRow = view.getTablePhieuTra().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int maphieutra = (int) view.getTablePhieuTra().getValueAt(selectedRow, 0);
                int maphieu = (int) view.getTablePhieuTra().getValueAt(selectedRow, 1);
                String tendocgia = (String) view.getTablePhieuTra().getValueAt(selectedRow, 2);
                String masachList = (String) view.getTablePhieuTra().getValueAt(selectedRow, 4);
                LocalDateTime ngaymuon = (LocalDateTime) view.getTablePhieuTra().getValueAt(selectedRow, 5);
                LocalDateTime ngaytradukien = (LocalDateTime) view.getTablePhieuTra().getValueAt(selectedRow, 6);
                LocalDateTime ngaytrathucte = (LocalDateTime) view.getTablePhieuTra().getValueAt(selectedRow, 7);
                double tongphiphat = (double) view.getTablePhieuTra().getValueAt(selectedRow, 8);
                String ghichu = (String) view.getTablePhieuTra().getValueAt(selectedRow, 9);

                // Lấy danh sách chi tiết phiếu trả
                List<ChiTietPhieuTra> chiTietList = phieuTraDAO.getChiTietPhieuTraByMaPhieuTra(maphieutra);
                List<String> tenSachList = new ArrayList<>();
                List<String> tinhTrangSachList = new ArrayList<>();
                for (ChiTietPhieuTra ctp : chiTietList) {
                    String tensach = getTenSach(ctp.getMasach());
                    tenSachList.add(tensach != null ? tensach : "Không xác định");
                    tinhTrangSachList.add(ctp.getTinhtrang());
                }

                setPhieuTraInfo(maphieu, tendocgia, masachList, ngaymuon, ngaytradukien, ngaytrathucte, tenSachList, tinhTrangSachList, ghichu);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi lấy thông tin phiếu trả", ex);
                JOptionPane.showMessageDialog(view, "Lỗi khi lấy thông tin phiếu trả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                clearFields();
            }
        } else {
            clearFields();
        }
    }

    private void loadSelectedPhieuMuon() {
        String selectedValue = view.getListPhieuMuon().getSelectedValue();
        if (selectedValue != null) {
            try {
                int maphieu = Integer.parseInt(selectedValue.split(" - ")[0]);
                selectedPhieuMuon = phieuMuonDAO.getPhieuMuonById(maphieu);
                if (selectedPhieuMuon != null) {
                    if (selectedPhieuMuon.getTrangthai().trim().replaceAll("\\s+", " ").equalsIgnoreCase("Đã trả")) {
                        JOptionPane.showMessageDialog(view, "Phiếu mượn này đã được trả!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        clearFields();
                        selectedPhieuMuon = null;
                        return;
                    }
                    String tenDocGia = getTenDocGia(selectedPhieuMuon.getMadocgia());
                    List<String> masachList = phieuMuonDAO.getSachListByPhieuMuon(selectedPhieuMuon.getMaphieu());
                    List<String> tenSachList = new ArrayList<>();
                    for (String masach : masachList) {
                        String tensach = getTenSach(masach);
                        tenSachList.add(tensach);
                    }
                    setPhieuMuonInfo(selectedPhieuMuon, tenDocGia, masachList, tenSachList);
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy phiếu mượn với mã: " + maphieu, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    clearFields();
                    selectedPhieuMuon = null;
                }
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi lấy thông tin phiếu mượn", ex);
                JOptionPane.showMessageDialog(view, "Lỗi khi lấy thông tin phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                clearFields();
                selectedPhieuMuon = null;
            } catch (NumberFormatException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi định dạng mã phiếu", ex);
                JOptionPane.showMessageDialog(view, "Lỗi định dạng mã phiếu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                clearFields();
                selectedPhieuMuon = null;
            }
        } else {
            clearFields();
            selectedPhieuMuon = null;
        }
    }

    private void btnTraSachActionPerformed(ActionEvent evt) {
        if (selectedPhieuMuon == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một phiếu mượn từ danh sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedPhieuMuon.getTrangthai().trim().replaceAll("\\s+", " ").equalsIgnoreCase("Đã trả")) {
            JOptionPane.showMessageDialog(view, "Phiếu mượn này đã được trả!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDateTime ngaytrathucte = view.getDate().getDateTimePermissive();
        if (ngaytrathucte == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày trả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (ngaytrathucte.isBefore(selectedPhieuMuon.getNgaymuon())) {
            JOptionPane.showMessageDialog(view, "Ngày trả thực tế không được nhỏ hơn ngày mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn trả sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Connection conn = null;
        try {
            conn = ConnectToSQLServer.getConnection();
            conn.setAutoCommit(false);

            List<String> masachList = phieuMuonDAO.getSachListByPhieuMuon(selectedPhieuMuon.getMaphieu());
            if (masachList == null || masachList.isEmpty()) {
                throw new SQLException("Không tìm thấy sách liên quan đến phiếu mượn.");
            }

            // Tính phí trả muộn
            long daysLate = ChronoUnit.DAYS.between(selectedPhieuMuon.getNgayTraDuKien().toLocalDate(), ngaytrathucte.toLocalDate());
            double phiTraMuon = daysLate > 0 ? daysLate * 5000 : 0;

            // Lấy tình trạng sách và tính phí hư hỏng/mất
            String[] tinhTrangSach = new String[masachList.size()];
            double[] phiHuHong = new double[masachList.size()];
            double tongPhiHuHong = 0;

            for (int i = 0; i < masachList.size() && i < 5; i++) {
                switch (i) {
                    case 0:
                        tinhTrangSach[i] = (String) view.getCbSach1().getSelectedItem();
                        break;
                    case 1:
                        tinhTrangSach[i] = (String) view.getCbSach2().getSelectedItem();
                        break;
                    case 2:
                        tinhTrangSach[i] = (String) view.getCbSach3().getSelectedItem();
                        break;
                    case 3:
                        tinhTrangSach[i] = (String) view.getCbSach4().getSelectedItem();
                        break;
                    case 4:
                        tinhTrangSach[i] = (String) view.getCbSach5().getSelectedItem();
                        break;
                }
                // Tính phí hư hỏng/mất: 100,000 VNĐ nếu không nguyên vẹn
                phiHuHong[i] = tinhTrangSach[i] != null && !tinhTrangSach[i].equals("Nguyên vẹn") ? 100000 : 0;
                tongPhiHuHong += phiHuHong[i];
            }

            // Tổng phí phạt = phí trả muộn + tổng phí hư hỏng/mất
            double tongphiphat = phiTraMuon + tongPhiHuHong;

            if (!phieuMuonDAO.updateTrangThai(selectedPhieuMuon.getMaphieu(), "Đã trả", conn)) {
                throw new SQLException("Không thể cập nhật trạng thái phiếu mượn.");
            }

            // Thêm phiếu trả với tổng phí phạt
            PhieuTra pt = new PhieuTra(
                    0,
                    selectedPhieuMuon.getMaphieu(),
                    selectedPhieuMuon.getMadocgia(),
                    selectedPhieuMuon.getManv(),
                    String.join(",", masachList),
                    selectedPhieuMuon.getNgaymuon(),
                    selectedPhieuMuon.getNgayTraDuKien(),
                    ngaytrathucte,
                    tongphiphat,
                    view.getTblGhiChu().getText().trim(),
                    tinhTrangSach[0]
            );

            if (!phieuTraDAO.addPhieuTra(pt, conn)) {
                throw new SQLException("Không thể thêm phiếu trả.");
            }

            int maPhieuTra = phieuTraDAO.getLatestPhieuTraId(conn);
            boolean success = true;

            // Thêm chi tiết phiếu trả
            for (int i = 0; i < masachList.size() && i < tinhTrangSach.length; i++) {
                String masach = masachList.get(i);
                String tinhTrang = tinhTrangSach[i] != null ? tinhTrangSach[i] : "Nguyên vẹn";

                Sach sach = sachDAO.getSachById(masach);
                if (sach == null) {
                    LOGGER.warning("Mã sách không tồn tại: " + masach);
                    success = false;
                    break;
                }

                ChiTietPhieuTra ctp = new ChiTietPhieuTra(
                        0,
                        maPhieuTra,
                        masach,
                        phiHuHong[i],
                        tinhTrang
                );

                if (!phieuTraDAO.addChiTietPhieuTra(ctp, conn)) {
                    LOGGER.warning("Không thể thêm chi tiết phiếu trả cho mã sách: " + masach);
                    success = false;
                    break;
                }

                // Tăng số lượng sách nếu nguyên vẹn
                if (tinhTrang.equals("Nguyên vẹn")) {
                    if (!sachDAO.tangSoLuongSach(masach)) {
                        LOGGER.warning("Không thể tăng số lượng sách cho mã sách: " + masach);
                        success = false;
                        break;
                    }
                }
            }

            if (success) {
                conn.commit();
                JOptionPane.showMessageDialog(view, "Trả sách thành công! Tổng phí phạt: " + tongphiphat + " VNĐ (Phí trả muộn: " + phiTraMuon + " VNĐ, Phí hư hỏng/mất: " + tongPhiHuHong + " VNĐ)", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
                loadPhieuMuonList();
                clearFields();
                selectedPhieuMuon = null;
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(view, "Trả sách thất bại do lỗi thêm phiếu trả hoặc chi tiết!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi trả sách", ex);
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Lỗi khi rollback giao dịch", rollbackEx);
            }
            JOptionPane.showMessageDialog(view, "Lỗi khi trả sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Lỗi khi đóng kết nối", e);
                }
            }
        }
    }

    private void btnXuatDuLieuActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("danh_sach_phieu_tra.xlsx"));
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

        int result = fileChooser.showSaveDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
                selectedFile = new File(filePath);
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("DanhSachPhieuTra");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Mã phiếu trả", "Mã phiếu", "Tên độc giả", "Tên nhân viên", "Mã sách", "Ngày mượn", "Ngày hẹn trả", "Ngày trả", "Tổng phí phạt", "Ghi chú"};
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                DefaultTableModel model = (DefaultTableModel) view.getTablePhieuTra().getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue((int) model.getValueAt(i, 0));
                    row.createCell(1).setCellValue((int) model.getValueAt(i, 1));
                    row.createCell(2).setCellValue((String) model.getValueAt(i, 2));
                    row.createCell(3).setCellValue((String) model.getValueAt(i, 3));
                    row.createCell(4).setCellValue((String) model.getValueAt(i, 4));
                    LocalDateTime ngayMuon = (LocalDateTime) model.getValueAt(i, 5);
                    row.createCell(5).setCellValue(ngayMuon != null ? ngayMuon.format(formatter) : "");
                    LocalDateTime ngayTraDuKien = (LocalDateTime) model.getValueAt(i, 6);
                    row.createCell(6).setCellValue(ngayTraDuKien != null ? ngayTraDuKien.format(formatter) : "");
                    LocalDateTime ngayTraThucTe = (LocalDateTime) model.getValueAt(i, 7);
                    row.createCell(7).setCellValue(ngayTraThucTe != null ? ngayTraThucTe.format(formatter) : "");
                    row.createCell(8).setCellValue((double) model.getValueAt(i, 8));
                    row.createCell(9).setCellValue((String) model.getValueAt(i, 9));
                }

                for (int i = 0; i < headers.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                try (FileOutputStream fileOut = new FileOutputStream(selectedFile)) {
                    workbook.write(fileOut);
                }
                JOptionPane.showMessageDialog(view, "Xuất dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi xuất file Excel", ex);
                JOptionPane.showMessageDialog(view, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearBookFields() {
        view.getTblSach1().setText("1.");
        view.getTblSach2().setText("2.");
        view.getTblSach3().setText("3.");
        view.getTblSach4().setText("4.");
        view.getTblSach5().setText("5.");
        view.getTblSach1().setVisible(false);
        view.getTblSach2().setVisible(false);
        view.getTblSach3().setVisible(false);
        view.getTblSach4().setVisible(false);
        view.getTblSach5().setVisible(false);
        view.getCbSach1().setVisible(false);
        view.getCbSach2().setVisible(false);
        view.getCbSach3().setVisible(false);
        view.getCbSach4().setVisible(false);
        view.getCbSach5().setVisible(false);
    }

    public void clearFields() {
        view.getTxtMaPhieu().setText("");
        view.getTxtTenDocGia().setText("");
        view.getDpNgayMuon().clear();
        view.getDpNgayHenTra().clear();
        view.getDate().clear();
        view.getTblGhiChu().setText("");
        clearBookFields();
        view.getListPhieuMuon().clearSelection();
        // Kích hoạt lại các trường khi xóa
        view.getTxtMaPhieu().setEnabled(true);
        view.getTxtTenDocGia().setEnabled(true);
        view.getDpNgayMuon().setEnabled(true);
        view.getDpNgayHenTra().setEnabled(true);
        view.getDate().setEnabled(true);
        view.getTblGhiChu().setEnabled(true);
        view.getCbSach1().setEnabled(true);
        view.getCbSach2().setEnabled(true);
        view.getCbSach3().setEnabled(true);
        view.getCbSach4().setEnabled(true);
        view.getCbSach5().setEnabled(true);
    }

    public void setPhieuMuonInfo(PhieuMuon pm, String tenDocGia, List<String> masachList, List<String> tenSachList) {
        view.getTxtMaPhieu().setText(String.valueOf(pm.getMaphieu()));
        view.getTxtTenDocGia().setText(tenDocGia != null ? tenDocGia : "Không xác định");
        view.getDpNgayMuon().setDateTimePermissive(pm.getNgaymuon());
        view.getDpNgayHenTra().setDateTimePermissive(pm.getNgayTraDuKien());
        clearBookFields();
        if (masachList != null && !masachList.isEmpty()) {
            for (int i = 0; i < masachList.size() && i < 5; i++) {
                String tensach = tenSachList.get(i);
                switch (i) {
                    case 0:
                        view.getTblSach1().setText(tensach != null ? "1. " + tensach : "1. Không xác định");
                        view.getTblSach1().setVisible(true);
                        view.getCbSach1().setVisible(true);
                        break;
                    case 1:
                        view.getTblSach2().setText(tensach != null ? "2. " + tensach : "2. Không xác định");
                        view.getTblSach2().setVisible(true);
                        view.getCbSach2().setVisible(true);
                        break;
                    case 2:
                        view.getTblSach3().setText(tensach != null ? "3. " + tensach : "3. Không xác định");
                        view.getTblSach3().setVisible(true);
                        view.getCbSach3().setVisible(true);
                        break;
                    case 3:
                        view.getTblSach4().setText(tensach != null ? "4. " + tensach : "4. Không xác định");
                        view.getTblSach4().setVisible(true);
                        view.getCbSach4().setVisible(true);
                        break;
                    case 4:
                        view.getTblSach5().setText(tensach != null ? "5. " + tensach : "5. Không xác định");
                        view.getTblSach5().setVisible(true);
                        view.getCbSach5().setVisible(true);
                        break;
                }
            }
        } else {
            view.getTblSach1().setText("1. Không có sách");
            view.getTblSach1().setVisible(true);
        }
        view.getTxtMaPhieu().setEnabled(true);
        view.getTxtTenDocGia().setEnabled(true);
        view.getDpNgayMuon().setEnabled(true);
        view.getDpNgayHenTra().setEnabled(true);
        view.getDate().setEnabled(true);
        view.getTblGhiChu().setEnabled(true);
        view.getCbSach1().setEnabled(true);
        view.getCbSach2().setEnabled(true);
        view.getCbSach3().setEnabled(true);
        view.getCbSach4().setEnabled(true);
        view.getCbSach5().setEnabled(true);
    }

    public void setPhieuTraInfo(int maphieu, String tenDocGia, String masachList, LocalDateTime ngaymuon, LocalDateTime ngaytradukien, LocalDateTime ngaytrathucte, List<String> tenSachList, List<String> tinhTrangSachList, String ghichu) {
        view.getTxtMaPhieu().setText(String.valueOf(maphieu));
        view.getTxtTenDocGia().setText(tenDocGia != null ? tenDocGia : "Không xác định");
        view.getDpNgayMuon().setDateTimePermissive(ngaymuon);
        view.getDpNgayHenTra().setDateTimePermissive(ngaytradukien);
        view.getDate().setDateTimePermissive(ngaytrathucte);
        view.getTblGhiChu().setText(ghichu != null ? ghichu : "");
        clearBookFields();
        if (masachList != null && !masachList.isEmpty()) {
            for (int i = 0; i < tenSachList.size() && i < 5; i++) {
                String tensach = tenSachList.get(i);
                String tinhTrang = tinhTrangSachList.size() > i ? tinhTrangSachList.get(i) : "Nguyên vẹn";
                switch (i) {
                    case 0:
                        view.getTblSach1().setText(tensach != null ? "1. " + tensach : "1. Không xác định");
                        view.getTblSach1().setVisible(true);
                        view.getCbSach1().setVisible(true);
                        view.getCbSach1().setSelectedItem(tinhTrang);
                        break;
                    case 1:
                        view.getTblSach2().setText(tensach != null ? "2. " + tensach : "2. Không xác định");
                        view.getTblSach2().setVisible(true);
                        view.getCbSach2().setVisible(true);
                        view.getCbSach2().setSelectedItem(tinhTrang);
                        break;
                    case 2:
                        view.getTblSach3().setText(tensach != null ? "3. " + tensach : "3. Không xác định");
                        view.getTblSach3().setVisible(true);
                        view.getCbSach3().setVisible(true);
                        view.getCbSach3().setSelectedItem(tinhTrang);
                        break;
                    case 3:
                        view.getTblSach4().setText(tensach != null ? "4. " + tensach : "4. Không xác định");
                        view.getTblSach4().setVisible(true);
                        view.getCbSach4().setVisible(true);
                        view.getCbSach4().setSelectedItem(tinhTrang);
                        break;
                    case 4:
                        view.getTblSach5().setText(tensach != null ? "5. " + tensach : "5. Không xác định");
                        view.getTblSach5().setVisible(true);
                        view.getCbSach5().setVisible(true);
                        view.getCbSach5().setSelectedItem(tinhTrang);
                        break;
                }
            }
        } else {
            view.getTblSach1().setText("1. Không có sách");
            view.getTblSach1().setVisible(true);
        }
        // Vô hiệu hóa tất cả các trường
        view.getTxtMaPhieu().setEnabled(false);
        view.getTxtTenDocGia().setEnabled(false);
        view.getDpNgayMuon().setEnabled(false);
        view.getDpNgayHenTra().setEnabled(false);
        view.getDate().setEnabled(false);
        view.getTblGhiChu().setEnabled(false);
        view.getCbSach1().setEnabled(false);
        view.getCbSach2().setEnabled(false);
        view.getCbSach3().setEnabled(false);
        view.getCbSach4().setEnabled(false);
        view.getCbSach5().setEnabled(false);
    }

    private String getTenDocGia(int madocgia) throws SQLException {
        return docGiaDAO.getDocGiaById(madocgia) != null ? docGiaDAO.getDocGiaById(madocgia).getTendocgia() : null;
    }

    private String getTenNhanVien(int manv) throws SQLException {
        return nhanVienDAO.getNhanVienById(manv) != null ? nhanVienDAO.getNhanVienById(manv).getTennv(): null;
    }

    private String getTenSach(String masach) throws SQLException {
        return sachDAO.getSachById(masach) != null ? sachDAO.getSachById(masach).getTensach() : null;
    }
}