package Control;

import Model.DAO.TheLoaiDAO;
import Model.Entity.TheLoai;
import View.ChucNang.PanelTheLoai;
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

public class TheLoaiController {
    private final TheLoaiDAO theLoaiDAO;
    private final PanelTheLoai view;

    public TheLoaiController(PanelTheLoai view) {
        this.theLoaiDAO = new TheLoaiDAO();
        this.view = view;
    }

    public void addTheLoai(String tenTheLoai, String moTa) throws SQLException {
        TheLoai theLoai = new TheLoai();
        theLoai.setTentheloai(tenTheLoai);
        theLoai.setMota(moTa.isEmpty() ? null : moTa);

        if (validateTheLoai(theLoai) && theLoaiDAO.addTheLoai(theLoai)) {
            view.showMessage("Thêm thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Thêm thể loại thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTheLoai(int matheloai, String tenTheLoai, String moTa) throws SQLException {
        TheLoai theLoai = new TheLoai();
        theLoai.setMatheloai(matheloai);
        theLoai.setTentheloai(tenTheLoai);
        theLoai.setMota(moTa.isEmpty() ? null : moTa);

        if (validateTheLoai(theLoai) && theLoaiDAO.updateTheLoai(theLoai)) {
            view.showMessage("Sửa thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Sửa thể loại thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteTheLoai(int matheloai) throws SQLException {
        if (theLoaiDAO.isTheLoaiInUse(matheloai)) {
            view.showMessage("Không thể xóa thể loại này vì đang được sử dụng trong sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (theLoaiDAO.deleteTheLoai(matheloai)) {
            view.showMessage("Xóa thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Xóa thể loại thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<TheLoai> getAllTheLoai() throws SQLException {
        return theLoaiDAO.getAllTheLoai();
    }

    public List<TheLoai> searchTheLoai(String searchText, String searchCriteria) throws SQLException {
        List<TheLoai> theLoaiList = theLoaiDAO.getAllTheLoai();
        List<TheLoai> filteredList = new ArrayList<>();

        for (TheLoai theLoai : theLoaiList) {
            boolean match = false;
            String matheloai = String.valueOf(theLoai.getMatheloai());
            String tentheloai = theLoai.getTentheloai() != null ? theLoai.getTentheloai() : "";

            if (searchCriteria.equals("Mã thể loại") && !searchText.isEmpty()) {
                match = matheloai.contains(searchText);
            } else if (searchCriteria.equals("Tên thể loại") && !searchText.isEmpty()) {
                match = tentheloai.toLowerCase().contains(searchText.toLowerCase());
            } else if (searchText.isEmpty()) {
                match = true;
            }

            if (match) {
                filteredList.add(theLoai);
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

                String tenTheLoai = getCellValue(row.getCell(1));
                String moTa = getCellValue(row.getCell(2));

                TheLoai theLoai = new TheLoai();
                theLoai.setTentheloai(tenTheLoai.isEmpty() ? null : tenTheLoai);
                theLoai.setMota(moTa.isEmpty() ? null : moTa);

                String validationError = validateTheLoaiForImport(theLoai, i + 1);
                if (validationError != null) {
                    errors.add(validationError);
                    continue;
                }

                if (theLoaiDAO.addTheLoai(theLoai)) {
                    addedCount++;
                } else {
                    errors.add(String.format("Dòng %d: Thêm thể loại '%s' thất bại.", i + 1, tenTheLoai));
                }
            }

            if (addedCount > 0) {
                view.loadTableData();
            }

            StringBuilder message = new StringBuilder();
            message.append(String.format("Nhập dữ liệu hoàn tất: %d thể loại được thêm thành công.\n", addedCount));
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
            Sheet sheet = workbook.createSheet("DanhSachTheLoai");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã thể loại", "Tên thể loại", "Mô tả"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            List<TheLoai> theLoaiList = theLoaiDAO.getAllTheLoai();
            for (int i = 0; i < theLoaiList.size(); i++) {
                TheLoai theLoai = theLoaiList.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(theLoai.getMatheloai());
                row.createCell(1).setCellValue(theLoai.getTentheloai() != null ? theLoai.getTentheloai() : "");
                row.createCell(2).setCellValue(theLoai.getMota() != null ? theLoai.getMota() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
            view.showMessage("Xuất dữ liệu thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private boolean validateTheLoai(TheLoai theLoai) {
        if (theLoai.getTentheloai() == null || theLoai.getTentheloai().trim().isEmpty()) {
            view.showMessage("Tên thể loại không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private String validateTheLoaiForImport(TheLoai theLoai, int rowNum) {
        if (theLoai.getTentheloai() == null || theLoai.getTentheloai().trim().isEmpty()) {
            return String.format("Dòng %d: Tên thể loại không được để trống.", rowNum);
        }
        return null;
    }
}
