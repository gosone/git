package Test2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class chat extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = 1L;
	static Thread f5;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		chat win=new chat();
		MyThread a=new MyThread();
		a.run();
	}
	JTextField text;
    
    JButton button;
     
    public JEditorPane edit;
    
    public static JEditorPane logs;
    
    String log="��ӭ����������";
    
    String name;
    
    JTextField enterName;
	public chat() {
		super("CHat");
		this.setLayout(null);
		enterName=new JTextField(15);
		JPanel p1 = new JPanel();
        p1.add(new JLabel("�������֣�"));
        p1.add(enterName);
        button = new JButton("ȷ��");
        button.addActionListener(this);
        p1.add(button);
        p1.setBounds(2, 303, 420, 40);
		add(p1);
        
		edit = new JEditorPane();
		logs = new JEditorPane();
		edit.setEditable(false);
		logs.setBounds(2,2,710,300);
		logs.setEditable(false);
		edit.setBounds(2,342,710,236);
		edit.setText("");
		edit.addKeyListener(this);
		add(logs);
		add(edit);
		setBounds(160, 60, 714, 580);
		setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			String words=name+':'+edit.getText();
			words =getURLEncoderString(words);
			edit.setText("");
			logs.setText(doGet("http://47.101.197.245:2333/java?words="+words));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		name =enterName.getText();
		edit.setText("");
		edit.setEditable(true);
	}
	public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// ���ؽ���ַ���
        try {
            // ����Զ��url���Ӷ���
            URL url = new URL(httpurl);
            // ͨ��Զ��url���Ӷ����һ�����ӣ�ǿת��httpURLConnection��
            connection = (HttpURLConnection) url.openConnection();
            // �������ӷ�ʽ��get
            connection.setRequestMethod("GET");
            // �������������������ĳ�ʱʱ�䣺15000����
            connection.setConnectTimeout(15000);
            // ���ö�ȡԶ�̷��ص�����ʱ�䣺60000����
            connection.setReadTimeout(60000);
            // ��������;
            connection.connect();
            // ͨ��connection���ӣ���ȡ������
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // ��װ������is����ָ���ַ���
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // �������
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// �ر�Զ������
        }

        return result;
    }
	public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
class MyThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
		chat.logs.setText(chat.doGet("http://47.101.197.245:2333/javal"));
		}
	}
}