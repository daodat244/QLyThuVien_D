package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.TaiKhoan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {
    public List<TaiKhoan> getAllTaiKhoan() throws SQLException {
        List<TaiKhoan> taikhoanList = new ArrayList<>();
        String query = "SELECT tk.*, n.tennv " +
                       "FROM taikhoan tk " +
                       "LEFT JOIN nhanvien n ON tk.manv = n.manv ";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TaiKhoan tk = new TaiKhoan(
                    rs.getInt("matk"),
                    rs.getInt("manv"),
                    rs.getString("tendangnhap"),
                    rs.getString("matkhau"),
                    rs.getString("role")
                );
                taikhoanList.add(tk);
            }
        }
        return taikhoanList;
    }
    
    public boolean addTK(TaiKhoan tk) throws SQLException {
        
        String checkQuery = "SELECT COUNT(*) FROM taikhoan WHERE manv = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, tk.getManv());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // Trả về false nếu nhân viên đã có tài khoản
                }
            }
        }
        
        String query = "INSERT INTO taikhoan (manv, tendangnhap, matkhau, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tk.getManv());
            stmt.setString(2, tk.getTendangnhap());
            stmt.setString(3, tk.getMatkhau());
            stmt.setString(4, tk.getRole());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateTK(TaiKhoan tk) throws SQLException {
        String query = "UPDATE taikhoan SET manv = ?, tendangnhap = ?, matkhau = ?, role = ? WHERE matk = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tk.getManv());
            stmt.setString(2, tk.getTendangnhap());
            stmt.setString(3, tk.getMatkhau());
            stmt.setString(4, tk.getRole());
            stmt.setInt(5, tk.getMatk());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteTK(int matk) throws SQLException {
        String query = "DELETE FROM taikhoan WHERE matk = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, matk);
            return stmt.executeUpdate() > 0;
        }
    }
    public TaiKhoan authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM taikhoan WHERE tendangnhap = ? AND matkhau = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TaiKhoan(
                        rs.getInt("matk"),
                        rs.getInt("manv"),
                        rs.getString("tendangnhap"),
                        rs.getString("matkhau"),
                        rs.getString("role")
                    );
                }
            }
        }
        return null;
    }
    
    public boolean isUsernameExists(String username, String excludeManv) throws SQLException {
        String sql = "SELECT COUNT(*) FROM taikhoan WHERE tendangnhap = ? AND manv != ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, excludeManv != null ? excludeManv : "");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
    // Kiểm tra nhân viên đã có tài khoản chưa
public boolean isNhanVienHasAccount(int manv) throws SQLException {
    String sql = "SELECT COUNT(*) FROM taikhoan WHERE manhanvien = ?";
    try (Connection conn = ConnectToSQLServer.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, manv);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }
    return false;
}

// Lấy manhanvien từ mataikhoan
public int getManhanvienByMataikhoan(int matk) throws SQLException {
    String sql = "SELECT manv FROM taikhoan WHERE matk = ?";
    try (Connection conn = ConnectToSQLServer.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, matk);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("manv");
        }
    }
    throw new SQLException("Không tìm thấy tài khoản với mataikhoan: " + matk);
}
    
}
