/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.TacGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TacGiaDAO {

    public List<TacGia> getAllTacGia() throws SQLException {
        List<TacGia> tacGiaList = new ArrayList<>();
        String query = "SELECT * FROM tacgia";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TacGia tacGia = new TacGia(
                        rs.getInt("matacgia"),
                        rs.getString("tentacgia"),
                        rs.getInt("namsinh"),
                        rs.getString("quequan"),
                        rs.getString("mota"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("quocgia")
                );
                tacGiaList.add(tacGia);
            }
        }
        return tacGiaList;
    }

    // Phương thức thêm mới tác giả
    public boolean addTacGia(TacGia tacGia) throws SQLException {
        String query = "INSERT INTO tacgia (tentacgia, namsinh, quequan, mota, sdt, email, quocgia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tacGia.getTentacgia());
            stmt.setInt(2, tacGia.getNamsinh());
            stmt.setString(3, tacGia.getQuequan());
            stmt.setString(4, tacGia.getMota());
            stmt.setString(5, tacGia.getSdt());
            stmt.setString(6, tacGia.getEmail());
            stmt.setString(7, tacGia.getQuocgia());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateTacGia(TacGia tacGia) throws SQLException {
        String query = "UPDATE tacgia SET tentacgia = ?, namsinh = ?, quequan = ?, mota = ?, sdt = ?, email = ?, quocgia = ? WHERE matacgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tacGia.getTentacgia());
            stmt.setInt(2, tacGia.getNamsinh());
            stmt.setString(3, tacGia.getQuequan());
            stmt.setString(4, tacGia.getMota());
            stmt.setString(5, tacGia.getSdt());
            stmt.setString(6, tacGia.getEmail());
            stmt.setString(7, tacGia.getQuocgia());
            stmt.setInt(8, tacGia.getMatacgia());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức xóa tác giả
    public boolean deleteTacGia(int matacgia) throws SQLException {
        String query = "DELETE FROM tacgia WHERE matacgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, matacgia);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isTacGiaInUse(int matacgia) throws SQLException {
        String query = "SELECT COUNT(*) FROM Sach WHERE matacgia = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, matacgia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean isSdtExists(String sdt, int excludeMatacgia) throws SQLException {
        if (sdt == null || sdt.trim().isEmpty()) {
            return false;
        }
        String query = "SELECT COUNT(*) FROM tacgia WHERE sdt = ? AND matacgia != ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sdt);
            stmt.setInt(2, excludeMatacgia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Kiểm tra xem email đã tồn tại chưa, ngoại trừ bản ghi có matacgia (dùng khi sửa)
    public boolean isEmailExists(String email, int excludeMatacgia) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String query = "SELECT COUNT(*) FROM tacgia WHERE email = ? AND matacgia != ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setInt(2, excludeMatacgia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
