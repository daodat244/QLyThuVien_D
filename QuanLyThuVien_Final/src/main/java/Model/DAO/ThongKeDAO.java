package Model.DAO;

import Utils.ConnectToSQLServer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    private Connection conn;

    // Hàm lấy số lượng sách từ bảng SACH
    public int getDauSach() {
        String sql = "SELECT COUNT(*) FROM sach";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongSach() {
        String sql = "SELECT SUM(soluong) FROM sach";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Trường hợp không có sách
    }

    public int getSoLuongNXB() {
        String sql = "SELECT COUNT(*) FROM nhaxuatban";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongTG() {
        String sql = "SELECT COUNT(*) FROM tacgia";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongSK() {
        String sql = "SELECT COUNT(*) FROM sukien";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongDocGia() {
        String sql = "SELECT COUNT(*) FROM docgia";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongTheLoai() {
        String sql = "SELECT COUNT(*) FROM theloai";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongMuonPhong() {
        String sql = "SELECT COUNT(*) FROM muonphong";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongPhieuMuon() {
        String sql = "SELECT COUNT(*) FROM phieumuon";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongPhieuTra() {
        String sql = "SELECT COUNT(*) FROM phieutra";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongNhanVien() {
        String sql = "SELECT COUNT(*) FROM nhanvien";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongTaiKhoan() {
        String sql = "SELECT COUNT(*) FROM taikhoan";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getTongPhiPhat() {
        String sql = "SELECT SUM(tongphiphat) AS tongphiphat "
                + "FROM phieutra;";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    public int getSoLuongPhongHoc() {
        String sql = "SELECT COUNT(*) FROM phonghoc";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi nếu có
        }
        return 0;  // Nếu không có sách, trả về 0
    }

    //----------------------
    //ThongKeSach
    //----------------------
    public int getTongSachDangMuon() {
        String sql = "SELECT COUNT(*) FROM phieumuon WHERE trangthai = N'Chưa trả'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTongSachConLai() {
        String sql = """
            SELECT SUM(s.soluong) - ISNULL((
                SELECT COUNT(*)
                FROM phieumuon pm
                WHERE pm.trangthai = N'Chưa trả'
            ), 0) AS sach_con_lai
            FROM sach s
            """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("sach_con_lai");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getMostBorrow() {
        String sql = "SELECT TOP 1 s.tensach, COUNT(ctpm.masach) AS so_luot_muon " +
                     "FROM ChiTietPhieuMuon ctpm " +
                     "JOIN sach s ON ctpm.masach = s.masach " +
                     "GROUP BY s.tensach " +
                     "ORDER BY so_luot_muon DESC";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("tensach");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Không có dữ liệu";
    }


    //----------------------
    //ThongKeTheLoai
    //----------------------
    public List<Object[]> thongKeSachTheoTheLoai() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT tl.tentheloai, s.tensach, s.soluong
            FROM sach s
            JOIN theloai tl ON s.matheloai = tl.matheloai
            ORDER BY tl.tentheloai, s.tensach
            """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String theLoai = rs.getString("tentheloai");
                String tenSach = rs.getString("tensach");
                int soLuong = rs.getInt("soluong");
                list.add(new Object[]{theLoai, tenSach, soLuong});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Object[]> thongKeSLSachTheoTheLoai() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT theloai.tentheloai, SUM(s.soluong) AS tongsoluong
            FROM sach s
            JOIN theloai ON s.matheloai = theloai.matheloai
            GROUP BY theloai.tentheloai
            ORDER BY theloai.tentheloai
            """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenTL = rs.getString("tentheloai");
                int tongSoLuong = rs.getInt("tongsoluong");

                list.add(new Object[]{tenTL, tongSoLuong});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    //----------------------
    //ThongKeNXB
    //----------------------
    public List<Object[]> thongKeSachTheoNXB() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT nhaxuatban.tennxb, s.tensach, s.soluong
            FROM sach s
            JOIN nhaxuatban ON s.manxb = nhaxuatban.manxb
            ORDER BY nhaxuatban.tennxb, s.tensach
            """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenNXB = rs.getString("tennxb");
                String tenSach = rs.getString("tensach");
                int soLuong = rs.getInt("soluong");

                list.add(new Object[]{tenNXB, tenSach, soLuong});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Object[]> thongKeSLSachTheoNXB() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT nhaxuatban.tennxb, SUM(s.soluong) AS tongsoluong
            FROM sach s
            JOIN nhaxuatban ON s.manxb = nhaxuatban.manxb
            GROUP BY nhaxuatban.tennxb
            ORDER BY nhaxuatban.tennxb
            """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenNXB = rs.getString("tennxb");
                int tongSoLuong = rs.getInt("tongsoluong");

                list.add(new Object[]{tenNXB, tongSoLuong});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static int countNXBTrongNuoc() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM nhaxuatban WHERE quocgia = N'Việt Nam'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int countNXBNgoaiNuoc() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM nhaxuatban WHERE quocgia = N'Nước Ngoài'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //----------------------
    //ThongKeTacGia
    //----------------------
    public List<Object[]> thongKeSachTheoTacGia() {
        List<Object[]> list = new ArrayList<>();
        String sql = """
            SELECT tacgia.tentacgia, s.tensach, s.soluong
            FROM sach s
            JOIN tacgia ON s.matacgia = tacgia.matacgia
            ORDER BY tacgia.tentacgia, s.tensach
            """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenTG = rs.getString("tentacgia");
                String tenSach = rs.getString("tensach");
                int soLuong = rs.getInt("soluong");

                list.add(new Object[]{tenTG, tenSach, soLuong});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Object[]> thongKeDauSachTheoTacGia() {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT tg.tentacgia, COUNT(DISTINCT s.tensach) AS soluong_dausach
            FROM sach s
            JOIN tacgia tg ON s.matacgia = tg.matacgia
            GROUP BY tg.tentacgia
            ORDER BY soluong_dausach DESC
            """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenTacGia = rs.getString("tentacgia");
                int soLuongDauSach = rs.getInt("soluong_dausach");
                result.add(new Object[]{tenTacGia, soLuongDauSach});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int countTGVietNam() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tacgia WHERE quocgia = N'Việt Nam'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int countTGNuocNgoai() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tacgia WHERE quocgia = N'Nước Ngoài'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    //----------------------
    //ThongKeSuKien
    //----------------------
    public int getPresentEvents() {
        String sql = "SELECT COUNT(*) FROM sukien WHERE tgiantochuc <= GETDATE() AND tgianketthuc >= GETDATE()";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> getPresentEventNames() {
        List<String> eventNames = new ArrayList<>();
        String sql = "SELECT tensukien FROM sukien WHERE tgiantochuc <= GETDATE() AND tgianketthuc >= GETDATE()";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                eventNames.add(rs.getString("tensukien"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventNames;
    }

    public int getPassEvents() {
        String sql = "SELECT COUNT(*) FROM sukien WHERE tgianketthuc < GETDATE()";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getComingEvents() {
        String sql = "SELECT COUNT(*) FROM sukien WHERE tgiantochuc > GETDATE()";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Object[]> thongKeSoLuongSuKienTheoNXB() {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT nxb.tennxb, COUNT(sk.masukien) AS soluong_sukien
            FROM sukien sk
            JOIN nhaxuatban nxb ON sk.manxb = nxb.manxb
            GROUP BY nxb.tennxb
            ORDER BY soluong_sukien DESC
        """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenNXB = rs.getString("tennxb");
                int soLuong = rs.getInt("soluong_sukien");
                result.add(new Object[]{tenNXB, soLuong});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //----------------------
    //ThongKeMuonPhong
    //----------------------
    public int getSoPhongDangMuon() {
        String sql = "SELECT COUNT(DISTINCT maphong) FROM muonphong WHERE trangthai = N'Đang mượn'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSoPhongConDungDuoc() {
        String sql = "SELECT COUNT(*) FROM phonghoc WHERE maphong NOT IN "
                + "(SELECT DISTINCT maphong FROM muonphong WHERE trangthai = N'Đang mượn')";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSoPhieuMuonPhongDangMuon() {
        String sql = "SELECT COUNT(*) FROM muonphong WHERE trangthai = N'Đang mượn'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSoPhieuMuonPhongDaTra() {
        String sql = "SELECT COUNT(*) FROM muonphong WHERE trangthai = N'Đã trả'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Object[]> getPhieuDangMuon() {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT dg.tendocgia AS tendocgia, p.tenphong, mp.tgianmuon, mp.tgiantra
            FROM muonphong mp
            JOIN docgia dg ON mp.madocgia = dg.madocgia
            JOIN phonghoc p ON mp.maphong = p.maphong
            WHERE mp.trangthai = N'Đang mượn'
        """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenDG = rs.getString("tendocgia");
                String tenPhong = rs.getString("tenphong");
                Timestamp tgMuon = rs.getTimestamp("tgianmuon");
                Timestamp tgTra = rs.getTimestamp("tgiantra");

                result.add(new Object[]{tenDG, tenPhong, tgMuon, tgTra});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Object[]> getPhieuDaTra() {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT dg.tendocgia AS tendocgia, p.tenphong, mp.tgianmuon, mp.tgiantra
            FROM muonphong mp
            JOIN docgia dg ON mp.madocgia = dg.madocgia
            JOIN phonghoc p ON mp.maphong = p.maphong
            WHERE mp.trangthai = N'Đã trả'
        """;

        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tenDG = rs.getString("tendocgia");
                String tenPhong = rs.getString("tenphong");
                Timestamp tgMuon = rs.getTimestamp("tgianmuon");
                Timestamp tgTra = rs.getTimestamp("tgiantra");

                result.add(new Object[]{tenDG, tenPhong, tgMuon, tgTra});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //----------------------
    //ThongKeNhanVien
    //----------------------
    public int getSoLuongNhanVienNam() {
        String sql = "SELECT COUNT(*) FROM nhanvien WHERE gioitinh = N'Nam'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSoLuongNhanVienNu() {
        String sql = "SELECT COUNT(*) FROM nhanvien WHERE gioitinh = N'Nữ'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<Object[]> getDanhSachNhanVienNam() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT tennv, sdt, ngaysinh, quequan FROM nhanvien WHERE gioitinh = N'Nam'";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String ten = rs.getString("tennv");
                String sdt = rs.getString("sdt");
                Date ngaysinh = rs.getDate("ngaysinh");
                String quequan = rs.getString("quequan");
                ds.add(new Object[]{ten, sdt, ngaysinh, quequan});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public List<Object[]> getDanhSachNhanVienNu() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT tennv, sdt, ngaysinh, quequan FROM nhanvien WHERE gioitinh = N'Nữ'";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String ten = rs.getString("tennv");
                String sdt = rs.getString("sdt");
                Date ngaysinh = rs.getDate("ngaysinh");
                String quequan = rs.getString("quequan");
                ds.add(new Object[]{ten, sdt, ngaysinh, quequan});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    //----------------------
    //ThongKeAccount
    //----------------------
    public int getSoLuongAccountQuanLy() {
        String sql = "SELECT COUNT(*) FROM taikhoan WHERE role = N'Quản lý'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getSoLuongAccountNhanVien() {
        String sql = "SELECT COUNT(*) FROM taikhoan WHERE role = N'Nhân viên'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    // Danh sách tài khoản Quản lý
    public List<Object[]> getDanhSachAccQL() {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT nv.tennv, tk.tendangnhap, tk.matkhau, tk.role
            FROM taikhoan tk
            JOIN nhanvien nv ON tk.manv = nv.manv
            WHERE tk.role = N'Quản lý'
        """;

        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tennv = rs.getString("tennv");
                String user = rs.getString("tendangnhap");
                String pass = rs.getString("matkhau");
                String role = rs.getString("role");

                result.add(new Object[]{tennv, user, pass, role});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Danh sách tài khoản Nhân viên
    public List<Object[]> getDanhSachAccNV() {
        List<Object[]> result = new ArrayList<>();
        String sql = """
            SELECT nv.tennv, tk.tendangnhap, tk.matkhau, tk.role
            FROM taikhoan tk
            JOIN nhanvien nv ON tk.manv = nv.manv
            WHERE tk.role = N'Nhân viên'
        """;

        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String tennv = rs.getString("tennv");
                String user = rs.getString("tendangnhap");
                String pass = rs.getString("matkhau");
                String role = rs.getString("role");

                result.add(new Object[]{tennv, user, pass, role});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    //----------------------
    //ThongKeDocGia
    //----------------------
    
    public int getSoLuongDocGiaNam() {
        String sql = "SELECT COUNT(*) FROM docgia WHERE gioitinh = N'Nam'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getSoLuongDocGiaNu() {
        String sql = "SELECT COUNT(*) FROM docgia WHERE gioitinh = N'Nữ'";
        try (Connection conn = ConnectToSQLServer.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public List<Object[]> getDanhSachDocGiaNam() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT tendocgia, sdt, email, diachi FROM docgia WHERE gioitinh = N'Nam'";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String ten = rs.getString("tendocgia");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                String diachi = rs.getString("diachi");
                ds.add(new Object[]{ten, sdt, email, diachi});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public List<Object[]> getDanhSachDocGiaNu() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT tendocgia, sdt, email, diachi FROM docgia WHERE gioitinh = N'Nữ'";
        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String ten = rs.getString("tendocgia");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                String diachi = rs.getString("diachi");
                ds.add(new Object[]{ten, sdt, email, diachi});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    //----------------------
    //ThongKePhieuMuon
    //----------------------
    
    public List<Object[]> getSoPhieuMuonQuaHan() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT pm.maphieu, pm.madocgia, dg.tendocgia, pm.ngaymuon, pm.ngaytradukien, "
                   + "DATEDIFF(DAY, pm.ngaytradukien, CURRENT_TIMESTAMP) AS soNgayQuaHan "
                   + "FROM PhieuMuon pm JOIN DocGia dg ON pm.madocgia = dg.madocgia "
                   + "WHERE pm.trangthai = N'Chưa trả' AND pm.ngaytradukien < CURRENT_TIMESTAMP";

        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int maphieu = rs.getInt("maphieu");
                int madocgia = rs.getInt("madocgia");
                String tendocgia = rs.getString("tendocgia");
                LocalDateTime ngaymuon = rs.getTimestamp("ngaymuon").toLocalDateTime();
                LocalDateTime ngayTraDukien = rs.getTimestamp("ngaytradukien").toLocalDateTime();
                int soNgayQuaHan = rs.getInt("soNgayQuaHan");

                // Thêm dữ liệu vào List
                ds.add(new Object[]{tendocgia, madocgia, tendocgia, ngaymuon, ngayTraDukien, soNgayQuaHan});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    //----------------------
    //ThongKePhieuTra
    //----------------------
    
    public List<Object[]> getSoSachHongMat() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT ctpt.masach, s.tensach, COUNT(ctpt.masach) AS soluong "
                   + "FROM chitietphieutra ctpt "
                   + "JOIN sach s ON ctpt.masach = s.masach "
                   + "WHERE ctpt.tinhtrang = N'Hư hỏng/Mất' "
                   + "GROUP BY ctpt.masach, s.tensach";

        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String masach = rs.getString("masach");
                    String tensach = rs.getString("tensach");
                    int soluong = rs.getInt("soluong");

                    // Thêm dữ liệu vào danh sách
                    ds.add(new Object[]{masach, tensach, soluong});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception, e.g., log it or show an error message to the user
        }
        return ds;
    }
    
    public List<Object[]> getSoSachHongMatChiTiet() {
        List<Object[]> ds = new ArrayList<>();
        String sql = "SELECT pt.maphieutra, pt.maphieu, dg.tendocgia, pm.ngaymuon, pt.ngaytrathucte, ctpt.masach, s.tensach " +
                     "FROM phieutra pt " +
                     "JOIN phieumuon pm ON pt.maphieu = pm.maphieu " +
                     "JOIN docgia dg ON pm.madocgia = dg.madocgia " +
                     "JOIN chitietphieutra ctpt ON pt.maphieutra = ctpt.maphieutra " +
                     "JOIN sach s ON ctpt.masach = s.masach " +
                     "WHERE ctpt.tinhtrang = N'Hư hỏng/Mất'";

        try (Connection conn = ConnectToSQLServer.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maphieutra = String.valueOf(rs.getInt("maphieutra"));
                String maphieu = String.valueOf(rs.getInt("maphieu"));
                String tendocgia = rs.getString("tendocgia");
                LocalDateTime ngaymuon = null, ngaytrathucte = null;
                Timestamp t1 = rs.getTimestamp("ngaymuon");
                if (t1 != null) ngaymuon = t1.toLocalDateTime();
                Timestamp t2 = rs.getTimestamp("ngaytrathucte");
                if (t2 != null) ngaytrathucte = t2.toLocalDateTime();
                String masach = rs.getString("masach");
                String tensach = rs.getString("tensach");
                ds.add(new Object[]{maphieutra, maphieu, tendocgia, ngaymuon, ngaytrathucte, masach, tensach});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }
}
