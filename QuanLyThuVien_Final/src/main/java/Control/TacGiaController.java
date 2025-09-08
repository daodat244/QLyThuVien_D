package Control;

import Model.DAO.TacGiaDAO;
import Model.Entity.TacGia;
import View.ChucNang.PanelTacGia;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TacGiaController {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private final TacGiaDAO tacGiaDAO;
    private final PanelTacGia view;

    private static final Set<String> VIETNAM_PROVINCES = new HashSet<>(Arrays.asList(
            "hà nội", "thành phố hồ chí minh", "đà nẵng", "hải phòng", "cần thơ",
            "an giang", "bà rịa - vũng tàu", "bắc giang", "bắc kạn", "bạc liêu",
            "bắc ninh", "bến tre", "bình định", "bình dương", "bình phước", "bình thuận",
            "cà mau", "cao bằng", "đắk lắk", "đắk nông", "điện biên", "đồng nai", "đồng tháp",
            "gia lai", "hà giang", "hà nam", "hà tĩnh", "hải dương", "hậu giang", "hòa bình",
            "hưng yên", "khánh hòa", "kiên giang", "kon tum", "lai châu", "lâm đồng", "lạng sơn",
            "lào cai", "long an", "nam định", "nghệ an", "ninh bình", "ninh thuận", "phú thọ",
            "phú yên", "quảng bình", "quảng nam", "quảng ngãi", "quảng ninh", "quảng trị",
            "sóc trăng", "sơn la", "tây ninh", "thái bình", "thái nguyên", "thanh hóa",
            "thừa thiên huế", "tiền giang", "trà vinh", "tuyên quang", "vĩnh long", "vĩnh phúc", "yên bái", "việt nam"
    ));

    public TacGiaController(PanelTacGia view) {
        this.tacGiaDAO = new TacGiaDAO();
        this.view = view;
    }

    private String normalize(String input) {
        if (input == null) {
            return "";
        }
        // Loại bỏ dấu tiếng Việt và chuẩn hóa chuỗi
        String normalized = Normalizer.normalize(input.trim().toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "") // Loại bỏ dấu
                .replaceAll("[,\\.]", "") // Loại bỏ dấu câu
                .replaceAll("\\s+", " ") // Chuẩn hóa khoảng trắng
                .replaceAll("vn", "") // Loại bỏ từ khóa "việt nam" hoặc "vn"
                .trim();
        return normalized;
    }

    private String determineQuocGia(String queQuan) {
        if (queQuan == null || queQuan.trim().isEmpty()) {
            return null;
        }
        String queQuanNormalized = normalize(queQuan);
        // Tách chuỗi quê quán thành các từ
        String[] words = queQuanNormalized.split(" ");
        for (String word : words) {
            if (VIETNAM_PROVINCES.contains(word)) {
                return "Việt Nam";
            }
        }
        return "Nước Ngoài";
    }

    public void addTacGia(String tenTacGia, String namSinhStr, String queQuan, String moTa, String sdt, String email) throws SQLException {
        TacGia tacGia = new TacGia();
        tacGia.setTentacgia(tenTacGia);
        tacGia.setNamsinh(namSinhStr.isEmpty() ? 0 : Integer.parseInt(namSinhStr));
        tacGia.setQuequan(queQuan.isEmpty() ? null : queQuan);
        tacGia.setMota(moTa.isEmpty() ? null : moTa);
        tacGia.setSdt(sdt.isEmpty() ? null : sdt);
        tacGia.setEmail(email.isEmpty() ? null : email);
        tacGia.setQuocgia(determineQuocGia(queQuan));

        String error = validateTacGia(tacGia, 0); // 0 for add operation
        if (error != null) {
            view.showMessage(error, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tacGiaDAO.addTacGia(tacGia)) {
            view.showMessage("Thêm tác giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Thêm tác giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTacGia(int matacgia, String tenTacGia, String namSinhStr, String queQuan, String moTa, String sdt, String email) throws SQLException {
        TacGia tacGia = new TacGia();
        tacGia.setMatacgia(matacgia);
        tacGia.setTentacgia(tenTacGia);
        tacGia.setNamsinh(namSinhStr.isEmpty() ? 0 : Integer.parseInt(namSinhStr));
        tacGia.setQuequan(queQuan.isEmpty() ? null : queQuan);
        tacGia.setMota(moTa.isEmpty() ? null : moTa);
        tacGia.setSdt(sdt.isEmpty() ? null : sdt);
        tacGia.setEmail(email.isEmpty() ? null : email);
        tacGia.setQuocgia(determineQuocGia(queQuan));

        String error = validateTacGia(tacGia, matacgia); // Pass matacgia for update operation
        if (error != null) {
            view.showMessage(error, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tacGiaDAO.updateTacGia(tacGia)) {
            view.showMessage("Sửa tác giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Sửa tác giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteTacGia(int matacgia) throws SQLException {
        if (tacGiaDAO.isTacGiaInUse(matacgia)) {
            view.showMessage("Không thể xóa tác giả này vì đang được sử dụng trong sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tacGiaDAO.deleteTacGia(matacgia)) {
            view.showMessage("Xóa tác giả thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Xóa tác giả thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<TacGia> getAllTacGia() throws SQLException {
        return tacGiaDAO.getAllTacGia();
    }

    public List<TacGia> searchTacGia(String searchText, String searchCriteria) throws SQLException {
        List<TacGia> tacGiaList = tacGiaDAO.getAllTacGia();
        List<TacGia> filteredList = new ArrayList<>();

        for (TacGia tacGia : tacGiaList) {
            boolean match = false;
            String matacgia = String.valueOf(tacGia.getMatacgia());
            String tentacgia = tacGia.getTentacgia() != null ? tacGia.getTentacgia() : "";

            if (searchCriteria.equals("Mã tác giả") && !searchText.isEmpty()) {
                match = matacgia.contains(searchText);
            } else if (searchCriteria.equals("Tên tác giả") && !searchText.isEmpty()) {
                match = tentacgia.toLowerCase().contains(searchText.toLowerCase());
            } else if (searchText.isEmpty()) {
                match = true;
            }

            if (match) {
                filteredList.add(tacGia);
            }
        }
        return filteredList;
    }

    public void importFromExcel(File file) throws IOException, SQLException {
        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            String[] expectedHeaders = {"Mã tác giả", "Tên tác giả", "Năm sinh", "Quê quán", "Mô tả", "Điện thoại", "Email", "Quốc gia"};
            boolean headerValid = true;
            for (int i = 0; i < expectedHeaders.length; i++) {
                String cellValue = getCellValue(headerRow.getCell(i));
                if (!cellValue.equals(expectedHeaders[i])) {
                    headerValid = false;
                    break;
                }
            }
            if (!headerValid) {
                view.showMessage("File Excel không đúng định dạng. Vui lòng kiểm tra tiêu đề cột!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<String> errors = new ArrayList<>();
            Set<String> tenTacGiaSet = new HashSet<>();
            Set<String> sdtSet = new HashSet<>();
            Set<String> emailSet = new HashSet<>();
            int addedCount = 0;
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String tenTacGia = getCellValue(row.getCell(1));
                String namSinhStr = getCellValue(row.getCell(2));
                String queQuan = getCellValue(row.getCell(3));
                String moTa = getCellValue(row.getCell(4));
                String sdt = getCellValue(row.getCell(5));
                String email = getCellValue(row.getCell(6));

                if (tenTacGia != null && !tenTacGia.trim().isEmpty()) {
                    if (!tenTacGiaSet.add(tenTacGia.trim())) {
                        errors.add(String.format("Dòng %d: Tên tác giả '%s' trùng lặp trong file.", i + 1, tenTacGia));
                        continue;
                    }
                    if (sdt != null && !sdt.trim().isEmpty() && !sdtSet.add(sdt.trim())) {
                        errors.add(String.format("Dòng %d: Số điện thoại '%s' trùng lặp trong file.", i + 1, sdt));
                        continue;
                    }
                    if (email != null && !email.trim().isEmpty() && !emailSet.add(email.trim())) {
                        errors.add(String.format("Dòng %d: Email '%s' trùng lặp trong file.", i + 1, email));
                        continue;
                    }
                }

                TacGia tacGia = new TacGia();
                tacGia.setTentacgia(tenTacGia.isEmpty() ? null : tenTacGia.trim());
                tacGia.setNamsinh(namSinhStr.isEmpty() ? 0 : Integer.parseInt(namSinhStr));
                tacGia.setQuequan(queQuan.isEmpty() ? null : queQuan.trim());
                tacGia.setMota(moTa.isEmpty() ? null : moTa.trim());
                tacGia.setSdt(sdt.isEmpty() ? null : sdt.trim());
                tacGia.setEmail(email.isEmpty() ? null : email.trim());
                tacGia.setQuocgia(determineQuocGia(queQuan));

                String validationError = validateTacGiaForImport(tacGia, i + 1, currentYear);
                if (validationError != null) {
                    errors.add(validationError);
                    continue;
                }

                if (tacGiaDAO.addTacGia(tacGia)) {
                    addedCount++;
                } else {
                    errors.add(String.format("Dòng %d: Thêm tác giả '%s' thất bại.", i + 1, tenTacGia));
                }
            }

            if (addedCount > 0) {
                view.loadTableData();
            }

            StringBuilder message = new StringBuilder();
            message.append(String.format("Nhập dữ liệu hoàn tất: %d tác giả được thêm thành công.\n", addedCount));
            if (!errors.isEmpty()) {
                message.append("Có lỗi xảy ra:\n");
                for (String error : errors) {
                    message.append(error).append("\n");
                }
                view.showMessage(message.toString(), "Lỗi", JOptionPane.WARNING_MESSAGE);
            } else {
                view.showMessage(message.toString(), "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }

    private String validateTacGia(TacGia tacGia, int matacgia) throws SQLException {
        if (tacGia.getTentacgia() == null || tacGia.getTentacgia().trim().isEmpty()) {
            return "Tên tác giả không được để trống!";
        }
        if (!tacGia.getTentacgia().matches("^[a-zA-ZÀ-ỹ\\s]+$")) {
            return "Tên NXB chỉ được chứa chữ cái!";
        }
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (tacGia.getNamsinh() != 0 && (tacGia.getNamsinh() < 1000 || tacGia.getNamsinh() > currentYear)) {
            return "Năm sinh phải từ 1000 đến " + currentYear + "!";
        }
        
        if (tacGia.getQuequan()== null || tacGia.getQuequan().trim().isEmpty()) {
            return "Quê quán không được để trống!";
        }

        if (tacGia.getSdt() != null && !tacGia.getSdt().trim().isEmpty()) {
            if (!tacGia.getSdt().matches("0\\d{9}")) {
                return "Số điện thoại phải có 10 chữ số và bắt đầu bằng 0!";
            }
            if (tacGiaDAO.isSdtExists(tacGia.getSdt(), matacgia)) {
                return "Số điện thoại đã được sử dụng!";
            }
        }

        if (tacGia.getEmail() != null && !tacGia.getEmail().trim().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(tacGia.getEmail()).matches()) {
                return "Email không hợp lệ!";
            }
            if (tacGiaDAO.isEmailExists(tacGia.getEmail(), matacgia)) {
                return "Email đã được sử dụng!";
            }
        }

        return null;
    }

    private String validateTacGiaForImport(TacGia tacGia, int rowNum, int currentYear) throws SQLException {
        if (tacGia.getTentacgia() == null || tacGia.getTentacgia().trim().isEmpty()) {
            return String.format("Dòng %d: Tên tác giả không được để trống.", rowNum);
        }
        if (tacGia.getNamsinh() != 0 && (tacGia.getNamsinh() < 1000 || tacGia.getNamsinh() > currentYear)) {
            return String.format("Dòng %d: Năm sinh phải từ 1400 đến %d.", rowNum, currentYear);
        }
        if (tacGia.getQuequan() == null || tacGia.getQuequan().trim().isEmpty()) {
            return String.format("Dòng %d: Quê quán không được để trống.", rowNum);
        }
        if (tacGia.getSdt() != null && !tacGia.getSdt().trim().isEmpty()) {
            if (!tacGia.getSdt().matches("0\\d{9}")) {
                return String.format("Dòng %d: Số điện thoại phải có 10 chữ số và bắt đầu bằng 0.", rowNum);
            }
            if (tacGiaDAO.isSdtExists(tacGia.getSdt(), 0)) {
                return String.format("Dòng %d: Số điện thoại đã được sử dụng.", rowNum);
            }
        }
        if (tacGia.getEmail() != null && !tacGia.getEmail().trim().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(tacGia.getEmail()).matches()) {
                return String.format("Dòng %d: Email không hợp lệ.", rowNum);
            }
            if (tacGiaDAO.isEmailExists(tacGia.getEmail(), 0)) {
                return String.format("Dòng %d: Email đã được sử dụng.", rowNum);
            }
        }
        return null;
    }
}
