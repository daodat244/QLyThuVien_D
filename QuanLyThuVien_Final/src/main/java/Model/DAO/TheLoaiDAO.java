package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.TheLoai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TheLoaiDAO {

    public List<TheLoai> getAllTheLoai() throws SQLException {
        List<TheLoai> theLoaiList = new ArrayList<>();
        String query = "SELECT * FROM theloai";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TheLoai theLoai = new TheLoai(
                        rs.getInt("matheloai"),
                        rs.getString("tentheloai"),
                        rs.getString("mota")
                );
                theLoaiList.add(theLoai);
            }
        }
        return theLoaiList;
    }

    // Phương thức thêm mới tác giả
    public boolean addTheLoai(TheLoai theLoai) throws SQLException {
        String query = "INSERT INTO theloai (tentheloai, mota) VALUES (?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, theLoai.getTentheloai());
            stmt.setString(2, theLoai.getMota());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức cập nhật thông tin tác giả
    public boolean updateTheLoai(TheLoai theLoai) throws SQLException {
        String query = "UPDATE theloai SET tentheloai = ?, mota = ? WHERE matheloai = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, theLoai.getTentheloai());
            stmt.setString(2, theLoai.getMota());
            stmt.setInt(3, theLoai.getMatheloai());
            return stmt.executeUpdate() > 0;
        }
    }

    // Phương thức xóa tác giả
    public boolean deleteTheLoai(int matheloai) throws SQLException {
        String query = "DELETE FROM theloai WHERE matheloai = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, matheloai);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isTheLoaiInUse(int matheloai) throws SQLException {
        String query = "SELECT COUNT(*) FROM Sach WHERE matheloai = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, matheloai);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    

}
