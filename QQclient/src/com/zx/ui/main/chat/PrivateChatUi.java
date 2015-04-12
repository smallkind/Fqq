package com.zx.ui.main.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.xd.bean.UserInfoVo;
import com.zx.business.chat.Send;
import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.factory.ClientBusinessFactory;
import com.zx.ui.login.LoginUi;
import com.zx.utils.DateTransferUtil;
import com.zx.utils.JImagePane;
import com.zx.utils.MyMouseAdapter;
import com.zx.utils.PrivateChatMap;
import com.zx.utils.SendSocketMap;
/**
 * 私聊
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class PrivateChatUi extends JFrame implements MouseListener{
	private UserInfoVo uv;
	private String qq;
	private Socket client=null;
	private JPanel messagePanel;//最上面显示消息的面板
	private JLabel maximize;//最大化按钮
	private JLabel minimize;//最小化按钮
	private JLabel exit;//关闭按钮
	private JLabel headLabel;//好友头像
	private JLabel nickName;//好友昵称
	private JLabel space;//空间
	private JLabel signature;//个性签名
	private JLabel file;//文件
	private JLabel set;//设置
	private JLabel show;//展示
	private MyLabel mylabel;//广告
	private JPanel centerPanel;//中间面板 
	private JTextArea showmessage;//显示信息
	private JImagePane picPanel;//图像面板
	private JPanel iconPanel;//工具面板
	private JLabel text;//字体
	private JLabel emoticon;//表情
	private JLabel shake;//窗口抖动
	private JLabel picture;//发送图片
	private JLabel cut;//剪切
	private JLabel history;//历史记录
	private JTextArea printPanel;//输入面板
	private JPanel buttonPanel;//按钮面板
	private JButton send;//发送按钮
	private JButton cancel;//取消按钮

	public PrivateChatUi(UserInfoVo uv,String qq){
		this.uv = uv;
		this.qq = qq;
		this.setSize(590, 500);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);//要去掉标题栏
		this.setLayout(null);
		MyMouseAdapter adapter = new MyMouseAdapter();
		//控制无标题栏的窗口移动
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
		//控制无标题栏的窗口移动
		this.add(getMP());
		this.add(getCP());
		this.add(getPicP());
		this.add(getIP());
		this.add(getPrintP());
		this.add(getBP());
		setIconImage(Toolkit.getDefaultToolkit().getImage(PrivateChatUi.class.getResource("/com/xd/imgs/login/QQ_64.png")));
		this.setVisible(true);
		try {
			client = new Socket("localhost",6666);
			SendSocketMap.map.put(uv.getQq(), client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * messagePanel;//最上面显示消息的面板
	 * @return
	 */
	private JPanel getMP(){
		if(messagePanel == null){
			messagePanel = new JPanel();
			messagePanel.setLayout(null);
			messagePanel.add(getMinimize());
			messagePanel.add(getMaximize());
			messagePanel.add(getExit());
			messagePanel.add(getHeadLabel());
			messagePanel.add(getNickName());
			messagePanel.add(getSpace());
			messagePanel.add(getSignature());
			messagePanel.add(getFile());
			messagePanel.add(getShow());
			messagePanel.add(getSet());
			messagePanel.add(getMy());
			messagePanel.setBounds(0, 0, 650, 80);
		}
		return messagePanel;
	}

	/**
	 * 获取最小化按钮
	 */
	private JLabel getMinimize(){
		if(minimize==null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize = new JLabel(icon);
			int w = this.getWidth();
			minimize.setBounds(w-28-39-20, 0, 28, 20);
			minimize.addMouseListener(this);
		}
		return minimize;
	}

	/**
	 * 获取最大化按钮
	 */
	private JLabel getMaximize(){
		if(maximize == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/login/maximize.png"));
			maximize = new JLabel(icon);
			int w = this.getWidth();
			maximize.setBounds(w-20-39, 0, 20, 20);
			maximize.addMouseListener(this);
		}
		return maximize;
	}

	/**
	 * 获取关闭按钮
	 */
	private JLabel getExit(){
		if(exit==null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit = new JLabel(icon);
			int w = this.getWidth();
			exit.setBounds(w-39, 0, 39, 20);
			exit.addMouseListener(this);
		}
		return exit;
	}

	/**
	 * 获取好友头像
	 */
	private JLabel getHeadLabel(){
		if(headLabel == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/head/"+uv.getPhotoID()+".png"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
			headLabel = new JLabel(icon1);
			headLabel.setBounds(10, 5, 40, 40);
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
			nickName.setLocation(new Point(55,5));
			nickName.setForeground(Color.BLACK);
			nickName.setFont(new Font("Dialog", Font.BOLD, 15));
			nickName.setSize(new Dimension(180,20));
			nickName.addMouseListener(this);
		}
		return nickName;
	}

	/**
	 * 获取空间Label
	 */
	private JLabel getSpace(){
		if(space == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/main/space1.png"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH));
			space = new JLabel(icon1);
			space.setBounds(55, 25, 30, 20);
			space.setBorder(null);
			space.addMouseListener(this);
		}
		return space;
	}

	/**
	 * 获取个性签名
	 */
	private JLabel getSignature(){
		if(signature == null){
			signature = new JLabel();
			signature.setText(uv.getSign());
			signature.setSize(new Dimension(150,20));
			signature.setLocation(new Point(85,25));
			signature.setForeground(Color.BLACK);
			signature.addMouseListener(this);
		}
		return signature;
	}

	/**
	 * 获取文件
	 */
	private JLabel getFile(){
		if(file == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/file_transfere.png"));
			file = new JLabel(icon);
			file.setBounds(10, 45, 40, 35);
			file.addMouseListener(this);
		}
		return file;
	}

	/**
	 * 获取show
	 */
	private JLabel getShow(){
		if(show == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/show_members.png"));
			show = new JLabel(icon);
			show.setBounds(50, 45, 40, 35);
			show.addMouseListener(this);
		}
		return show;
	}

	/**
	 * 获取设置
	 */
	private JLabel getSet(){
		if(set == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/setting.png"));
			set = new JLabel(icon);
			set.setBounds(90, 45, 40, 35);
			set.addMouseListener(this);
		}
		return set;
	}

	/**
	 * 获取广告
	 */
	private MyLabel getMy(){
		if(mylabel == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/1.png"));
			mylabel = new MyLabel();
			mylabel.setIcon(icon);
			new Thread(mylabel).start();
			mylabel.setBounds(450, 40, 140, 40);
		}
		return mylabel;
	}

	/**
	 * centerPanel;//中间面板
	 * @return
	 */
	private JPanel getCP(){
		if(centerPanel == null){
			centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout());
			centerPanel.add(new JScrollPane(getShowmessage()),BorderLayout.CENTER);
			centerPanel.setBounds(0, 80, 450, 280);
		}
		return centerPanel;
	}

	/**
	 * 显示信息区域
	 */
	public JTextArea getShowmessage(){
		if(showmessage == null){
			showmessage = new JTextArea();
			showmessage.setLineWrap(true);//自动换行
			showmessage.setEditable(false);
			showmessage.setBackground(Color.white);
			//showmessage.setBorder(BorderFactory.createLineBorder(Color.gray));
		}
		return showmessage;
	}

	/**
	 * private JPanel picPanel;//图像面板
	 * @return
	 */
	private JImagePane getPicP(){
		if(picPanel == null){
			if(uv.getSex().equals("女")){
				picPanel = new JImagePane(new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/gril.png")).getImage(),JImagePane.SCALED);
			}else{
				picPanel = new JImagePane(new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/boy.png")).getImage(),JImagePane.SCALED);	
			}
			picPanel.setBounds(450, 80, 140, 420);
		}
		return picPanel;
	}



	/**
	 * iconPanel;//工具面板
	 * @return
	 */
	private JPanel getIP(){
		if(iconPanel == null){
			iconPanel = new JPanel();
			iconPanel.setBounds(0, 360, 450, 25);
			iconPanel.setLayout(null);
			iconPanel.add(getText());
			iconPanel.add(getEmoticon());
			iconPanel.add(getShake());
			iconPanel.add(getPicture());
			iconPanel.add(getCut());
			iconPanel.add(getHistory());
		}
		return iconPanel;
	}

	/**
	 *获取字体选择工具栏
	 */
	private JLabel getText(){
		if(text == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/text.png"));
			text = new JLabel(icon);
			text.setBounds(0, 0, 30, 25);
			text.addMouseListener(this);
		}
		return text;
	}

	/**
	 * 获取表情
	 */
	private JLabel getEmoticon(){
		if(emoticon == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/emoticon.png"));
			emoticon = new JLabel(icon);
			emoticon.setBounds(30, 0, 30, 25);
			emoticon.addMouseListener(this);
		}
		return emoticon;
	}

	/**
	 * 获取窗口抖动
	 */
	private JLabel getShake(){
		if(shake == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/shake.png"));
			shake = new JLabel(icon);
			shake.setBounds(60, 0, 30, 25);
			shake.addMouseListener(this);
		}
		return shake;
	}

	/**
	 * 获取发送图片
	 */
	private  JLabel getPicture(){
		if(picture == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/pictures.png"));
			picture = new JLabel(icon);
			picture.setBounds(90, 0, 30, 25);
			picture.addMouseListener(this);
		}
		return picture;
	}

	/**
	 * 获取截图
	 */
	private JLabel getCut(){
		if(cut == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/screenCapture.png"));
			cut = new JLabel(icon);
			cut.setBounds(120, 0, 30, 25);
			cut.addMouseListener(this);
		}
		return cut;
	}

	/**
	 * 获取消息记录
	 */
	private JLabel getHistory(){
		if(history == null){
			ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/history.png"));
			history = new JLabel(icon);
			history.setText("消息记录");
			history.setBounds(360, 0, 90, 25);
			history.addMouseListener(this);
		}
		return history;
	}

	/**
	 * printPanel;//输入面板
	 * @return
	 */
	private JTextArea getPrintP(){
		if(printPanel == null){
			printPanel = new JTextArea();
			printPanel.setBackground(centerPanel.getBackground());
			printPanel.setBounds(0, 385, 450, 80);
			printPanel.setLineWrap(true);
		}
		return printPanel;
	}

	/**
	 * buttonPanel;//按钮面板
	 * @return
	 */
	private JPanel getBP(){
		if(buttonPanel == null){
			buttonPanel = new JPanel();
			buttonPanel.setLayout(null);
			buttonPanel.setBounds(0, 465, 450, 35);
			JLabel label = new JLabel("上天猫，就够了！");
			label.setBounds(0, 0, 150, 35);
			buttonPanel.add(label);
			buttonPanel.add(getCancel());
			buttonPanel.add(getSend());
		}
		return buttonPanel;
	}

	/**
	 * 获取取消按钮
	 */
	private JButton getCancel(){
		if(cancel == null){
			cancel = new JButton();
			cancel.setText("取消");
			cancel.setBackground(Color.gray);
			cancel.setBounds(300, 0, 70, 27);
		}
		return cancel;
	}

	/**
	 *获取发送按钮 
	 */
	private JButton getSend(){
		if(send == null){
			send = new JButton();
			send.setText("发送");
			send.setBackground(Color.gray);
			send.setBounds(375, 0, 70, 27);
			send.addMouseListener(this);
		}
		return send;
	}

	/**
	 * 实现切换广告的功能
	 * @author TuoTuo
	 *
	 */
	class MyLabel extends JLabel implements Runnable{
		private int i = 1;
		@Override
		public void run() {
			while(true){
				i++;
				if(i==6){
					i=1;
				}
				try {
					Thread.sleep(2000);
					ImageIcon icon = new ImageIcon(PrivateChatUi.class.getResource("/com/xd/imgs/chat/"+i+".png"));
					mylabel.setIcon(icon);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == exit){
			List<Object> list = new ArrayList<Object>();
			String cmd = "end";
			String sqq = qq;
			String rqq = uv.getQq();
			list.add(cmd);
			list.add(sqq);
			list.add(rqq);
			new Send(client, list).start();
			PrivateChatMap.pcm.get(qq).remove(uv.getQq());
			dispose();
		}
		//		if(e.getSource() == maximize){
		//			this.setExtendedState(Frame.MAXIMIZED_BOTH);
		//			int z = this.getWidth();
		//			getMinimize().setBounds(z-39-20-28, 0, 28, 20);
		//			getMaximize().setBounds(z-39-20, 0, 20, 20);
		//			getExit().setBounds(z-39, 0, 39, 20);
		//		}
		if(e.getSource() == minimize){
			this.setExtendedState(Frame.ICONIFIED);
		}
		if(e.getSource() == space){
			//TODO:
		}
		if(e.getSource() == send){
			List<Object> list = new ArrayList<Object>();
			String cmd = "chat";
			String sqq = qq;
			String rqq = uv.getQq();
			String msg = printPanel.getText();
			printPanel.setText("");
			String time = DateTransferUtil.long2String(new Date().getTime());
			ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
			UserInfoVo uiv = cbe.getFriendInfo(qq);
			getShowmessage().append("\r\n"+uiv.getNickname()+"  "+time);
			getShowmessage().append("\r\n"+msg);
			list.add(cmd);
			list.add(sqq);
			list.add(rqq);
			list.add(msg);
			new Send(client, list).start();//这样写资源消耗太大
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
		if(e.getSource() == maximize){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/maximize_active.png"));
			maximize.setIcon(icon);
			maximize.setToolTipText("最大化");
		}
		if(e.getSource() == exit){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close_active.png"));
			exit.setIcon(icon);
			exit.setToolTipText("关闭");
		}
		if(e.getSource() == space){
			space.setToolTipText("进入空间");
		}
		if(e.getSource() == signature){
			signature.setToolTipText("个性签名更新："+signature.getText());
		}
		if(e.getSource() == file){
			file.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			file.setToolTipText("传送文件");
		}
		if(e.getSource() == show){
			show.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			show.setToolTipText("屏幕分享");
		}
		if(e.getSource() == set){
			set.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			set.setToolTipText("应用");
		}
		if(e.getSource() == text){
			text.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			text.setToolTipText("字体选择工具栏");
		}
		if(e.getSource() == emoticon){
			emoticon.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			emoticon.setToolTipText("选择表情");
		}
		if(e.getSource() == shake){
			shake.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			shake.setToolTipText("向好友发送窗口抖动");
		}
		if(e.getSource() == picture){
			picture.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			picture.setToolTipText("发送图片");
		}
		if(e.getSource() == cut){
			cut.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			cut.setToolTipText("屏幕截图");
		}
		if(e.getSource() == history){
			history.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			history.setToolTipText("显示消息记录");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == minimize){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/minimize.png"));
			minimize.setIcon(icon);
		}
		if(e.getSource() == maximize){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/maximize.png"));
			maximize.setIcon(icon);
		}
		if(e.getSource() == exit){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit.setIcon(icon);
		}
		if(e.getSource() == file){
			file.setBorder(null);
		}
		if(e.getSource() == show){
			show.setBorder(null);
		}
		if(e.getSource() == set){
			set.setBorder(null);
		}
		if(e.getSource() == text){
			text.setBorder(null);
		}
		if(e.getSource() == emoticon){
			emoticon.setBorder(null);
		}
		if(e.getSource() == shake){
			shake.setBorder(null);
		}
		if(e.getSource() == picture){
			picture.setBorder(null);
		}
		if(e.getSource() == cut){
			cut.setBorder(null);
		}
		if(e.getSource() == history){
			history.setBorder(null);
		}
	}
}

