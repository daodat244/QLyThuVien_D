package Control;

import Model.DAO.DocGiaDAO;
import Model.Entity.DocGia;
import View.ChucNang.PanelDocGia;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DocGiaController {
    private final DocGiaDAO docGiaDAO;
    private final PanelDocGia view;

    public DocGiaController(PanelDocGia view) {
        this.docGiaDAO = new DocGiaDAO();
        this.view = view;
    }

    public void addDocGia(String tenDocGia, String sdt, String email, String diaChi, String gioiTinh) throws SQLException {
        DocGia docGia = new DocGia();
        docGia.setTendocgia(tenDocGia);
        docGia.setSdt(sdt);
        docGia.setEmail(email);
        docGia.setDiachi(diaChi);
        docGia.setGioitinh(gioiTinh);

        String validationError = validateDocGia(docGia, 0);
        if (validationError != null) {
            view.showMessage(validationError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (docGiaDAO.addDG(docGia)) {
            view.showMessage("Thêm độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Thêm độc giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateDocGia(int maDocGia, String tenDocGia, String sdt, String email, String diaChi, String gioiTinh) throws SQLException {
        DocGia docGia = new DocGia();
        docGia.setMadocgia(maDocGia);
        docGia.setTendocgia(tenDocGia);
        docGia.setSdt(sdt);
        docGia.setEmail(email);
        docGia.setDiachi(diaChi);
        docGia.setGioitinh(gioiTinh);

        String validationError = validateDocGia(docGia, maDocGia);
        if (validationError != null) {
            view.showMessage(validationError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (docGiaDAO.updateDG(docGia)) {
            view.showMessage("Sửa độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Sửa độc giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteDocGia(int maDocGia) throws SQLException {
        if (docGiaDAO.isDocGiaInUse(maDocGia)) {
            view.showMessage("Không thể xóa độc giả này vì đang có phiếu mượn liên quan!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (docGiaDAO.deleteDG(maDocGia)) {
            view.showMessage("Xóa độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Xóa độc giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<DocGia> getAllDocGia() throws SQLException {
        return docGiaDAO.getAllDocGia();
    }

    public List<DocGia> searchDocGia(String searchText, String searchCriteria) throws SQLException {
        List<DocGia> docGiaList = docGiaDAO.getAllDocGia();
        List<DocGia> filteredList = new ArrayList<>();

        for (DocGia dg : docGiaList) {
            boolean match = false;
            String madocgia = String.valueOf(dg.getMadocgia());
            String tendocgia = dg.getTendocgia() != null ? dg.getTendocgia() : "";

            if (searchCriteria.equals("Mã Độc Giả") && !searchText.isEmpty()) {
                match = madocgia.contains(searchText);
            } else if (searchCriteria.equals("Tên Độc Giả") && !searchText.isEmpty()) {
                match = tendocgia.toLowerCase().contains(searchText.toLowerCase());
            } else if (searchText.isEmpty()) {
                match = true;
            }

            if (match) {
                filteredList.add(dg);
            }
        }
        return filteredList;
    }

    public void importFromExcel(File file) throws IOException, SQLException {
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<String> errors = new ArrayList<>();
            int addedCount = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String tenDocGia = getCellValue(row.getCell(1));
                String sdt = getCellValue(row.getCell(2));
                String email = getCellValue(row.getCell(3));
                String diaChi = getCellValue(row.getCell(4));
                String gioiTinh = getCellValue(row.getCell(5));

                DocGia docGia = new DocGia();
                docGia.setTendocgia(tenDocGia.isEmpty() ? null : tenDocGia);
                docGia.setSdt(sdt.isEmpty() ? null : sdt);
                docGia.setEmail(email.isEmpty() ? null : email);
                docGia.setDiachi(diaChi.isEmpty() ? null : diaChi);
                docGia.setGioitinh(gioiTinh.isEmpty() ? null : gioiTinh);

                String validationError = validateDocGiaForImport(docGia, i + 1);
                if (validationError != null) {
                    errors.add(validationError);
                    continue;
                }

                if (docGiaDAO.addDG(docGia)) {
                    addedCount++;
                } else {
                    errors.add(String.format("Dòng %d: Thêm độc giả '%s' thất bại.", i + 1, tenDocGia));
                }
            }

            if (addedCount > 0) {
                view.loadTableData();
            }

            StringBuilder message = new StringBuilder();
            message.append(String.format("Nhập dữ liệu hoàn tất: %d độc giả được thêm thành công.\n", addedCount));
            if (!errors.isEmpty()) {
                message.append("Có lỗi xảy ra:\n");
                for (String error : errors) {
                    message.append(error).append("\n");
                }
                view.showMessage(message.toString(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                view.showMessage(message.toString(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void exportToExcel(File file) throws IOException, SQLException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DanhSachDocGia");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã độc giả", "Tên độc giả", "Số điện thoại", "Email", "Địa chỉ"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            List<DocGia> docGiaList = docGiaDAO.getAllDocGia();
            for (int i = 0; i < docGiaList.size(); i++) {
                DocGia dg = docGiaList.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(dg.getMadocgia());
                row.createCell(1).setCellValue(dg.getTendocgia() != null ? dg.getTendocgia() : "");
                row.createCell(2).setCellValue(dg.getSdt() != null ? dg.getSdt() : "");
                row.createCell(3).setCellValue(dg.getEmail() != null ? dg.getEmail() : "");
                row.createCell(4).setCellValue(dg.getDiachi() != null ? dg.getDiachi() : "");
                row.createCell(5).setCellValue(dg.getGioitinh() != null ? dg.getGioitinh() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
            view.showMessage("Xuất dữ liệu độc giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private String validateDocGia(DocGia docGia, int maDocGia) throws SQLException {
        if (docGia.getTendocgia() == null || docGia.getTendocgia().trim().isEmpty() || docGia.getTendocgia().length() > 50) {
            return "Tên độc giả không hợp lệ! Phải từ 1 đến 50 ký tự.";
        }
        if (!docGia.getTendocgia().matches("^[a-zA-Z0-9À-ỹ\\s]+$")) {
            return "Tên độc giả chỉ được chứa chữ cái, số và khoảng trắng!";
        }
        if (docGia.getSdt() == null || !docGia.getSdt().matches("0[0-9]{9}")) {
            return "Số điện thoại không hợp lệ! Phải bắt đầu bằng 0 và có đúng 10 chữ số.";
        }
        if (docGia.getEmail() == null || !docGia.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return "Email không hợp lệ! Vui lòng nhập đúng định dạng (ví dụ: ten@domain.com).";
        }
        if (docGia.getDiachi() == null || docGia.getDiachi().trim().isEmpty() || docGia.getDiachi().length() > 50) {
            return "Địa chỉ không hợp lệ! Phải từ 1 đến 50 ký tự.";
        }
        if (!docGia.getDiachi().matches("^[a-zA-Z0-9À-ỹ\\s,/-]+$")) {
            return "Địa chỉ chỉ được chứa chữ cái, số, khoảng trắng, dấu phẩy, gạch chéo, gạch ngang!";
        }
        if (docGia.getGioitinh() == null || (!docGia.getGioitinh().equals("Nam") && !docGia.getGioitinh().equals("Nữ"))) {
            return "Giới tính không hợp lệ! Chỉ cho phép 'Nam' hoặc 'Nữ'.";
        }
        if (docGiaDAO.isDuplicatePhoneOrEmail(docGia.getSdt(), docGia.getEmail(), maDocGia)) {
            return "Số điện thoại hoặc email đã tồn tại!";
        }
        return null;
    }

    private String validateDocGiaForImport(DocGia docGia, int rowNum) {
        if (docGia.getTendocgia() == null || docGia.getTendocgia().trim().isEmpty() || docGia.getTendocgia().length() > 50) {
            return String.format("Dòng %d: Tên độc giả không hợp lệ! Phải từ 1 đến 50 ký tự.", rowNum);
        }
        if (!docGia.getTendocgia().matches("^[a-zA-Z0-9À-ỹ\\s]+$")) {
            return String.format("Dòng %d: Tên độc giả chỉ được chứa chữ cái, số và khoảng trắng!", rowNum);
        }
        if (docGia.getSdt() == null || !docGia.getSdt().matches("0[0-9]{9}")) {
            return String.format("Dòng %d: Số điện thoại không hợp lệ! Phải bắt đầu bằng 0 và có đúng 10 chữ số.", rowNum);
        }
        if (docGia.getEmail() == null || !docGia.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return String.format("Dòng %d: Email không hợp lệ! Vui lòng nhập đúng định dạng (ví dụ: ten@domain.com).", rowNum);
        }
        if (docGia.getDiachi() == null || docGia.getDiachi().trim().isEmpty() || docGia.getDiachi().length() > 50) {
            return String.format("Dòng %d: Địa chỉ không hợp lệ! Phải từ 1 đến 50 ký tự.", rowNum);
        }
        if (!docGia.getDiachi().matches("^[a-zA-Z0-9À-ỹ\\s,/-]+$")) {
            return String.format("Dòng %d: Địa chỉ chỉ được chứa chữ cái, số, khoảng trắng, dấu phẩy, gạch chéo, gạch ngang!", rowNum);
        }
        if (docGia.getGioitinh() == null || (!docGia.getGioitinh().equals("Nam") && !docGia.getGioitinh().equals("Nữ"))) {
            return String.format("Dòng %d: Giới tính không hợp lệ! Chỉ cho phép 'Nam' hoặc 'Nữ'.", rowNum);
        }
        return null;
    }
}
