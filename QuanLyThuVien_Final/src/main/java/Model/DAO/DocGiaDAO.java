package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.DocGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocGiaDAO {

    public List<DocGia> getAllDocGia() throws SQLException {
        List<DocGia> dgList = new ArrayList<>();
        String query = "SELECT * FROM docgia";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DocGia dg = new DocGia(
                        rs.getInt("madocgia"),
                        rs.getString("tendocgia"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("diachi"),
                        rs.getString("gioitinh")
                );
                dgList.add(dg);
            }
        }
        return dgList;
    }

    public boolean addDG(DocGia dg) throws SQLException {
        String query = "INSERT INTO docgia (tendocgia, sdt, email, diachi, gioitinh) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, dg.getTendocgia());
            stmt.setString(2, dg.getSdt());
            stmt.setString(3, dg.getEmail());
            stmt.setString(4, dg.getDiachi());
            stmt.setString(5, dg.getGioitinh());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateDG(DocGia dg) throws SQLException {
        String query = "UPDATE docgia SET tendocgia = ?, sdt = ?, email = ?, diachi = ?, gioitinh = ? WHERE madocgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, dg.getTendocgia());
            stmt.setString(2, dg.getSdt());
            stmt.setString(3, dg.getEmail());
            stmt.setString(4, dg.getDiachi());
            stmt.setString(5, dg.getGioitinh());
            stmt.setInt(6, dg.getMadocgia());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDG(int madocgia) throws SQLException {
        String query = "DELETE FROM docgia WHERE madocgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, madocgia);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isDuplicatePhoneOrEmail(String sdt, String email, int madocgia) throws SQLException {
        String query = "SELECT COUNT(*) FROM docgia WHERE (sdt = ? OR email = ?) AND madocgia != ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sdt);
            stmt.setString(2, email);
            stmt.setInt(3, madocgia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean isDocGiaInUse(int madocgia) throws SQLException {
        String query = "SELECT COUNT(*) FROM PhieuMuon WHERE madocgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, madocgia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public DocGia getDocGiaById(int madocgia) throws SQLException {
        String query = "SELECT * FROM docgia WHERE madocgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, madocgia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DocGia(
                            rs.getInt("madocgia"),
                            rs.getString("tendocgia"),
                            rs.getString("sdt"),
                            rs.getString("email"),
                            rs.getString("diachi"),
                            rs.getString("gioitinh")
                    );
                }
            }
        }
        return null;
    }

    public DocGia getDocGiaByTen(String tenDocGia) throws SQLException {
        String sql = "SELECT * FROM docgia WHERE tendocgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenDocGia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DocGia(
                            rs.getInt("madocgia"),
                            rs.getString("tendocgia"),
                            rs.getString("sdt"),
                            rs.getString("email"),
                            rs.getString("diachi"),
                            rs.getString("gioitinh")
                    );
                }
            }
        }
        return null;
    }

    public int getMaDocGiaByTen(String tenDocGia) throws SQLException {
        String query = "SELECT madocgia FROM docgia WHERE tendocgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenDocGia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("madocgia");
                }
            }
        }
        return -1;
    }
}
