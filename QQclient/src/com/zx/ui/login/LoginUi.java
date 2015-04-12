package com.zx.ui.login;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.xd.bean.UserInfoVo;
import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.factory.ClientBusinessFactory;
import com.zx.ui.main.MainUi;
import com.zx.utils.JImagePane;
import com.zx.utils.MyMouseAdapter;
import com.zx.utils.OpenUrl;

/**
 * 登陆界面
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class LoginUi extends JFrame implements MouseListener{
	private TrayIcon trayIcon = null;//系统托盘
	private JImagePane panel;
	private JTextField userName;//用户名输入框
	private JPasswordField userPwd;//密码输入框
	private JCheckBox box;//记住密码选择框
	private JButton login;//登陆按钮
	private JLabel skin;//换肤按钮
	private JLabel minimize;//最小化按钮
	private JLabel exit;//最大化按钮
	private JLabel register;//注册
	private JLabel back;//找回密码
	private JLabel headLabel; //头像
	//  public static LoginUi l = new LoginUi();
	/**
	 * 构造函数
	 */
	public LoginUi(){
		this.setSize(450, 330);
		this.setLocationRelativeTo(null);//使登陆界面在屏幕正中间显示
		this.setResizable(false);//设置窗口大小不可变
		this.setUndecorated(true);//要去掉标题栏
		init();
		systemTrayinit();
		MyMouseAdapter adapter = new MyMouseAdapter();
		//控制无标题栏的窗口移动
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
		//控制无标题栏的窗口移动
		//设置应用程序的图标不在任务栏上显示
		this.setType(java.awt.Window.Type.UTILITY);
		this.add(panel);
		this.setVisible(true);
	}

	/**
	 *初始化系统托盘 
	 */
	public void systemTrayinit(){
		if(SystemTray.isSupported()){//检查当前系统是否支持系统托盘
			SystemTray tray = SystemTray.getSystemTray();///获取表示桌面托盘区的 SystemTray 实例。
			//TODO:这个托盘图标不好看
			Image image = Toolkit.getDefaultToolkit().getImage(LoginUi.class.getResource("/com/xd/imgs/login/CT.gif"));
			image = image.getScaledInstance(18, 18, Image.SCALE_SMOOTH); //缩放图片
			PopupMenu popupMenu = new PopupMenu(); 
			MenuItem  menuItema  = new MenuItem("打开主面板"); 
			MenuItem  exitItem  = new MenuItem("退出");
			exitItem.addActionListener(new  ActionListener(){ 
				public void actionPerformed(ActionEvent e)     {  
					try{     
						System.exit(0);     
					}catch(Exception   ex)   {  
						ex.printStackTrace();   
					}   
				} 
			}); 
			menuItema.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{     
						setVisible(true);     
					}catch(Exception   ex)   {  
						ex.printStackTrace();   
					} 	
				}
			});
			popupMenu.add(menuItema); 
			popupMenu.add(exitItem); 
			trayIcon = new TrayIcon(image, "托托",  popupMenu);
			trayIcon.addMouseListener(this);
			try{   
				tray.add(trayIcon);  // 将 TrayIcon 添加到 SystemTray。 
			} catch   (AWTException   e)     {   
				System.err.println(e);   
			} 
		}else{
			System.out.println("你的系统不支持系统托盘"); 
		}
	}

	/**
	 * 初始化函数
	 */
	public void init(){
		panel = new JImagePane(new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/skin/qqLogin.jpg")).getImage(),JImagePane.SCALED);
		panel.setLayout(null);
		panel.add(getSkin());
		panel.add(getMinimize());
		panel.add(getExit());
		panel.add(getHeadLabel());
		panel.add(getUserName());
		panel.add(getBox());
		panel.add(getUserPwd());
		panel.add(getRegister());
		panel.add(getBack());
		panel.add(getLogin());
	}

	/**
	 * 获取换肤按钮
	 */
	private JLabel getSkin(){
		if(skin == null){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/skin.png"));
			skin = new JLabel(icon);
			skin.setBounds(355, 0, 28, 20);
			skin.addMouseListener(this);
		}
		return skin;
	}

	/**
	 * 获取最小化按钮
	 */
	private JLabel getMinimize(){
		if(minimize==null){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize = new JLabel(icon);
			minimize.setBounds(383, 0, 28, 20);
			minimize.addMouseListener(this);
		}
		return minimize;
	}

	/**
	 * 获取关闭按钮
	 */
	private JLabel getExit(){
		if(exit==null){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit = new JLabel(icon);
			exit.setBounds(411, 0, 39, 20);
			exit.addMouseListener(this);
		}
		return exit;
	}

	/**
	 * 获取头像
	 * 
	 */
	private JLabel getHeadLabel(){
		if(headLabel==null){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/head/001.png"));
			headLabel = new JLabel(icon);
			headLabel.setBounds(30, 180 ,87,83);
		}
		return headLabel;
	}

	/**
	 * 获取用户名输入框
	 * @param args
	 */
	private JTextField getUserName(){
		if(userName==null){
			userName = new JTextField();
			userName.setBounds(130, 180, 180, 30);
			userName.addFocusListener(new FocusListener() {
				//焦点丢失
				@Override
				public void focusLost(FocusEvent e) {
					String qq = userName.getText();
					ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
					String photoId = cbe.getPhotoId(qq);
					if(photoId!=null){
						ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/head/"+photoId+".png"));
						ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(87, 83, Image.SCALE_SMOOTH));
						headLabel.setIcon(icon1);
					}
				}

				@Override
				public void focusGained(FocusEvent e) {
				}
			});
		}
		return userName;
	}

	/**
	 * 获取密码输入框
	 * @param args
	 */
	private JPasswordField getUserPwd(){
		if(userPwd==null){
			userPwd = new JPasswordField();
			userPwd.setBounds(130, 220, 180, 30);
		}
		return userPwd;
	}

	/**
	 * 获取记住密码组件
	 * @param args
	 */
	private JCheckBox getBox(){
		if(box==null){
			box = new JCheckBox("记住密码");
			box.setForeground(Color.white);
			box.setBounds(130, 250, 80, 20);
			box.setOpaque(false);
		}
		return box;
	}

	/**
	 * 获取注册账号组件
	 * @param args
	 */
	private JLabel getRegister(){
		if(register==null){
			register = new JLabel("注册账号");
			register.setBounds(320, 180, 60, 30);
			register.setForeground(Color.BLUE);
			register.addMouseListener(this);
		}
		return register;
	}

	/**
	 * 获取找回密码组件
	 * @param args
	 */
	private JLabel getBack(){
		if(back==null){
			back = new JLabel("找回密码");
			back.setBounds(320, 220, 60, 30);
			back.setForeground(Color.BLUE);
			back.addMouseListener(this);
		}
		return back;
	}

	/**
	 * 获取登陆按钮
	 * @param args
	 */
	private JButton getLogin(){
		if(login == null){
			Font font = new Font("黑体",1,20);
			login = new JButton("登陆");
			login.setFont(font);
			login.setBounds(130, 280, 180, 30);
			login.setContentAreaFilled(false);//设置按钮透明化
			login.setForeground(Color.RED);
			login.addMouseListener(this);
		}
		return login;
	}

	public static void main(String[] args) {
		new LoginUi();
	}

	/**
	 * 鼠标监听
	 */
	//鼠标按键在组件上单击（按下并释放）时调用。
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == exit){
			System.exit(0);
		}
		if(e.getSource() == minimize){
			this.setVisible(false);
		}
		if(e.getSource() == skin){
			//TODO:
		}
		if(e.getSource() == register){
			OpenUrl.openURL("http://zc.qq.com/chs/index.html");
			//new RegisterUi();
			//dispose();
		}
		if(e.getSource() == back){
			OpenUrl.openURL("http://aq.qq.com/cn2/findpsw/pc/pc_find_pwd_input_account");
		}
		if(e.getSource() == login){
			String nm = userName.getText();
			@SuppressWarnings("deprecation")
			String pwd = userPwd.getText();
			ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
			List<Object> list = cbe.login(nm, pwd);
			int flag = (Integer) list.get(0);
			if(flag == 0){
				JOptionPane.showMessageDialog(null, "该账号不存在");
			}
			if(flag == 1){
				JOptionPane.showMessageDialog(null, "密码错误");
			}
			if(flag == 2){
				JOptionPane.showMessageDialog(null, "该账号已登录");
			}
			if(flag == 3){
				UserInfoVo uv = (UserInfoVo) list.get(1);
				new MainUi(uv);
				dispose();
				SystemTray.getSystemTray().remove(trayIcon);//移除系统托盘
			}
		}
		if(e.getSource() == trayIcon){
			this.setVisible(true);
		}
	}
	//鼠标按键在组件上按下时调用。
	@Override
	public void mousePressed(MouseEvent e) {		
	}
	//鼠标按钮在组件上释放时调用。
	@Override
	public void mouseReleased(MouseEvent e) {		
	}
	//鼠标进入到组件上时调用。
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == skin){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/skin_active.png"));
			skin.setIcon(icon);
			skin.setToolTipText("更改外观");
		}
		if(e.getSource() == minimize){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/minimize_active.png"));
			minimize.setIcon(icon);
			minimize.setToolTipText("最小化");
		}
		if(e.getSource() == exit){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close_active.png"));
			exit.setIcon(icon);
			exit.setToolTipText("关闭");
		}
		if(e.getSource() == register){
			register.setBorder(BorderFactory.createLineBorder(Color.red));
		}
		if(e.getSource() == back){
			back.setBorder(BorderFactory.createLineBorder(Color.red));
		}
	}
	//鼠标离开组件时调用。
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == skin){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/skin.png"));
			skin.setIcon(icon);
		}
		if(e.getSource() == minimize){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize.setIcon(icon);
		}
		if(e.getSource() == exit){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit.setIcon(icon);
		}
		if(e.getSource() == register){
			register.setBorder(null);
		}
		if(e.getSource() == back){
			back.setBorder(null);
		}
	}

}
