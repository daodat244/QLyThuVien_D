package Control;

import Model.DAO.DocGiaDAO;
import Model.DAO.NhanVienDAO;
import Model.DAO.PhieuMuonDAO;
import Model.DAO.SachDAO;
import Model.DAO.TaiKhoanDAO;
import Model.Entity.ChiTietPhieuMuon;
import Model.Entity.PhieuMuon;
import Model.Entity.Sach;
import View.ChucNang.PanelPhieuMuon;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Utils.ConnectToSQLServer;
import java.awt.event.ActionListener;

public class PhieuMuonController {

    private static final Logger LOGGER = Logger.getLogger(PhieuMuonController.class.getName());
    private PanelPhieuMuon view;
    private PhieuMuonDAO phieuMuonDAO;
    private DocGiaDAO docGiaDAO;
    private NhanVienDAO nhanVienDAO;
    private SachDAO sachDAO;
    private TaiKhoanDAO taiKhoanDAO;
    private int maNhanVien;
    private List<String> danhSachSachDaThem;
    private boolean isGiaHanMode;
    private boolean isProcessing = false;

    public PhieuMuonController(PanelPhieuMuon view, int mataikhoan) {
        this.view = view;
        this.phieuMuonDAO = new PhieuMuonDAO();
        this.docGiaDAO = new DocGiaDAO();
        this.nhanVienDAO = new NhanVienDAO();
        this.sachDAO = new SachDAO();
        this.taiKhoanDAO = new TaiKhoanDAO();
        this.danhSachSachDaThem = new ArrayList<>();
        this.isGiaHanMode = false;
        setupTableRenderers();
        loadSelectedPhieuMuon();
        loadSachTable();
        updateMaNhanVien(mataikhoan);
    }

    // Phương thức để cập nhật maNhanVien từ mataikhoan
    public void setMaTK(int mataikhoan) {
        updateMaNhanVien(mataikhoan);
    }

    private void updateMaNhanVien(int mataikhoan) {
        try {
            this.maNhanVien = taiKhoanDAO.getManhanvienByMataikhoan(mataikhoan);
            String tenNhanVien = getTenNhanVien(maNhanVien);
            setTenNhanVien(tenNhanVien != null ? tenNhanVien : "Không xác định");
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi cập nhật thông tin nhân viên", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật thông tin nhân viên: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setupEventListeners() {
        for (ActionListener al : view.getBtnThem().getActionListeners()) {
            view.getBtnThem().removeActionListener(al);
        }
        view.getBtnThem().addActionListener(this::btnThemActionPerformed);

        for (ActionListener al : view.getBtnSua().getActionListeners()) {
            view.getBtnSua().removeActionListener(al);
        }
        view.getBtnSua().addActionListener(this::btnSuaActionPerformed);

        for (ActionListener al : view.getBtnXoa().getActionListeners()) {
            view.getBtnXoa().removeActionListener(al);
        }
        view.getBtnXoa().addActionListener(this::btnXoaActionPerformed);

        for (ActionListener al : view.getBtnKiemtra2().getActionListeners()) {
            view.getBtnKiemtra2().removeActionListener(al);
        }
        view.getBtnKiemtra2().addActionListener(this::btnKiemtra2ActionPerformed);

        for (ActionListener al : view.getBtnNhapDuLieu().getActionListeners()) {
            view.getBtnNhapDuLieu().removeActionListener(al);
        }
        view.getBtnNhapDuLieu().addActionListener(this::btnNhapDuLieuActionPerformed);

        for (ActionListener al : view.getBtnXuatDuLieu().getActionListeners()) {
            view.getBtnXuatDuLieu().removeActionListener(al);
        }
        view.getBtnXuatDuLieu().addActionListener(this::btnXuatDuLieuActionPerformed);

        for (ActionListener al : view.getBtnThemSach().getActionListeners()) {
            view.getBtnThemSach().removeActionListener(al);
        }
        view.getBtnThemSach().addActionListener(this::btnThemSachActionPerformed);

        for (ActionListener al : view.getBtnXoaSach().getActionListeners()) {
            view.getBtnXoaSach().removeActionListener(al);
        }
        view.getBtnXoaSach().addActionListener(this::btnXoaSachActionPerformed);

        for (ActionListener al : view.getBtnGiaHan().getActionListeners()) {
            view.getBtnGiaHan().removeActionListener(al);
        }
        view.getBtnGiaHan().addActionListener(this::btnGiaHanActionPerformed);

        view.getTablePhieuMuon().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedPhieuMuon();
            }
        });
        view.getTxtTimKiem().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchPhieuMuon();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchPhieuMuon();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchPhieuMuon();
            }
        });
        view.getTxtTimKiemSach().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchSach();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchSach();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchSach();
            }
        });
    }

    private void setupTableRenderers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        view.getTablePhieuMuon().getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof LocalDateTime) {
                    setText(((LocalDateTime) value).format(formatter));
                } else {
                    super.setValue(value);
                }
            }
        });
        view.getTablePhieuMuon().getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                if (value instanceof LocalDateTime) {
                    setText(((LocalDateTime) value).format(formatter));
                } else {
                    super.setValue(value);
                }
            }
        });
        view.getTablePhieuMuon().getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {
                setText(value != null ? value.toString() : "Chưa xác định");
                setHorizontalAlignment(CENTER);
            }
        });
    }

    public void loadTableData() {
        try {
            List<PhieuMuon> phieuMuonList = phieuMuonDAO.getAllPhieuMuonWithDetails();
            List<Object[]> data = new ArrayList<>();
            for (PhieuMuon pm : phieuMuonList) {
                String tendocgia = getTenDocGia(pm.getMadocgia());
                String tennv = getTenNhanVien(pm.getManv());
                String masachList = phieuMuonDAO.getMasachListByPhieuMuon(pm.getMaphieu());
                data.add(new Object[]{
                    pm.getMaphieu(),
                    tendocgia != null ? tendocgia : "Không xác định",
                    tennv != null ? tennv : "Không xác định",
                    masachList,
                    pm.getNgaymuon(),
                    pm.getNgayTraDuKien(),
                    pm.getTrangthai() != null ? pm.getTrangthai() : "Chưa xác định"
                });
            }
            loadPhieuMuonTable(data);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải dữ liệu phiếu mượn", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadSachTable() {
        try {
            List<Sach> sachList = sachDAO.getAllSach();
            for (Sach sach : sachList) {
                LOGGER.info("Sách " + sach.getMasach() + ": Số lượng = " + sach.getSoluong());
            }
            loadSachTable(sachList);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tải danh sách sách", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi tải danh sách sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedPhieuMuon() {
        int selectedRow = view.getTablePhieuMuon().getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int maphieu = (int) view.getTablePhieuMuon().getValueAt(selectedRow, 0);
                String tendocgia = (String) view.getTablePhieuMuon().getValueAt(selectedRow, 1);
                String masachList = (String) view.getTablePhieuMuon().getValueAt(selectedRow, 3);
                LocalDateTime ngaymuon = (LocalDateTime) view.getTablePhieuMuon().getValueAt(selectedRow, 4);
                LocalDateTime ngaytra = (LocalDateTime) view.getTablePhieuMuon().getValueAt(selectedRow, 5);

                int madocgia = getMaDocGiaByTen(tendocgia);
                view.getTxtMaDocGia().setText(madocgia != -1 ? String.valueOf(madocgia) : "");
                setTenDocGia(tendocgia.equals("Không xác định") ? null : tendocgia);
                view.getDateNgayMuon().setDateTimePermissive(ngaymuon != null ? ngaymuon : null);
                view.getDateNgayTraDuKien().setDateTimePermissive(ngaytra != null ? ngaytra : null);
                loadSachDaThem(masachList);
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi lấy thông tin phiếu mượn", ex);
                JOptionPane.showMessageDialog(view, "Lỗi khi lấy thông tin phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadSachDaThem(String maSachList) {
        danhSachSachDaThem.clear();
        List<Object[]> sachDaThem = new ArrayList<>();
        if (maSachList != null && !maSachList.equals("Không có sách")) {
            String[] maSachArray = maSachList.split(",");
            for (String masach : maSachArray) {
                masach = masach.trim();
                danhSachSachDaThem.add(masach);
                try {
                    String tensach = getTenSach(masach);
                    sachDaThem.add(new Object[]{masach, tensach != null ? tensach : "Không xác định"});
                } catch (SQLException ex) {
                    LOGGER.log(Level.SEVERE, "Lỗi khi lấy tên sách: " + masach, ex);
                    JOptionPane.showMessageDialog(view, "Lỗi khi lấy tên sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        loadSachDaThemTable(sachDaThem);
    }

    private void btnThemActionPerformed(ActionEvent evt) {
        Connection conn = null;
        try {
            conn = ConnectToSQLServer.getConnection();
            conn.setAutoCommit(false);

            String maDocGiaText = view.getTxtMaDocGia().getText().trim();
            LocalDateTime ngayMuon = view.getDateNgayMuon().getDateTimePermissive();
            LocalDateTime ngayTraDuKien = view.getDateNgayTraDuKien().getDateTimePermissive();

            if (maDocGiaText.isEmpty() || ngayMuon == null || ngayTraDuKien == null || danhSachSachDaThem.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập mã độc giả, chọn ngày mượn, ngày hẹn trả và thêm ít nhất một sách!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int madocgia = Integer.parseInt(maDocGiaText);
            if (getTenDocGia(madocgia) == null) {
                JOptionPane.showMessageDialog(view, "Mã độc giả không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (String masach : danhSachSachDaThem) {
                Sach sach = sachDAO.getSachById(masach);
                if (sach == null) {
                    JOptionPane.showMessageDialog(view, "Mã sách " + masach + " không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (sach.getSoluong() <= 0) {
                    JOptionPane.showMessageDialog(view, "Sách " + sach.getTensach() + " đã hết hàng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            if (ngayMuon.isAfter(ngayTraDuKien)) {
                JOptionPane.showMessageDialog(view, "Ngày mượn không được sau ngày hẹn trả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Kiểm tra ngày hẹn trả không vượt quá 30 ngày
            long daysBetween = ChronoUnit.DAYS.between(ngayMuon.toLocalDate(), ngayTraDuKien.toLocalDate());
            if (daysBetween > 30) {
                JOptionPane.showMessageDialog(view, "Ngày hẹn trả không được vượt quá 30 ngày so với ngày mượn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PhieuMuon pm = new PhieuMuon(0, madocgia, maNhanVien, ngayMuon, ngayTraDuKien, "Chưa trả");
            if (!phieuMuonDAO.addPhieuMuon(pm)) {
                JOptionPane.showMessageDialog(view, "Thêm phiếu mượn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                conn.rollback();
                return;
            }

            int maphieu = phieuMuonDAO.getLatestPhieuMuonId();
            boolean success = true;
            for (String masach : danhSachSachDaThem) {
                ChiTietPhieuMuon ctp = new ChiTietPhieuMuon(0, maphieu, masach);
                if (!phieuMuonDAO.addChiTietPhieuMuon(ctp)) {
                    LOGGER.warning("Không thể thêm chi tiết phiếu mượn cho mã sách: " + masach);
                    success = false;
                    break;
                }
                if (!sachDAO.giamSoLuongSach(masach)) {
                    LOGGER.warning("Không thể giảm số lượng sách cho mã sách: " + masach);
                    success = false;
                    break;
                }
            }
            if (success) {
                conn.commit();
                danhSachSachDaThem.clear();
                clearFields();
                loadSachDaThemTable(new ArrayList<>());
                loadTableData();
                loadSachTable();
                JOptionPane.showMessageDialog(view, "Thêm phiếu mượn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(view, "Thêm phiếu mượn thất bại do lỗi liên kết sách hoặc cập nhật số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, "Mã độc giả không hợp lệ", ex);
            JOptionPane.showMessageDialog(view, "Mã độc giả phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm phiếu mượn", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Lỗi khi đóng kết nối hoặc đặt lại autoCommit", e);
                }
            }
            view.getBtnThem().setEnabled(true);
        }
    }

    private void btnSuaActionPerformed(ActionEvent evt) {
        int row = view.getTablePhieuMuon().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một phiếu mượn để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = ConnectToSQLServer.getConnection();
            conn.setAutoCommit(false);

            String maDocGiaText = view.getTxtMaDocGia().getText().trim();
            LocalDateTime ngayMuon = view.getDateNgayMuon().getDateTimePermissive();
            LocalDateTime ngayTraDuKien = view.getDateNgayTraDuKien().getDateTimePermissive();
            String trangthai = (String) view.getTablePhieuMuon().getValueAt(row, 6);
            if ("Đã trả".equalsIgnoreCase(trangthai.trim())) {
                JOptionPane.showMessageDialog(view, "Không thể sửa phiếu mượn đã trả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (maDocGiaText.isEmpty() || ngayMuon == null || ngayTraDuKien == null || (!isGiaHanMode && danhSachSachDaThem.isEmpty())) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int madocgia = Integer.parseInt(maDocGiaText);
            int maphieu = (int) view.getTablePhieuMuon().getValueAt(row, 0);

            if (getTenDocGia(madocgia) == null) {
                JOptionPane.showMessageDialog(view, "Mã độc giả không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (getTenNhanVien(maNhanVien) == null) {
                JOptionPane.showMessageDialog(view, "Mã nhân viên không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isGiaHanMode) {
                for (String masach : danhSachSachDaThem) {
                    Sach sach = sachDAO.getSachById(masach);
                    if (sach == null) {
                        JOptionPane.showMessageDialog(view, "Mã sách " + masach + " không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // Chỉ kiểm tra số lượng cho sách mới
                    List<String> sachCu = phieuMuonDAO.getSachListByPhieuMuon(maphieu);
                    if (!sachCu.contains(masach) && sach.getSoluong() <= 0) {
                        JOptionPane.showMessageDialog(view, "Sách " + sach.getTensach() + " đã hết hàng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
            }
            if (ngayMuon.isAfter(ngayTraDuKien)) {
                JOptionPane.showMessageDialog(view, "Ngày mượn không được sau ngày hẹn trả!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Kiểm tra ngày hẹn trả không vượt quá 30 ngày
            long daysBetween = ChronoUnit.DAYS.between(ngayMuon.toLocalDate(), ngayTraDuKien.toLocalDate());
            if (daysBetween > 30) {
                JOptionPane.showMessageDialog(view, "Ngày hẹn trả không được vượt quá 30 ngày so với ngày mượn!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PhieuMuon pm = new PhieuMuon(maphieu, madocgia, maNhanVien, ngayMuon, ngayTraDuKien, trangthai);
            List<String> sachCu = phieuMuonDAO.getSachListByPhieuMuon(maphieu);

            if (!phieuMuonDAO.updatePhieuMuon(pm)) {
                JOptionPane.showMessageDialog(view, "Sửa phiếu mượn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                conn.rollback();
                return;
            }

            boolean success = true;
            if (!isGiaHanMode) {
                // Xóa chi tiết phiếu mượn cũ
                phieuMuonDAO.deleteChiTietPhieuMuon(maphieu);

                // Thêm chi tiết phiếu mượn mới
                for (String masach : danhSachSachDaThem) {
                    ChiTietPhieuMuon ctp = new ChiTietPhieuMuon(0, maphieu, masach);

                    if (!phieuMuonDAO.addChiTietPhieuMuon(ctp)) {
                        LOGGER.warning("Không thể thêm chi tiết phiếu mượn cho mã sách: " + masach);
                        success = false;
                        break;
                    }

                    // Giảm số lượng sách cho sách mới (không có trong danh sách cũ)
                    if (!sachCu.contains(masach)) {
                        if (!sachDAO.giamSoLuongSach(masach)) {
                            LOGGER.warning("Không thể giảm số lượng sách cho mã sách: " + masach);
                            success = false;
                            break;
                        }
                    }
                }

                // Khôi phục số lượng sách cho những sách bị xóa khỏi danh sách mượn
                for (String masach : sachCu) {
                    if (!danhSachSachDaThem.contains(masach)) {
                        if (!sachDAO.tangSoLuongSach(masach)) {
                            LOGGER.warning("Không thể tăng số lượng sách cho mã sách: " + masach);
                            success = false;
                            break;
                        }
                    }
                }
            }

            if (success) {
                conn.commit();
                JOptionPane.showMessageDialog(view, "Sửa phiếu mượn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
                loadSachTable();
                clearFields();
                danhSachSachDaThem.clear();
                if (isGiaHanMode) {
                    isGiaHanMode = false;
                    setGiaHanMode(false);
                }
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(view, "Sửa phiếu mượn thất bại do lỗi liên kết sách hoặc cập nhật số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, "Mã độc giả không hợp lệ", ex);
            JOptionPane.showMessageDialog(view, "Mã độc giả phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi sửa phiếu mượn", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi sửa phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Lỗi khi rollback giao dịch", rollbackEx);
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Lỗi khi đóng kết nối hoặc đặt lại autoCommit", e);
                }
            }
        }
    }

    private void btnXoaActionPerformed(ActionEvent evt) {
        int row = view.getTablePhieuMuon().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một phiếu mượn để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa phiếu mượn này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Connection conn = null;
        try {
            conn = ConnectToSQLServer.getConnection();
            conn.setAutoCommit(false);

            Object maphieuObj = view.getTablePhieuMuon().getValueAt(row, 0);
            int maphieu = (maphieuObj instanceof Number) ? ((Number) maphieuObj).intValue() : Integer.parseInt(maphieuObj.toString());

            String trangthai = (String) view.getTablePhieuMuon().getValueAt(row, 6);
            trangthai = (trangthai != null) ? trangthai.trim() : null;

            if ("Đã trả".equalsIgnoreCase(trangthai)) {
                JOptionPane.showMessageDialog(view, "Phiếu mượn này đã trả, không thể xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<String> sachList = phieuMuonDAO.getSachListByPhieuMuon(maphieu);

            if (!phieuMuonDAO.deleteChiTietPhieuMuon(maphieu)) {
                conn.rollback();
                JOptionPane.showMessageDialog(view, "Xóa chi tiết phiếu mượn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!phieuMuonDAO.deletePhieuMuon(maphieu)) {
                conn.rollback();
                JOptionPane.showMessageDialog(view, "Xóa phiếu mượn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = true;
            if ("Chưa trả".equalsIgnoreCase(trangthai)) {
                for (String masach : sachList) {
                    if (!sachDAO.tangSoLuongSach(masach)) {
                        success = false;
                        break;
                    }
                }
            }

            if (success) {
                conn.commit();
                JOptionPane.showMessageDialog(view, "Xóa phiếu mượn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
                loadSachTable();
                clearFields();
                danhSachSachDaThem.clear();
            } else {
                conn.rollback();
                JOptionPane.showMessageDialog(view, "Xóa thất bại do lỗi cập nhật số lượng sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException | ClassCastException | NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi xóa phiếu mượn", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi xóa phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                LOGGER.log(Level.SEVERE, "Lỗi khi rollback giao dịch", rollbackEx);
            }
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

    private void btnKiemtra2ActionPerformed(ActionEvent evt) {
        try {
            String maDocGiaText = view.getTxtMaDocGia().getText().trim();
            int madocgia = Integer.parseInt(maDocGiaText);
            String tendocgia = getTenDocGia(madocgia);
            setTenDocGia(tendocgia != null ? tendocgia : "Không tìm thấy");
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, "Mã độc giả không hợp lệ", ex);
            JOptionPane.showMessageDialog(view, "Mã độc giả phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi kiểm tra mã độc giả", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi kiểm tra mã độc giả: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnThemSachActionPerformed(ActionEvent evt) {
        int selectedRow = view.getTableSach().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sách để thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra số lượng sách đã thêm
        if (danhSachSachDaThem.size() >= 5) {
            JOptionPane.showMessageDialog(view, "Bạn chỉ được phép thêm tối đa 5 quyển sách!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String masach = (String) view.getTableSach().getValueAt(selectedRow, 0);
        int soluong = (int) view.getTableSach().getValueAt(selectedRow, 2);

        if (soluong <= 0) {
            JOptionPane.showMessageDialog(view, "Sách này đã hết hàng!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (danhSachSachDaThem.contains(masach)) {
            JOptionPane.showMessageDialog(view, "Sách này đã được thêm!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String tensach = getTenSach(masach);
            danhSachSachDaThem.add(masach);
            List<Object[]> sachDaThem = new ArrayList<>();
            for (String ms : danhSachSachDaThem) {
                String ts = getTenSach(ms);
                sachDaThem.add(new Object[]{ms, ts != null ? ts : "Không xác định"});
            }
            loadSachDaThemTable(sachDaThem);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi thêm sách: " + masach, ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnXoaSachActionPerformed(ActionEvent evt) {
        int selectedRow = view.getTableSachDaThem().getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sách để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String masach = (String) view.getTableSachDaThem().getValueAt(selectedRow, 0);
        danhSachSachDaThem.remove(masach);
        List<Object[]> sachDaThem = new ArrayList<>();
        for (String ms : danhSachSachDaThem) {
            try {
                String ts = getTenSach(ms);
                sachDaThem.add(new Object[]{ms, ts != null ? ts : "Không xác định"});
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi lấy tên sách: " + ms, ex);
                JOptionPane.showMessageDialog(view, "Lỗi khi lấy tên sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        loadSachDaThemTable(sachDaThem);
    }

    private void btnGiaHanActionPerformed(ActionEvent evt) {
        if (isProcessing) {
            return;
        }
        isProcessing = true;
        view.getBtnGiaHan().setEnabled(false);

        int selectedRow = view.getTablePhieuMuon().getSelectedRow();
        try {
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn một phiếu mượn để gia hạn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int maphieu = (int) view.getTablePhieuMuon().getValueAt(selectedRow, 0);
            PhieuMuon pm = phieuMuonDAO.getPhieuMuonById(maphieu);
            if (pm == null) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy phiếu mượn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String trangThai = pm.getTrangthai() != null ? pm.getTrangthai().trim() : null;
            if (trangThai == null || trangThai.equalsIgnoreCase("Đã trả")) {
                JOptionPane.showMessageDialog(view, "Phiếu mượn này đã trả hoặc trạng thái không hợp lệ, không thể gia hạn!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            isGiaHanMode = !isGiaHanMode;
            setGiaHanMode(isGiaHanMode);
            if (isGiaHanMode) {
                view.getDateNgayTraDuKien().setDateTimePermissive(pm.getNgayTraDuKien());
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi kiểm tra phiếu mượn để gia hạn", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi kiểm tra phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } finally {
            isProcessing = false;
            view.getBtnGiaHan().setEnabled(true);
        }
    }

    private void btnNhapDuLieuActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu phiếu mượn");
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

        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(selectedFile); Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheet("DanhSachPhieuMuon");
                if (sheet == null) {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy sheet 'DanhSachPhieuMuon'!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int successCount = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }

                    try {
                        String tenDocGia = row.getCell(0).getStringCellValue().trim();
                        String tenNhanVien = row.getCell(1).getStringCellValue().trim();
                        String maSachStr = row.getCell(2).getStringCellValue().trim();
                        String ngaymuonStr = row.getCell(3).getStringCellValue().trim();
                        String ngaytradukienStr = row.getCell(4).getStringCellValue().trim();
                        String trangthai = row.getCell(5).getStringCellValue().trim();

                        LocalDateTime ngaymuon = LocalDateTime.parse(ngaymuonStr, formatter);
                        LocalDateTime ngaytradukien = LocalDateTime.parse(ngaytradukienStr, formatter);

                        // Kiểm tra ngày hẹn trả không vượt quá 30 ngày
                        long daysBetween = ChronoUnit.DAYS.between(ngaymuon.toLocalDate(), ngaytradukien.toLocalDate());
                        if (daysBetween > 30) {
                            LOGGER.warning("Dòng " + (i + 1) + ": Ngày hẹn trả vượt quá 30 ngày so với ngày mượn!");
                            continue;
                        }

                        boolean resultAdd = phieuMuonDAO.addPhieuMuonFromExcel(
                                tenDocGia, tenNhanVien, maSachStr, ngaymuon, ngaytradukien, trangthai
                        );

                        if (resultAdd) {
                            successCount++;
                        } else {
                            LOGGER.warning("Không thêm được phiếu mượn tại dòng " + (i + 1));
                        }

                    } catch (Exception ex) {
                        LOGGER.warning("Lỗi khi xử lý hàng " + (i + 1) + ": " + ex.getMessage());
                    }
                }

                JOptionPane.showMessageDialog(view, "Đã nhập thành công " + successCount + " phiếu mượn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();

            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Lỗi khi đọc file Excel", ex);
                JOptionPane.showMessageDialog(view, "Lỗi khi đọc file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void btnXuatDuLieuActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("danh_sach_phieu_muon.xlsx"));
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
                Sheet sheet = workbook.createSheet("DanhSachPhieuMuon");
                Row headerRow = sheet.createRow(0);
                String[] headers = {"Mã Phiếu", "Tên độc giả", "Tên nhân viên", "Mã sách", "Ngày mượn", "Ngày hẹn trả", "Trạng thái"};
                for (int i = 0; i < headers.length; i++) {
                    headerRow.createCell(i).setCellValue(headers[i]);
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                DefaultTableModel model = (DefaultTableModel) view.getTablePhieuMuon().getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    row.createCell(0).setCellValue((int) model.getValueAt(i, 0));
                    row.createCell(1).setCellValue((String) model.getValueAt(i, 1));
                    row.createCell(2).setCellValue((String) model.getValueAt(i, 2));
                    row.createCell(3).setCellValue((String) model.getValueAt(i, 3));
                    LocalDateTime ngayMuon = (LocalDateTime) model.getValueAt(i, 4);
                    row.createCell(4).setCellValue(ngayMuon != null ? ngayMuon.format(formatter) : "");
                    LocalDateTime ngayTraDuKien = (LocalDateTime) model.getValueAt(i, 5);
                    row.createCell(5).setCellValue(ngayTraDuKien != null ? ngayTraDuKien.format(formatter) : "");
                    row.createCell(6).setCellValue((String) model.getValueAt(i, 6));
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

    private void searchPhieuMuon() {
        try {
            String searchText = view.getTxtTimKiem().getText().trim();
            String searchCriteria = (String) view.getCbTimKiem().getSelectedItem();
            List<PhieuMuon> phieuMuonList = searchText.isEmpty() ? phieuMuonDAO.getAllPhieuMuonWithDetails() : phieuMuonDAO.searchPhieuMuon(searchText, searchCriteria);
            List<Object[]> data = new ArrayList<>();
            for (PhieuMuon pm : phieuMuonList) {
                String tendocgia = getTenDocGia(pm.getMadocgia());
                String tennv = getTenNhanVien(pm.getManv());
                String masachList = phieuMuonDAO.getMasachListByPhieuMuon(pm.getMaphieu());
                data.add(new Object[]{
                    pm.getMaphieu(),
                    tendocgia != null ? tendocgia : "Không xác định",
                    tennv != null ? tennv : "Không xác định",
                    masachList,
                    pm.getNgaymuon(),
                    pm.getNgayTraDuKien(),
                    pm.getTrangthai() != null ? pm.getTrangthai() : "Chưa xác định"
                });
            }
            loadPhieuMuonTable(data);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm kiếm phiếu mượn", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchSach() {
        try {
            String searchText = view.getTxtTimKiemSach().getText().trim();
            String searchCriteria = (String) view.getCbTimKiemSach().getSelectedItem();
            List<Sach> sachList = sachDAO.getAllSach();
            List<Sach> filteredSachList = new ArrayList<>();
            for (Sach sach : sachList) {
                boolean match = false;
                if (searchCriteria.equals("Mã sách") && !searchText.isEmpty()) {
                    match = sach.getMasach().toLowerCase().contains(searchText.toLowerCase());
                } else if (searchCriteria.equals("Tên sách") && !searchText.isEmpty()) {
                    match = sach.getTensach().toLowerCase().contains(searchText.toLowerCase());
                } else if (searchText.isEmpty()) {
                    match = true;
                }
                if (match) {
                    filteredSachList.add(sach);
                }
            }
            loadSachTable(filteredSachList);
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Lỗi khi tìm kiếm sách", ex);
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadSachDaThemTable(List<Object[]> sachDaThem) {
        DefaultTableModel model = (DefaultTableModel) view.getTableSachDaThem().getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện có trong bảng
        for (Object[] row : sachDaThem) {
            model.addRow(row); // Thêm từng hàng vào bảng
        }
    }

    public void loadSachTable(List<Sach> sachList) {
        DefaultTableModel model = (DefaultTableModel) view.getTableSach().getModel();
        model.setRowCount(0);
        for (Sach sach : sachList) {
            model.addRow(new Object[]{sach.getMasach(), sach.getTensach(), sach.getSoluong()});
        }
    }

    public void loadPhieuMuonTable(List<Object[]> data) {
        DefaultTableModel model = (DefaultTableModel) view.getTablePhieuMuon().getModel();
        model.setRowCount(0);
        for (Object[] row : data) {
            model.addRow(row);
        }
    }

    public void clearFields() {
        view.getTxtMaDocGia().setText("");
        view.getDateNgayMuon().clear();
        view.getDateNgayTraDuKien().clear();
        view.getLblTenDocGia().setText("Tên độc giả: ");
        ((DefaultTableModel) view.getTableSachDaThem().getModel()).setRowCount(0);
    }

    public void setGiaHanMode(boolean isGiaHan) {
        view.getTxtMaDocGia().setEnabled(!isGiaHan);
        view.getDateNgayMuon().setEnabled(!isGiaHan);
        view.getBtnThem().setEnabled(!isGiaHan);
        view.getBtnXoa().setEnabled(!isGiaHan);
        view.getBtnKiemtra2().setEnabled(!isGiaHan);
        view.getBtnNhapDuLieu().setEnabled(!isGiaHan);
        view.getBtnXuatDuLieu().setEnabled(!isGiaHan);
        view.getBtnThemSach().setEnabled(!isGiaHan);
        view.getBtnXoaSach().setEnabled(!isGiaHan);
        view.getDateNgayTraDuKien().setEnabled(true);
        view.getBtnSua().setEnabled(true);
        view.getBtnGiaHan().setText(isGiaHan ? "Hủy gia hạn" : "Gia hạn phiếu mượn");
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

    private int getMaDocGiaByTen(String tenDocGia) throws SQLException {
        return tenDocGia.equals("Không xác định") ? -1 : docGiaDAO.getMaDocGiaByTen(tenDocGia);
    }

    public void setTenNhanVien(String ten) {
        view.getLblTenNhanVien().setText("Tên nhân viên: " + (ten != null ? ten : ""));
    }

    public void setTenDocGia(String ten) {
        view.getLblTenDocGia().setText("Tên độc giả: " + (ten != null ? ten : ""));
    }
}