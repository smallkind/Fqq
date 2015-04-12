package com.zx.ui.main;

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.xd.bean.UserInfoVo;
import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.factory.ClientBusinessFactory;


/**
 * 好友列表面板
 * @author xiaoyang
 *
 */
@SuppressWarnings("serial")
public class FriendListPanel extends JPanel {
	private int clickB=0;
	private String qq;
	/**
	 * 构造函数
	 */
	public FriendListPanel(String qq) {
		this.setBackground(Color.white);
		this.qq = qq;
		ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
		initialize(cbe.getSubGroup(qq));
	}
	
	/**
	 * 初始化组件
	 */
	@SuppressWarnings("unchecked")
	private void initialize(List<Object> list) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setSize(300, 285);
		this.setOpaque(false);
		String groupName=null;
		List<Object> lt=null;
		for(int i=0;i<list.size();i++){
			if(i%2==0){
				groupName = (String) list.get(i);
			}
			if(i%2==1){
				lt= (List<Object>) list.get(i);
				addJLabel(groupName,lt);
			}
		}
	}
	
	/**
	 * 更新UI界面；
	 */
	private void update(){
		this.updateUI();
	}
	
	/**
	 * 点击标签，将后面的标签全部设为不可视；
	 * @param jb
	 */
	private void clickBlack2(JLabel []jb){
		for(int i=1;i<jb.length;i++){
			try{
				jb[i].setVisible(false);
			}catch(Exception e){
				e.printStackTrace();
			}
		} 
		update();
	}
	
	/**
	 * 点击标签，将后面的标签全部设为可视；
	 * @param jb
	 */
	private void clickBlack(JLabel []jb){
		for(int i=1;i<jb.length;i++){
			try{
				jb[i].setVisible(true);
			}catch(Exception e){
				e.printStackTrace();
			}

		} 
		update();
	}
	
	/**
	 * 添加好友分组的内容；
	 */
	private void addJLabel(String groupName,List<Object> l){
		final JLabel []jb=new JLabel[l.size()+1];
		jb[0] = new JLabel();
		jb[0].setText(groupName);
		jb[0].setIcon(new ImageIcon(FriendListPanel.class.getResource("/com/xd/imgs/main/arrow_left.png")));
		jb[0].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jb[0].addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {

				clickB+=1;
				if(clickB%2==1){
					clickBlack(jb);
					jb[0].setIcon(new ImageIcon(FriendListPanel.class.getResource("/com/xd/imgs/main/arrow_down.png")));
				}else{
					clickBlack2(jb);
					jb[0].setIcon(new ImageIcon(FriendListPanel.class.getResource("/com/xd/imgs/main/arrow_left.png")));
				}	    
			}
		});
		this.add(jb[0],null);
		UserInfoVo uv =null;
		for(int i=1;i<jb.length;i++){
			jb[i]=new JLabel();
			jb[i].setIcon(new ImageIcon(FriendListPanel.class.getResource("/com/xd/imgs/main/bg.jpg")));
			jb[i].setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
			ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
			uv = cbe.getFriendInfo((String)l.get(i-1));
			jb[i].add(new MemberModel(uv,qq).jPanel);
			jb[i].setVisible(false);
			this.add(jb[i],null);
		}

	}
}
