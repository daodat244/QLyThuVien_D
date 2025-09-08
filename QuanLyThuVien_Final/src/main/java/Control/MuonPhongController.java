package Control;

import Model.DAO.DocGiaDAO;
import Model.DAO.MuonPhongDAO;
import Model.DAO.PhongHocDAO;
import Model.Entity.DocGia;
import Model.Entity.MuonPhong;
import Model.Entity.PhongHoc;
import View.ChucNang.PanelMuonPhong;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MuonPhongController {

    private final MuonPhongDAO muonPhongDAO;
    private final PhongHocDAO phongDAO;
    private final PanelMuonPhong view;

    public MuonPhongController(PanelMuonPhong view) {
        this.muonPhongDAO = new MuonPhongDAO();
        this.phongDAO = new PhongHocDAO();
        this.view = view;
        loadComboBoxes();
        initEventHandlers();
    }

    private void initEventHandlers() {
        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddMuonPhong();
            }
        });
        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateMuonPhong();
            }
        });
        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteMuonPhong();
            }
        });
        view.getBtnNhapDuLieu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleImportData();
            }
        });
        view.getBtnXuatDuLieu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExportData();
            }
        });
        view.getBtnKiemTra().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleKiemTra();
            }
        });
        view.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReset();
            }
        });
        view.getTxtTimKiem().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterMuonPhong();
            }

            public void removeUpdate(DocumentEvent e) {
                filterMuonPhong();
            }

            public void changedUpdate(DocumentEvent e) {
                filterMuonPhong();
            }
        });
        view.getTableMuonPhong().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleSuKienTableClick();
            }
        });
        view.getCBPhong().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kiemTraTinhTrangPhong();
            }
        });
    }

    public void handleAddMuonPhong() {
        try {
            MuonPhong muonPhong = new MuonPhong();

            PhongHoc selectedPhong = (PhongHoc) view.getCBPhong().getSelectedItem();
            if (selectedPhong == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn phòng học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            muonPhong.setMaphong(selectedPhong.getMaphong());

            String inputMaDocGia = view.getTxtMaDocGia().getText().trim();
            if (inputMaDocGia.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập mã độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int madocgia = Integer.parseInt(inputMaDocGia);
            muonPhong.setMadocgia(madocgia);

            LocalDateTime tgMuon = view.getDTPMuon().getDateTimeStrict();
            LocalDateTime tgTra = view.getDTPTra().getDateTimeStrict();
            if (tgMuon == null || tgTra == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn thời gian mượn và trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tgMuon.isAfter(tgTra)) {
                JOptionPane.showMessageDialog(view, "Thời gian mượn phải nhỏ hơn thời gian trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra trùng lịch
            if (muonPhongDAO.isPhongInUse(selectedPhong.getMaphong(), tgMuon, tgTra, 0)) {
                JOptionPane.showMessageDialog(view, "Phòng này đã được mượn trong khoảng thời gian đó!", "Trùng lịch", JOptionPane.WARNING_MESSAGE);
                return;
            }

            muonPhong.setTgianmuon(tgMuon);
            muonPhong.setTgiantra(tgTra);
            muonPhong.setGhichu(view.getTxaGhiChu().getText().trim());
//            muonPhong.setTrangthai(tgMuon.isAfter(LocalDateTime.now()) ? "Đặt trước" : "Đang mượn");
            muonPhong.setTrangthai("Đang mượn");

            if (muonPhongDAO.addMuonPhong(muonPhong)) {
                JOptionPane.showMessageDialog(view, "Thêm phiếu mượn phòng thành công!");
                muonPhongDAO.capNhatTrangThaiTuDong();
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm phiếu mượn phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Mã độc giả không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm phiếu mượn phòng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void handleUpdateMuonPhong() {
        int row = view.getTableMuonPhong().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một phiếu mượn phòng để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            MuonPhong muonPhong = new MuonPhong();
            int maphieump = Integer.parseInt(view.getTableMuonPhong().getValueAt(row, 0).toString());
            muonPhong.setMaphieump(maphieump);
            muonPhong.setGhichu(view.getTxaGhiChu().getText().trim());

            LocalDateTime tgMuon = view.getDTPMuon().getDateTimeStrict();
            LocalDateTime tgTra = view.getDTPTra().getDateTimeStrict();
            if (tgMuon == null || tgTra == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn thời gian mượn và trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (tgMuon.isAfter(tgTra)) {
                JOptionPane.showMessageDialog(view, "Thời gian mượn phải nhỏ hơn thời gian trả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String inputMaDocGia = view.getTxtMaDocGia().getText().trim();
            if (inputMaDocGia.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập mã độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int madocgia = Integer.parseInt(inputMaDocGia);
            muonPhong.setMadocgia(madocgia);

            PhongHoc selectedPhongHoc = (PhongHoc) view.getCBPhong().getSelectedItem();
            if (selectedPhongHoc == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn một phòng học!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            muonPhong.setMaphong(selectedPhongHoc.getMaphong());

            // Kiểm tra trùng lịch (loại trừ chính phiếu này)
            if (muonPhongDAO.isPhongInUse(selectedPhongHoc.getMaphong(), tgMuon, tgTra, maphieump)) {
                JOptionPane.showMessageDialog(view, "Phòng này đã được mượn trong khoảng thời gian đó!", "Trùng lịch", JOptionPane.WARNING_MESSAGE);
                return;
            }

            muonPhong.setTgianmuon(tgMuon);
            muonPhong.setTgiantra(tgTra);

            muonPhong.setTrangthai(tgTra.isBefore(LocalDateTime.now()) || tgTra.isEqual(LocalDateTime.now()) ? "Đã trả" : "Đang mượn");
            
            if (muonPhongDAO.updateMuonPhong(muonPhong)) {
                JOptionPane.showMessageDialog(view, "Sửa phiếu mượn phòng thành công!");
                muonPhongDAO.capNhatTrangThaiTuDong();
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Sửa phiếu mượn phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi sửa phiếu mượn phòng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteMuonPhong() {
        int row = view.getTableMuonPhong().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một phiếu mượn phòng để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa phiếu mượn phòng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maphieump = Integer.parseInt(view.getTableMuonPhong().getValueAt(row, 0).toString());
                if (muonPhongDAO.deleteMuonPhong(maphieump)) {
                    JOptionPane.showMessageDialog(view, "Xóa phiếu mượn phòng thành công!");
                    loadTableData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa phiếu mượn phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa phiếu mượn phòng: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void handleImportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập");
        int userSelection = fileChooser.showOpenDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            try (Workbook workbook = new XSSFWorkbook(file)) {
                Sheet sheet = workbook.getSheetAt(0);
                int successCount = 0, errorCount = 0;
                StringBuilder errorLog = new StringBuilder();

                boolean skipHeader = true;
                for (Row row : sheet) {
                    if (skipHeader) {
                        skipHeader = false;
                        continue;
                    }

                    try {
                        String tenPhong = row.getCell(0).toString().trim();
                        String tenDocGia = row.getCell(1).toString().trim();
                        String tgMuonStr = row.getCell(2).toString().trim();
                        String tgTraStr = row.getCell(3).toString().trim();
                        String ghiChu = row.getCell(4) != null ? row.getCell(4).toString().trim() : "";

                        PhongHocDAO phongDAO = new PhongHocDAO();
                        DocGiaDAO docGiaDAO = new DocGiaDAO();

                        PhongHoc phong = phongDAO.findByTenPhong(tenPhong);
                        DocGia docGia = docGiaDAO.getDocGiaByTen(tenDocGia);

                        if (phong == null) {
                            throw new Exception("Không tìm thấy phòng: " + tenPhong);
                        }
                        if (docGia == null) {
                            throw new Exception("Không tìm thấy độc giả: " + tenDocGia);
                        }

                        LocalDateTime tgMuon = LocalDateTime.parse(tgMuonStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                        LocalDateTime tgTra = LocalDateTime.parse(tgTraStr, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

                        if (tgMuon.isAfter(tgTra)) {
                            throw new Exception("Thời gian mượn lớn hơn thời gian trả");
                        }

                        MuonPhong mp = new MuonPhong();
                        mp.setMaphong(phong.getMaphong());
                        mp.setMadocgia(docGia.getMadocgia());
                        mp.setTgianmuon(tgMuon);
                        mp.setTgiantra(tgTra);
                        mp.setGhichu(ghiChu);

                        // Gán trạng thái dựa trên tgiantra
                        mp.setTrangthai(tgTra.isBefore(LocalDateTime.now()) ? "Đã trả" : "Đang mượn");
                        
                        if (muonPhongDAO.addMuonPhong(mp)) {
                            successCount++;
                        } else {
                            throw new Exception("Không thể thêm vào CSDL");
                        }
                    } catch (Exception exRow) {
                        errorCount++;
                        errorLog.append("Dòng ").append(row.getRowNum() + 1).append(": ")
                                .append(exRow.getMessage()).append("\n");
                        exRow.printStackTrace();
                    }
                }

                // Cập nhật trạng thái các phiếu cũ sau khi import xong
                muonPhongDAO.capNhatTrangThaiTuDong();

                loadTableData();
                JOptionPane.showMessageDialog(view,
                        "Đã nhập: " + successCount + " dòng, lỗi: " + errorCount
                        + (errorCount > 0 ? "\n\nChi tiết:\n" + errorLog.toString() : "")
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi đọc file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void handleExportData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("PhieuMuonPhong");
                DefaultTableModel model = (DefaultTableModel) view.getTableMuonPhong().getModel();

                // Ghi header
                Row header = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    Cell cell = header.createCell(i);
                    cell.setCellValue(model.getColumnName(i));
                }

                // Ghi dữ liệu
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        row.createCell(j).setCellValue(value != null ? value.toString() : "");
                    }
                }

                try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
                    workbook.write(fos);
                    JOptionPane.showMessageDialog(view, "Xuất file Excel thành công: " + fileToSave.getAbsolutePath());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xuất Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void handleKiemTra() {
        try {
            String input = view.getTxtMaDocGia().getText().trim();
            if (input.isEmpty()) {
                view.getLblTenDocGia().setText("Vui lòng nhập mã độc giả.");
                return;
            }

            int madocgia = Integer.parseInt(input);

            DocGiaDAO docGiaDAO = new DocGiaDAO();
            DocGia docGia = docGiaDAO.getDocGiaById(madocgia);

            if (docGia != null) {
                view.getLblTenDocGia().setText(docGia.getTendocgia());
            } else {
                view.getLblTenDocGia().setText("Không tìm thấy độc giả.");
            }

        } catch (NumberFormatException ex) {
            view.getLblTenDocGia().setText("Mã độc giả không hợp lệ.");
        } catch (SQLException ex) {
            view.getLblTenDocGia().setText("Lỗi kết nối CSDL: " + ex.getMessage());
        }
    }

    public void handleReset() {
        view.getTxtTimKiem().setText("");
        clearFields();
        loadTableData();
    }

    public void filterMuonPhong() {
        String keyword = view.getTxtTimKiem().getText().trim();
        String selectedFilter = (String) view.getCBTimKiem().getSelectedItem();

        List<Object[]> result;

        try {
            if (keyword.isEmpty()) {
                result = muonPhongDAO.getAllMuonPhongWithDetails();
            } else if ("Mã phiếu".equalsIgnoreCase(selectedFilter)) {
                try {
                    int id = Integer.parseInt(keyword);
                    result = muonPhongDAO.getMuonPhongById(id);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Mã phiếu phải là số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if ("Tên phòng".equalsIgnoreCase(selectedFilter)) {
                result = muonPhongDAO.getMuonPhongByTenPhong(keyword);
            } else if ("Tên độc giả".equalsIgnoreCase(selectedFilter)) {
                result = muonPhongDAO.getMuonPhongByTenDocGia(keyword);
            } else {
                JOptionPane.showMessageDialog(view, "Tiêu chí tìm kiếm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel model = (DefaultTableModel) view.getTableMuonPhong().getModel();
            model.setRowCount(0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (Object[] row : result) {
                LocalDateTime muon = (LocalDateTime) row[3];
                LocalDateTime tra = (LocalDateTime) row[4];
                String ghiChu = (String) row[5];
                String trangThai = (String) row[6];

                model.addRow(new Object[]{
                    row[0], // maphieump
                    row[1], // tenphong
                    row[2], // tendocgia
                    muon != null ? muon.format(formatter) : "",
                    tra != null ? tra.format(formatter) : "",
                    ghiChu, trangThai
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadTableData() {
        try {
            // Cập nhật trạng thái trước khi load dữ liệu
            muonPhongDAO.capNhatTrangThaiTuDong();

            List<Object[]> mpList = muonPhongDAO.getAllMuonPhongWithDetails();
            DefaultTableModel model = (DefaultTableModel) view.getTableMuonPhong().getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (Object[] row : mpList) {
                int maPhieu = (int) row[0];
                String tenPhong = (String) row[1];
                String tenDocGia = (String) row[2];
                LocalDateTime tgMuon = (LocalDateTime) row[3];
                LocalDateTime tgTra = (LocalDateTime) row[4];
                String ghiChu = (String) row[5];
                String trangThai = (String) row[6]; // <-- thêm dòng này nếu chưa có

                model.addRow(new Object[]{
                    maPhieu,
                    tenPhong,
                    tenDocGia,
                    tgMuon != null ? tgMuon.format(formatter) : "",
                    tgTra != null ? tgTra.format(formatter) : "",
                    ghiChu,
                    trangThai // <-- thêm dòng này nếu bảng có cột "Trạng thái"
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void handleSuKienTableClick() {
        int row = view.getTableMuonPhong().getSelectedRow();
        if (row >= 0) {
            try {
                // Lấy dữ liệu từ bảng
                int maphieump = Integer.parseInt(view.getTableMuonPhong().getValueAt(row, 0).toString());
                String tenPhong = view.getTableMuonPhong().getValueAt(row, 1).toString();
                String tenDocGia = view.getTableMuonPhong().getValueAt(row, 2).toString();
                String ghiChu = view.getTableMuonPhong().getValueAt(row, 5).toString();

                view.getLblTenDocGia().setText(tenDocGia);
                view.getTxaGhiChu().setText(ghiChu);

                // Lấy bản ghi đầy đủ từ DAO (để lấy mã độc giả, ngày mượn, trả)
                MuonPhongDAO dao = new MuonPhongDAO();
                List<Object[]> list = dao.getMuonPhongById(maphieump);
                if (!list.isEmpty()) {
                    Object[] mp = list.get(0);

                    LocalDateTime tgMuon = (LocalDateTime) mp[3];
                    LocalDateTime tgTra = (LocalDateTime) mp[4];
                    view.getDTPMuon().setDateTimeStrict(tgMuon);
                    view.getDTPTra().setDateTimeStrict(tgTra);
                }

                // Gán mã độc giả (lookup theo tên độc giả)
                DocGiaDAO docGiaDAO = new DocGiaDAO();
                DocGia docGia = docGiaDAO.getDocGiaByTen(tenDocGia);
                if (docGia != null) {
                    view.getTxtMaDocGia().setText(String.valueOf(docGia.getMadocgia()));
                }

                // Chọn đúng phòng học trong combobox
                for (int i = 0; i < view.getCBPhong().getItemCount(); i++) {
                    Object item = view.getCBPhong().getItemAt(i);
                    if (item instanceof PhongHoc phong && phong.getTenphong().equalsIgnoreCase(tenPhong)) {
                        view.getCBPhong().setSelectedIndex(i);
                        break;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xử lý dữ liệu phiếu mượn: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadComboBoxes() {
        try {
            // Tải Nhà Xuất Bản
            List<PhongHoc> phonghocList = phongDAO.getAllPhongHoc();
            for (PhongHoc phonghoc : phonghocList) {
                view.getCBPhong().addItem(phonghoc);
            }
            view.getCBPhong().setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof PhongHoc) {
                        PhongHoc phonghoc = (PhongHoc) value;
                        setText(phonghoc.getTenphong());
                    }
                    return this;
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu ComboBox: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void kiemTraTinhTrangPhong() {
        try {
            PhongHoc selectedPhong = (PhongHoc) view.getCBPhong().getSelectedItem();
            if (selectedPhong == null) {
                view.getLblRoomAvailable().setText("");
                return;
            }

            int maphong = selectedPhong.getMaphong();
            LocalDateTime now = LocalDateTime.now();

            boolean isInUse = muonPhongDAO.isPhongInUse(maphong, now, now, 0);

            if (isInUse) {
                view.getLblRoomAvailable().setText("Phòng đang được mượn");
                view.getLblRoomAvailable().setForeground(Color.RED);
            } else {
                view.getLblRoomAvailable().setText("Phòng khả dụng");
                view.getLblRoomAvailable().setForeground(new Color(0, 128, 0)); // xanh lá đậm
            }

        } catch (Exception ex) {
            view.getLblRoomAvailable().setText("Không kiểm tra được");
            view.getLblRoomAvailable().setForeground(Color.GRAY);
        }
    }


    private void clearFields() {
        view.getTxtMaDocGia().setText("");
        view.getTxaGhiChu().setText("");
    }
}
