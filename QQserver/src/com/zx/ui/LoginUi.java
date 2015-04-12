package com.zx.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
/**
 * 服务器的登陆界面
 * @author zx
 *
 */
@SuppressWarnings("serial")
public class LoginUi extends JFrame implements ActionListener{
	private final String userName = "admin";
	private final String userPwd = "123456";
	private JButton ok = null;//确定按钮
	private JButton cancel = null;//取消按钮
	private JTextField name = null;//用户名输入框
	private JTextField pwd = null;//密码输入框
	
	/**
	 * 构造函数
	 */
    public LoginUi(){
    	super("服务器");
    	this.setSize(300, 250);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	this.setResizable(false);
    	this.setLayout(null);
    	JLabel label1 = new JLabel("用户名:");
    	label1.setBounds(40, 40, 60, 40);
    	name = new JTextField();
    	name.setBounds(100, 40, 150, 40);
    	
    	JLabel label2 = new JLabel("密码:");
    	label2.setBounds(55, 90, 60, 40);
    	pwd = new JTextField();
    	pwd.setBounds(100, 90, 150, 40);
    	
    	this.add(label1);
    	this.add(name);   
    	this.add(label2);
    	this.add(pwd);
    	this.add(getOk());
    	this.add(getCancel());
    	this.setVisible(true);
    }
    
    /**
     * 获取确定按钮
     * @return
     */
    private JButton getOk(){
    	if(ok == null){
    		ok = new JButton();
    		ok.setText("确定");
    		ok.setBounds(40, 140, 80, 40);
    		ok.addActionListener(this);
    	}
    	return ok;
    }
    
    /**
     * 获取取消按钮
     * @return
     */
    private JButton getCancel(){
    	if(cancel == null){
    		cancel = new JButton();
    		cancel.setText("取消");
    		cancel.setBounds(160, 140, 80, 40);
    	}
    	return cancel;
    }
    
    /**
     * 响应监听
     */
	@Override
	public void actionPerformed(ActionEvent e) {
         if(e.getSource() == ok){
        	 String nm = name.getText();
        	 String p = pwd.getText();
        	 if(nm==null || nm.equals("") || p==null || p.equals("")){
        		 JOptionPane.showMessageDialog(null, "输入不能为空");
        	 }
        	 if(nm.equals(userName)&&p.equals(userPwd)){
        		 ServerUi.smu.setVisible(true);
        		 dispose();
        	 }
         }		
         if(e.getSource() == cancel){
        	 System.exit(0);
         }
	}
	
	 public static void main(String[] args) {
			new LoginUi();
		}
}
