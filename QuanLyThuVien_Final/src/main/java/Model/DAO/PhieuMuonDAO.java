package Model.DAO;

import Model.Entity.ChiTietPhieuMuon;
import Model.Entity.DocGia;
import Model.Entity.PhieuMuon;
import Model.DAO.DocGiaDAO;
import Model.DAO.NhanVienDAO;
import Utils.ConnectToSQLServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {

    public PhieuMuon getPhieuMuonById(int maphieu) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        PhieuMuon phieuMuon = null;

        try {
            conn = ConnectToSQLServer.getConnection();
            String query = "SELECT maphieu, madocgia, manv, ngaymuon, ngaytradukien, trangthai "
                    + "FROM phieumuon WHERE maphieu = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, maphieu);
            rs = stmt.executeQuery();

            if (rs.next()) {
                phieuMuon = new PhieuMuon(
                        rs.getInt("maphieu"),
                        rs.getInt("madocgia"),
                        rs.getInt("manv"),
                        rs.getObject("ngaymuon", LocalDateTime.class),
                        rs.getObject("ngaytradukien", LocalDateTime.class),
                        rs.getString("trangthai")
                );
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return phieuMuon;
    }

    public boolean addChiTietPhieuMuon(ChiTietPhieuMuon ctp) throws SQLException {
        String query = "INSERT INTO ChiTietPhieuMuon (maphieu, masach) VALUES (?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ctp.getMaphieu());
            stmt.setString(2, ctp.getMasach());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteChiTietPhieuMuon(int maphieu) throws SQLException {
        String query = "DELETE FROM ChiTietPhieuMuon WHERE maphieu = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maphieu);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public List<String> getSachListByPhieuMuon(int maphieu) throws SQLException {
        List<String> sachList = new ArrayList<>();
        String query = "SELECT masach FROM ChiTietPhieuMuon WHERE maphieu = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maphieu);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sachList.add(rs.getString("masach"));
                }
            }
        }
        return sachList;
    }

    public String getMasachListByPhieuMuon(int maphieu) throws SQLException {
        List<String> sachList = getSachListByPhieuMuon(maphieu);
        return sachList.isEmpty() ? "Không có sách" : String.join(",", sachList);
    }

    public List<PhieuMuon> getAllPhieuMuonWithDetails() throws SQLException {
        List<PhieuMuon> phieuMuonList = new ArrayList<>();
        String query = "SELECT pm.maphieu, pm.madocgia, pm.manv, pm.ngaymuon, pm.ngaytradukien, pm.trangthai "
                + "FROM phieumuon pm";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhieuMuon pm = new PhieuMuon(
                        rs.getInt("maphieu"),
                        rs.getInt("madocgia"),
                        rs.getInt("manv"),
                        rs.getObject("ngaymuon", LocalDateTime.class),
                        rs.getObject("ngaytradukien", LocalDateTime.class),
                        rs.getString("trangthai")
                );
                phieuMuonList.add(pm);
            }
        }
        return phieuMuonList;
    }

    public List<PhieuMuon> searchPhieuMuon(String searchText, String searchCriteria) throws SQLException {
        List<PhieuMuon> phieuMuonList = new ArrayList<>();
        String query = "SELECT pm.maphieu, pm.madocgia, pm.manv, pm.ngaymuon, pm.ngaytradukien, pm.trangthai, "
                + "d.tendocgia, nv.tennv "
                + "FROM phieumuon pm "
                + "LEFT JOIN docgia d ON pm.madocgia = d.madocgia "
                + "LEFT JOIN nhanvien nv ON pm.manv = nv.manv "
                + "WHERE ";
        if (searchCriteria.equals("Mã phiếu")) {
            query += "pm.maphieu LIKE ?";
        } else if (searchCriteria.equals("Tên sinh viên")) {
            query += "d.tendocgia LIKE ?";
        } else if (searchCriteria.equals("Tên nhân viên")) {
            query += "nv.tennv LIKE ?";
        } else if (searchCriteria.equals("Tên sách")) {
            query += "EXISTS (SELECT 1 FROM ChiTietPhieuMuon ctp JOIN sach s ON ctp.masach = s.masach WHERE ctp.maphieu = pm.maphieu AND s.tensach LIKE ?)";
        }

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + searchText + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PhieuMuon pm = new PhieuMuon(
                            rs.getInt("maphieu"),
                            rs.getInt("madocgia"),
                            rs.getInt("manv"),
                            rs.getObject("ngaymuon", LocalDateTime.class),
                            rs.getObject("ngaytradukien", LocalDateTime.class),
                            rs.getString("trangthai")
                    );
                    phieuMuonList.add(pm);
                }
            }
        }
        return phieuMuonList;
    }

    public boolean addPhieuMuon(PhieuMuon pm) throws SQLException {
        String query = "INSERT INTO phieumuon (madocgia, manv, ngaymuon, ngaytradukien, trangthai) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pm.getMadocgia());
            stmt.setInt(2, pm.getManv());
            stmt.setObject(3, pm.getNgaymuon());
            stmt.setObject(4, pm.getNgayTraDuKien());
            stmt.setString(5, pm.getTrangthai() != null ? pm.getTrangthai() : "Chưa trả");
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean updatePhieuMuon(PhieuMuon pm) throws SQLException {
        String query = "UPDATE phieumuon SET madocgia = ?, manv = ?, ngaymuon = ?, ngaytradukien = ?, trangthai = ? WHERE maphieu = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pm.getMadocgia());
            stmt.setInt(2, pm.getManv());
            stmt.setObject(3, pm.getNgaymuon());
            stmt.setObject(4, pm.getNgayTraDuKien());
            stmt.setString(5, pm.getTrangthai() != null ? pm.getTrangthai() : "Chưa trả");
            stmt.setInt(6, pm.getMaphieu());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deletePhieuMuon(int maphieu) throws SQLException {
        String query = "DELETE FROM phieumuon WHERE maphieu = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maphieu);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateTrangThai(int maphieu, String trangthai, Connection conn) throws SQLException {
        String query = "UPDATE phieumuon SET trangthai = ? WHERE maphieu = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, trangthai);
            stmt.setInt(2, maphieu);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public int getLatestPhieuMuonId() throws SQLException {
        String query = "SELECT MAX(maphieu) FROM phieumuon";
        try (Connection conn = ConnectToSQLServer.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public boolean addPhieuMuonFromExcel(String tenDocGia, String tenNhanVien, String maSachStr,
            LocalDateTime ngayMuon, LocalDateTime ngayTraDuKien, String trangThai) {
        String insertPhieu = "INSERT INTO phieumuon (madocgia, manv, ngaymuon, ngaytradukien, trangthai) VALUES (?, ?, ?, ?, ?)";
        String insertChiTiet = "INSERT INTO chitietphieumuon (maphieu, masach) VALUES (?, ?)";
        String updateSoLuong = "UPDATE sach SET soluong = soluong - 1 WHERE masach = ? AND soluong > 0";

        try (Connection conn = ConnectToSQLServer.getConnection()) {
            conn.setAutoCommit(false);
            tenDocGia = tenDocGia.trim();
            tenNhanVien = tenNhanVien.trim();
            DocGiaDAO docGiaDAO = new DocGiaDAO();
            int madocgia = docGiaDAO.getMaDocGiaByTen(tenDocGia);
            NhanVienDAO nhanVienDAO = new NhanVienDAO();
            int manv = nhanVienDAO.getMaNhanVienByTen(tenNhanVien);
            if (madocgia == -1 || manv == -1) {
                System.err.println("Không tìm thấy mã độc giả hoặc nhân viên từ tên.");
                conn.rollback();
                return false;
            }

            // 1. Thêm phiếu mượn
            PreparedStatement psPhieu = conn.prepareStatement(insertPhieu, PreparedStatement.RETURN_GENERATED_KEYS);
            psPhieu.setInt(1, madocgia);
            psPhieu.setInt(2, manv);
            psPhieu.setTimestamp(3, Timestamp.valueOf(ngayMuon));
            psPhieu.setTimestamp(4, Timestamp.valueOf(ngayTraDuKien));
            psPhieu.setString(5, trangThai);
            int affected = psPhieu.executeUpdate();
            if (affected == 0) {
                conn.rollback();
                return false;
            }

            ResultSet rs = psPhieu.getGeneratedKeys();
            if (!rs.next()) {
                conn.rollback();
                return false;
            }
            int maphieu = rs.getInt(1);
            rs.close();
            psPhieu.close();

            // 2. Tách danh sách mã sách và thêm từng cuốn
            String[] maSachs = maSachStr.split(",");
            for (String masach : maSachs) {
                masach = masach.trim();
                // 2.1. Giảm số lượng
                PreparedStatement psUpdate = conn.prepareStatement(updateSoLuong);
                psUpdate.setString(1, masach);
                int updated = psUpdate.executeUpdate();
                psUpdate.close();
                if (updated == 0) {
                    conn.rollback(); // hết sách hoặc không tồn tại
                    return false;
                }

                // 2.2. Thêm vào chitietphieumuon
                PreparedStatement psChiTiet = conn.prepareStatement(insertChiTiet);
                psChiTiet.setInt(1, maphieu);
                psChiTiet.setString(2, masach);
                psChiTiet.executeUpdate();
                psChiTiet.close();
            }

            conn.commit();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
