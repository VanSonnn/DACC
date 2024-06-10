package Phone;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;

public class ChatClient extends JFrame implements Runnable{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private DefaultListModel<String> model;
    private  JList<String> listHistory;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ChatClient frame = new ChatClient();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ChatClient() {
        model = new DefaultListModel<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(910, 100, 480, 451);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        listHistory = new JList<>();
        listHistory.setBounds(20, 59, 426, 265);
        listHistory.setCellRenderer(new MessageRenderer());
        contentPane.add(listHistory);

        JLabel lblNewLabel_1 = new JLabel("Tư vấn khách hàng");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblNewLabel_1.setBounds(20, 11, 195, 27);
        contentPane.add(lblNewLabel_1);

        JTextArea txtMessage = new JTextArea();
        txtMessage.setBounds(20, 355, 330, 38);
        contentPane.add(txtMessage);
       
        startClient();       

        JButton btsend = new JButton("Gửi");
        btsend.setBackground(new Color(255, 99, 71));
        btsend.setForeground(new Color(255, 255, 255));
        btsend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String message = txtMessage.getText();
                sendMessage(message);            
                txtMessage.setText("");
                addMessageToHistory(message);
                
            }
        });
        btsend.setBounds(372, 355, 74, 38);
        contentPane.add(btsend);
    }

    private void startClient() {
        try {          
            socket = new Socket("localhost", 7888);         
            Thread t = new Thread(ChatClient.this);
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(message);
            output.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            while (true) {
                if (socket != null) {
                    model.addElement("Quản lí:" + input.readUTF());
                    listHistory.setModel(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addMessageToHistory(String message) {
        // Lấy model từ JList
        DefaultListModel<String> model = (DefaultListModel<String>) listHistory.getModel();
        // Thêm tin nhắn vào model
        model.addElement("Bạn:"+message);
        // Cập nhật JList để hiển thị tin nhắn mới
        listHistory.setModel(model);
    }
    public class MessageRenderer extends DefaultListCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String text = value.toString();

            // Xác định loại tin nhắn (từ bạn hay từ đối phương)
            if (text.startsWith("Bạn:")) {
                label.setHorizontalAlignment(JLabel.RIGHT); // Căn lề phải
                label.setText("<html><body style='width: 150px; height: 20px; background-color: #FF6347; border-radius: 50px;'>" + text + "</body></html>");
            } else {
                label.setHorizontalAlignment(JLabel.LEFT); // Căn lề trái
                label.setText("<html><body style='width: 150px; height: 20px; background-color: #DCDCDC; border-radius: 50px;'>" + text + "</body></html>");
            }

            // Đặt khoảng cách giữa các ô tin nhắn
            label.setBorder(new EmptyBorder(5, 5, 5, 5)); // Điều chỉnh giá trị 5 theo nhu cầu của bạn

            return label;
        }
    }
}
//Cuối cùng
