package Model.DAO;

import Model.Entity.ChiTietPhieuMuon;
import Model.Entity.ChiTietPhieuTra;
import Utils.ConnectToSQLServer;
import Model.Entity.PhieuMuon;
import Model.Entity.PhieuTra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PhieuTraDAO {

    public boolean addPhieuTra(PhieuTra pt, Connection conn) throws SQLException {
        String query = "INSERT INTO phieutra (maphieu, madocgia, manv, masach, ngaymuon, ngaytradukien, ngaytrathucte, tongphiphat, ghichu, tinhtrang) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, pt.getMaphieu());
            stmt.setInt(2, pt.getMadocgia());
            stmt.setInt(3, pt.getManv());
            stmt.setString(4, pt.getMasach());
            stmt.setObject(5, pt.getNgaymuon());
            stmt.setObject(6, pt.getNgaytradukien());
            stmt.setObject(7, pt.getNgaytrathucte());
            stmt.setDouble(8, pt.getTongphiphat());
            stmt.setString(9, pt.getGhichu());
            stmt.setString(10, pt.getTinhtrang());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public PhieuMuon getPhieuMuonById(int maphieu) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectToSQLServer.getConnection();
            String sql = "SELECT * FROM PhieuMuon WHERE maphieu = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, maphieu);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        PhieuMuon pm = new PhieuMuon(
                                rs.getInt("maphieu"),
                                rs.getInt("madocgia"),
                                rs.getInt("manv"),
                                rs.getObject("ngaymuon", LocalDateTime.class),
                                rs.getObject("ngaytradukien", LocalDateTime.class),
                                rs.getString("trangthai")
                        );
                        return pm;
                    }
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<PhieuTra> getAllPhieuTra() throws SQLException {
        List<PhieuTra> phieuTraList = new ArrayList<>();
        String query = "SELECT maphieutra, maphieu, madocgia, manv, masach, ngaymuon, ngaytradukien, ngaytrathucte, tongphiphat, ghichu, tinhtrang FROM phieutra";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhieuTra pt = new PhieuTra(
                        rs.getInt("maphieutra"),
                        rs.getInt("maphieu"),
                        rs.getInt("madocgia"),
                        rs.getInt("manv"),
                        rs.getString("masach"),
                        rs.getObject("ngaymuon", LocalDateTime.class),
                        rs.getObject("ngaytradukien", LocalDateTime.class),
                        rs.getObject("ngaytrathucte", LocalDateTime.class),
                        rs.getDouble("tongphiphat"),
                        rs.getString("ghichu"),
                        rs.getString("tinhtrang")
                );
                phieuTraList.add(pt);
            }
        }
        return phieuTraList;
    }

    public boolean addChiTietPhieuTra(ChiTietPhieuTra ctp, Connection conn) throws SQLException {
        String query = "INSERT INTO chitietphieutra (maphieutra, masach, phiphat, tinhtrang) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ctp.getMaphieutra());
            stmt.setString(2, ctp.getMasach());
            stmt.setDouble(3, ctp.getPhiphat());
            stmt.setString(4, ctp.getTinhtrang());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public int getLatestPhieuTraId(Connection conn) throws SQLException {
        String query = "SELECT TOP 1 maphieutra FROM phieutra ORDER BY maphieutra DESC";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public List<String> getSachListByPhieuTra(int maphieutra) throws SQLException {
        List<String> sachList = new ArrayList<>();
        String query = "SELECT masach FROM chitietphieutra WHERE maphieutra = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maphieutra);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sachList.add(rs.getString("masach"));
                }
            }
        }
        return sachList;
    }

    public String getMasachListByPhieuTra(int maphieutra) throws SQLException {
        List<String> sachList = getSachListByPhieuTra(maphieutra);
        return sachList.isEmpty() ? "Không có sách" : String.join(",", sachList);
    }

    public List<ChiTietPhieuTra> getChiTietPhieuTraByMaPhieuTra(int maphieutra) throws SQLException {
        List<ChiTietPhieuTra> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM chitietphieutra WHERE maphieutra = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maphieutra);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuTra ctp = new ChiTietPhieuTra(
                            rs.getInt("id"),
                            rs.getInt("maphieutra"),
                            rs.getString("masach"),
                            rs.getDouble("phiphat"),
                            rs.getString("tinhtrang")
                    );
                    chiTietList.add(ctp);
                }
            }
        }
        return chiTietList;
    }
//public boolean deleteChiTietPhieuTra(int maphieutra) throws SQLException {
//    String query = "DELETE FROM chitietphieutra WHERE maphieutra = ?";
//    try (Connection conn = ConnectToSQLServer.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query)) {
//        stmt.setInt(1, maphieutra);
//        int rowsAffected = stmt.executeUpdate();
//        return rowsAffected > 0;
//    }
//}
}
