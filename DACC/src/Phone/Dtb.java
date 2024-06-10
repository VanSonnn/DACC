package Phone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Dtb {
    public static void main(String[] args) {
        try {
            // Lấy kết nối đến CSDL
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("Kết nối thành công đến CSDL.");
                
                          
                conn.close(); // Đóng kết nối sau khi sử dụng
            } else {
                System.out.println("Không thể kết nối đến CSDL.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi kết nối đến CSDL: " + e.getMessage());
        }
    }

    // Thay đổi các thông số kết nối tùy thuộc vào cơ sở dữ liệu của bạn
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Dtb";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345678";

    // Phương thức để kết nối CSDL
    public static Connection getConnection() throws SQLException {
        try {
            // Đăng ký JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Trả về kết nối
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Xử lý khi không tìm thấy driver
            e.printStackTrace();
            throw new SQLException("Database driver not found.");
        }
    }
    
    // Phương thức để thêm người dùng vào bảng account
   
}
