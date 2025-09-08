package Control;

import Model.DAO.NhaXuatBanDAO;
import Model.DAO.SuKienDAO;
import Model.Entity.NhaXuatBan;
import Model.Entity.SuKien;
import View.ChucNang.PanelSuKien;
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

public class SuKienController {
    private final SuKienDAO suKienDAO;
    private final NhaXuatBanDAO nxbDAO;
    private final PanelSuKien view;
    
    public SuKienController(PanelSuKien view) {
        this.suKienDAO = new SuKienDAO();
        this.nxbDAO = new NhaXuatBanDAO();
        this.view = view;
        loadComboBoxes();
        initEventHandlers();
    }
    
    private void initEventHandlers(){
        
        view.getBtnThem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddSuKien();
            }
        });
        view.getBtnSua().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateSuKien();
            }
        });
        view.getBtnXoa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteSuKien();
            }
        });
        view.getTxtTimKiem().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterSuKien();
            }

            public void removeUpdate(DocumentEvent e) {
                filterSuKien();
            }

            public void changedUpdate(DocumentEvent e) {
                filterSuKien();
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
        view.getBtnReset().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReset();
            }
        });
        view.getTableSuKien().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleSuKienTableClick();
            }
        });

    }
    
    public void handleAddSuKien() {
        try {
            SuKien suKien = new SuKien();
            String tenSuKien = view.getTxtTenSuKien().getText().trim();
            String moTa = view.getTxaMoTa().getText().trim();

            LocalDateTime tgiantochuc = view.getDTPToChuc().getDateTimeStrict();
            LocalDateTime tgianketthuc = view.getDTPKetThuc().getDateTimeStrict();

            if (tenSuKien.isEmpty() || moTa.isEmpty() || tgiantochuc == null || tgianketthuc == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ: tên, mô tả, thời gian tổ chức và kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!tenSuKien.matches("[\\p{L}\\p{N} ]+")) {
                JOptionPane.showMessageDialog(null, "Tên sự kiện không được chứa ký tự đặc biệt!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tenSuKien.matches(".*[a-zA-Z]\\d.*") || tenSuKien.matches(".*\\d[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(null, "Tên sự kiện không được để chữ và số dính liền nhau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!tgianketthuc.isAfter(tgiantochuc)) {
                JOptionPane.showMessageDialog(null, "Thời gian kết thúc phải sau thời gian tổ chức!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            NhaXuatBan selectedNXB = (NhaXuatBan) view.getCBNXB().getSelectedItem();
            if (selectedNXB == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một nhà xuất bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            suKien.setTensukien(tenSuKien);
            suKien.setMota(moTa);
            suKien.setTgiantochuc(tgiantochuc);
            suKien.setTgianketthuc(tgianketthuc);
            suKien.setManxb(selectedNXB.getManxb());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedTgianToChuc = tgiantochuc.format(formatter);
            String formattedTgianKetThuc = tgianketthuc.format(formatter);

            if (suKienDAO.themSuKien(suKien)) {
                JOptionPane.showMessageDialog(null, "Thêm sự kiện thành công!");

                loadTableData(); // Có thể dùng model.addRow nếu không cần tải lại toàn bộ
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm sự kiện thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm sự kiện: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void handleUpdateSuKien() {
        int row = view.getTableSuKien().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sự kiện để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            SuKien suKien = new SuKien();
            suKien.setMasukien(Integer.parseInt(view.getTableSuKien().getValueAt(row, 0).toString()));
            suKien.setMota(view.getTxaMoTa().getText().trim());

            String tenSuKien = view.getTxtTenSuKien().getText().trim();
            if (tenSuKien.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập tên sự kiện!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!tenSuKien.matches("[\\p{L}\\p{N} ]+")) {
                JOptionPane.showMessageDialog(view, "Tên sự kiện không được chứa ký tự đặc biệt!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tenSuKien.matches(".*[a-zA-Z]\\d.*") || tenSuKien.matches(".*\\d[a-zA-Z].*")) {
                JOptionPane.showMessageDialog(view, "Tên sự kiện không được để chữ và số dính liền nhau!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            suKien.setTensukien(tenSuKien);

            LocalDateTime tgiantochuc = view.getDTPToChuc().getDateTimeStrict();
            LocalDateTime tgianketthuc = view.getDTPKetThuc().getDateTimeStrict();

            if (tgiantochuc == null || tgianketthuc == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn đầy đủ thời gian tổ chức và kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!tgianketthuc.isAfter(tgiantochuc)) {
                JOptionPane.showMessageDialog(view, "Thời gian kết thúc phải sau thời gian tổ chức!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            suKien.setTgiantochuc(tgiantochuc);
            suKien.setTgianketthuc(tgianketthuc);

            NhaXuatBan selectedNXB = (NhaXuatBan) view.getCBNXB().getSelectedItem();
            if (selectedNXB == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn một nhà xuất bản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            suKien.setManxb(selectedNXB.getManxb());

            if (suKienDAO.updateSuKien(suKien)) {
                JOptionPane.showMessageDialog(view, "Sửa sự kiện thành công!");
                loadTableData(); // phương thức này phải format thời gian hiển thị đẹp
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Sửa sự kiện thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi sửa sự kiện: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void handleDeleteSuKien() {
        int row = view.getTableSuKien().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sự kiện để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa sự kiện này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int masukien = Integer.parseInt(view.getTableSuKien().getValueAt(row, 0).toString());
                if (suKienDAO.deleteSuKien(masukien)) {
                    JOptionPane.showMessageDialog(view, "Xóa sự kiện thành công!");
                    loadTableData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa sự kiện thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa sự kiện: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                        // Đọc và làm sạch dữ liệu từ Excel
                        String tenSuKien = row.getCell(0).toString().trim().replace('\u00A0', ' ');
                        String tenNXB = row.getCell(1).toString().trim();
                        String tgToChucStr = row.getCell(2).toString().trim();
                        String tgKetThucStr = row.getCell(3).toString().trim();
                        String moTa = row.getCell(4) != null ? row.getCell(4).toString().trim() : "";

                        // Kiểm tra dữ liệu bắt buộc
                        if (tenSuKien.isEmpty() || tenNXB.isEmpty() || tgToChucStr.isEmpty() || tgKetThucStr.isEmpty()) {
                            throw new Exception("Thiếu dữ liệu bắt buộc!");
                        }

                        // Regex kiểm tra tên sự kiện
                        if (!tenSuKien.matches("[\\p{L}\\p{N} ]+")) {
                            throw new Exception("Tên sự kiện không hợp lệ (không ký tự đặc biệt)");
                        }
                        if (tenSuKien.matches(".*[a-zA-Z]\\d.*") || tenSuKien.matches(".*\\d[a-zA-Z].*")) {
                            throw new Exception("Tên sự kiện không được để chữ và số dính liền!");
                        }

                        // Tìm NXB từ DB
                        NhaXuatBan nxb = nxbDAO.findByTenNXB(tenNXB);
                        if (nxb == null) {
                            throw new Exception("Không tìm thấy nhà xuất bản: " + tenNXB);
                        }

                        // Parse thời gian
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                        LocalDateTime tgiantochuc = LocalDateTime.parse(tgToChucStr, formatter);
                        LocalDateTime tgianketthuc = LocalDateTime.parse(tgKetThucStr, formatter);

                        if (!tgianketthuc.isAfter(tgiantochuc)) {
                            throw new Exception("Thời gian kết thúc phải sau thời gian tổ chức!");
                        }

                        // Tạo đối tượng sự kiện
                        SuKien sk = new SuKien();
                        sk.setTensukien(tenSuKien);
                        sk.setManxb(nxb.getManxb());
                        sk.setTgiantochuc(tgiantochuc);
                        sk.setTgianketthuc(tgianketthuc);
                        sk.setMota(moTa);

                        // Thêm vào DB
                        if (suKienDAO.themSuKien(sk)) {
                            successCount++;
                        } else {
                            throw new Exception("Không thể thêm sự kiện vào CSDL");
                        }

                    } catch (Exception exRow) {
                        errorCount++;
                        errorLog.append("Dòng ").append(row.getRowNum() + 1).append(": ")
                                .append(exRow.getMessage()).append("\n");
                        exRow.printStackTrace(); // log lỗi console
                    }
                }

                loadTableData(); // làm mới bảng
                JOptionPane.showMessageDialog(view,
                    "Đã nhập: " + successCount + " dòng, lỗi: " + errorCount +
                    (errorCount > 0 ? "\n\nChi tiết:\n" + errorLog.toString() : "")
                );

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi đọc file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    public void handleExportData(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        int userSelection = fileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("DanhSachSuKien");
                DefaultTableModel model = (DefaultTableModel) view.getTableSuKien().getModel();

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
    
    public void handleReset(){
        clearFields();
        loadTableData();
    }
    
    public void filterSuKien() {
        String keyword = view.getTxtTimKiem().getText().trim();
        String selectedFilter = (String) view.getCBTimKiem().getSelectedItem();

        try {
            List<Object[]> result;

            if ("Tên sự kiện".equalsIgnoreCase(selectedFilter)) {
                result = suKienDAO.getSuKienByTen(keyword);
            } else if ("Mã sự kiện".equalsIgnoreCase(selectedFilter)) {
                try {
                    int id = Integer.parseInt(keyword);
                    result = suKienDAO.getSuKienById(id);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Mã sự kiện phải là số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(view, "Tiêu chí tìm kiếm không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel model = (DefaultTableModel) view.getTableSuKien().getModel();
            model.setRowCount(0);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (Object[] row : result) {
                LocalDateTime tgToChuc = (LocalDateTime) row[3];
                LocalDateTime tgKetThuc = (LocalDateTime) row[4];

                String formattedTgTC = tgToChuc != null ? tgToChuc.format(formatter) : "";
                String formattedTgKT = tgKetThuc != null ? tgKetThuc.format(formatter) : "";

                model.addRow(new Object[]{
                    row[0], // masukien
                    row[1], // tensukien
                    row[2], // tennxb
                    formattedTgTC,
                    formattedTgKT,
                    row[5]  // mota
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadComboBoxes() {
        try {
            // Tải Nhà Xuất Bản
            List<NhaXuatBan> nxbList = nxbDAO.getAllNhaXuatBan();
            for (NhaXuatBan nxb : nxbList) {
                view.getCBNXB().addItem(nxb);
            }
            view.getCBNXB().setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof NhaXuatBan) {
                        NhaXuatBan nxb = (NhaXuatBan) value;
                        setText(nxb.getTennxb());
                    }
                    return this;
                }
            });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void loadTableData() {
        try {
            List<Object[]> skList = suKienDAO.getAllSuKienWithDetails(); // đã chuẩn hóa DAO để trả về List<Object[]>
            DefaultTableModel model = (DefaultTableModel) view.getTableSuKien().getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ

            // Định dạng LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            for (Object[] row : skList) {
                String formattedTgToChuc = row[3] != null ? ((LocalDateTime) row[3]).format(formatter) : "";
                String formattedTgKetThuc = row[4] != null ? ((LocalDateTime) row[4]).format(formatter) : "";
                model.addRow(new Object[] {
                    row[0], // masukien
                    row[1], // tensukien
                    row[2], // tennxb
                    formattedTgToChuc,
                    formattedTgKetThuc,
                    row[5]  // mota
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu sự kiện: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void handleSuKienTableClick() {
        int row = view.getTableSuKien().getSelectedRow();
        if (row >= 0) {
            view.getTxtTenSuKien().setText(view.getTableSuKien().getValueAt(row, 1).toString());
            view.getTxaMoTa().setText(view.getTableSuKien().getValueAt(row, 5).toString());

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

                String tgTCStr = view.getTableSuKien().getValueAt(row, 3).toString();
                String tgKTStr = view.getTableSuKien().getValueAt(row, 4).toString();

                LocalDateTime tgToChuc = LocalDateTime.parse(tgTCStr, formatter);
                LocalDateTime tgKetThuc = LocalDateTime.parse(tgKTStr, formatter);

                view.getDTPToChuc().setDateTimeStrict(tgToChuc);
                view.getDTPKetThuc().setDateTimeStrict(tgKetThuc);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi phân tích thời gian: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }

            String tenNXB = view.getTableSuKien().getValueAt(row, 2).toString();
            for (int i = 0; i < view.getCBNXB().getItemCount(); i++) {
                NhaXuatBan nxb = (NhaXuatBan) view.getCBNXB().getItemAt(i);
                if (nxb.getTennxb().equalsIgnoreCase(tenNXB)) {
                    view.getCBNXB().setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    
    private void clearFields() {
        view.getTxtTenSuKien().setText("");
        view.getTxaMoTa().setText("");
        view.getDTPToChuc().clear(); // clear DateTimePicker tổ chức
        view.getDTPKetThuc().clear(); // clear DateTimePicker kết thúc
        view.getCBNXB().setSelectedIndex(-1); // bỏ chọn nhà xuất bản
    }

}

