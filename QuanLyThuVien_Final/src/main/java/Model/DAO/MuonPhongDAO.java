package Model.DAO;

import Utils.ConnectToSQLServer;
import Model.Entity.MuonPhong;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MuonPhongDAO {

    private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    public List<Object[]> getAllMuonPhongWithDetails() throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT mp.maphieump, ph.tenphong, dg.tendocgia, mp.tgianmuon, mp.tgiantra, mp.ghichu, mp.trangthai "
                + "FROM muonphong mp "
                + "LEFT JOIN docgia dg ON mp.madocgia = dg.madocgia "
                + "LEFT JOIN phonghoc ph ON mp.maphong = ph.maphong";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                result.add(new Object[]{
                    rs.getInt("maphieump"),
                    rs.getString("tenphong"),
                    rs.getString("tendocgia"),
                    convertTimestampToLocalDateTime(rs.getTimestamp("tgianmuon")),
                    convertTimestampToLocalDateTime(rs.getTimestamp("tgiantra")),
                    rs.getString("ghichu"),
                    rs.getString("trangthai")
                });
            }
        }
        return result;
    }

    public boolean addMuonPhong(MuonPhong mp) throws SQLException {
        String query = "INSERT INTO muonphong (maphong, madocgia, tgianmuon, tgiantra, ghichu, trangthai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, mp.getMaphong());
            stmt.setInt(2, mp.getMadocgia());
            stmt.setTimestamp(3, mp.getTgianmuon() != null ? Timestamp.valueOf(mp.getTgianmuon()) : null);
            stmt.setTimestamp(4, mp.getTgiantra() != null ? Timestamp.valueOf(mp.getTgiantra()) : null);
            stmt.setString(5, mp.getGhichu());
            stmt.setString(6, mp.getTrangthai());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateMuonPhong(MuonPhong mp) throws SQLException {
        String query = "UPDATE muonphong SET maphong = ?, madocgia = ?, tgianmuon = ?, tgiantra = ?, ghichu = ?, trangthai = ? WHERE maphieump = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, mp.getMaphong());
            stmt.setInt(2, mp.getMadocgia());
            stmt.setTimestamp(3, mp.getTgianmuon() != null ? Timestamp.valueOf(mp.getTgianmuon()) : null);
            stmt.setTimestamp(4, mp.getTgiantra() != null ? Timestamp.valueOf(mp.getTgiantra()) : null);
            stmt.setString(5, mp.getGhichu());
            stmt.setString(6, mp.getTrangthai());
            stmt.setInt(7, mp.getMaphieump());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteMuonPhong(int maphieump) throws SQLException {
        String query = "DELETE FROM muonphong WHERE maphieump = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maphieump);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean isPhongInUse(int maphong, LocalDateTime tgianmuon, LocalDateTime tgiantra, int excludeMaphieump) throws SQLException {
        String query = "SELECT COUNT(*) FROM muonphong WHERE maphong = ? "
                     + "AND maphieump != ? "
                     + "AND trangthai = N'Đang mượn' "
                     + "AND ("
                     + "      (tgianmuon <= ? AND tgiantra >= ?) "  // bắt đầu trong khoảng
                     + "   OR (tgianmuon <= ? AND tgiantra >= ?) "  // kết thúc trong khoảng
                     + "   OR (tgianmuon >= ? AND tgiantra <= ?) "  // nằm hoàn toàn trong khoảng
                     + ")";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, maphong);
            stmt.setInt(2, excludeMaphieump);
            stmt.setTimestamp(3, Timestamp.valueOf(tgianmuon));
            stmt.setTimestamp(4, Timestamp.valueOf(tgianmuon));
            stmt.setTimestamp(5, Timestamp.valueOf(tgiantra));
            stmt.setTimestamp(6, Timestamp.valueOf(tgiantra));
            stmt.setTimestamp(7, Timestamp.valueOf(tgianmuon));
            stmt.setTimestamp(8, Timestamp.valueOf(tgiantra));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    public List<Object[]> getMuonPhongById(int maphieump) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT mp.maphieump, ph.tenphong, dg.tendocgia, mp.tgianmuon, mp.tgiantra, mp.ghichu, mp.trangthai " +
                       "FROM muonphong mp " +
                       "LEFT JOIN docgia dg ON mp.madocgia = dg.madocgia " +
                       "LEFT JOIN phonghoc ph ON mp.maphong = ph.maphong " +
                       "WHERE mp.maphieump = ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, maphieump);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Object[]{
                        rs.getInt("maphieump"),
                        rs.getString("tenphong"),
                        rs.getString("tendocgia"),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgianmuon")),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgiantra")),
                        rs.getString("ghichu"),
                        rs.getString("trangthai")    
                    });
                }
            }
        }
        return result;
    }

    public List<Object[]> getMuonPhongByTenPhong(String tenPhong) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT mp.maphieump, ph.tenphong, dg.tendocgia, mp.tgianmuon, mp.tgiantra, mp.ghichu " +
                       "FROM muonphong mp " +
                       "LEFT JOIN docgia dg ON mp.madocgia = dg.madocgia " +
                       "LEFT JOIN phonghoc ph ON mp.maphong = ph.maphong " +
                       "WHERE ph.tenphong LIKE ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + tenPhong + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Object[]{
                        rs.getInt("maphieump"),
                        rs.getString("tenphong"),
                        rs.getString("tendocgia"),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgianmuon")),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgiantra")),
                        rs.getString("ghichu")
                    });
                }
            }
        }
        return result;
    }

    public List<Object[]> getMuonPhongByTenDocGia(String tenDocGia) throws SQLException {
        List<Object[]> result = new ArrayList<>();
        String query = "SELECT mp.maphieump, ph.tenphong, dg.tendocgia, mp.tgianmuon, mp.tgiantra, mp.ghichu " +
                       "FROM muonphong mp " +
                       "LEFT JOIN docgia dg ON mp.madocgia = dg.madocgia " +
                       "LEFT JOIN phonghoc ph ON mp.maphong = ph.maphong " +
                       "WHERE dg.tendocgia LIKE ?";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + tenDocGia + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Object[]{
                        rs.getInt("maphieump"),
                        rs.getString("tenphong"),
                        rs.getString("tendocgia"),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgianmuon")),
                        convertTimestampToLocalDateTime(rs.getTimestamp("tgiantra")),
                        rs.getString("ghichu")
                    });
                }
            }
        }
        return result;
    }
    
    public void capNhatTrangThaiTuDong() throws SQLException {
        String query = "UPDATE muonphong SET trangthai = N'Đã trả' WHERE tgiantra <= GETDATE() AND trangthai = N'Đang mượn'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }
}
