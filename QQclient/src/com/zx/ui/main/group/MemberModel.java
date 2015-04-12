package com.zx.ui.main.group;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MemberModel{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public JButton jButton = null;//显示群头像；

	public JPanel jPanel = new JPanel();//模板容器；

	private JLabel lb_nickName = null;//显示群名字；

	private String nickname = null;


	public MemberModel(String nickname) {
		super();
		this.nickname = nickname;//群名称；
		initialize();
	}


	private void initialize() {
		lb_nickName = new JLabel();
		lb_nickName.setBounds(new Rectangle(52, 10, 150, 20));
		lb_nickName.setFont(new Font("Dialog", Font.BOLD, 14));
		lb_nickName.setText(nickname);
		lb_nickName.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				exchangeEnter();
				lb_nickName.setToolTipText(lb_nickName.getText());
			}
			public void mouseExited(java.awt.event.MouseEvent e) {
				exchangeExited();
			}

		});
		//jPanel.setBackground(Color.gray);
		jPanel.setSize(new Dimension(300, 60));
		jPanel.setLayout(null);
		jPanel.add(getJButton(), null);
		jPanel.add(lb_nickName, null);
		jPanel.addMouseListener(new java.awt.event.MouseAdapter() {  
			public void mouseExited(java.awt.event.MouseEvent e) {
				exchangeExited();//鼠标移出模板区，改变背景颜色；
			}

			public void mouseEntered(java.awt.event.MouseEvent e) {
				exchangeEnter();//鼠标移进模板区，改变背景颜色；
			}
		});
	}

	private void exchangeEnter() {
		jPanel.setBackground(new Color(192,224,248));
	}

	private void exchangeExited() {
		jPanel.setBackground(null);
	}


	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(8, 10, 40, 40));
			jButton.setBackground(new Color(236, 255, 236));
			//jButton.setBackground(Color.gray);
			//jButton.setEnabled(false);
			ImageIcon icon = new ImageIcon(MemberModel.class.getResource("/com/xd/imgs/head/401.png"));
			ImageIcon icon1 = new ImageIcon(icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
			jButton.setIcon(icon1);
			jButton.addMouseListener(new java.awt.event.MouseAdapter() {  
				public void mouseExited(java.awt.event.MouseEvent e) {   
					exchangeExited();//鼠标移出模板区，改变背景颜色；
				}  
				public void mouseEntered(java.awt.event.MouseEvent e) {   
					exchangeEnter();//鼠标移进模板区，改变背景颜色；
				}
			});
			jButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//new PublicChatUi();
				}
			});
		}
		return jButton;
	}
}


