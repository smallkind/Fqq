package com.zx.ui.main.skin;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.factory.ClientBusinessFactory;
import com.zx.ui.login.LoginUi;
import com.zx.ui.main.MainUi;
import com.zx.utils.MyMouseAdapter;
import com.zx.utils.OpenUrl;
/**
 * 换肤界面
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class SkinUi extends JFrame implements MouseListener{
	private JLabel exit;//关闭按钮 125*125
	private JLabel label;
	private JPanel skinPanel;
	private JButton more;
	private JButton define;
	private String qq;
	public SkinUi(String qq){
		this.qq = qq;
		this.setSize(750, 440);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setUndecorated(true);//要去掉标题栏
		this.setLayout(null);
		MyMouseAdapter adapter = new MyMouseAdapter();
		//控制无标题栏的窗口移动
		this.addMouseMotionListener(adapter);
		this.addMouseListener(adapter);
		//控制无标题栏的窗口移动
		this.add(getLabel());
		this.add(getExit());
		this.add(getSkinPanel());
		this.add(getDefine());
		this.add(getMore());
		setIconImage(Toolkit.getDefaultToolkit().getImage(SkinUi.class.getResource("/com/xd/imgs/login/QQ_64.png")));
	}

	private JLabel getLabel(){
		if(label == null){
			label = new JLabel();
			label.setText("更改外观");
			label.setForeground(Color.BLUE);
			label.setBounds(10, 0, 100, 20);
		}
		return label;
	}

	/**
	 * 获取关闭按钮
	 */
	private JLabel getExit(){
		if(exit==null){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit = new JLabel(icon);
			exit.setBounds(711, 0, 39, 20);
			exit.addMouseListener(this);
		}
		return exit;
	}

	/**
	 *获取皮肤面板 
	 */
	private JPanel getSkinPanel(){
		if(skinPanel == null){
			skinPanel = new JPanel();
			skinPanel.setLayout(new GridLayout(3, 6));
			for(int i=0;i<18;i++){
				final int j =i;
				ImageIcon icon = new ImageIcon(SkinUi.class.getResource("/com/xd/imgs/skin/"+i+".jpg"));
				ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH));
				final JLabel label = new JLabel(icon1);
				skinPanel.add(label);
				label.addMouseListener(new MouseListener() {
					@Override
					public void mousePressed(MouseEvent e) {
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						if(e.getSource() == label){
							label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
							label.setText(" ");
						}
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getSource() == label){
							boolean flag;
							ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
							flag = cbe.setBg(j,qq);
							if(flag){
								Image icon = new ImageIcon(SkinUi.class.getResource("/com/xd/imgs/skin/"+j+".jpg")).getImage();
								MainUi.panel.setBackgroundImage(icon);
							}
						}
					}
					@Override
					public void mouseReleased(MouseEvent e) {
					}
					@Override
					public void mouseExited(MouseEvent e) {
						if(e.getSource() == label){
							label.setBorder(null);
							label.setText(null);
						}
					}
				});
			}
			skinPanel.setBounds(0,30, 750, 360);
		}
		return skinPanel;
	}

	private JButton getDefine(){
		if(define == null){
			define = new JButton();
			define.setText("自定义皮肤");
			define.setBackground(Color.GRAY);
			define.setBounds(10, 405, 160, 20);
		}
		return define;
	}

	private JButton getMore(){
		if(more == null){
			more = new JButton();
			more.setText("更多皮肤");
			more.setBounds(180, 405, 160, 20);
			more.setBackground(Color.GRAY);
			more.addMouseListener(this);
		}
		return more;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == exit){
			dispose();
		}
		if(e.getSource() == more){
			OpenUrl.openURL("http://style.qq.com/decorate/");
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
		if(e.getSource() == exit){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close_active.png"));
			exit.setIcon(icon);
			exit.setToolTipText("关闭");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == exit){
			ImageIcon icon = new ImageIcon(LoginUi.class.getResource("/com/xd/imgs/login/close.png"));
			exit.setIcon(icon);
		}

	}
	
}
