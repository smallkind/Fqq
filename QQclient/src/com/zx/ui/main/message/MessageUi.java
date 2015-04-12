package com.zx.ui.main.message;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.xd.bean.UserInfoVo;
import com.zx.ui.login.LoginUi;
import com.zx.utils.JImagePane;
import com.zx.utils.MyMouseAdapter;
/**
 * 显示自己信息的界面，可以修改自己的信息
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class MessageUi extends JFrame implements MouseListener{
	private UserInfoVo uv;
	public static JImagePane panel;//背景图片
	private JLabel skin;//换肤按钮
	private JLabel minimize;//最小化按钮
	private JLabel exit;//最大化按钮
	private JLabel headLabel;//头像 90 10
	private JLabel nickName;//昵称
	private JLabel qq;//qq号
	private JLabel  signature;//个性签名
	private JTabbedPane tabbedPane;//切换组件
	private JPanel showMessage;//资料展示
	private JButton edit;//编辑资料
	private JLabel nickname;//昵称
	private JLabel account;//账号
	private JLabel personal;//个人说明
	private JLabel person;//个人
	private JLabel home;//故乡
	private JLabel location;//所在地
	private JLabel phone;//手机
	private JLabel page;//主页
	private JLabel mail;//邮箱
	
	public MessageUi(UserInfoVo uv){
		this.uv = uv;
		this.setSize(460, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);//设置窗口大小不可变
		this.setUndecorated(true);//要去掉标题栏
		MyMouseAdapter adapter = new MyMouseAdapter();
		//控制无标题栏的窗口移动
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
		//控制无标题栏的窗口移动
		init();
		setIconImage(Toolkit.getDefaultToolkit().getImage(MessageUi.class.getResource("/com/xd/imgs/login/QQ_64.png")));
		this.add(panel);
	}

	public void init(){
		panel = new JImagePane(new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/skin/qqLogin.jpg")).getImage(),JImagePane.SCALED);
		panel.setLayout(null);
		panel.add(getSkin());
		panel.add(getMinimize());
		panel.add(getExit());
		panel.add(getHeadLabel());
		panel.add(getNickName());
		panel.add(getQq());
		panel.add(getSignature());
		panel.add(getTabbedPane());
	}

	/**
	 * 获取换肤按钮
	 */
	private JLabel getSkin(){
		if(skin == null){
			ImageIcon icon = new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/login/skin.png"));
			skin = new JLabel(icon);
			skin.setBounds(365, 0, 28, 20);
			skin.addMouseListener(this);
		}
		return skin;
	}

	/**
	 * 获取最小化按钮
	 */
	private JLabel getMinimize(){
		if(minimize==null){
			ImageIcon icon = new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize = new JLabel(icon);
			minimize.setBounds(393, 0, 28, 20);
			minimize.addMouseListener(this);
		}
		return minimize;
	}

	/**
	 * 获取关闭按钮
	 */
	private JLabel getExit(){
		if(exit==null){
			ImageIcon icon = new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit = new JLabel(icon);
			exit.setBounds(421, 0, 39, 20);
			exit.addMouseListener(this);
		}
		return exit;
	}

	/**
	 * 获取头像
	 */
	private JLabel getHeadLabel(){
		if(headLabel == null){
			ImageIcon icon = new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/head/"+uv.getPhotoID()+".png"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
			headLabel = new JLabel(icon1);
			headLabel.setBounds(10, 90 ,80,80);
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
			nickName.setLocation(new Point(95,90));
			nickName.setForeground(Color.WHITE);
			nickName.setFont(new Font("Dialog", Font.BOLD, 18));
			nickName.setSize(new Dimension(80,40));
			nickName.addMouseListener(this);
		}
		return nickName;
	}

	/**
	 * 获取QQ号
	 */
	private JLabel getQq(){
		if(qq == null){
			qq = new JLabel();
			qq.setText(uv.getQq());
			qq.setForeground(Color.WHITE);
			qq.setBounds(180, 90, 80, 40);
		}
		return qq;
	}

	/**
	 * 获取个性签名
	 */
	private JLabel getSignature(){
		if(signature == null){
			signature = new JLabel();
			signature.setText(uv.getSign());
			signature.setForeground(Color.WHITE);
			signature.setFont(new Font("Dialog", Font.BOLD, 18));
			signature.setBounds(95, 130, 300, 40);
		}	
		return signature;
	}

	/**
	 *获取切换组件 
	 */
	private JTabbedPane getTabbedPane(){
		if(tabbedPane == null){
			tabbedPane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane.addTab("资料", getShowMessage());
			tabbedPane.addTab("相册", new JImagePane(new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/page/2.jpg")).getImage(),JImagePane.SCALED));
			tabbedPane.addTab("动态", new JImagePane(new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/page/3.jpg")).getImage(),JImagePane.SCALED));
			tabbedPane.addTab("标签", new JImagePane(new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/page/4.jpg")).getImage(),JImagePane.SCALED));
			tabbedPane.addTab("账户", new JImagePane(new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/page/5.jpg")).getImage(),JImagePane.SCALED));
			tabbedPane.addTab("手机", new JImagePane(new ImageIcon(MessageUi.class.getResource("/com/xd/imgs/page/6.jpg")).getImage(),JImagePane.SCALED));
			tabbedPane.setBounds(0, 180, 460, 420);
			tabbedPane.setOpaque(false);
		}
		return tabbedPane;
	}

	/**
	 * 展示资料的面板
	 */
	private JPanel getShowMessage(){
		if(showMessage == null){
			showMessage = new JPanel();
			showMessage.setLayout(null);
			showMessage.setBackground(Color.white);
			showMessage.add(getEdit());
			JLabel label1 = new JLabel("昵  称：");
			label1.setBounds(20, 40, 60, 20);
			showMessage.add(label1);
			showMessage.add(getNickname());
			JLabel label2 = new JLabel("账  号：");
			label2.setBounds(20, 70, 60, 20);
			showMessage.add(label2);
			showMessage.add(getAccount());
			JLabel label3 = new JLabel("个人说明");
			label3.setBounds(20, 100, 60, 20);
			showMessage.add(label3);
			showMessage.add(getPersonal());
			JLabel label4 = new JLabel("个  人：");
			label4.setBounds(20, 150, 60, 20);
			showMessage.add(label4);
			showMessage.add(getPerson());
			JLabel label5 = new JLabel("故  乡：");
			label5.setBounds(20, 180, 60, 20);
			showMessage.add(label5);
			showMessage.add(getHome());
			JLabel label6 = new JLabel("所在地：");
			label6.setBounds(20, 210, 60, 20);
			showMessage.add(label6);
			showMessage.add(getLocaTion());
			JLabel label7= new JLabel("手  机：");
			label7.setBounds(20, 280, 60, 20);
			showMessage.add(label7);
			showMessage.add(getPhone());
			JLabel label8 = new JLabel("主  页：");
			label8.setBounds(20, 310, 60, 20);
			showMessage.add(label8);
			showMessage.add(getPage());
			JLabel label9 = new JLabel("邮  箱：");
			label9.setBounds(20, 340, 60, 20);
			showMessage.add(label9);
			showMessage.add(getEmail());
			showMessage.setBounds(0, 0, 460, 420);
		}
		return showMessage;
	}

	/**
	 * 获取编辑资料按钮
	 */
	private JButton getEdit(){
		if(edit == null){
			edit = new JButton();
			edit.setText("编辑资料");
			edit.setBackground(Color.GRAY);
			edit.setBounds(320, 10, 120, 20);
			edit.addMouseListener(this);
		}
		return edit;
	}

	/**
	 * 获取昵称
	 */
	private JLabel getNickname(){
		if(nickname == null){
			nickname = new JLabel();
			nickname.setText(uv.getNickname());
			nickname.setBounds(80, 40, 200, 20);
		}
		return nickname;
	}

	/**
	 * 获取账号
	 */
	private JLabel getAccount(){
		if(account == null){
			account = new JLabel();
			account.setText(uv.getQq());
			account.setBounds(80, 70, 200, 20);
		}
		return account;
	}

	/**
	 *获取个人说明 
	 */
	private JLabel getPersonal(){
		if(personal == null){
			personal = new JLabel();
			personal.setText("坚持就是胜利~~~~");
			personal.setBounds(80, 100, 200, 20);
		}
		return personal;
	}

	/**
	 * 获取个人
	 */
	private JLabel getPerson(){
		if(person == null){
			person = new JLabel();
			person.setText(uv.getSex()+" "+"22岁    1月1号     属人    天枰座");
			person.setBounds(80, 150, 300, 20);
		}
		return person;
	}

	/**
	 * 获取故乡
	 */
	private JLabel getHome(){
		if(home == null){
			home = new JLabel();
			if(uv.getQq().equals("434461146")){
				home.setText("火星");
			}else{
				home.setText("地球");
			}
			home.setBounds(80, 180, 100, 20);
		}
		return home;	
	}

	/**
	 * 获取所在地
	 */
	private JLabel getLocaTion(){
		if(location == null){
			location = new JLabel();
			if(uv.getQq().equals("434461146")){
				location.setText("宇宙银河省火星市智障大街250号");
			}else{
				location.setText("宇宙银河省地球市聪明大街1号");
			}
			location.setBounds(80, 210, 300, 20);
		}
		return location;
	}

	/**
	 * 获取手机
	 */
	private JLabel getPhone(){
		if(phone == null){
			phone = new JLabel();
			phone.setText("182********");
			phone.setBounds(80, 280, 200, 20);
		}
		return phone;
	}
	
	/**
	 * 获取主页
	 */
	private JLabel getPage(){
		if(page == null){
			page = new JLabel();
			page.setText("http://www."+uv.getQq()+".com");
			page.setBounds(80, 310, 200, 20);
		}
		return page;
	}
	
	/**
	 * 获取邮箱
	 */
	private JLabel getEmail(){
		if(mail == null){
			mail = new JLabel();
			mail.setText(uv.getQq()+"@qq.com");
			mail.setBounds(80, 340, 200, 20);
		}
		return mail;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == exit){
			dispose();
		}
		if(e.getSource() == minimize){
			this.setExtendedState(JFrame.ICONIFIED);
		}
		if(e.getSource() == skin){
			new com.zx.ui.main.message.SkinUi().setVisible(true);
		}	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
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
	}
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
	}
}
