/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Model.DAO.NhanVienDAO;
import Model.Entity.NhanVien;
import View.ChucNang.PanelNhanVien;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVienController {

    private final NhanVienDAO nhanVienDAO;
    private final PanelNhanVien view;

    public NhanVienController(PanelNhanVien view) {
        this.nhanVienDAO = new NhanVienDAO();
        this.view = view;
    }

    public void addNhanVien(String tenNhanVien, String sdt, LocalDate ngaySinh, String queQuan, String gioiTinh) throws SQLException {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setTennv(tenNhanVien);
        nhanVien.setSdt(sdt);
        nhanVien.setNgaysinh(ngaySinh != null ? Date.from(ngaySinh.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        nhanVien.setQuequan(queQuan);
        nhanVien.setGioitinh(gioiTinh);

        String validationError = validateNhanVien(nhanVien, 0);
        if (validationError != null) {
            view.showMessage(validationError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nhanVienDAO.addNV(nhanVien)) {
            view.showMessage("Thêm nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Thêm nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateNhanVien(int maNhanVien, String tenNhanVien, String sdt, LocalDate ngaySinh, String queQuan, String gioiTinh) throws SQLException {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setManv(maNhanVien);
        nhanVien.setTennv(tenNhanVien);
        nhanVien.setSdt(sdt);
        nhanVien.setNgaysinh(ngaySinh != null ? Date.from(ngaySinh.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
        nhanVien.setQuequan(queQuan);
        nhanVien.setGioitinh(gioiTinh);

        String validationError = validateNhanVien(nhanVien, maNhanVien);
        if (validationError != null) {
            view.showMessage(validationError, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nhanVienDAO.updateNV(nhanVien)) {
            view.showMessage("Sửa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Sửa nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteNhanVien(int maNhanVien) throws SQLException {
        if (nhanVienDAO.isNhanVienInUse(maNhanVien)) {
            view.showMessage("Không thể xóa nhân viên này vì đang có phiếu mượn liên quan!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nhanVienDAO.deleteNV(maNhanVien)) {
            view.showMessage("Xóa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Xóa nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<NhanVien> getAllNhanVien() throws SQLException {
        return nhanVienDAO.getAllNhanVien();
    }

    public List<NhanVien> searchNhanVien(String searchText, String searchCriteria) throws SQLException {
        List<NhanVien> nhanVienList = nhanVienDAO.getAllNhanVien();
        List<NhanVien> filteredList = new ArrayList<>();

        for (NhanVien nv : nhanVienList) {
            boolean match = false;
            String manhanvien = String.valueOf(nv.getManv());
            String tennhanvien = nv.getTennv()!= null ? nv.getTennv(): "";

            if (searchCriteria.equals("Mã Nhân Viên") && !searchText.isEmpty()) {
                match = manhanvien.contains(searchText);
            } else if (searchCriteria.equals("Tên Nhân Viên") && !searchText.isEmpty()) {
                match = tennhanvien.toLowerCase().contains(searchText.toLowerCase());
            } else if (searchText.isEmpty()) {
                match = true;
            }

            if (match) {
                filteredList.add(nv);
            }
        }
        return filteredList;
    }

    public void importFromExcel(File file) throws IOException, SQLException {
        try (FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<String> errors = new ArrayList<>();
            int addedCount = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String tenNhanVien = getCellValue(row.getCell(1));
                String sdt = getCellValue(row.getCell(2));
                String ngaySinhStr = getCellValue(row.getCell(3));
                String queQuan = getCellValue(row.getCell(4));
                String gioiTinh = getCellValue(row.getCell(5));

                LocalDate ngaySinh = null;
                if (!ngaySinhStr.isEmpty()) {
                    try {
                        ngaySinh = LocalDate.parse(ngaySinhStr, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } catch (Exception e) {
                        errors.add(String.format("Dòng %d: Ngày sinh '%s' không đúng định dạng yyyy-MM-dd.", i + 1, ngaySinhStr));
                        continue;
                    }
                }

                NhanVien nhanVien = new NhanVien();
                nhanVien.setTennv(tenNhanVien.isEmpty() ? null : tenNhanVien);
                nhanVien.setSdt(sdt.isEmpty() ? null : sdt);
                nhanVien.setNgaysinh(ngaySinh != null ? Date.from(ngaySinh.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null);
                nhanVien.setQuequan(queQuan.isEmpty() ? null : queQuan);
                nhanVien.setGioitinh(gioiTinh.isEmpty() ? null : gioiTinh);

                String validationError = validateNhanVienForImport(nhanVien, i + 1);
                if (validationError != null) {
                    errors.add(validationError);
                    continue;
                }

                if (nhanVienDAO.addNV(nhanVien)) {
                    addedCount++;
                } else {
                    errors.add(String.format("Dòng %d: Thêm nhân viên '%s' thất bại.", i + 1, tenNhanVien));
                }
            }

            if (addedCount > 0) {
                view.loadTableData();
            }

            StringBuilder message = new StringBuilder();
            message.append(String.format("Nhập dữ liệu hoàn tất: %d nhân viên được thêm thành công.\n", addedCount));
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
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DanhSachNhanVien");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã nhân viên", "Tên nhân viên", "Số điện thoại", "Ngày sinh", "Quê quán", "Giới tính"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            List<NhanVien> nhanVienList = nhanVienDAO.getAllNhanVien();
            for (int i = 0; i < nhanVienList.size(); i++) {
                NhanVien nv = nhanVienList.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(nv.getManv());
                row.createCell(1).setCellValue(nv.getTennv()!= null ? nv.getTennv(): "");
                row.createCell(2).setCellValue(nv.getSdt() != null ? nv.getSdt() : "");
                row.createCell(3).setCellValue(nv.getNgaysinh() != null ? nv.getNgaysinh().toString() : "");
                row.createCell(4).setCellValue(nv.getQuequan() != null ? nv.getQuequan() : "");
                row.createCell(5).setCellValue(nv.getGioitinh() != null ? nv.getGioitinh() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
            view.showMessage("Xuất dữ liệu nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private String validateNhanVien(NhanVien nhanVien, int maNhanVien) throws SQLException {
        if (nhanVien.getTennv() == null || nhanVien.getTennv().trim().isEmpty() || nhanVien.getTennv().length() > 100) {
            return "Tên nhân viên không hợp lệ! Phải từ 1 đến 100 ký tự.";
        }
        if (!nhanVien.getTennv().matches("^[a-zA-Z0-9À-ỹ\\s]+$")) {
            return "Tên nhân viên chỉ được chứa chữ cái, số và khoảng trắng!";
        }
        if (nhanVien.getSdt() == null || !nhanVien.getSdt().matches("0[0-9]{9}")) {
            return "Số điện thoại không hợp lệ! Phải bắt đầu bằng 0 và có đúng 10 chữ số.";
        }
        if (nhanVienDAO.isDuplicatePhone(nhanVien.getSdt(), maNhanVien)) {
            return "Số điện thoại đã tồn tại!";
        }
        if (nhanVien.getNgaysinh() == null || !isValidBirthDate(nhanVien.getNgaysinh())) {
            return "Ngày sinh không hợp lệ! Nhân viên phải đủ 18 tuổi (sinh trước hoặc trong năm " + (LocalDate.now().getYear() - 18) + ").";
        }
        if (nhanVien.getQuequan() == null || nhanVien.getQuequan().trim().isEmpty() || nhanVien.getQuequan().length() > 200) {
            return "Quê quán không hợp lệ! Phải từ 1 đến 200 ký tự.";
        }
        if (!nhanVien.getQuequan().matches("^[a-zA-Z0-9À-ỹ\\s,/-]+$")) {
            return "Quê quán chỉ được chứa chữ cái, số, khoảng trắng, dấu phẩy, gạch chéo, gạch ngang!";
        }
        if (nhanVien.getGioitinh() == null || (!nhanVien.getGioitinh().equals("Nam") && !nhanVien.getGioitinh().equals("Nữ"))) {
            return "Giới tính không hợp lệ! Chỉ cho phép 'Nam' hoặc 'Nữ'.";
        }
        return null;
    }

    private String validateNhanVienForImport(NhanVien nhanVien, int rowNum) {
        if (nhanVien.getTennv() == null || nhanVien.getTennv().trim().isEmpty() || nhanVien.getTennv().length() > 100) {
            return String.format("Dòng %d: Tên nhân viên không hợp lệ! Phải từ 1 đến 100 ký tự.", rowNum);
        }
        if (!nhanVien.getTennv().matches("^[a-zA-Z0-9À-ỹ\\s]+$")) {
            return String.format("Dòng %d: Tên nhân viên chỉ được chứa chữ cái, số và khoảng trắng!", rowNum);
        }
        if (nhanVien.getSdt() == null || !nhanVien.getSdt().matches("0[0-9]{9}")) {
            return String.format("Dòng %d: Số điện thoại không hợp lệ! Phải bắt đầu bằng 0 và có đúng 10 chữ số.", rowNum);
        }
        if (nhanVien.getNgaysinh() == null || !isValidBirthDate(nhanVien.getNgaysinh())) {
            return String.format("Dòng %d: Ngày sinh không hợp lệ! Nhân viên phải đủ 18 tuổi (sinh trước hoặc trong năm %d).", rowNum, LocalDate.now().getYear() - 18);
        }
        if (nhanVien.getQuequan() == null || nhanVien.getQuequan().trim().isEmpty() || nhanVien.getQuequan().length() > 200) {
            return String.format("Dòng %d: Quê quán không hợp lệ! Phải từ 1 đến 200 ký tự.", rowNum);
        }
        if (!nhanVien.getQuequan().matches("^[a-zA-Z0-9À-ỹ\\s,/-]+$")) {
            return String.format("Dòng %d: Quê quán chỉ được chứa chữ cái, số, khoảng trắng, dấu phẩy, gạch chéo, gạch ngang!", rowNum);
        }
        if (nhanVien.getGioitinh() == null || (!nhanVien.getGioitinh().equals("Nam") && !nhanVien.getGioitinh().equals("Nữ"))) {
            return String.format("Dòng %d: Giới tính không hợp lệ! Chỉ cho phép 'Nam' hoặc 'Nữ'.", rowNum);
        }
        return null;
    }

    private boolean isValidBirthDate(Date date) {
        if (date == null) {
            return false;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int currentYear = LocalDate.now().getYear();
        return (currentYear - localDate.getYear()) >= 18;
    }
}
