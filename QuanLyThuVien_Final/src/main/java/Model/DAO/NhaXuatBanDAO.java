package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.NhaXuatBan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhaXuatBanDAO {

    public List<NhaXuatBan> getAllNhaXuatBan() throws SQLException {
        List<NhaXuatBan> nxbList = new ArrayList<>();
        String query = "SELECT * FROM nhaxuatban";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                NhaXuatBan nxb = new NhaXuatBan(
                        rs.getInt("manxb"),
                        rs.getString("tennxb"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("diachi"),
                        rs.getString("quocgia")
                );
                nxbList.add(nxb);
            }
        }
        return nxbList;
    }

    // Phương thức thêm mới tác giả
    public boolean addNXB(NhaXuatBan nxb) throws SQLException {
        String query = "INSERT INTO nhaxuatban (tennxb, sdt, email, diachi, quocgia) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nxb.getTennxb());
            stmt.setString(2, nxb.getSdt());
            stmt.setString(3, nxb.getEmail());
            stmt.setString(4, nxb.getDiachi());
            stmt.setString(5, nxb.getQuocgia());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức cập nhật thông tin tác giả
    public boolean updateNXB(NhaXuatBan nxb) throws SQLException {
        String query = "UPDATE nhaxuatban SET sdt = ?, email = ?, diachi = ?, quocgia = ? WHERE manxb = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nxb.getSdt());
            stmt.setString(2, nxb.getEmail());
            stmt.setString(3, nxb.getDiachi());
            stmt.setString(4, nxb.getQuocgia());
            stmt.setInt(5, nxb.getManxb());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức xóa tác giả
    public boolean deleteNXB(int manxb) throws SQLException {
        String query = "DELETE FROM nhaxuatban WHERE manxb = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, manxb);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isTenNXBExists(String tennxb) throws SQLException {
        String query = "SELECT COUNT(*) FROM nhaxuatban WHERE tennxb = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tennxb);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean isNXBInUse(int manxb) throws SQLException {
        String query = "SELECT COUNT(*) FROM Sach WHERE manxb = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, manxb);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean isSdtExists(String sdt, int excludeManxb) throws SQLException {
        if (sdt == null || sdt.trim().isEmpty()) {
            return false;
        }
        String query = "SELECT COUNT(*) FROM nhaxuatban WHERE sdt = ? AND manxb != ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sdt);
            stmt.setInt(2, excludeManxb);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean isEmailExists(String email, int excludeManxb) throws SQLException {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String query = "SELECT COUNT(*) FROM nhaxuatban WHERE email = ? AND manxb != ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setInt(2, excludeManxb);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public NhaXuatBan findByTenNXB(String tenNXB) throws SQLException {
        String query = "SELECT * FROM nhaxuatban WHERE tennxb = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, tenNXB);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    NhaXuatBan nxb = new NhaXuatBan();
                    nxb.setManxb(rs.getInt("manxb"));
                    nxb.setTennxb(rs.getString("tennxb"));
                    return nxb;
                }
            }
        }
        return null;
    }

}
