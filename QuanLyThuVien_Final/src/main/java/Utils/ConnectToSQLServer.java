package Utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectToSQLServer {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=QLyThuVien;encrypt=true;trustServerCertificate=true");
        config.setUsername("sa");
        config.setPassword("123456");
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        config.setMaximumPoolSize(10); // Số kết nối tối đa trong pool
        config.setMinimumIdle(2); // Số kết nối tối thiểu
        config.setConnectionTimeout(30000); // Thời gian chờ kết nối (ms)
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
