package Phone;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatServer extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;
    private DefaultListModel<String> model;
    private JList<String> listHistory;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ChatServer frame = new ChatServer();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ChatServer() {
        model = new DefaultListModel<>();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 602, 512);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblServer = new JLabel("Máy chủ");
        lblServer.setBounds(10, 68, 113, 24);
        contentPane.add(lblServer);

        listHistory = new JList<>();
        listHistory.setBounds(31, 103, 497, 201);
        contentPane.add(listHistory);

        JTextArea txtmessage = new JTextArea();
        txtmessage.setRows(3);
        txtmessage.setBounds(31, 351, 321, 73);
        contentPane.add(txtmessage);
        //Tạo kết nối
        startServer();
        
  
        JButton btnSend = new JButton("Gửi");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String message =txtmessage.getText();
                sendMessage(message);
                txtmessage.setText("");
                addMessageToHistory(message);
            }
        });
        btnSend.setBounds(422, 369, 106, 55);
        contentPane.add(btnSend);
    }

    private void startServer() {
        try {
            model.addElement("Đang kết nối máy chủ...");
            listHistory.setModel(model);
            serverSocket = new ServerSocket(7888);
            socket = serverSocket.accept();
            model.addElement("Đã kết nối máy chủ.");
            listHistory.setModel(model);
            Thread t = new Thread(ChatServer.this);
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
                    model.addElement("Client say:" + input.readUTF());                   
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
        model.addElement("You:"+message);
        // Cập nhật JList để hiển thị tin nhắn mới
        listHistory.setModel(model);
    }
}
