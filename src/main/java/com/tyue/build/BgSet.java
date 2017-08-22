package com.tyue.build;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BgSet extends JFrame {
	private static final long serialVersionUID = 1L;

	public BgSet() {
		// 设置标题
		super("GreatFish");
		setBounds(100, 100, 600, 600);
		// 背景图片的路径。
		String path = "image/3.jpg";
		ImageIcon background = new ImageIcon(path);
		JLabel label = new JLabel(background);
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
	}
}