package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public List<NhanVien> getAllNhanVien() throws SQLException {
        List<NhanVien> nvList = new ArrayList<>();
        String query = "SELECT * FROM nhanvien";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getInt("manv"),
                        rs.getString("tennv"),
                        rs.getString("sdt"),
                        rs.getDate("ngaysinh"),
                        rs.getString("quequan"),
                        rs.getString("gioitinh")
                );
                nvList.add(nv);
            }
        }
        return nvList;
    }

    // Phương thức thêm mới nhân viên
    public boolean addNV(NhanVien nv) throws SQLException {
        String query = "INSERT INTO nhanvien (tennv, sdt, ngaysinh, quequan, gioitinh) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nv.getTennv());
            stmt.setString(2, nv.getSdt());
            stmt.setDate(3, nv.getNgaysinh() != null ? new java.sql.Date(nv.getNgaysinh().getTime()) : null);
            stmt.setString(4, nv.getQuequan());
            stmt.setString(5, nv.getGioitinh());

            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức cập nhật thông tin nhân viên
    public boolean updateNV(NhanVien nv) throws SQLException {
        String query = "UPDATE nhanvien SET tennv = ?, sdt = ?, ngaysinh = ?, quequan = ?, gioitinh = ? WHERE manv = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nv.getTennv());
            stmt.setString(2, nv.getSdt());
            stmt.setDate(3, nv.getNgaysinh() != null ? new java.sql.Date(nv.getNgaysinh().getTime()) : null);
            stmt.setString(4, nv.getQuequan());
            stmt.setString(5, nv.getGioitinh());
            stmt.setInt(6, nv.getManv());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức xóa nhân viên
    public boolean deleteNV(int manhanvien) throws SQLException {
        String query = "DELETE FROM nhanvien WHERE manv = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, manhanvien);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isNhanVienExists(int manhanvien) throws SQLException {
        String sql = "SELECT COUNT(*) FROM nhanvien WHERE manv = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, manhanvien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean isDuplicatePhone(String sdt, int manhanvien) throws SQLException {
        String query = "SELECT COUNT(*) FROM nhanvien WHERE sdt = ? AND manv != ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sdt);
            stmt.setInt(2, manhanvien);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }
    }

    public NhanVien getNhanVienById(int manv) throws SQLException {
        String query = "SELECT * FROM nhanvien WHERE manv = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, manv);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    NhanVien nhanVien = new NhanVien();
                    nhanVien.setManv(rs.getInt("manv"));
                    nhanVien.setTennv(rs.getString("tennv"));
                    nhanVien.setSdt(rs.getString("sdt"));
                    nhanVien.setNgaysinh(rs.getDate("ngaysinh"));
                    nhanVien.setQuequan(rs.getString("quequan"));
//                    System.out.println("Found staff: manv = " + rs.getInt("manv") + ", tennv = " + rs.getString("tennv"));
                    return nhanVien;
                } else {
                    System.out.println("Không tìm thấy nhân viên với manv = " + manv);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getNhanVienById: " + e.getMessage());
            throw e;
        }
        return null;
    }

    public int getMaNhanVienByTen(String tenNhanVien) throws SQLException {
        String query = "SELECT manv FROM nhanvien WHERE tennv = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenNhanVien);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int manv = rs.getInt("manv");
//                    System.out.println("Found manv = " + manv + " for tennv = " + tenNhanVien);
                    return manv;
                } else {
                    System.out.println("Không có mã nv ứng với tên nhân viên = " + tenNhanVien);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getMaNhanVienByTen: " + e.getMessage());
            throw e;
        }
        return -1;
    }

    public boolean isNhanVienInUse(int manhanvien) throws SQLException {
        String query = "SELECT COUNT(*) FROM PhieuMuon WHERE manv = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, manhanvien);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
