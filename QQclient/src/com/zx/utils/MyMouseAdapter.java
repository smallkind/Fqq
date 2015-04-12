package com.zx.utils;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
/**
 * 无标题栏窗口移动工具类
 * @author zx
 *
 */
public class MyMouseAdapter extends MouseAdapter {
	private int offsetX, offsetY;
	public void mouseDragged(MouseEvent e) {
		SwingUtilities.getRoot((Component) e.getSource()).setLocation(e.getXOnScreen() - offsetX, e.getYOnScreen()- offsetY);
	}
	public void mousePressed(MouseEvent e) {
		offsetX = e.getX();
		offsetY = e.getY();
	}
}
