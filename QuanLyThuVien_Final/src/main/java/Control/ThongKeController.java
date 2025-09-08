package Control;

import Model.DAO.PhongHocDAO;
import Model.DAO.ThongKeDAO;
import Model.Entity.PhongHoc;
import View.ChucNang.FrameThongKeAccount;
import View.ChucNang.FrameThongKeDocGia;
import View.ChucNang.FrameThongKeMuonPhong;
import View.ChucNang.FrameThongKeNXB;
import View.ChucNang.FrameThongKeNhanVien;
import View.ChucNang.FrameThongKePhieuMuon;
import View.ChucNang.FrameThongKePhieuTra;
import View.ChucNang.FrameThongKeSach;
import View.ChucNang.FrameThongKeSuKien;
import View.ChucNang.FrameThongKeTacGia;
import View.ChucNang.FrameThongKeTheLoai;
import View.ChucNang.PanelThongKe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class ThongKeController {
    private final ThongKeDAO thongKeDAO;
    private PhongHocDAO phongHocDAO;
    private PanelThongKe viewThongKe = null;
    private FrameThongKeSach viewThongKeSach = null;
    private FrameThongKeTheLoai viewThongKeTL = null;
    private FrameThongKeNXB viewThongKeNXB = null;
    private FrameThongKeTacGia viewThongKeTG = null;
    private FrameThongKeSuKien viewThongKeSK = null;
    private FrameThongKeMuonPhong viewThongKeMP = null;
    private FrameThongKeNhanVien viewThongKeNV = null;
    private FrameThongKeAccount viewThongKeAcc = null;
    private FrameThongKeDocGia viewThongKeDG = null;
    private FrameThongKePhieuMuon viewThongKePM = null;
    private FrameThongKePhieuTra viewThongKePT = null;

    // Constructor cho PanelThongKe (dashboard tổng quát)
    public ThongKeController(PanelThongKe view) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKe = view;
        loadData();
        Timer timer = new Timer(700, e -> loadData());
        timer.start();
        initThongKeHandlers();
    }
    // Constructor cho FrameThongKeSach (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeSach viewSach) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeSach = viewSach;
        loadDataSach();
    }    
    // Constructor cho FrameThongKeTL (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeTheLoai viewTL) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeTL = viewTL;
        loadDataTL();
    }
    // Constructor cho FrameThongKeNXB (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeNXB viewNXB) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeNXB = viewNXB;
        loadDataNXB();
    }
    // Constructor cho FrameThongKeTacGia (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeTacGia viewTG) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeTG = viewTG;
        loadDataTG();
    }
    // Constructor cho FrameThongKeSK (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeSuKien viewSK) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeSK = viewSK;
        loadDataSK();
    }
    // Constructor cho FrameThongKeMP (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeMuonPhong viewMP) {
        this.thongKeDAO = new ThongKeDAO();
        this.phongHocDAO = new PhongHocDAO();
        this.viewThongKeMP = viewMP;
        loadDataMP();
    }
    // Constructor cho FrameThongKeNhanVien (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeNhanVien viewNV) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeNV = viewNV;
        loadDataNV();
    }
    // Constructor cho FrameThongKeAccount (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeAccount viewAcc) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeAcc = viewAcc;
        loadDataAcc();
    }
    // Constructor cho FrameThongKeDocGia (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKeDocGia viewDG) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKeDG = viewDG;
        loadDataDG();
    }
    // Constructor cho FrameThongKePhieuMuon (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKePhieuMuon viewPM) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKePM = viewPM;
        loadDataPM();
    }
    // Constructor cho FrameThongKePhieuTra (thống kê sách dạng bảng)
    public ThongKeController(FrameThongKePhieuTra viewPT) {
        this.thongKeDAO = new ThongKeDAO();
        this.viewThongKePT = viewPT;
        loadDataPT();
    }

    // ------------------------------------------
    // Dành cho PanelThongKe (Tổng quát)
    // ------------------------------------------
    public void initThongKeHandlers() {
        viewThongKe.getBtnSach().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeSach frame = new FrameThongKeSach();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnTheLoai().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeTheLoai frame = new FrameThongKeTheLoai();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnNXB().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeNXB frame = new FrameThongKeNXB();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnTacGia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeTacGia frame = new FrameThongKeTacGia();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnSuKien().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeSuKien frame = new FrameThongKeSuKien();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnMuonPhong().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeMuonPhong frame = new FrameThongKeMuonPhong();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnNhanVien().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeNhanVien frame = new FrameThongKeNhanVien();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnAccount().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeAccount frame = new FrameThongKeAccount();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnDocGia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKeDocGia frame = new FrameThongKeDocGia();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnPhieuMuon().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKePhieuMuon frame = new FrameThongKePhieuMuon();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        viewThongKe.getBtnPhieuTra().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameThongKePhieuTra frame = new FrameThongKePhieuTra();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
    
    private void loadData() {
        try {
            int soLuongSach = thongKeDAO.getDauSach();
            int soLuongNXB = thongKeDAO.getSoLuongNXB();
            int soLuongTG = thongKeDAO.getSoLuongTG();
            int soLuongSK = thongKeDAO.getSoLuongSK();
            int soLuongDG = thongKeDAO.getSoLuongDocGia();
            int soLuongTL = thongKeDAO.getSoLuongTheLoai();
            int soLuongMP = thongKeDAO.getSoLuongMuonPhong();
            int soLuongPM = thongKeDAO.getSoLuongPhieuMuon();
            int soLuongPT = thongKeDAO.getSoLuongPhieuTra();
            int soLuongNV = thongKeDAO.getSoLuongNhanVien();
            int soLuongTK = thongKeDAO.getSoLuongTaiKhoan();
            int tongPhiPhat = thongKeDAO.getTongPhiPhat();

            viewThongKe.getLblDauSach().setText(String.valueOf(soLuongSach));
            viewThongKe.getLblNXB().setText(String.valueOf(soLuongNXB));
            viewThongKe.getLblTacGia().setText(String.valueOf(soLuongTG));
            viewThongKe.getLblEvent().setText(String.valueOf(soLuongSK));
            viewThongKe.getLblDocGia().setText(String.valueOf(soLuongDG));
            viewThongKe.getLblTheLoai().setText(String.valueOf(soLuongTL));
            viewThongKe.getLblPhieuPhong().setText(String.valueOf(soLuongMP));
            viewThongKe.getLblPhieuMuon().setText(String.valueOf(soLuongPM));
            viewThongKe.getLblPhieuTra().setText(String.valueOf(soLuongPT));
            viewThongKe.getLblNhanVien().setText(String.valueOf(soLuongNV));
            viewThongKe.getLblTaiKhoan().setText(String.valueOf(soLuongTK));
            viewThongKe.getLblPhiPhat().setText(String.valueOf(tongPhiPhat));
        } catch (Exception e) {
            // Nếu lỗi, hiển thị lên tất cả các label
            String msg = "Lỗi CSDL: " + e.getMessage();
            viewThongKe.getLblDauSach().setText(msg);
            viewThongKe.getLblNXB().setText(msg);
            viewThongKe.getLblTacGia().setText(msg);
            viewThongKe.getLblEvent().setText(msg);
            viewThongKe.getLblDocGia().setText(msg);
            viewThongKe.getLblTheLoai().setText(msg);
            viewThongKe.getLblPhieuPhong().setText(msg);
            viewThongKe.getLblPhieuMuon().setText(msg);
            viewThongKe.getLblPhieuTra().setText(msg);
            viewThongKe.getLblNhanVien().setText(msg);
            viewThongKe.getLblTaiKhoan().setText(msg);
            viewThongKe.getLblPhiPhat().setText(msg);
        }
    }

    // ------------------------------------------
    // Dành cho FrameThongKeSach
    // ------------------------------------------
    
    private void loadDataSach() {
        int dauSach = thongKeDAO.getDauSach();
        int soLuongSach = thongKeDAO.getSoLuongSach();
        int sachDangMuon = thongKeDAO.getTongSachDangMuon();
        int sachConLai = thongKeDAO.getTongSachConLai();
        String mostBorrow = thongKeDAO.getMostBorrow();
        
        viewThongKeSach.getLblDauSach().setText(String.valueOf(dauSach));
        viewThongKeSach.getLblSoLuongSach().setText(String.valueOf(soLuongSach));
        viewThongKeSach.getLblDangMuon().setText(String.valueOf(sachDangMuon));
        viewThongKeSach.getLblSachConLai().setText(String.valueOf(sachConLai));
        viewThongKeSach.getLblMostBorrow().setText(String.valueOf(mostBorrow));
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeTL
    // ------------------------------------------
    
    private void loadDataTL() {
        int soLuongTL = thongKeDAO.getSoLuongTheLoai();
            
        viewThongKeTL.getLblTongTheLoai().setText(String.valueOf(soLuongTL));
    }
    
    public void hienThiThongKeTheoTheLoaiBang() {
        List<Object[]> data = thongKeDAO.thongKeSachTheoTheLoai();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Thể loại", "Tên sách", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeTL.getTblSachTheLoai().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void hienThiThongKeSLSachTheoTheLoai() {
        List<Object[]> data = thongKeDAO.thongKeSLSachTheoTheLoai();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Thể loại", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeTL.getTblSLSachTheLoai().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeNXB
    // ------------------------------------------
    private void loadDataNXB(){
        int soLuongNXB = thongKeDAO.getSoLuongNXB();
//        int soNXBTrongNuoc = ThongKeDAO.countNXBTrongNuoc();
//        int soNXBNgoaiNuoc = ThongKeDAO.countNXBNgoaiNuoc();
        
        viewThongKeNXB.getLblTongNXB().setText(String.valueOf(soLuongNXB));
//        viewThongKeNXB.getLblNXBVN().setText(String.valueOf(soNXBTrongNuoc));
//        viewThongKeNXB.getLblNXBNG().setText(String.valueOf(soNXBNgoaiNuoc));
    }
    
    public void hienThiThongKeTheoNXB() {
        List<Object[]> data = thongKeDAO.thongKeSachTheoNXB();
        DefaultTableModel model = new DefaultTableModel(new String[]{"NXB", "Tên sách", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeNXB.getTblSachNXB().setModel(model);  // Gắn dữ liệu vào bảng
    }
    public void hienThiThongKeSLSachTheoNXB() {
        List<Object[]> data = thongKeDAO.thongKeSLSachTheoNXB();
        DefaultTableModel model = new DefaultTableModel(new String[]{"NXB", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeNXB.getTblSLSachNXB().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeTacGia
    // ------------------------------------------
    private void loadDataTG(){
        int soLuongTG = thongKeDAO.getSoLuongTG();
        int soTGVietNam = ThongKeDAO.countTGVietNam();
        int soTGNuocNgoai = ThongKeDAO.countTGNuocNgoai();
        
        viewThongKeTG.getLblTongTacGia().setText(String.valueOf(soLuongTG));
        viewThongKeTG.getLblTongTacGiaVN().setText(String.valueOf(soTGVietNam));
        viewThongKeTG.getLblTongTacGiaNG().setText(String.valueOf(soTGNuocNgoai));
    }
    
    public void hienThiThongKeTheoTacGia() {
        List<Object[]> data = thongKeDAO.thongKeSachTheoTacGia();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tác giả", "Tên sách", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeTG.getTblTacGiaSach().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void hienThiThongKeDauSachTheoTacGia() {
        List<Object[]> data = thongKeDAO.thongKeDauSachTheoTacGia();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tác giả", "Số lượng đầu sách"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeTG.getTblTacGiaDauSach().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeTacGia
    // ------------------------------------------
    private void loadDataSK(){
        int soLuongSK = thongKeDAO.getSoLuongSK();
        List<String> presentEvents = thongKeDAO.getPresentEventNames();
        String skPresents = String.join(", ", presentEvents); // Chuỗi tên cách nhau bằng dấu phẩy
        int countSkPresents = thongKeDAO.getPresentEvents();
        int skPass = thongKeDAO.getPassEvents();
        int skComing = thongKeDAO.getComingEvents();
        
        viewThongKeSK.getLblTongSuKien().setText(String.valueOf(soLuongSK));
        viewThongKeSK.getLblSuKienPresentsName().setText(String.valueOf(skPresents));
        viewThongKeSK.getLblSuKienPresents().setText(String.valueOf(countSkPresents));
        viewThongKeSK.getLblSuKienPass().setText(String.valueOf(skPass));
        viewThongKeSK.getLblSuKienComing().setText(String.valueOf(skComing));
    }
    
    public void thongKeSuKienTheoNXB() {
        List<Object[]> data = thongKeDAO.thongKeSoLuongSuKienTheoNXB();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Nhà tài trợ", "Số lượng sự kiện"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeSK.getTblNXBEvents().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeMuonPhong
    // ------------------------------------------
    private void loadDataMP(){
        int soLuongMP = thongKeDAO.getSoLuongMuonPhong();
        int soPhongHoc = thongKeDAO.getSoLuongPhongHoc();
        int soPhieuDangMuon = thongKeDAO.getSoPhieuMuonPhongDangMuon();
        int soPhieuDaTra = thongKeDAO.getSoPhieuMuonPhongDaTra();
        int roomAvailable = thongKeDAO.getSoPhongConDungDuoc();
        int roomUnavailable = thongKeDAO.getSoPhongDangMuon();
        
        viewThongKeMP.getLblPhieuMuonPhong().setText(String.valueOf(soLuongMP));
        viewThongKeMP.getLblPhongHoc().setText(String.valueOf(soPhongHoc));
        viewThongKeMP.getLblDangMuon().setText(String.valueOf(soPhieuDangMuon));
        viewThongKeMP.getLblDaTra().setText(String.valueOf(soPhieuDaTra));
        viewThongKeMP.getLblRoomAvailable().setText(String.valueOf(roomAvailable));
        viewThongKeMP.getLblRoomUnavailable().setText(String.valueOf(roomUnavailable));
    }
    
    public void hienThiThongKePhongHoc() {
        try {
            List<PhongHoc> data = phongHocDAO.getAllPhongHoc();
            DefaultTableModel model = new DefaultTableModel(new String[]{"Mã phòng", "Tên phòng", "Mô tả"}, 0);

            for (PhongHoc row : data) {
                model.addRow(new Object[]{
                    row.getMaphong(),
                    row.getTenphong(),
                    row.getMota()
                });
            }

            viewThongKeMP.getTblPhongHoc().setModel(model); // Cập nhật dữ liệu bảng
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phòng học: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void hienThiPhieuDangMuon() {
        List<Object[]> data = thongKeDAO.getPhieuDangMuon();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên độc giả", "Tên phòng", "Thời gian mượn", "Thời gian trả"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeMP.getTblDangMuon().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void hienThiPhieuDaTra() {
        List<Object[]> data = thongKeDAO.getPhieuDaTra();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên độc giả", "Tên phòng", "Thời gian mượn", "Thời gian trả"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeMP.getTblDaTra().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeNhanVien
    // ------------------------------------------
    private void loadDataNV(){
        int soLuongNV = thongKeDAO.getSoLuongNhanVien();
        int soNVNam = thongKeDAO.getSoLuongNhanVienNam();
        int soNVNu = thongKeDAO.getSoLuongNhanVienNu();
        
        viewThongKeNV.getLblTongNhanVien().setText(String.valueOf(soLuongNV));
        viewThongKeNV.getLblTongNhanVienNam().setText(String.valueOf(soNVNam));
        viewThongKeNV.getLblTongNhanVienNu().setText(String.valueOf(soNVNu));
    }
    
    public void hienThiNhanVienNam(){
        List<Object[]> data = thongKeDAO.getDanhSachNhanVienNam();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên nhân viên", "Số điện thoại", "Ngày sinh", "Quê quán"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeNV.getTblNhanVienNam().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void hienThiNhanVienNu(){
        List<Object[]> data = thongKeDAO.getDanhSachNhanVienNu();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên nhân viên", "Số điện thoại", "Ngày sinh", "Quê quán"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeNV.getTblNhanVienNu().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeAccount
    // ------------------------------------------
    private void loadDataAcc(){
        int soLuongAcc = thongKeDAO.getSoLuongTaiKhoan();
        int soAccQuanLy = thongKeDAO.getSoLuongAccountQuanLy();
        int soAccNhanVien = thongKeDAO.getSoLuongAccountNhanVien();
        
        viewThongKeAcc.getLblAcc().setText(String.valueOf(soLuongAcc));
        viewThongKeAcc.getLblAccQuanLy().setText(String.valueOf(soAccQuanLy));
        viewThongKeAcc.getLblAccNhanVien().setText(String.valueOf(soAccNhanVien));
    }
    
    public void hienThiAccQuanLy(){
        List<Object[]> data = thongKeDAO.getDanhSachAccQL();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên nhân viên", "Username", "Password", "Chức vụ"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeAcc.getTblQuanLy().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void hienThiAccNhanVien(){
        List<Object[]> data = thongKeDAO.getDanhSachAccNV();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên nhân viên", "Username", "Password", "Chức vụ"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeAcc.getTblNhanVien().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKeDocGia
    // ------------------------------------------
    private void loadDataDG(){
        int soLuongNV = thongKeDAO.getSoLuongNhanVien();
        int soDGNam = thongKeDAO.getSoLuongDocGiaNam();
        int soDGNu = thongKeDAO.getSoLuongDocGiaNu();
        
        viewThongKeDG.getLblTongDocGia().setText(String.valueOf(soLuongNV));
        viewThongKeDG.getLblDocGiaNam().setText(String.valueOf(soDGNam));
        viewThongKeDG.getLblDocGiaNu().setText(String.valueOf(soDGNu));
    }
    
    public void hienThiDocGiaNam(){
        List<Object[]> data = thongKeDAO.getDanhSachDocGiaNam();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên độc giả", "Số điện thoại", "Email", "Địa chỉ"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeDG.getTblDGNam().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void hienThiDocGiaNu(){
        List<Object[]> data = thongKeDAO.getDanhSachDocGiaNu();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tên độc giả", "Số điện thoại", "Email", "Địa chỉ"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKeDG.getTblDGNu().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKePhieuMuon
    // ------------------------------------------
    private void loadDataPM(){
        int soLuongPM = thongKeDAO.getSoLuongPhieuMuon();
  
        viewThongKePM.getLblTongPhieuMuon().setText(String.valueOf(soLuongPM));
    }
    
    public void getSoPhieuMuonQuaHan(){
        List<Object[]> data = thongKeDAO.getSoPhieuMuonQuaHan();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã phiếu", "Mã độc giả", "Tên độc giả", "Ngày mượn", "Ngày trả dự kiến", "Số ngày quá hạn"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKePM.getTblPhieuMuon().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    // ------------------------------------------
    // Dành cho FrameThongKePhieuTra
    // ------------------------------------------
    
    private void loadDataPT(){
        int soLuongPT = thongKeDAO.getSoLuongPhieuTra();
  
        viewThongKePT.getLblTongPhieuTra().setText(String.valueOf(soLuongPT));
    }
    
    public void getSoSachHongMat(){
        List<Object[]> data = thongKeDAO.getSoSachHongMat();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã sách", "Tên sách", "Số lượng"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKePT.getTblSachTra().setModel(model);  // Gắn dữ liệu vào bảng
    }
    
    public void getSoSachHongMatChiTiet(){
        List<Object[]> data = thongKeDAO.getSoSachHongMatChiTiet();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã phiếu trả", "Mã phiếu", "Tên độc giả", "Ngày mượn", "Ngày trả thực tế", "Mã sách", "Tên sách"}, 0);

        for (Object[] row : data) {
            model.addRow(row);
        }

        viewThongKePT.getTblSachTraChiTiet().setModel(model);  // Gắn dữ liệu vào bảng
    }
}
