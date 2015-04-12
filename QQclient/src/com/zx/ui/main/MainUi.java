package com.zx.ui.main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import com.xd.bean.UserInfoVo;
import com.zx.business.chat.Send;
import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.factory.ClientBusinessFactory;
import com.zx.ui.login.LoginUi;
import com.zx.ui.main.config.SystemUi;
import com.zx.ui.main.group.GroupListPanel;
import com.zx.ui.main.message.MessageUi;
import com.zx.ui.main.skin.SkinUi;
import com.zx.utils.JImagePane;
import com.zx.utils.MyMouseAdapter;
import com.zx.utils.OpenUrl;
import com.zx.utils.PrivateChatMap;
import com.zx.utils.SocketMap;
/**
 * TuoTuoTal的主界面，显示好友列表，群分组等
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class MainUi extends JFrame implements MouseListener,ActionListener{
	private UserInfoVo uv = null;//接受用户信息
	//上
	public static JImagePane panel = null;//背景图片的Panel(上)
	private JLabel skin;//换肤按钮
	private JLabel minimize;//最小化按钮
	private JLabel exit;//关闭按钮
	private JLabel headLabel;//用户头像  坐标15,45
	private TrayIcon trayIcon = null;//系统托盘
	public static JLabel nickName;//昵称
	private JMenuBar stateMenu;//状态栏
	private JMenu jMenu;//状态
	public static JTextField  signature;//个性签名
	private JLabel space;//空间
	private JLabel mail;//邮件
	private JLabel blog;//微博
	private JLabel add;//添加
	private JLabel set;//设置
	private JTextField searchBar;//搜索栏
	private JLabel search;//搜素图标
	private JPanel tp;//好友，群，消息记录切换
	private JLabel contact;//联系人
	private JLabel group;//群，讨论组
	private JLabel message;//消息记录
	//中
	private JPanel middlePanel;//好友，群，消息面板(中)
	private JScrollPane scrollPane = null;//好友列表添加滚动条
	private JScrollPane groupPanel;//群面板
	//下
	private JImagePane belowPanel;//底下面板

	/**
	 * 构造函数
	 */
	public MainUi(final UserInfoVo uv){
		this.uv = uv;
		this.setResizable(false);
		this.setUndecorated(true);//要去掉标题栏
		this.setLayout(null);
		MyMouseAdapter adapter = new MyMouseAdapter();
		//控制无标题栏的窗口移动
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
		//控制无标题栏的窗口移动
		this.add(getPanel());
		this.add(getMiddlePanel());
		this.add(getBelowPanel());
		init();
		systemTrayinit();
		//设置应用程序的图标不在任务栏上显示
		this.setType(java.awt.Window.Type.UTILITY);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();
		this.setBounds(width*2/3, height/7,300 , 530);
		this.setVisible(true);
		new Thread(){
			public void run() {
				ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
				cbe.startSocket(uv.getQq());
			};
		}.start();
	}

	/**
	 * 初始化函数
	 */
	public void init(){
		//上
		panel.add(getSkin());
		panel.add(getMinimize());
		panel.add(getExit());
		panel.add(getHeadLabel());
		panel.add(getNickName());
		panel.add(getStateMenu());
		panel.add(getSignature());
		panel.add(getSpace());
		panel.add(getMail());
		panel.add(getBlog());
		panel.add(getAdd());
		panel.add(getSet());
		panel.add(getSearch());
		panel.add(getSearchBar());
		panel.add(getTP());
		//中
		middlePanel.add(getScrollPane());
	}

	/**
	 *初始化系统托盘 
	 */
	public void systemTrayinit(){
		if(SystemTray.isSupported()){//检查当前系统是否支持系统托盘
			SystemTray tray = SystemTray.getSystemTray();///获取表示桌面托盘区的 SystemTray 实例。
			//TODO:这个托盘图标不好看
			Image image = Toolkit.getDefaultToolkit().getImage(MainUi.class.getResource("/com/xd/imgs/login/CT.gif"));
			image = image.getScaledInstance(18, 18, Image.SCALE_SMOOTH); //缩放图片
			PopupMenu popupMenu = new PopupMenu(); 
			MenuItem  menuItema  = new MenuItem("打开主面板"); 
			MenuItem  exitItem  = new MenuItem("退出");
			exitItem.addActionListener(new  ActionListener(){ 
				public void actionPerformed(ActionEvent e)     {  
					try{     
						ClientBusinessEbi cbi = ClientBusinessFactory.newInstance();
						if(cbi.offline(uv.getQq())){
							System.exit(0);
						}else{
							JOptionPane.showMessageDialog(null, "下线不成功");
						} 
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
	 * 获取背景图片面板
	 */
	private JImagePane getPanel(){
		if(panel == null){
			panel = new JImagePane(new ImageIcon(MainUi.class.getResource("/com/xd/imgs/skin/"+uv.getBg()+".jpg")).getImage(),JImagePane.SCALED);
			panel.setLayout(null);
			panel.setBounds(0, 0, 300, 185);
		}
		return panel;
	}

	/**
	 * 获取换肤按钮
	 */
	private JLabel getSkin(){
		if(skin == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/login/skin.png"));
			skin = new JLabel(icon);
			skin.setBounds(205, 0, 28, 20);
			skin.addMouseListener(this);
		}
		return skin;
	}

	/**
	 * 获取最小化按钮
	 */
	private JLabel getMinimize(){
		if(minimize==null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize = new JLabel(icon);
			minimize.setBounds(233, 0, 28, 20);
			minimize.addMouseListener(this);
		}
		return minimize;
	}

	/**
	 * 获取关闭按钮
	 */
	private JLabel getExit(){
		if(exit==null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit = new JLabel(icon);
			exit.setBounds(261, 0, 39, 20);
			exit.addMouseListener(this);
		}
		return exit;
	}

	/**
	 * 获取头像
	 */
	private JLabel getHeadLabel(){
		if(headLabel == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/head/"+uv.getPhotoID()+".png"));
			//			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/qq.gif"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
			headLabel = new JLabel(icon1);
			headLabel.setBounds(15, 45 ,60,60);
			//			headLabel = new JLabel(icon);
			//			headLabel.setBounds(15, 45 ,79,72);
			headLabel.addMouseListener(this);
		}
		return headLabel;
	}

	/**
	 *获取昵称 
	 */
	private JLabel getNickName(){
		if(nickName == null){
			nickName = new JLabel();
			nickName.setText(uv.getNickname());
			nickName.setLocation(new Point(80,45));
			nickName.setForeground(Color.WHITE);
			nickName.setFont(new Font("Dialog", Font.BOLD, 15));
			nickName.setSize(new Dimension(60,20));
			nickName.addMouseListener(this);
		}
		return nickName;
	}

	/**
	 * 获取状态
	 */
	private JMenuBar getStateMenu(){
		if(stateMenu == null){
			stateMenu = new JMenuBar();
			stateMenu.setBorder(null);
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/status_online.png"));
			jMenu = new JMenu();
			jMenu.setIcon(icon);
			jMenu.setOpaque(false);
			JMenuItem[] jMenuItems=new JMenuItem[7];
			String imageStr[]={"status_online.png","status_callme.png","status_away.png","status_busy.png","status_silent.png","status_hidden.png","status_offline.png"};
			String NameStr[]={"我在线上","Q我吧","离开","忙碌","请勿打扰","隐身","离线"};
			for(int i=0;i<6;i++)
			{
				ImageIcon  ic = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/"+imageStr[i]));
				jMenuItems[i]=new JMenuItem(NameStr[i],ic);
				jMenuItems[i].addActionListener(this);
				jMenuItems[i].setOpaque(false);
				jMenu.add(jMenuItems[i]);
			}	
			stateMenu.add(jMenu);
			stateMenu.setBounds(145, 45, 30, 20);
			stateMenu.setOpaque(false);
			//stateMenu.setBackground(new Color(2,2,2));
			stateMenu.setVisible(true);
			stateMenu.addMouseListener(this);
		}
		return stateMenu;
	}

	/**
	 * 获取个性签名
	 */
	private JTextField getSignature(){
		if(signature == null){
			signature = new JTextField();
			//signature.setHorizontalAlignment(JTextField.RIGHT);//向左对齐
			if(uv.getSign()!=null&&!uv.getSign().equals("null")){
				signature.setText(uv.getSign());
			}else{
				signature.setText("编辑个性签名");
			}
			signature.setSize(new Dimension(150,20));
			signature.setLocation(new Point(80,65));
			signature.setBorder(new EmptyBorder(0,0,0,0));
			signature.setForeground(Color.WHITE);
			signature.setEditable(false);
			signature.setOpaque(false);
			signature.addMouseListener(this);
		}
		return signature;
	}

	/**
	 * 获取空间Label
	 */
	private JLabel getSpace(){
		if(space == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/space1.png"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH));
			space = new JLabel(icon1);
			space.setBounds(80, 85, 30, 20);
			space.setOpaque(false);
			space.setBorder(null);
			space.addMouseListener(this);
		}
		return space;
	}

	/**
	 * 获取邮件Label
	 */
	private JLabel getMail(){
		if(mail == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/mail1.png"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH));
			mail = new JLabel(icon1);
			mail.setBorder(null);
			mail.setOpaque(false);
			mail.setBounds(110, 85, 30, 20);
			mail.addMouseListener(this);
		}
		return mail;
	}

	/**
	 * 获取添加Label
	 */
	private JLabel getBlog(){
		if(blog == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/weibo.png"));
			//ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(25, 17, Image.SCALE_SMOOTH));
			blog = new JLabel(icon);
			blog.setOpaque(false);
			blog.setBounds(140, 85, 30, 20);
			blog.addMouseListener(this);
		}
		return blog;
	}

	/**
	 * 获取添加Label
	 */
	private JLabel getAdd(){
		if(add == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/add.png"));
			add = new JLabel(icon);
			add.setOpaque(false);
			add.setBounds(170, 85, 30, 20);
			add.addMouseListener(this);
		}
		return add;
	}

	/**
	 * 获取设置Label
	 */
	private JLabel getSet(){
		if(set == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/setting.png"));
			set = new JLabel(icon);
			set.setOpaque(false);
			set.setBounds(270, 85, 30, 20);
			set.addMouseListener(this);
		}
		return set;
	}

	/**
	 * 获取搜素图标
	 */
	private JLabel getSearch(){
		if(search == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/search_normal.png"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			search = new JLabel(icon1);
			search.setOpaque(false);
			search.setBounds(280, 115, 20, 30);
			search.addMouseListener(this);
		}
		return search;
	}

	/**
	 * 获取搜索栏
	 */
	private JTextField getSearchBar(){
		if(searchBar == null){
			searchBar = new JTextField();
			searchBar.setText("搜索：联系人，讨论组，群，企业");
			searchBar.setOpaque(false);
			searchBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			searchBar.setForeground(Color.WHITE);
			searchBar.setBounds(0, 115, 300, 30);
			//searchBar.addMouseListener(this);
		}
		return searchBar;
	}

	/**
	 *获取好友，群，消息面板 
	 */
	private JPanel getMiddlePanel(){
		if(middlePanel == null){
			middlePanel = new JPanel();
			middlePanel.setBounds(0, 185, 300,285);
			middlePanel.setLayout(null);
		}
		return middlePanel;
	}

	/**
	 * 获取联系人
	 */
	private JLabel getContact(){
		if(contact == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/group1_down.png"));
			contact = new JLabel(icon);
			contact.addMouseListener(this);
			contact.setOpaque(false);
		}
		return contact;
	}

	/**
	 * 获取讨论组
	 */
	private JLabel getGroup(){
		if(group == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/group2.png"));
			group = new JLabel(icon);
			group.addMouseListener(this);
			group.setOpaque(false);
		}
		return group;
	}

	/**
	 *获取消息记录 
	 */
	private JLabel getMessage(){
		if(message == null){
			ImageIcon icon = new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/group3.png"));
			message = new JLabel(icon);
			message.addMouseListener(this);
			message.setOpaque(false);
		}
		return message;
	}

	/**
	 * 获取TP
	 */
	private JPanel getTP(){
		if(tp == null){
			tp = new JPanel();
			tp.setLayout(new GridLayout(1, 4));
			tp.add(getContact());
			tp.add(getGroup());
			tp.add(getMessage());
			tp.setOpaque(false);
			tp.setBounds(0, 145, 300, 40);
		}
		return tp;
	}

	/**
	 * 初始化好友列表的滚动条
	 */
	private JScrollPane getScrollPane(){
		if(scrollPane == null){
			scrollPane = new JScrollPane(new FriendListPanel(uv.getQq()));
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );//不显示水平滚动条
			scrollPane.setBounds(0, 0, 300, 285);
		}
		return scrollPane;
	}

	/**
	 *获取群面板 
	 */
	private JScrollPane getGroupPanel(){
		if(groupPanel == null){
			groupPanel = new JScrollPane(new GroupListPanel(uv.getQq()));
			groupPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );//不显示水平滚动条
			groupPanel.setBounds(0, 0, 300, 285);
		}
		return groupPanel;
	}

	/**
	 *获取最下面的面板 
	 */
	private JImagePane getBelowPanel(){
		if(belowPanel == null){
			belowPanel = new JImagePane(new ImageIcon(MainUi.class.getResource("/com/xd/imgs/main/lowPanel.jpg")).getImage(),JImagePane.SCALED);
			belowPanel.setLayout(null);
			belowPanel.setBounds(0, 470, 300, 60);
		}
		return belowPanel;
	}

	/**
	 * 鼠标监听
	 */
	//鼠标按键在组件上单击（按下并释放）时调用。
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == exit){
			ClientBusinessEbi cbi = ClientBusinessFactory.newInstance();
			if(cbi.offline(uv.getQq())){
				try {
					List<Object> list = new ArrayList<Object>();
					String cmd = "end";
					String sqq = "0";
					String rqq = uv.getQq();
					list.add(cmd);
					list.add(sqq);
					list.add(rqq);
					new Send(SocketMap.map.get(uv.getQq()), list).start();
					PrivateChatMap.pcm.remove(uv.getQq());
					System.exit(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(null, "下线不成功");
			}
		}
		if(e.getSource() == minimize){
			this.setVisible(false);
		}
		if(e.getSource() == skin){
			new SkinUi(uv.getQq()).setVisible(true);
		}
		if(e.getSource() == headLabel){
			new MessageUi(uv).setVisible(true);
		}
		if(e.getSource() == trayIcon){
			this.setVisible(true);
		}
		if(e.getSource() == signature){
			signature.setEditable(true);
			signature.setBackground(Color.white);
		}
		if(e.getSource() == space){
			OpenUrl.openURL("Http://user.qzone.qq.com");
		}
		if(e.getSource() == mail){
			OpenUrl.openURL("Http://mail.qq.com");
		}
		if(e.getSource() == blog){
			OpenUrl.openURL("Http://t.qq.com");
		}
		if(e.getSource() == set){
			new SystemUi();
		}
		if(e.getSource() == search){
			//TODO
		}
		if(e.getSource() == contact){
			ImageIcon icon;
			icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group2.png"));
			group.setIcon(icon);
			icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group3.png"));
			message.setIcon(icon);
			icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group1_down.png"));
			contact.setIcon(icon);
			middlePanel.removeAll();
			middlePanel.add(getScrollPane());
			middlePanel.updateUI();
		}
		if(e.getSource() == group){
			ImageIcon icon ;
			icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group1.png"));
			contact.setIcon(icon);
			icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group3.png"));
			message.setIcon(icon);
			icon= new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group2_down.png"));
			group.setIcon(icon);
			middlePanel.removeAll();
			middlePanel.add(getGroupPanel());
			middlePanel.updateUI();
		}
		if(e.getSource() == message){
			ImageIcon icon ;
			icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group1.png"));
			contact.setIcon(icon);
			icon= new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group2.png"));
			group.setIcon(icon);
			icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/main/group3_down.png"));
			message.setIcon(icon);
			//TODO
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
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
		if(e.getSource() == headLabel){
			headLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		if(e.getSource() == nickName){
			nickName.setToolTipText(nickName.getText());
		}
		if(e.getSource() == stateMenu){
			stateMenu.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
		if(e.getSource() == signature){
			signature.setToolTipText(signature.getText());
			signature.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
		if(e.getSource() == space){
			space.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			space.setToolTipText("空间");
		}
		if(e.getSource() == mail){
			mail.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			mail.setToolTipText("邮件");
		}
		if(e.getSource() == blog){
			blog.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			blog.setToolTipText("博客");
		}
		if(e.getSource() == add){
			add.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			add.setToolTipText("界面管理器");
		}
		if(e.getSource() == set){
			set.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			set.setToolTipText("系统设置");
		}
		if(e.getSource() == search){
			search.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			search.setToolTipText("查询好友");
		}
		if(e.getSource() == contact){
			contact.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
		if(e.getSource() == group){
			group.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
		if(e.getSource() == message){
			message.setBorder(BorderFactory.createLineBorder(Color.GRAY));
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
		if(e.getSource() == headLabel){
			headLabel.setBorder(null);
		}
		if(e.getSource() == stateMenu){
			stateMenu.setBorder(null);
		}
		if(e.getSource() == signature){
			signature.setEditable(false);
			signature.setBorder(new EmptyBorder(0,0,0,0));
		}
		if(e.getSource() == space){
			space.setBorder(null);
		}
		if(e.getSource() == mail){
			mail.setBorder(null);
		}
		if(e.getSource() == blog){
			blog.setBorder(null);
		}
		if(e.getSource() == add){
			add.setBorder(null);
		}
		if(e.getSource() == set){
			set.setBorder(null);
		}
		if(e.getSource() == search){
			search.setBorder(null);
		}
		if(e.getSource() == contact){
			contact.setBorder(null);
		}
		if(e.getSource() == group){
			group.setBorder(null);
		}
		if(e.getSource() == message){
			message.setBorder(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem target = (JMenuItem)e.getSource();
		ImageIcon  ic = (ImageIcon)target.getIcon();
		jMenu.setIcon(ic);	
	}
}
