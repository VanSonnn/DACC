package Phone;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Phone.LoginKH.CustomTextField;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import java.awt.Color;
import javax.swing.ImageIcon;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tftdn;
	private JPasswordField tfmk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Đăng nhập");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 437, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(0, 2, 425, 423);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton login = new JButton("Login");
		login.setFont(new Font("Tahoma", Font.BOLD, 15));
		login.setForeground(new Color(255, 255, 255));
		login.setBackground(new Color(0, 206, 209));
		login.setBounds(138, 272, 129, 34);
		panel_1.add(login);
		
		tftdn = new HintTextField("Tên đăng nhập");
		tftdn.setBounds(85, 158, 247, 28);
		panel_1.add(tftdn);
		
		 tfmk = new HintPasswordField("Mật khẩu");
		tfmk.setBounds(85, 204, 246, 28);
		panel_1.add(tfmk);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(new Color(138, 43, 226));
		lblNewLabel.setForeground(new Color(255, 20, 147));

		// Load the original image
		ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/chandungreal1.png"));
		Image originalImage = originalIcon.getImage();

		// Resize the image
		int width = 120; // Kích thước mới cho chiều rộng
		int height = 100; // Kích thước mới cho chiều cao
		Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// Tạo ImageIcon từ hình ảnh đã điều chỉnh kích thước
		ImageIcon resizedIcon = new ImageIcon(resizedImage);

		lblNewLabel.setIcon(resizedIcon);
		lblNewLabel.setBounds(146, 0, 115, 100); // Đặt kích thước mới cho JLabel
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("LOGIN MANAGER");
		lblNewLabel_1.setForeground(new Color(169, 169, 169));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1.setBounds(111, 109, 198, 23);
		panel_1.add(lblNewLabel_1);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
               
             String username = tftdn.getText(); 
       	     String pass = tfmk.getText();
       	     ResultSet rs = registerUser(username, pass);
       	     try {
       	            if (rs.next()) {
       	                TrangChu trangchu = new TrangChu();
       	                trangchu.setVisible(true);
       	             JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(panel_1);
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
	}
	
	
	class HintTextField extends JTextField implements FocusListener {

	    private String hint;

	    public HintTextField(String hint) {
	        this.hint = hint;
	        this.setForeground(Color.GRAY);
	        this.setText(hint);
	        this.addFocusListener(this);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (getText().isEmpty() && !hasFocus()) {
	            g.setColor(Color.GRAY);
	            g.setFont(getFont().deriveFont(Font.ITALIC));
	            int padding = (getHeight() - g.getFontMetrics().getHeight()) / 2;
	            g.drawString(hint, getInsets().left, getHeight() - padding - 1);
	        }
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
	        if (getText().equals(hint)) {
	            setText("");
	            setForeground(Color.BLACK);
	        }
	    }

	    @Override
	    public void focusLost(FocusEvent e) {
	        if (getText().isEmpty()) {
	            setText(hint);
	            setForeground(Color.GRAY);
	        }
	    }
	}
	class HintPasswordField extends JPasswordField implements FocusListener {

	    private String hint;

	    public HintPasswordField(String hint) {
	        this.hint = hint;
	        this.setForeground(Color.GRAY);
	        this.setEchoChar((char)0); // Hiển thị text trong khi không focus
	        this.setText(hint);
	        this.addFocusListener(this);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (getText().isEmpty() && !hasFocus()) {
	            g.setColor(Color.GRAY);
	            g.setFont(getFont().deriveFont(Font.ITALIC));
	            int padding = (getHeight() - g.getFontMetrics().getHeight()) / 2;
	            g.drawString(hint, getInsets().left, getHeight() - padding - 1);
	        }
	    }

	    @Override
	    public void focusGained(FocusEvent e) {
	        if (getText().equals(hint)) {
	            setText("");
	            setForeground(Color.BLACK);
	            setEchoChar('*'); // Khi focus, hiển thị ký tự * cho mật khẩu
	        }
	    }

	    @Override
	    public void focusLost(FocusEvent e) {
	        if (getText().isEmpty()) {
	            setText(hint);
	            setForeground(Color.GRAY);
	            setEchoChar((char)0); // Khi mất focus và không có text, hiển thị text mờ
	        }
	    }
	}
	private ResultSet registerUser(String username, String pass) {
	    ResultSet rs = null;
	    try {
	        Connection conn = Dtb.getConnection();
	        String sql = "SELECT * FROM SigninQl WHERE USERNAME=? AND PASS=?";
	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, username);
	        statement.setString(2, pass);
	        rs = statement.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rs;
	}
}
