package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.PhongHoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhongHocDAO {
    public List<PhongHoc> getAllPhongHoc() throws SQLException {
        List<PhongHoc> phonghocList = new ArrayList<>();
        String query = "SELECT * FROM phonghoc";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PhongHoc phonghoc = new PhongHoc(
                    rs.getInt("maphong"),
                    rs.getString("tenphong"),
                    rs.getString("mota")
                );
                phonghocList.add(phonghoc);
            }
        }
        return phonghocList;
    }

    // PhongHocDAO.java
    public PhongHoc findByTenPhong(String tenPhong) throws SQLException {
        String sql = "SELECT * FROM PhongHoc WHERE TenPhong = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tenPhong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PhongHoc(rs.getInt("maphong"), rs.getString("tenphong"), rs.getString("mota") );
            }
        }
        return null;
    }
}
