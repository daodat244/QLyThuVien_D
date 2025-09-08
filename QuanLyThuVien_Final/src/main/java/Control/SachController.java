package Control;

import Model.DAO.SachDAO;
import Model.DAO.TacGiaDAO;
import Model.DAO.NhaXuatBanDAO;
import Model.DAO.TheLoaiDAO;
import Model.Entity.Sach;
import Model.Entity.TacGia;
import Model.Entity.NhaXuatBan;
import Model.Entity.TheLoai;
import Utils.ConnectToSQLServer;
import View.ChucNang.PanelSach;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class SachController {

    private final PanelSach view;
    private final SachDAO sachDAO;
    private final TacGiaDAO tacGiaDAO;
    private final NhaXuatBanDAO nxbDAO;
    private final TheLoaiDAO theLoaiDAO;

    public SachController(PanelSach view) {
        this.view = view;
        this.sachDAO = new SachDAO();
        this.tacGiaDAO = new TacGiaDAO();
        this.nxbDAO = new NhaXuatBanDAO();
        this.theLoaiDAO = new TheLoaiDAO();
    }

    public void loadComboBoxes() {
        try {
            // Tác giả
            List<TacGia> tacGiaList = tacGiaDAO.getAllTacGia();
            view.getCbTacGia().removeAllItems();
            TacGia placeholderTacGia = new TacGia();
            placeholderTacGia.setMatacgia(-1);
            placeholderTacGia.setTentacgia("--Chọn tác giả--");
            view.getCbTacGia().addItem(placeholderTacGia);
            for (TacGia tacGia : tacGiaList) {
                view.getCbTacGia().addItem(tacGia);
            }
            view.getCbTacGia().setSelectedIndex(0); // Chọn placeholder mặc định

            // Nhà xuất bản
            List<NhaXuatBan> nxbList = nxbDAO.getAllNhaXuatBan();
            view.getCbNXB().removeAllItems();
            NhaXuatBan placeholderNXB = new NhaXuatBan();
            placeholderNXB.setManxb(-1);
            placeholderNXB.setTennxb("--Chọn NXB--");
            view.getCbNXB().addItem(placeholderNXB);
            for (NhaXuatBan nxb : nxbList) {
                view.getCbNXB().addItem(nxb);
            }
            view.getCbNXB().setSelectedIndex(0); // Chọn placeholder mặc định

            // Thể loại
            List<TheLoai> theLoaiList = theLoaiDAO.getAllTheLoai();
            view.getCbTheLoai().removeAllItems();
            TheLoai placeholderTheLoai = new TheLoai();
            placeholderTheLoai.setMatheloai(-1);
            placeholderTheLoai.setTentheloai("--Chọn thể loại--");
            view.getCbTheLoai().addItem(placeholderTheLoai);
            for (TheLoai theLoai : theLoaiList) {
                view.getCbTheLoai().addItem(theLoai);
            }
            view.getCbTheLoai().setSelectedIndex(0); // Chọn placeholder mặc định
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadTableData() {
        try {
            List<Object[]> sachList = sachDAO.getAllSachWithDetails();
            updateTable(sachList);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchSach(String keyword, String criteria) {
        try {
            List<Object[]> sachList = sachDAO.searchSachWithDetails(keyword.trim().toLowerCase(), criteria);
            updateTable(sachList);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Object[]> sachList) {
        DefaultTableModel model = (DefaultTableModel) view.getTableSach().getModel();
        model.setRowCount(0);
        for (Object[] row : sachList) {
            model.addRow(new Object[]{
                row[0], // masach
                row[1], // tensach
                row[2], 
                row[3], 
                row[4], 
                row[5], 
                row[6], // sotrang
                row[7] // soluong
            });
        }
    }
    
    private Sach getSachFromForm() {
        Sach sach = new Sach();
        sach.setMasach(view.getTxtMaSach().getText().trim());
        sach.setTensach(view.getTxtTenSach().getText().trim());
        sach.setMatacgia(((TacGia) view.getCbTacGia().getSelectedItem()).getMatacgia());
        sach.setManxb(((NhaXuatBan) view.getCbNXB().getSelectedItem()).getManxb());
        sach.setMatheloai(((TheLoai) view.getCbTheLoai().getSelectedItem()).getMatheloai());
        String namXB = view.getTxtNamXB().getText().trim();
        sach.setNamxb(namXB.isEmpty() ? 0 : Integer.parseInt(namXB));
        String soTrang = view.getTxtSoTrang().getText().trim();
        sach.setSotrang(soTrang.isEmpty() ? 0 : Integer.parseInt(soTrang));
        sach.setSoluong(Integer.parseInt(view.getTxtSoLuong().getText().trim()));
        sach.setAnh(view.getAnhData());
        return sach;
    }

    public void selectSachFromTable(int row) {
        if (row >= 0) {
            try {
                String maSach = view.getTableSach().getValueAt(row, 0).toString();
                view.getTxtMaSach().setText(maSach);
                view.getTxtTenSach().setText(view.getTableSach().getValueAt(row, 1).toString());
                view.getTxtNamXB().setText(view.getTableSach().getValueAt(row, 5) != null ? view.getTableSach().getValueAt(row, 5).toString() : "");
                view.getTxtSoTrang().setText(view.getTableSach().getValueAt(row, 6) != null ? view.getTableSach().getValueAt(row, 6).toString() : "");
                view.getTxtSoLuong().setText(view.getTableSach().getValueAt(row, 7).toString());

                String tenTacGia = view.getTableSach().getValueAt(row, 2).toString();
                String tenNXB = view.getTableSach().getValueAt(row, 3).toString();
                String tenTheLoai = view.getTableSach().getValueAt(row, 4).toString();

                for (int i = 0; i < view.getCbTacGia().getItemCount(); i++) {
                    TacGia tacGia = view.getCbTacGia().getItemAt(i);
                    if (tacGia.getTentacgia().equals(tenTacGia)) {
                        view.getCbTacGia().setSelectedIndex(i);
                        break;
                    }
                }
                for (int i = 0; i < view.getCbNXB().getItemCount(); i++) {
                    NhaXuatBan nxb = view.getCbNXB().getItemAt(i);
                    if (nxb.getTennxb().equals(tenNXB)) {
                        view.getCbNXB().setSelectedIndex(i);
                        break;
                    }
                }
                for (int i = 0; i < view.getCbTheLoai().getItemCount(); i++) {
                    TheLoai theLoai = view.getCbTheLoai().getItemAt(i);
                    if (theLoai.getTentheloai().equals(tenTheLoai)) {
                        view.getCbTheLoai().setSelectedIndex(i);
                        break;
                    }
                }

                // Lấy ảnh từ cơ sở dữ liệu
                Sach sach = sachDAO.getSachById(maSach);
                view.setAnhData(sach != null ? sach.getAnh() : null);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi lấy dữ liệu sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi chọn sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public void addSach() {
        try {
            Sach sach = getSachFromForm();
            if (validateSach(sach, true) && sachDAO.addSach(sach)) {
                JOptionPane.showMessageDialog(view, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ cho Năm XB, Số trang hoặc Số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateSach() {
        int row = view.getTableSach().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sách để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Sach sach = getSachFromForm();
            sach.setMasach(view.getTableSach().getValueAt(row, 0).toString());
            if (validateSach(sach, false) && sachDAO.updateSach(sach)) {
                JOptionPane.showMessageDialog(view, "Sửa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(view, "Sửa sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập số hợp lệ cho Năm XB, Số trang hoặc Số lượng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi sửa sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteSach() {
        int row = view.getTableSach().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một sách để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa sách này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                
                String masach = view.getTableSach().getValueAt(row, 0).toString();
                if (sachDAO.inUse(masach)) {
                    JOptionPane.showMessageDialog(view,
                            "Không thể xóa! Sách với mã " + masach + " đang được mượn.",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (sachDAO.deleteSach(masach)) {
                    JOptionPane.showMessageDialog(view, "Xóa sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    loadTableData();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa sách thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public boolean addSach(Sach sach) throws SQLException {
        String query = "INSERT INTO sach (masach, tensach, matacgia, manxb, matheloai, namxb, sotrang, soluong, anh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sach.getMasach());
            stmt.setString(2, sach.getTensach());
            stmt.setInt(3, sach.getMatacgia());
            stmt.setInt(4, sach.getManxb());
            stmt.setInt(5, sach.getMatheloai());
            stmt.setInt(6, sach.getNamxb());
            stmt.setInt(7, sach.getSotrang());
            stmt.setInt(8, sach.getSoluong());
            stmt.setBytes(9, sach.getAnh());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateSach(Sach sach) throws SQLException {
        String query = "UPDATE sach SET tensach = ?, matacgia = ?, manxb = ?, matheloai = ?, namxb = ?, sotrang = ?, soluong = ?, anh = ? WHERE masach = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sach.getTensach());
            stmt.setInt(2, sach.getMatacgia());
            stmt.setInt(3, sach.getManxb());
            stmt.setInt(4, sach.getMatheloai());
            stmt.setInt(5, sach.getNamxb());
            stmt.setInt(6, sach.getSotrang());
            stmt.setInt(7, sach.getSoluong());
            stmt.setBytes(8, sach.getAnh()); // Thêm ảnh
            stmt.setString(9, sach.getMasach());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteSach(String masach) throws SQLException {
        String query = "DELETE FROM sach WHERE masach = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, masach);
            return stmt.executeUpdate() > 0;
        }
    }

    public void searchSachByYear(String namBatDauStr, String namKetThucStr) {
        try {

            int namBatDau = 0;
            int namKetThuc = Calendar.getInstance().get(Calendar.YEAR);
            if (!namBatDauStr.isEmpty()) {
                try {
                    namBatDau = Integer.parseInt(namBatDauStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Năm bắt đầu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (!namKetThucStr.isEmpty()) {
                try {
                    namKetThuc = Integer.parseInt(namKetThucStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Năm kết thúc không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (namBatDau > namKetThuc) {
                JOptionPane.showMessageDialog(view, "Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (namBatDau < 0 || namKetThuc < 0) {
                JOptionPane.showMessageDialog(view, "Năm không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (namKetThuc > Calendar.getInstance().get(Calendar.YEAR)) {
                JOptionPane.showMessageDialog(view, "Năm kết thúc không được lớn hơn năm hiện tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Object[]> sachList = sachDAO.searchSachByYearRange(namBatDau, namKetThuc);
            updateTable(sachList);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi khi tìm kiếm sách theo năm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void exportToExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new File("danh_sach_sach.xlsx"));
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

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getAbsolutePath().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }
            try {
                List<Object[]> sachList = sachDAO.getAllSachWithDetails();
                if (sachList.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Không có dữ liệu để xuất!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try (Workbook workbook = new XSSFWorkbook()) {
                    Sheet sheet = workbook.createSheet("DanhSachSach");
                    Row headerRow = sheet.createRow(0);
                    String[] headers = {"Mã sách", "Tên sách", "Tác giả", "NXB", "Thể loại", "Năm XB", "Số trang", "Số lượng"};
                    for (int i = 0; i < headers.length; i++) {
                        headerRow.createCell(i).setCellValue(headers[i]);
                    }
                    for (int i = 0; i < sachList.size(); i++) {
                        Row row = sheet.createRow(i + 1);
                        Object[] data = sachList.get(i);                                                                                                                                                                                 //SachWithDetails sach = sachList.get(i);
                        row.createCell(0).setCellValue(data[0] != null ? data[0].toString() : "");                                                                                                                                        //sach.getMasach() != null
                        row.createCell(1).setCellValue(data[1] != null ? data[1].toString() : "");
                        row.createCell(2).setCellValue(data[2] != null ? data[2].toString() : "");
                        row.createCell(3).setCellValue(data[3] != null ? data[3].toString() : "");
                        row.createCell(4).setCellValue(data[4] != null ? data[4].toString() : "");
                        row.createCell(5).setCellValue(data[5] instanceof Integer ? ((Integer) data[5]).intValue() : 0);
                        row.createCell(6).setCellValue(data[6] instanceof Integer ? ((Integer) data[6]).intValue() : 0);
                        row.createCell(7).setCellValue(data[7] instanceof Integer ? ((Integer) data[7]).intValue() : 0);
                    }
                    for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                    }
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        workbook.write(fos);
                        JOptionPane.showMessageDialog(view, "Xuất file Excel thành công: " + file.getAbsolutePath(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (IOException | SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xuất file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void importFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu sách");
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

        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
                Sheet sheet = workbook.getSheetAt(0);
                Row headerRow = sheet.getRow(0);
                String[] expectedHeaders = {"Mã sách", "Tên sách", "Tác giả", "NXB", "Thể loại", "Năm XB", "Số trang", "Số lượng"};

                // Kiểm tra tiêu đề
                boolean headerValid = true;
                for (int i = 0; i < expectedHeaders.length; i++) {
                    String cellValue = getCellValue(headerRow.getCell(i));
                    if (!cellValue.equals(expectedHeaders[i])) {
                        headerValid = false;
                        break;
                    }
                }
                if (!headerValid) {
                    JOptionPane.showMessageDialog(view, "File Excel không đúng định dạng. Vui lòng kiểm tra tiêu đề cột!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int successCount = 0;
                int errorCount = 0;
                StringBuilder errorMessages = new StringBuilder();
                List<TacGia> tacGiaList = tacGiaDAO.getAllTacGia();
                List<NhaXuatBan> nxbList = nxbDAO.getAllNhaXuatBan();
                List<TheLoai> theLoaiList = theLoaiDAO.getAllTheLoai();
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) {
                        continue;
                    }

                    try {
                        if (row.getLastCellNum() < 8) {
                            errorMessages.append("Dòng ").append(i + 1).append(": File Excel không đủ cột (yêu cầu 8 cột).\n");
                            errorCount++;
                            continue;
                        }

                        Sach sach = new Sach();
                        String maSach = getCellValue(row.getCell(0));
                        String tenSach = getCellValue(row.getCell(1));
                        String tenTacGia = getCellValue(row.getCell(2));
                        String tenNXB = getCellValue(row.getCell(3));
                        String tenTheLoai = getCellValue(row.getCell(4));
                        String namXBStr = getCellValue(row.getCell(5));
                        String soTrangStr = getCellValue(row.getCell(6));
                        String soLuongStr = getCellValue(row.getCell(7));

                        int namXB = 0;
                        if (!namXBStr.isEmpty()) {
                            try {
                                namXB = Integer.parseInt(namXBStr);
                            } catch (NumberFormatException e) {
                                errorMessages.append("Dòng ").append(i + 1).append(": Năm xuất bản không hợp lệ.\n");
                                errorCount++;
                                continue;
                            }
                        }

                        int soTrang = 0;
                        if (!soTrangStr.isEmpty()) {
                            try {
                                soTrang = Integer.parseInt(soTrangStr);
                            } catch (NumberFormatException e) {
                                errorMessages.append("Dòng ").append(i + 1).append(": Số trang không hợp lệ.\n");
                                errorCount++;
                                continue;
                            }
                        }

                        int soLuong;
                        try {
                            soLuong = Integer.parseInt(soLuongStr);
                        } catch (NumberFormatException e) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Số lượng không hợp lệ.\n");
                            errorCount++;
                            continue;
                        }

                        if (maSach.isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã sách không được để trống.\n");
                            errorCount++;
                            continue;
                        }
                        if (tenSach.isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Tên sách không được để trống.\n");
                            errorCount++;
                            continue;
                        }
                        if (tenTacGia.isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Tên tác giả không được để trống.\n");
                            errorCount++;
                            continue;
                        }
                        if (tenNXB.isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Tên nhà xuất bản không được để trống.\n");
                            errorCount++;
                            continue;
                        }
                        if (tenTheLoai.isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Tên thể loại không được để trống.\n");
                            errorCount++;
                            continue;
                        }
                        if (soLuongStr.isEmpty()) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Số lượng không được để trống.\n");
                            errorCount++;
                            continue;
                        }
                        if (namXB > currentYear) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Năm xuất bản phải nhỏ hơn hoặc bằng năm hiện tại (").append(currentYear).append(").\n");
                            errorCount++;
                            continue;
                        }
                        if (soTrang < 0) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Số trang không được âm.\n");
                            errorCount++;
                            continue;
                        }
                        if (soLuong < 0) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Số lượng phải lớn hơn hoặc bằng 0.\n");
                            errorCount++;
                            continue;
                        }
                        if (sachDAO.getSachById(maSach) != null) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Mã sách '").append(maSach).append("' đã tồn tại.\n");
                            errorCount++;
                            continue;
                        }

                        int maTacGia = -1;
                        for (TacGia tg : tacGiaList) {
                            if (tg.getTentacgia().equalsIgnoreCase(tenTacGia)) {
                                maTacGia = tg.getMatacgia();
                                break;
                            }
                        }
                        if (maTacGia == -1) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Tác giả '").append(tenTacGia).append("' không tồn tại.\n");
                            errorCount++;
                            continue;
                        }

                        int maNXB = -1;
                        for (NhaXuatBan nxb : nxbList) {
                            if (nxb.getTennxb().equalsIgnoreCase(tenNXB)) {
                                maNXB = nxb.getManxb();
                                break;
                            }
                        }
                        if (maNXB == -1) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Nhà xuất bản '").append(tenNXB).append("' không tồn tại.\n");
                            errorCount++;
                            continue;
                        }

                        int maTheLoai = -1;
                        for (TheLoai tl : theLoaiList) {
                            if (tl.getTentheloai().equalsIgnoreCase(tenTheLoai)) {
                                maTheLoai = tl.getMatheloai();
                                break;
                            }
                        }
                        if (maTheLoai == -1) {
                            errorMessages.append("Dòng ").append(i + 1).append(": Thể loại '").append(tenTheLoai).append("' không tồn tại.\n");
                            errorCount++;
                            continue;
                        }

                        sach.setMasach(maSach);
                        sach.setTensach(tenSach);
                        sach.setMatacgia(maTacGia);
                        sach.setManxb(maNXB);
                        sach.setMatheloai(maTheLoai);
                        sach.setNamxb(namXB);
                        sach.setSotrang(soTrang);
                        sach.setSoluong(soLuong);
                        sach.setAnh(null); // Không nhập ảnh từ Excel

                        if (sachDAO.addSach(sach)) {
                            successCount++;
                        } else {
                            errorMessages.append("Dòng ").append(i + 1).append(": Không thể thêm sách '").append(tenSach).append("'.\n");
                            errorCount++;
                        }
                    } catch (SQLException ex) {
                        errorMessages.append("Dòng ").append(i + 1).append(": Lỗi khi thêm sách: ").append(ex.getMessage()).append("\n");
                        errorCount++;
                    }
                }

                loadTableData();
                String message = "Đã nhập thành công " + successCount + " sách.\n";
                if (errorCount > 0) {
                    message += "Có " + errorCount + " lỗi:\n" + errorMessages.toString();
                }
                JOptionPane.showMessageDialog(view, message, "Kết quả nhập dữ liệu", errorCount > 0 ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi đọc file Excel: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateSach(Sach sach, boolean isAdd) {
        if (sach.getMasach() == null || sach.getMasach().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Mã sách không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!sach.getMasach().matches("^S\\d{3}$")) {
            JOptionPane.showMessageDialog(view, "Mã sách phải bắt đầu bằng 'S' và theo sau là 3 chữ số (VD: S001)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (sach.getTensach() == null || sach.getTensach().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tên sách không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (sach.getMatacgia() == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một tác giả hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (sach.getManxb() == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một nhà xuất bản hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (sach.getMatheloai() == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một thể loại hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (isAdd) {
            try {
                Sach existingSach = sachDAO.getSachById(sach.getMasach());
                if (existingSach != null) {
                    JOptionPane.showMessageDialog(view, "Mã sách đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Lỗi khi kiểm tra mã sách: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (sach.getNamxb() != 0) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (sach.getNamxb() > currentYear || sach.getNamxb() <= 0) {
                JOptionPane.showMessageDialog(view, "Năm xuất bản phải nhỏ hơn hoặc bằng năm hiện tại (" + currentYear + ") và lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        if (sach.getSotrang() < 0) {
            JOptionPane.showMessageDialog(view, "Số trang không được âm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (sach.getSoluong() <= 0) {
            JOptionPane.showMessageDialog(view, "Số lượng phải lớn hơn 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    public void clearFields() {
        view.getTxtMaSach().setText("");
        view.getTxtTenSach().setText("");
        view.getTxtNamXB().setText("");
        view.getTxtSoTrang().setText("");
        view.getTxtSoLuong().setText("");
        if (view.getCbTacGia().getItemCount() > 0) {
            view.getCbTacGia().setSelectedIndex(0);
        }
        if (view.getCbNXB().getItemCount() > 0) {
            view.getCbNXB().setSelectedIndex(0);
        }
        if (view.getCbTheLoai().getItemCount() > 0) {
            view.getCbTheLoai().setSelectedIndex(0);
        }
        view.getTxtMaSach().setEditable(true);
        view.setAnhData(null); // Xóa ảnh
    }

}
