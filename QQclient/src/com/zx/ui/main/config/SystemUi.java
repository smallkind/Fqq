package com.zx.ui.main.config;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.zx.ui.login.LoginUi;
import com.zx.ui.main.MainUi;
import com.zx.utils.MyMouseAdapter;
/**
 * 系统设置界面
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class SystemUi extends JFrame implements MouseListener{
	private JLabel minimize;//最小化按钮
	private JLabel exit;//关闭按钮
	private JLabel font;
	private JLabel C_black;
	private JLabel C_red;
	private JLabel C_white;
	private JLabel C_blue;
	private JLabel C_green;
	private JLabel C_gray;
	public SystemUi(){
		this.setSize(200, 220);
		this.setLocationRelativeTo(null);
		this.setResizable(false);//设置窗口大小不可变
		this.setUndecorated(true);//要去掉标题栏
		MyMouseAdapter adapter = new MyMouseAdapter();
		//控制无标题栏的窗口移动
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
		init();
		this.setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(SystemUi.class.getResource("/com/xd/imgs/login/QQ_64.png")));
		this.setVisible(true);
	}
	
	public void init(){
		this.add(getMinimize());
		this.add(getExit());
		font = new JLabel("字 体 颜 色 ：");
		font.setBounds(45, 20, 100, 20);
		this.add(font);
		C_black = new JLabel("黑");
		C_black.setBounds(95, 50, 20, 20);
		C_black.addMouseListener(this);
		this.add(C_black);
		C_red = new JLabel("红");
		C_red.setBounds(95, 80, 20, 20);
		C_red.addMouseListener(this);
		this.add(C_red);
		C_white = new JLabel("百");
		C_white.setBounds(95, 110, 20, 20);
		C_white.addMouseListener(this);
		this.add(C_white);
		C_blue = new JLabel("蓝");
		C_blue.setBounds(95, 140, 20, 20);
		C_blue.addMouseListener(this);
		this.add(C_blue);
		C_green = new JLabel("绿");
		C_green.setBounds(95, 170, 20, 20);
		C_green.addMouseListener(this);
		this.add(C_green);
		C_gray = new JLabel("灰");
		C_gray.setBounds(95, 200, 20, 20);
		C_gray.addMouseListener(this);
		this.add(C_gray);
	}
	
	/**
	 * 获取最小化按钮
	 */
	private JLabel getMinimize(){
		if(minimize==null){
			ImageIcon icon = new ImageIcon(SystemUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize = new JLabel(icon);
			minimize.setBounds(133, 0, 28, 20);
			minimize.addMouseListener(this);
		}
		return minimize;
	}

	/**
	 * 获取关闭按钮
	 */
	private JLabel getExit(){
		if(exit==null){
			ImageIcon icon = new ImageIcon(SystemUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit = new JLabel(icon);
			exit.setBounds(161, 0, 39, 20);
			exit.addMouseListener(this);
		}
		return exit;
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == exit){
			dispose();
		}
		if(e.getSource() == minimize){
			this.setExtendedState(JFrame.ICONIFIED);
		}
		if(e.getSource() == C_black){
			MainUi.nickName.setForeground(Color.BLACK);
			MainUi.signature.setForeground(Color.BLACK);
		}
		if(e.getSource() == C_blue){
			MainUi.nickName.setForeground(Color.BLUE);
			MainUi.signature.setForeground(Color.BLUE);
		}
		if(e.getSource() == C_gray){
			MainUi.nickName.setForeground(Color.GRAY);
			MainUi.signature.setForeground(Color.GRAY);
		}
		if(e.getSource() == C_green){
			MainUi.nickName.setForeground(Color.GREEN);
			MainUi.signature.setForeground(Color.GREEN);
		}
		if(e.getSource() == C_red){
			MainUi.nickName.setForeground(Color.RED);
			MainUi.signature.setForeground(Color.RED);
		}
		if(e.getSource() == C_white){
			MainUi.nickName.setForeground(Color.WHITE);
			MainUi.signature.setForeground(Color.WHITE);
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
		if(e.getSource() == C_black){
			C_black.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		if(e.getSource() == C_blue){
			C_blue.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		if(e.getSource() == C_gray){
			C_gray.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		if(e.getSource() == C_green){
			C_green.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		if(e.getSource() == C_red){
			C_red.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
		if(e.getSource() == C_white){
			C_white.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == minimize){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize.setIcon(icon);
		}
		if(e.getSource() == exit){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit.setIcon(icon);
		}
		if(e.getSource() == C_black){
			C_black.setBorder(null);
		}
		if(e.getSource() == C_blue){
			C_blue.setBorder(null);
		}
		if(e.getSource() == C_gray){
			C_gray.setBorder(null);
		}
		if(e.getSource() == C_green){
			C_green.setBorder(null);
		}
		if(e.getSource() == C_red){
			C_red.setBorder(null);
		}
		if(e.getSource() == C_white){
			C_white.setBorder(null);
		}
	}
}
