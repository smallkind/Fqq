package com.zx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import com.zx.business.ebi.ChatServerBusinessEbi;
import com.zx.business.ebi.ServerBusinessEbi;
import com.zx.business.factory.ChatServerBusinessFactory;
import com.zx.business.factory.ServerBusinessFactory;

/**
 * 服务器的界面
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class ServerUi extends JFrame implements ActionListener{
	public JTextArea area;//显示信息的文本区域
	@SuppressWarnings("rawtypes")
	public DefaultListModel lm;//显示在线用户列表
	private JMenuItem run;//开启
	private JMenuItem exit;//退出
	public static final ServerUi smu = new ServerUi();//单例的设计思想，解决登陆，退出显示信息的问题
   
	/**
	 * 构造方法
	 */
	@SuppressWarnings("rawtypes")
	public ServerUi(){
		this.getContentPane().add(new JScrollPane(getArea()));

		//在线用户列表
		lm = new DefaultListModel();
		@SuppressWarnings("unchecked")
		JList list = new JList(lm);
		JScrollPane jc = new JScrollPane(list);
		jc.setPreferredSize(new Dimension(100, this.getHeight()));
		jc.setBorder(new TitledBorder("在线"));
		this.getContentPane().add(jc,BorderLayout.EAST);

		//菜单
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("控制(C)");
		bar.add(menu);
		menu.setMnemonic('C'); //助记符  可以让 用户按Alt+'C'激活
		run = new JMenuItem("开启");
		run.setAccelerator(KeyStroke.getKeyStroke('R', KeyEvent.CTRL_MASK));
		run.setActionCommand("run");
		menu.add(run);
		menu.addSeparator();
	    exit = new JMenuItem("退出");
		exit.setAccelerator(KeyStroke.getKeyStroke('E', KeyEvent.CTRL_MASK));
		exit.setActionCommand("exit");
		menu.add(exit);
		this.setJMenuBar(bar);
		run.addActionListener(this);
		exit.addActionListener(this);
		
		this.setTitle("QQ服务器");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final int winWidth =500;
		final int winHeight =400;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();
		this.setBounds(width/2-winWidth/2, height/2-winHeight/2, winWidth, winHeight);
	}

	/**
	 * 获取显示信息的文本区域
	 */
	public JTextArea getArea(){
		if(area == null){
			area = new JTextArea();
			area.setEditable(false);
		}
		return area;
	}

	/**
	 * 监听消息响应
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("run")){
			ServerBusinessEbi sbe = ServerBusinessFactory.newInstance();
			sbe.startServer();
			ChatServerBusinessEbi csbe = ChatServerBusinessFactory.newInstance();
			csbe.startServer();
			run.setEnabled(false);
		}
		if(e.getActionCommand().equals("exit")){
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		smu.setVisible(true);
	}
}
