package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.SuKien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SuKienDAO {
    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    public List<Object[]> getAllSuKienWithDetails() throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT sk.masukien, sk.tensukien, nxb.tennxb, sk.tgiantochuc, sk.tgianketthuc, sk.mota " +
                       "FROM sukien sk " +
                       "LEFT JOIN nhaxuatban nxb ON sk.manxb = nxb.manxb";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = new Object[] {
                    rs.getInt("masukien"),
                    rs.getString("tensukien"),
                    rs.getString("tennxb"),
                    convertTimestampToLocalDateTime(rs.getTimestamp("tgiantochuc")),
                    convertTimestampToLocalDateTime(rs.getTimestamp("tgianketthuc")),
                    rs.getString("mota")
                };
                result.add(row);
            }
        }
        return result;
    }

    public boolean themSuKien(SuKien sk) throws SQLException {
        String query = "INSERT INTO sukien (tensukien, manxb, tgiantochuc, tgianketthuc, mota) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sk.getTensukien());
            stmt.setInt(2, sk.getManxb());
            LocalDateTime tgiantochuc = sk.getTgiantochuc();
            LocalDateTime tgianketthuc = sk.getTgianketthuc();
            stmt.setTimestamp(3, tgiantochuc != null ? Timestamp.valueOf(tgiantochuc) : null);
            stmt.setTimestamp(4, tgianketthuc != null ? Timestamp.valueOf(tgianketthuc) : null);
            stmt.setString(5, sk.getMota());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateSuKien(SuKien sk) throws SQLException {
        String query = "UPDATE sukien SET tensukien = ?, manxb = ?, tgiantochuc = ?, tgianketthuc = ?, mota = ? WHERE masukien = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sk.getTensukien());          
            stmt.setInt(2, sk.getManxb());
            LocalDateTime tgiantochuc = sk.getTgiantochuc();
            LocalDateTime tgianketthuc = sk.getTgianketthuc();
            stmt.setTimestamp(3, tgiantochuc != null ? Timestamp.valueOf(tgiantochuc) : null);
            stmt.setTimestamp(4, tgianketthuc != null ? Timestamp.valueOf(tgianketthuc) : null);
            stmt.setString(5, sk.getMota());
            stmt.setInt(6, sk.getMasukien());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteSuKien(int masukien) throws SQLException {
        String query = "DELETE FROM sukien WHERE masukien = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, masukien);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Object[]> getSuKienByTen(String keyword) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT sk.masukien, sk.tensukien, nxb.tennxb, sk.tgiantochuc, sk.tgianketthuc, sk.mota " +
                       "FROM sukien sk " +
                       "LEFT JOIN nhaxuatban nxb ON sk.manxb = nxb.manxb " +
                       "WHERE LOWER(sk.tensukien) LIKE ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword.toLowerCase() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[] {
                        rs.getInt("masukien"),
                        rs.getString("tensukien"),
                        rs.getString("tennxb"),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgiantochuc")),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgianketthuc")),
                        rs.getString("mota")
                    };
                    result.add(row);
                }
            }
        }
        return result;
    }

    public List<Object[]> getSuKienById(int masukien) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT sk.masukien, sk.tensukien, nxb.tennxb, sk.tgiantochuc, sk.tgianketthuc, sk.mota " +
                       "FROM sukien sk " +
                       "LEFT JOIN nhaxuatban nxb ON sk.manxb = nxb.manxb " +
                       "WHERE sk.masukien = ?";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, masukien);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[] {
                        rs.getInt("masukien"),
                        rs.getString("tensukien"),
                        rs.getString("tennxb"),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgiantochuc")),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgianketthuc")),
                        rs.getString("mota")
                    };
                    result.add(row);
                }
            }
        }
        return result;
    }
}
