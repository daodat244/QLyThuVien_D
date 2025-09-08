package Control;

import Model.DAO.NhaXuatBanDAO;
import Model.Entity.NhaXuatBan;
import View.ChucNang.PanelNhaXuatBan;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Arrays;

public class NhaXuatBanController {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");                                            //^[a-zA-Z0-9._%+-]+@gmail\\.com$

    private final NhaXuatBanDAO nhaXuatBanDAO;
    private final PanelNhaXuatBan view;

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

    public NhaXuatBanController(PanelNhaXuatBan view) {
        this.nhaXuatBanDAO = new NhaXuatBanDAO();
        this.view = view;
    }

    private String normalize(String input) {
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

    public void addNXB(String ten, String sdt, String email, String diaChi) throws SQLException {
        NhaXuatBan nxb = new NhaXuatBan();
        nxb.setTennxb(ten);
        nxb.setSdt(sdt);
        nxb.setEmail(email);
        nxb.setDiachi(diaChi);
        nxb.setQuocgia(determineQuocGia(diaChi));

        String error = validateNXB(nxb, 0); // 0 for add operation
        if (error != null) {
            view.showMessage(error, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nhaXuatBanDAO.addNXB(nxb)) {
            view.showMessage("Thêm nhà xuất bản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Thêm nhà xuất bản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateNXB(int manxb, String ten, String sdt, String email, String diaChi) throws SQLException {
        NhaXuatBan nxb = new NhaXuatBan();
        nxb.setManxb(manxb);
        nxb.setTennxb(ten);
        nxb.setSdt(sdt);
        nxb.setEmail(email);
        nxb.setDiachi(diaChi);
        nxb.setQuocgia(determineQuocGia(diaChi));

        String error = validateNXB(nxb, manxb); // Pass manxb for update operation
        if (error != null) {
            view.showMessage(error, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nhaXuatBanDAO.updateNXB(nxb)) {
            view.showMessage("Sửa nhà xuất bản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Sửa nhà xuất bản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteNXB(int manxb) throws SQLException {
        if (nhaXuatBanDAO.isNXBInUse(manxb)) {
            view.showMessage("Không thể xóa nhà xuất bản này vì đang được sử dụng trong sách!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nhaXuatBanDAO.deleteNXB(manxb)) {
            view.showMessage("Xóa nhà xuất bản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            view.loadTableData();
            view.clearFields();
        } else {
            view.showMessage("Xóa nhà xuất bản thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<NhaXuatBan> getAllNhaXuatBan() throws SQLException {
        return nhaXuatBanDAO.getAllNhaXuatBan();
    }

    public List<NhaXuatBan> searchNXB(String searchText, String searchCriteria) throws SQLException {
        List<NhaXuatBan> nxbList = nhaXuatBanDAO.getAllNhaXuatBan();
        List<NhaXuatBan> filteredList = new ArrayList<>();

        for (NhaXuatBan nxb : nxbList) {
            boolean match = false;
            String manxbStr = String.valueOf(nxb.getManxb());
            String tennxb = nxb.getTennxb();

            if (searchCriteria.equals("Mã NXB") && !searchText.isEmpty()) {
                match = manxbStr.contains(searchText);
            } else if (searchCriteria.equals("Tên NXB") && !searchText.isEmpty()) {
                match = tennxb.toLowerCase().contains(searchText.toLowerCase());
            } else if (searchText.isEmpty()) {
                match = true;
            }

            if (match) {
                filteredList.add(nxb);
            }
        }
        return filteredList;
    }

    public void importFromExcel(File file) throws IOException, SQLException {
        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            String[] expectedHeaders = {"Mã NXB", "Tên NXB", "Điện thoại", "Email", "Địa chỉ", "Quốc gia"};
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
            Set<String> tenNXBSet = new HashSet<>();
            Set<String> sdtSet = new HashSet<>();
            Set<String> emailSet = new HashSet<>();
            int addedCount = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                String tennxb = getCellValue(row.getCell(1));
                String sdt = getCellValue(row.getCell(2));
                String email = getCellValue(row.getCell(3));
                String diachi = getCellValue(row.getCell(4));

                if (tennxb != null && !tennxb.trim().isEmpty()) {
                    if (!tenNXBSet.add(tennxb.trim())) {
                        errors.add(String.format("Dòng %d: Tên NXB '%s' trùng lặp trong file.", i + 1, tennxb));
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

                NhaXuatBan nxb = new NhaXuatBan();
                nxb.setTennxb(tennxb.isEmpty() ? null : tennxb.trim());
                nxb.setSdt(sdt.isEmpty() ? null : sdt.trim());
                nxb.setEmail(email.isEmpty() ? null : email.trim());
                nxb.setDiachi(diachi.isEmpty() ? null : diachi.trim());
                nxb.setQuocgia(determineQuocGia(diachi));

                String validationError = validateNXBForImport(nxb, i + 1);
                if (validationError != null) {
                    errors.add(validationError);
                    continue;
                }

                if (nhaXuatBanDAO.addNXB(nxb)) {
                    addedCount++;
                } else {
                    errors.add(String.format("Dòng %d: Thêm NXB '%s' thất bại.", i + 1, tennxb));
                }
            }

            if (addedCount > 0) {
                view.loadTableData();
            }

            StringBuilder message = new StringBuilder();
            message.append(String.format("Nhập dữ liệu hoàn tất: %d NXB được thêm thành công.\n", addedCount));
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

    private String validateNXB(NhaXuatBan nxb, int manxb) throws SQLException {
        if (nxb.getTennxb() == null || nxb.getTennxb().trim().isEmpty()) {
            return "Tên NXB không được để trống!";
        }
        if (!nxb.getTennxb().matches("^[a-zA-ZÀ-ỹ0-9\\s]+$")) {
            return "Tên NXB chỉ được chứa chữ cái, số!";
        }
        if (manxb == 0 && nhaXuatBanDAO.isTenNXBExists(nxb.getTennxb().trim())) {
            return "Tên NXB đã tồn tại!";
        }
        if (nxb.getSdt() != null && !nxb.getSdt().trim().isEmpty()) {
            if (!PHONE_PATTERN.matcher(nxb.getSdt()).matches()) {
                return "Số điện thoại phải có 10 số và bắt đầu bằng 0.";
            }
            if (nhaXuatBanDAO.isSdtExists(nxb.getSdt(), manxb)) {
                return "Số điện thoại đã được sử dụng.";
            }
        }
        if (nxb.getEmail() == null || nxb.getEmail().trim().isEmpty()) {
            return "Email không được để trống!";
        }
        if (!EMAIL_PATTERN.matcher(nxb.getEmail()).matches()) {
            return "Email không hợp lệ!";
        }
        if (nhaXuatBanDAO.isEmailExists(nxb.getEmail(), manxb)) {
            return "Email đã được sử dụng!";
        }
        if (nxb.getDiachi() == null || nxb.getDiachi().trim().isEmpty()) {
            return "Địa chỉ không được để trống!";
        }
        return null;
    }

    private String validateNXBForImport(NhaXuatBan nxb, int rowNum) throws SQLException {
        if (nxb.getTennxb() == null || nxb.getTennxb().trim().isEmpty()) {
            return String.format("Dòng %d: Tên NXB không được để trống.", rowNum);
        }
        if (nhaXuatBanDAO.isTenNXBExists(nxb.getTennxb().trim())) {
            return String.format("Dòng %d: Tên NXB '%s' đã tồn tại trong cơ sở dữ liệu.", rowNum, nxb.getTennxb());
        }
        if (!nxb.getTennxb().matches("^[a-zA-ZÀ-ỹ0-9\\s]+$")) {
            return String.format("Dòng %d: Tên NXB chỉ được chứa chữ cái, số và dấu cách.", rowNum);
        }
        if (nxb.getSdt() != null && !nxb.getSdt().trim().isEmpty()) {
            if (!PHONE_PATTERN.matcher(nxb.getSdt()).matches()) {
                return String.format("Dòng %d: Số điện thoại phải có 10 số và bắt đầu bằng 0.", rowNum);
            }
            if (nhaXuatBanDAO.isSdtExists(nxb.getSdt(), 0)) {
                return String.format("Dòng %d: Số điện thoại '%s' đã được sử dụng.", rowNum, nxb.getSdt());
            }
        }
        if (nxb.getEmail() == null || nxb.getEmail().trim().isEmpty()) {
            return String.format("Dòng %d: Email không được để trống.", rowNum);
        }
        if (!EMAIL_PATTERN.matcher(nxb.getEmail()).matches()) {
            return String.format("Dòng %d: Email không hợp lệ.", rowNum);
        }
        if (nhaXuatBanDAO.isEmailExists(nxb.getEmail(), 0)) {
            return String.format("Dòng %d: Email '%s' đã được sử dụng.", rowNum, nxb.getEmail());
        }
        if (nxb.getDiachi()== null || nxb.getDiachi().trim().isEmpty()) {
            return String.format("Dòng %d: Địa chỉ NXB không được để trống.", rowNum);
        }
        return null;
    }
}
