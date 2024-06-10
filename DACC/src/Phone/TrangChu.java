package Phone;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Statement;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class TrangChu extends JFrame implements Runnable{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	
	private JTable table_1;
	private JTable tablenhanvien;
	private JTable tablekho;
	private JTable table;
	private ServerSocket serverSocket;
	private Socket socket;
	private DataOutputStream output;
	private DataInputStream input;
	private DefaultListModel<String> model;
	private JList<String> listHistory;
	private boolean isServerStarted = false;
	private JTextField tfhoten;
	private JTextField tfngaysinh;
	private JTextField tfluong;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TrangChu frame = new TrangChu();
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
	public TrangChu() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1432, 876);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(44,62,80));
		panel_1.setForeground(SystemColor.window);
		panel_1.setBounds(0, 0, 223, 837);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_tv = new JPanel();
		panel_tv.setBounds(222, 0, 1192, 837);
		contentPane.add(panel_tv);
		panel_tv.setLayout(null);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(41,128,185));
		panel_2.setBounds(1, 0, 1190, 85);
		panel_tv.add(panel_2);
		panel_2.setLayout(null);
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 255, 255));
		tabbedPane.setBounds(1, 84, 1190, 742);
		panel_tv.add(tabbedPane);				
		
		// Tạo JPanel mới cho ChatServer
		model = new DefaultListModel<>();
		JPanel chatPanel = new JPanel();
		chatPanel.setBackground(new Color(255, 255, 255));
		chatPanel.setLayout(null);

		listHistory = new JList<>();
		listHistory.setBackground(new Color(236,240,241));
		 listHistory.setCellRenderer(new MessageRenderer());
		listHistory.setBounds(25, 11, 1125, 594);
		chatPanel.add(listHistory);

		JTextArea txtmessage = new JTextArea();
		txtmessage.setRows(1);
		txtmessage.setBounds(25, 634, 1003, 49);
		txtmessage.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		chatPanel.add(txtmessage);
		
	
		 JButton btnSend = new JButton("Gửi");
		 btnSend.setFont(new Font("Tahoma", Font.BOLD, 15));
		 btnSend.setForeground(new Color(255, 255, 255));
		 btnSend.setBackground(new Color(41,128,185));
	        btnSend.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	String message =txtmessage.getText();
	                sendMessage(message);
	                txtmessage.setText("");
	                addMessageToHistory(message);
	               
	            }
	            });
	       
	        btnSend.setBounds(1057, 634, 93, 49);
	        chatPanel.add(btnSend);
	       
		// Thêm ChatServer vào tabbedPane
	        tabbedPane.addTab("Khách hàng", null, chatPanel, null);
	      
		
		JLabel lblNewLabel_2 = new JLabel("Customer Support");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(476, 21, 285, 42);
		panel_2.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 27));
		
		JPanel panel_kho = 	new JPanel();
		panel_kho.setBounds(222, 0, 1196, 830);
		contentPane.add(panel_kho);
		panel_kho.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(SystemColor.inactiveCaptionBorder);
		panel_6.setBounds(10, 505, 1281, 268);
		panel_kho.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("Tên điện thoại:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5.setBounds(269, 60, 123, 19);
		panel_6.add(lblNewLabel_5);
		
		JLabel lblNewLabel_11 = new JLabel("Quản lý kho");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_11.setBounds(555, 11, 209, 19);
		panel_6.add(lblNewLabel_11);
		
		JLabel lblNewLabel_5_2 = new JLabel("Số lượng: ");
		lblNewLabel_5_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5_2.setBounds(731, 60, 123, 19);
		panel_6.add(lblNewLabel_5_2);
		
		JSpinner tfsoluong = new JSpinner();
		tfsoluong.setBounds(829, 61, 41, 20);
		panel_6.add(tfsoluong);
//		
		JComboBox comboBoxDT = new JComboBox();
		comboBoxDT.setModel(new DefaultComboBoxModel(new String[] {"Iphone 15 Pro Max", "Iphone 15 Pro", "Iphone 15 Plus", "Iphone 15", "Iphone 14 Pro Max", "Iphone 14 Pro", "Iphone 14 Plus", "Iphone 14", "Iphone 13", "Iphone 12"}));
		comboBoxDT.setBounds(402, 60, 175, 22);
		panel_6.add(comboBoxDT);
//		
		JButton btadd = new JButton("Add");
		btadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				String tendt = (String) comboBoxDT.getSelectedItem();
				int soluong = (int) tfsoluong.getValue();
				 addDataToDatabase(tendt, soluong); 
				 
			}
		});
		btadd.setBackground(new Color(64, 224, 208));
		btadd.setBounds(284, 169, 100, 35);
		panel_6.add(btadd);
		
		JButton btdelete = new JButton("Delete");
		btdelete.setBackground(new Color(64, 224, 208));
		btdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tendt = (String) comboBoxDT.getSelectedItem();
				deletePhone(tendt);
			}
		});
		btdelete.setBounds(623, 169, 100, 35);
		panel_6.add(btdelete);
		
		JButton btsubtract = new JButton("Subtract");
		btsubtract.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tendt = (String) comboBoxDT.getSelectedItem();
				int soluong = (int) tfsoluong.getValue();
				subtractQuantityFromDatabase(tendt, soluong); 
			}
		});
		btsubtract.setBackground(new Color(64, 224, 208));
		btsubtract.setBounds(465, 169, 89, 35);
		panel_6.add(btsubtract);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetTable();
			}
		});
		btnReset.setBackground(new Color(64, 224, 208));
		btnReset.setBounds(799, 169, 100, 35);
		panel_6.add(btnReset);
		
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(SystemColor.inactiveCaptionBorder);
		panel_7.setBounds(10, 11, 1281, 444);
		panel_kho.add(panel_7);
		panel_7.setLayout(null);
		
		DefaultTableModel model = fetchData(); // Lấy mô hình dữ liệu từ hàm fetchData()
		table = new JTable(model); // Tạo JTable với mô hình dữ liệu đã lấy được

		// Đặt vị trí và kích thước cho JScrollPane chứa JTable
		JScrollPane scrollPane1 = new JScrollPane(table);
		scrollPane1.setBounds(28, 27, 1107, 394); // Đặt vị trí và kích thước cho JScrollPane
		panel_7.add(scrollPane1); // Thêm JScrollPane vào panel_7

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 479, 1281, 2);
		panel_kho.add(separator);

		JPanel panel_tc = new JPanel();
		panel_tc.setBackground(new Color(255, 255, 255));
		panel_tc.setBounds(225, 0, 1193, 837);
		contentPane.add(panel_tc);
		panel_tc.setLayout(null);

		DefaultTableModel modelnhanvien = loadNhanVienData(); // Lấy mô hình dữ liệu từ hàm fetchData()
		tablenhanvien = new JTable(modelnhanvien); // Tạo JTable với mô hình dữ liệu đã lấy được

		JScrollPane scrollPaneNhanvien = new JScrollPane(tablenhanvien);
		scrollPaneNhanvien.setBounds(10, 179, 1111, 407); // Đặt vị trí và kích thước cho JScrollPane
		panel_tc.add(scrollPaneNhanvien); // Thêm JScrollPane vào panel_tc

		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(41,128,185));
		panel.setBounds(0, 0, 1193, 98);
		panel_tc.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_7 = new JLabel("Quản lí nhân viên");
		lblNewLabel_7.setForeground(new Color(255, 255, 255));
		lblNewLabel_7.setBounds(501, 35, 231, 33);
		panel.add(lblNewLabel_7);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 25));
		
		JLabel lblNewLabel = new JLabel("Danh sách nhân viên");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 127, 230, 41);
		panel_tc.add(lblNewLabel);
		
		
		
		
		tfhoten = new JTextField();
		tfhoten.setBounds(101, 650, 152, 23);
		panel_tc.add(tfhoten);
		tfhoten.setColumns(10);
		
		tfngaysinh = new JTextField();
		tfngaysinh.setColumns(10);
		tfngaysinh.setBounds(374, 650, 152, 23);
		panel_tc.add(tfngaysinh);
		
		 lblNewLabel_2 = new JLabel("Họ tên");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(39, 654, 48, 14);
		panel_tc.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Ngày sinh");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(289, 654, 75, 19);
		panel_tc.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Giới tính");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_1.setBounds(583, 654, 63, 14);
		panel_tc.add(lblNewLabel_2_1_1);
		
		JComboBox cbgioitinh = new JComboBox();
		cbgioitinh.setModel(new DefaultComboBoxModel(new String[] {"Nam ", "Nữ"}));
		cbgioitinh.setBounds(659, 650, 56, 23);
		panel_tc.add(cbgioitinh);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("Chức vụ");
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_1_1.setBounds(743, 654, 63, 14);
		panel_tc.add(lblNewLabel_2_1_1_1);
		
		JComboBox cbchucvu = new JComboBox();
		cbchucvu.setModel(new DefaultComboBoxModel(new String[] {"Nhân viên", "Quản lí", "Bảo vệ", "Dọn vệ sinh"}));
		cbchucvu.setBounds(807, 650, 84, 23);
		panel_tc.add(cbchucvu);
		
		JLabel lblNewLabel_2_1_1_1_1 = new JLabel("Lương");
		lblNewLabel_2_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1_1_1_1.setBounds(919, 652, 48, 14);
		panel_tc.add(lblNewLabel_2_1_1_1_1);
		
		tfluong = new JTextField();
		tfluong.setColumns(10);
		tfluong.setBounds(969, 650, 152, 23);
		panel_tc.add(tfluong);
		
		JButton btnNewButton = new JButton("Thêm");
		btnNewButton.setBackground(new Color(135, 206, 235));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String hoten = tfhoten.getText();
				String ngaysinh = tfngaysinh.getText();
				String gioitinh = (String) cbgioitinh.getSelectedItem();
				String chucvu = (String) cbchucvu.getSelectedItem();
				String luong = tfluong.getText();
				themNhanVien(hoten, ngaysinh, gioitinh, chucvu, luong);
				
			}
		});
		btnNewButton.setBounds(358, 716, 89, 39);
		panel_tc.add(btnNewButton);
		
		JButton btnXa = new JButton("Xóa");
		btnXa.setBackground(new Color(220, 20, 60));
		btnXa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnXa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectedRow = tablenhanvien.getSelectedRow();
			        if (selectedRow != -1) { // Kiểm tra xem có hàng nào được chọn không
			            // Lấy họ tên từ cột đầu tiên của hàng được chọn
			            String hoTen = (String) tablenhanvien.getValueAt(selectedRow, 0);
			            // Gọi hàm xóa nhân viên với họ tên đã lấy được
			            xoaNhanVien(hoTen);
			}
		}});
		btnXa.setBounds(626, 715, 89, 41);
		panel_tc.add(btnXa);
		
		 separator = new JSeparator();
		separator.setBackground(new Color(240, 248, 255));
		separator.setBounds(0, 619, 1193, 2);
		panel_tc.add(separator);
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/Tích2.png")); // Tải biểu tượng
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);

		ImageIcon icon1 = new ImageIcon(getClass().getResource("/img/thungrac.png")); // Tải biểu tượng mới
		Image scaledIcon1 = icon1.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);


		
		JPanel panel_nv = new JPanel();
		panel_nv.setBounds(224, 0, 1194, 837);
		contentPane.add(panel_nv);
		panel_nv.setLayout(null);
		
		table_1 = new JTable();
		table_1.setBounds(10, 62, 684, 341);
		panel_nv.add(table_1);
		
		JLabel lblNewLabel_8 = new JLabel("Thông tin nhân viên:");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel_8.setBounds(10, 11, 211, 28);
		panel_nv.add(lblNewLabel_8);
		
		JLabel lblNewLabel_1 = new JLabel("Kho hàng");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_kho.setVisible(true);
				panel_tv.setVisible(false);
				panel_tc.setVisible(false);
				panel_nv.setVisible(false);
			}
		});
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(22, 162, 125, 42);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblTVn = new JLabel("Tư vấn");
		lblTVn.setForeground(new Color(255, 255, 255));
		lblTVn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 panel_tv.setVisible(true);
				 panel_kho.setVisible(false);
				 panel_tc.setVisible(false);
				 panel_nv.setVisible(false);
				 if(!isServerStarted) {
					 startServer(); 
					 isServerStarted=true;
				 }
				  			
				}
		});
		
		lblTVn.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTVn.setBounds(22, 234, 125, 42);
		panel_1.add(lblTVn);
		
		JLabel lblNewLabel_6 = new JLabel("Nhân viên");
		lblNewLabel_6.setForeground(new Color(255, 255, 255));
		lblNewLabel_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panel_tc.setVisible(true);
				panel_tv.setVisible(false);
				panel_kho.setVisible(false);
				panel_nv.setVisible(false);
				

			}
		});
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_6.setBounds(22, 95, 125, 42);
		panel_1.add(lblNewLabel_6);
		
		JLabel lblNewLabel_3 = new JLabel(" Quản lí");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(78, 18, 135, 42);
		panel_1.add(lblNewLabel_3);
		
		 ImageIcon icon10 = new ImageIcon(TrangChu.class.getResource("/img/managerr2.png"));
	        Image img = icon10.getImage();
	        Image resizedImg = img.getScaledInstance(100, 90, java.awt.Image.SCALE_SMOOTH);
	        ImageIcon resizedIcon = new ImageIcon(resizedImg);
	        JLabel lblNewLabel_4 = new JLabel(resizedIcon);
	        
	        // Đặt vị trí và kích thước cho JLabel
	        lblNewLabel_4.setBounds(10, 18, 60, 53);
	        
	        // Thêm JLabel vào panel
	        panel_1.add(lblNewLabel_4);
	        
	        JSeparator separator_1 = new JSeparator();
	        separator_1.setBounds(0, 82, 223, 2);
	        panel_1.add(separator_1);
		 this.setSize(1432,876);
		
	}
		
	
	
	public static DefaultTableModel fetchData() {
	    DefaultTableModel model = new DefaultTableModel(new Object[]{"Tên Điện Thoại", "Số Lượng"}, 0);
	    
	    try (Connection connection = Dtb.getConnection();
	         PreparedStatement statement = connection.prepareStatement("SELECT TOP (1000) TenDienThoai, SoLuong FROM DienThoai");
	         ResultSet resultSet = statement.executeQuery()) {
	    	

	        while (resultSet.next()) {
	            String tenDienThoai = resultSet.getString("TenDienThoai");
	            int soLuong = resultSet.getInt("SoLuong");
	            model.addRow(new Object[]{tenDienThoai, soLuong});
	        }

	    } catch (SQLException e) {
	   
	        e.printStackTrace();
	    }	  
	    return model;
	}
	// Nút add kho///////////////////////////////
	private void addDataToDatabase(String tendt, int soluong) {
	    try {
	        Connection connection = Dtb.getConnection();
	        
	        // Kiểm tra xem Tên điện thoại đã tồn tại trong cơ sở dữ liệu chưa
	        String checkSql = "SELECT * FROM DienThoai WHERE TenDienThoai = ?";
	        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
	        checkStatement.setString(1, tendt);
	        ResultSet resultSet = checkStatement.executeQuery();
	        
	        if (resultSet.next()) {
	            // Nếu Tên điện thoại đã tồn tại, cập nhật số lượng
	            int currentQuantity = resultSet.getInt("SoLuong");
	            int newQuantity = currentQuantity + soluong;
	            
	            // Cập nhật số lượng trong cơ sở dữ liệu
	            String updateSql = "UPDATE DienThoai SET SoLuong = ? WHERE TenDienThoai = ?";
	            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
	            updateStatement.setInt(1, newQuantity);
	            updateStatement.setString(2, tendt);
	            updateStatement.executeUpdate();
	            updateStatement.close();
	        } else {
	            // Nếu Tên điện thoại chưa tồn tại, thêm mới vào cơ sở dữ liệu
	            String insertSql = "INSERT INTO DienThoai (TenDienThoai, SoLuong) VALUES (?, ?)";
	            PreparedStatement insertStatement = connection.prepareStatement(insertSql);
	            insertStatement.setString(1, tendt);
	            insertStatement.setInt(2, soluong);
	            insertStatement.executeUpdate();
	            insertStatement.close();
	        }
	        
	        // Sau khi thêm dữ liệu hoặc cập nhật, cập nhật lại bảng hiển thị
	        DefaultTableModel model = fetchData(); // Lấy lại dữ liệu từ cơ sở dữ liệu
	        table.setModel(model); // Cập nhật mô hình dữ liệu của JTable
	        
	        resultSet.close();
	        checkStatement.close();
	        connection.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        System.out.println("Lỗi khi thêm dữ liệu vào cơ sở dữ liệu!");
	    }
	}

///subtract////////////////////////////////////////////////
	private void subtractQuantityFromDatabase(String tendt, int subtractQuantity) {
	    try {
	        Connection connection = Dtb.getConnection();
	        
	        // Lấy số lượng hiện tại từ cơ sở dữ liệu
	        String sql = "SELECT SoLuong FROM DienThoai WHERE TenDienThoai = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, tendt);
	        ResultSet resultSet = statement.executeQuery();
	        
	        if (resultSet.next()) {
	            int currentQuantity = resultSet.getInt("SoLuong");
	            
	            // Kiểm tra xem số lượng cần trừ có lớn hơn số lượng hiện tại không
	            if (subtractQuantity > currentQuantity) {
	                JOptionPane.showMessageDialog(null, "Số lượng cần trừ lớn hơn số lượng hiện có.", "Thông báo", JOptionPane.WARNING_MESSAGE);
	                return;
	            }
	            
	            // Tính số lượng mới sau khi trừ đi
	            int newQuantity = currentQuantity - subtractQuantity;
	            
	            // Cập nhật số lượng mới vào cơ sở dữ liệu
	            String updateSql = "UPDATE DienThoai SET SoLuong = ? WHERE TenDienThoai = ?";
	            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
	            updateStatement.setInt(1, newQuantity);
	            updateStatement.setString(2, tendt);
	            updateStatement.executeUpdate();
	            updateStatement.close();
	            
	            // Sau khi cập nhật số lượng, cập nhật lại bảng hiển thị
	            DefaultTableModel model = fetchData();
	            table.setModel(model);
	        } else {
	            JOptionPane.showMessageDialog(null, "Không tìm thấy Tên điện thoại trong cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        }
	        
	        resultSet.close();
	        statement.close();
	        connection.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi trừ đi số lượng từ cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	}
	////Deleteeeeeee////////////////////////
	private void deletePhone(String tendt) {
	    try {
	        Connection connection = Dtb.getConnection();
	        String sql = "DELETE FROM DienThoai WHERE TenDienThoai = ?";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, tendt);
	        int rowsDeleted = statement.executeUpdate();
	        statement.close();
	        connection.close();
	        
	        // Kiểm tra xem có hàng nào được xóa không
	        if (rowsDeleted > 0) {
	            JOptionPane.showMessageDialog(null, "Đã xóa điện thoại '" + tendt + "' thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	            // Sau khi xóa, cập nhật lại bảng hiển thị
	            DefaultTableModel model = fetchData();
	            table.setModel(model);
	        } else {
	            JOptionPane.showMessageDialog(null, "Không tìm thấy điện thoại '" + tendt + "' trong cơ sở dữ liệu.", "Thông báo", JOptionPane.WARNING_MESSAGE);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi xóa điện thoại từ cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void resetTable() {
	    DefaultTableModel model = fetchData(); // Lấy lại dữ liệu từ cơ sở dữ liệu
	    table.setModel(model); // Cập nhật bảng với dữ liệu mới
	}
	////Nhân viên//////////////////////
	 public static DefaultTableModel loadNhanVienData() {
	        DefaultTableModel modelnhanvien = new DefaultTableModel(new String[]{"HoTen", "NgaySinh", "GioiTinh", "ChucVu", "LuongThang"}, 0);
	        try {
	            Connection connection = Dtb.getConnection();
	            String sql = "SELECT HoTen, NgaySinh, GioiTinh, ChucVu, LuongThang FROM NhanVien";
	            PreparedStatement statement = connection.prepareStatement(sql);
	            ResultSet resultSet = statement.executeQuery();

	            while (resultSet.next()) {
	                String hoTen = resultSet.getString("HoTen");
	                String ngaySinh = resultSet.getDate("NgaySinh").toString();
	                String gioiTinh = resultSet.getString("GioiTinh");
	                String chucVu = resultSet.getString("ChucVu");
	                String luongthang = resultSet.getString("LuongThang");

	                modelnhanvien.addRow(new Object[]{hoTen, ngaySinh, gioiTinh, chucVu, luongthang});
	            }

	           
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			return modelnhanvien;
	    }
	 //Kết nối
	 private void startServer() {
	        try {	        	           
	         	serverSocket = new ServerSocket(7888);
	            socket = serverSocket.accept();	          
	            Thread t = new Thread(TrangChu.this);
	            t.start();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 //Gửi tin nhắn
	 private void sendMessage(String message) {
	        try {
	            output = new DataOutputStream(socket.getOutputStream());
	            output.writeUTF(message);
	            output.flush();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 //Thêm tin nhắn bản thân vào history
	  public void addMessageToHistory(String message) {
	        // Lấy model từ JList
	        DefaultListModel<String> model = (DefaultListModel<String>) listHistory.getModel();
	        // Thêm tin nhắn vào model
	        model.addElement("Bạn:"+message);
	        // Cập nhật JList để hiển thị tin nhắn mới
	        listHistory.setModel(model);
	    }
	//Đọc tin nhắn từ Client
	@Override
	 public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            while (true) {
                if (socket != null) {
                    model.addElement("Khách hàng:" + input.readUTF());                   
                    listHistory.setModel(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	class PlaceholderTextArea extends JTextArea {
	    private String placeholder;

	    public PlaceholderTextArea(String placeholder) {
	        this.placeholder = placeholder;
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (getText().isEmpty()) {
	            g.setColor(Color.GRAY);
	            FontMetrics metrics = g.getFontMetrics(getFont());
	            int x = getInsets().left;
	            int y = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
	            g.drawString(placeholder, x, y);
	        }
	    }
	}
	
	//Thêm nhân viên
	public void themNhanVien(String hoTen, String ngaySinh, String gioiTinh, String chucVu, String luongThang) {
	   
	    try{
	    	Connection connection = Dtb.getConnection();
    	    String sql = "INSERT INTO NhanVien (HoTen, NgaySinh, GioiTinh, ChucVu, LuongThang) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql) ;	
            
	        pstmt.setString(1, hoTen);
	        pstmt.setString(2, ngaySinh);
	        pstmt.setString(3, gioiTinh);
	        pstmt.setString(4, chucVu);
	        pstmt.setString(5, luongThang);        
	        pstmt.executeUpdate();
	        DefaultTableModel model = loadNhanVienData();
	        tablenhanvien.setModel(model);
	        JOptionPane.showMessageDialog(null, "Thêm nhân viên thành công!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	//Xóa nhân viên
	public void xoaNhanVien(String hoTen) {
		   
	    try{
	    	Connection connection = Dtb.getConnection();
	    	String sql = "DELETE FROM NhanVien WHERE HoTen = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql) ;	        
	        pstmt.setString(1, hoTen);      
	        pstmt.executeUpdate();
	        DefaultTableModel model = loadNhanVienData();
	        tablenhanvien.setModel(model);
	        JOptionPane.showMessageDialog(null, "Xóa nhân viên thành công!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	 private class MessageRenderer extends DefaultListCellRenderer {
	        private static final long serialVersionUID = 1L;

	        @Override
	        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	            String text = value.toString();

	            // Xác định loại tin nhắn (từ bạn hay từ đối phương)
	            if (text.startsWith("Bạn:")) {
	                label.setHorizontalAlignment(JLabel.RIGHT); // Căn lề phải
	                label.setText("<html><body style='width: 300px; height: 20px; background-color: #6699FF; border-radius: 50px;'>" + text + "</body></html>");
	            } else {
	                label.setHorizontalAlignment(JLabel.LEFT); // Căn lề trái
	                label.setText("<html><body style='width: 300px; height: 20px; background-color: #DCDCDC; border-radius: 50px;'>" + text + "</body></html>");
	            }
	            label.setBorder(new EmptyBorder(5, 5, 5, 5));
	            return label;
	        }
	    }
}


