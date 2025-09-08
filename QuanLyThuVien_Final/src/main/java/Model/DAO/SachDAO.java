package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.Sach;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SachDAO {

    public List<Sach> getAllSach() throws SQLException {
        List<Sach> sachList = new ArrayList<>();
        String query = "SELECT masach, tensach, soluong FROM sach";

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Sach sach = new Sach();
                sach.setMasach(rs.getString("masach"));
                sach.setTensach(rs.getString("tensach"));
                sach.setSoluong(rs.getInt("soluong"));
                sachList.add(sach);
            }
        }
        return sachList;
    }

    public List<Object[]> getAllSachWithDetails() throws SQLException {
        List<Object[]> sachList = new ArrayList<>();
        String query = "SELECT s.masach, s.tensach, s.namxb, s.sotrang, s.soluong, t.tentacgia, n.tennxb, tl.tentheloai, s.anh "
                + "FROM sach s "
                + "LEFT JOIN tacgia t ON s.matacgia = t.matacgia "
                + "LEFT JOIN nhaxuatban n ON s.manxb = n.manxb "
                + "LEFT JOIN theloai tl ON s.matheloai = tl.matheloai "
                + "WHERE s.namxb = 2021";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[]{
                    rs.getString("masach"),
                    rs.getString("tensach"),
                    rs.getString("tentacgia"),
                    rs.getString("tennxb"),
                    rs.getString("tentheloai"),
                    rs.getInt("namxb"),
                    rs.getInt("sotrang"),
                    rs.getInt("soluong"),
                    rs.getBytes("anh") // Thêm ảnh
                };
                sachList.add(row);
            }
        }
        return sachList;
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

    public List<Object[]> searchSachWithDetails(String keyword, String criteria) throws SQLException {
        List<Object[]> sachList = new ArrayList<>();
        String query = "SELECT s.masach, s.tensach, s.namxb, s.sotrang, s.soluong, t.tentacgia, n.tennxb, tl.tentheloai, s.anh "
                + "FROM sach s "
                + "LEFT JOIN tacgia t ON s.matacgia = t.matacgia "
                + "LEFT JOIN nhaxuatban n ON s.manxb = n.manxb "
                + "LEFT JOIN theloai tl ON s.matheloai = tl.matheloai "
                + "WHERE ";
        switch (criteria) {
            case "Mã sách" ->
                query += "s.masach LIKE ?";
            case "Tên sách" ->
                query += "s.tensach LIKE ?";
            case "Tác giả" ->
                query += "t.tentacgia LIKE ?";
            case "NXB" ->
                query += "n.tennxb LIKE ?";
            case "Thể loại" ->
                query += "tl.tentheloai LIKE ?";
            default -> {
                return getAllSachWithDetails();
            }
        }
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[]{
                        rs.getString("masach"),
                        rs.getString("tensach"),
                        rs.getString("tentacgia"),
                        rs.getString("tennxb"),
                        rs.getString("tentheloai"),
                        rs.getInt("namxb"),
                        rs.getInt("sotrang"),
                        rs.getInt("soluong"),
                        rs.getBytes("anh") // Thêm ảnh
                    };
                    sachList.add(row);
                }
            }
        }
        return sachList;
    }

    public List<Object[]> searchSachByYearRange(int namBatDau, int namKetThuc) throws SQLException {
        List<Object[]> sachList = new ArrayList<>();
        String query = "SELECT s.masach, s.tensach, s.namxb, s.sotrang, s.soluong, t.tentacgia, n.tennxb, tl.tentheloai, s.anh "
                + "FROM sach s "
                + "LEFT JOIN tacgia t ON s.matacgia = t.matacgia "
                + "LEFT JOIN nhaxuatban n ON s.manxb = n.manxb "
                + "LEFT JOIN theloai tl ON s.matheloai = tl.matheloai "
                + "WHERE s.namxb BETWEEN ? AND ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, namBatDau);
            stmt.setInt(2, namKetThuc);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[]{
                        rs.getString("masach"),
                        rs.getString("tensach"),
                        rs.getString("tentacgia"),
                        rs.getString("tennxb"),
                        rs.getString("tentheloai"),
                        rs.getInt("namxb"),
                        rs.getInt("sotrang"),
                        rs.getInt("soluong"),
                        rs.getBytes("anh")
                    };
                    sachList.add(row);
                }
            }
        }
        return sachList;
    }

    public boolean inUse(String masach) throws SQLException {
        String query = "SELECT COUNT(*) FROM chitietphieumuon cpm "
                + "JOIN phieumuon pm ON cpm.maphieu = pm.maphieu "
                + "WHERE cpm.masach = ? ";

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, masach);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false; // Sách không được mượn
    }

    public Sach getSachById(String masach) throws SQLException {
        String query = "SELECT masach, tensach, matacgia, manxb, matheloai, namxb, sotrang, soluong, anh FROM sach WHERE masach = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, masach);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Sach sach = new Sach();
                    sach.setMasach(rs.getString("masach"));
                    sach.setTensach(rs.getString("tensach"));
                    sach.setMatacgia(rs.getInt("matacgia"));
                    sach.setManxb(rs.getInt("manxb"));
                    sach.setMatheloai(rs.getInt("matheloai"));
                    sach.setNamxb(rs.getInt("namxb"));
                    sach.setSotrang(rs.getInt("sotrang"));
                    sach.setSoluong(rs.getInt("soluong"));
                    byte[] anh = rs.getBytes("anh");
                    sach.setAnh(anh);
                    return sach;
                } else {
                    System.out.println("Không tìm thấy sách với mã: " + masach);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getSachById: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public boolean giamSoLuongSach(String masach) throws SQLException {
        String sql = "UPDATE sach SET soluong = soluong - 1 WHERE masach = ? AND soluong > 0";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, masach);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean tangSoLuongSach(String masach) throws SQLException {
        String sql = "UPDATE sach SET soluong = soluong + 1 WHERE masach = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, masach);
            return ps.executeUpdate() > 0;
        }
    }
}
