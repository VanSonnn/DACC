package Phone;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class LoginKH extends JFrame implements Runnable{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField tftensignin;
    private ServerSocket serversocket;
    private Socket socket;
    private PrintWriter Output;
    private JPasswordField tfpasssignin;
    private JTextField tftensignup;
    private JPasswordField tfpasssingup1;
    private JPasswordField tfpasssignup2;
    private JButton btnNewButton;
    private JButton btnNewButton_2_1;
    private JTextField tfemail;
    private JTextField textField; 
    private ServerSocket serverSocket;
    private DataOutputStream output;
    private DataInputStream input;
    private DefaultListModel<String> model;
    private boolean isClientStarted = false;
    

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginKH frame = new LoginKH();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public LoginKH() {
    	model = new DefaultListModel<>();
        setTitle("Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(100, 100, 761, 477);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel_signin = new JPanel();
        panel_signin.setBackground(SystemColor.window);
        panel_signin.setBounds(276, 0, 473, 442);
        contentPane.add(panel_signin);
        panel_signin.setLayout(null);
   
       

        tftensignin = new CustomTextField("Tên đăng nhập");
        tftensignin.setBounds(102, 147, 267, 30);
        panel_signin.add(tftensignin);
        
        JLabel lblNewLabel_2 = new JLabel("Sign in");
        lblNewLabel_2.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if(!isClientStarted) {
         	       startServer();
         	       isClientStarted=true;
         		}        	
        	}
        });
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblNewLabel_2.setForeground(new Color(46, 139, 87));
        lblNewLabel_2.setBounds(195, 79, 115, 41);
        panel_signin.add(lblNewLabel_2);
        
        tfpasssignin = new PlaceholderPasswordField("Mật khẩu");
        tfpasssignin.setBounds(102, 188, 267, 30);
        panel_signin.add(tfpasssignin);
        
      
       
        
        JButton btsignin = new JButton("Sign in");
        btsignin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 String username = tftensignin.getText(); 
        	     String pass = tfpasssignin.getText();
        	     ResultSet rs = registerUser(username, pass);
        	     try {
        	            if (rs.next()) {
        	                Dash dash = new Dash();
        	                dash.setVisible(true);
        	                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(btsignin);
        	                topFrame.dispose();
        	            } else {
        	             JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu.");
        	            }
        	            rs.close(); // Đóng ResultSet sau khi sử dụng
        	        } catch (SQLException ex) {
        	            ex.printStackTrace();
        	        }
        	}
        });
        btsignin.setBackground(new Color(46, 139, 87));
        btsignin.setForeground(new Color(255, 255, 255));
        btsignin.setBounds(184, 268, 108, 41);
        panel_signin.add(btsignin);
        
       

        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(46, 139, 87));
        panel.setBounds(0, 0, 277, 442);
        contentPane.add(panel);
        panel.setLayout(null);
        
        JLabel lblWelcome = new JLabel("Welcome Store!");
        lblWelcome.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblWelcome.setForeground(new Color(255, 255, 255));
        lblWelcome.setBounds(54, 159, 169, 48);
        panel.add(lblWelcome);
        
        JPanel panel_signup = new JPanel();
        panel_signup.setBackground(new Color(255, 255, 255));
        panel_signup.setBounds(279, 0, 470, 442);
        panel_signup.setVisible(false);  // Ban đầu đặt nó là vô hình
        contentPane.add(panel_signup);
        panel_signup.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Create Account");
        lblNewLabel.setForeground(new Color(46, 139, 87));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblNewLabel.setBounds(162, 65, 210, 40);
        panel_signup.add(lblNewLabel);
        
        tftensignup = new CustomTextField("Tên đăng nhập");
        tftensignup.setBounds(105, 127, 267, 30);
        panel_signup.add(tftensignup);
        
        tfpasssingup1 = new PlaceholderPasswordField("Mật khẩu");
        tfpasssingup1.setBounds(105, 185, 267, 30);
        panel_signup.add(tfpasssingup1);
        
        
        tfemail = new CustomTextField("Email");
        tfemail.setBounds(105, 250, 267, 30);
        
        panel_signup.add(tfemail);
        tfemail.setColumns(10);
        JButton btsignup = new JButton("Sign up");
        btsignup.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {      		
        		String username = tftensignup.getText();
   		        String pass = tfpasssingup1.getText(); 
   		        String gmail = tfemail.getText();	        		        
   		        registerUser(username, gmail,pass); 
   		     JOptionPane.showMessageDialog(null, "Đăng ký thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        	}
        });
        btsignup.setForeground(new Color(255, 255, 255));
        btsignup.setBackground(new Color(46, 139, 87));
        btsignup.setFont(new Font("Tahoma", Font.BOLD, 15));
        btsignup.setBounds(189, 345, 106, 40);
        panel_signup.add(btsignup);
        
        
		
        btnNewButton = new JButton("Sign up");
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel_signup.setVisible(true);
                panel_signin.setVisible(false);
                btnNewButton.setVisible(false);
                btnNewButton_2_1.setVisible(true);
            }
        });
        btnNewButton.setForeground(new Color(255, 255, 255));
        btnNewButton.setBackground(new Color(46, 139, 87));
        btnNewButton.setBounds(76, 241, 110, 28);
        // Tạo một viền màu trắng
        btnNewButton.setBorder(new LineBorder(Color.WHITE));
        panel.add(btnNewButton);
        
        btnNewButton_2_1 = new JButton("Sign in");
        btnNewButton_2_1.setBounds(76, 241, 110, 28);
        panel.add(btnNewButton_2_1);
        btnNewButton_2_1.setForeground(new Color(255, 255, 255));
        btnNewButton_2_1.setBackground(new Color(46, 139, 87));
        btnNewButton_2_1.setVisible(false);
        btnNewButton_2_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		panel_signup.setVisible(false);
        		panel_signin.setVisible(true);
        		btnNewButton_2_1.setVisible(false);
        		btnNewButton.setVisible(true);
        	}
        });
        btnNewButton_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnNewButton_2_1.setBorder(new LineBorder(Color.WHITE));
        panel.add(btnNewButton);
        

        
    }

 
    public class CustomTextField extends JTextField implements FocusListener {
        private String placeholder;

        public CustomTextField(String placeholder) {
            this.placeholder = placeholder;
            setFont(new Font("Tahoma", Font.PLAIN, 14));
            setForeground(Color.GRAY);
            setText(placeholder);
            addFocusListener(this);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (getText().equals(placeholder)) {
                setText("");
                setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (getText().isEmpty()) {
                setText(placeholder);
                setForeground(Color.GRAY);
            }
        }
    }
    public class PlaceholderPasswordField extends JPasswordField implements FocusListener {
        private String placeholder;

        public PlaceholderPasswordField(String placeholder) {
            this.placeholder = placeholder;
            setFont(new Font("Tahoma", Font.PLAIN, 14));
            setForeground(Color.GRAY);
            setText(placeholder);
            setEchoChar((char)0); // Hiện chữ mờ
            addFocusListener(this);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (getText().equals(placeholder)) {
                setText("");
                setForeground(Color.BLACK);
                setEchoChar('*'); // Khi focus, hiện ký tự thực
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (getText().isEmpty()) {
                setText(placeholder);
                setForeground(Color.GRAY);
                setEchoChar((char)0); // Khi mất focus, hiện chữ mờ
            }
        }
    }
    public class PlaceholderTextField extends JTextField implements FocusListener {
        private String placeholder;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            addFocusListener(this);
            setText(placeholder);
            setForeground(Color.GRAY);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (getText().equals(placeholder)) {
                setText("");
                setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (getText().isEmpty()) {
                setText(placeholder);
                setForeground(Color.GRAY);
            }
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            if (!hasFocus()) {
                setText(placeholder);
                setForeground(Color.GRAY);
            }
        }
    }
    public class PlaceholderPasswordField2 extends JPasswordField implements FocusListener {
        private String placeholder;
        private boolean showingPlaceholder;

        public PlaceholderPasswordField2(String placeholder) {
            this.placeholder = placeholder;
            this.showingPlaceholder = true;
            addFocusListener(this);
            setEchoChar((char) 0); // Show the placeholder text
            setText(placeholder);
            setForeground(Color.GRAY);
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (showingPlaceholder) {
                setText("");
                setEchoChar('*'); // Restore the echo character for password
                setForeground(Color.BLACK);
                showingPlaceholder = false;
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (getPassword().length == 0) {
                setEchoChar((char) 0); // Show the placeholder text
                setText(placeholder);
                setForeground(Color.GRAY);
                showingPlaceholder = true;
            }
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            if (showingPlaceholder) {
                setText(placeholder);
                setForeground(Color.GRAY);
            }
        }
    }
    
    //Sign in Server///////////////////
    private ResultSet registerUser(String username, String pass) {
	    ResultSet rs = null;
	    try {
	        Connection conn = Dtb.getConnection();
	        String hashedPass = hashPassword(pass);
	        String sql = "SELECT * FROM SigninKH WHERE USERNAME=? AND PASS=?";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, username);
	        statement.setString(2, hashedPass);
	        rs = statement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rs;
	}
    //Sign up Server////////////////////
    private void registerUser(String username, String gmail, String pass) {
	    try {
	        Connection conn = Dtb.getConnection();
	        String hashedPass = hashPassword(pass);
	        String sql = "INSERT INTO SigninKH (username, gmail, pass ) VALUES (?, ?, ? )";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, username);
	        statement.setString(2, gmail);
	        statement.setString(3, hashedPass);        
	        statement.executeUpdate();
	        conn.close();
	        // Xử lý sau khi đăng ký thành công
	    } catch (SQLException e) {
	        // Xử lý khi có lỗi SQL
	        e.printStackTrace();
	    }
	}
    //Sign up Client////////////////////
    private void registerUser2(String username, String gmail, String pass) {
	    try {
	        Connection conn = Dtb.getConnection();   
	        String sql = "INSERT INTO SigninKH (username, gmail, pass ) VALUES (?, ?, ? )";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, username);
	        statement.setString(2, gmail);
	        statement.setString(3, pass);        
	        statement.executeUpdate();
	        conn.close();
	        // Xử lý sau khi đăng ký thành công
	    } catch (SQLException e) {
	        // Xử lý khi có lỗi SQL
	        e.printStackTrace();
	    }
	}
    class PlaceholderTextFieldemail extends JTextField {
        private String placeholder;

        public PlaceholderTextFieldemail(String placeholder) {
            this.placeholder = placeholder;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Nếu không có văn bản được nhập và textfield không có focus, vẽ placeholder
            if (getText().isEmpty() && ! (FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                g2.dispose();
            }
        }
    }
    //Kết nối Socket//////////////
    private void startServer() {
        try {           
            serverSocket = new ServerSocket(1234);
            socket = serverSocket.accept();
            Thread t = new Thread(LoginKH.this);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Đa luồng để nhận phản hồi từ Client////
    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            while (true) {
                if (socket != null) {
                    String data = input.readUTF();
                    String[] parts = data.split(",");
                    
                    if (parts.length == 3) { // Dữ liệu đăng ký
                        String username = parts[0];
                        String email = parts[1];
                        String password = parts[2];
                        registerUser2(username, email, password);     
                    } else if (parts.length == 2) { // Dữ liệu đăng nhập
                        String username2 = parts[0];
                        String password2 = parts[1];
                        boolean loginSuccess = checkCredentials(username2, password2);
                        sendLoginResult(loginSuccess);              
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Kiểm tra User và Pass từ Client gửi qua có tồn tại không để đăng nhập//
    private boolean checkCredentials(String username, String password) {
        boolean isValid = false;
        try {
            Connection conn = Dtb.getConnection();
            String sql = "SELECT * FROM SigninKH WHERE USERNAME=? AND PASS=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }
    //Gửi kết quả đăng nhập qua Client//
    private void sendLoginResult(boolean loginSuccess) {
        try {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            // Ghi kết quả đăng nhập vào DataOutputStream
            if (loginSuccess) {
                output.writeUTF("Được phép đăng nhập");
            } else {
                output.writeUTF("Không được phép đăng nhập");
            }
            output.flush(); // Đảm bảo dữ liệu được gửi đi ngay lập tức
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Mã hóa/////////////////////
    private String hashPassword(String password) {
        try {
            // Tạo một đối tượng MessageDigest với thuật toán MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Tính toán giá trị băm của mật khẩu
            byte[] messageDigest = md.digest(password.getBytes());

            // Chuyển đổi mảng byte thành dạng số nguyên không âm (BigInteger)
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Xử lý khi thuật toán băm không khả dụng
            e.printStackTrace();
            return null;
        }
    }
  }
