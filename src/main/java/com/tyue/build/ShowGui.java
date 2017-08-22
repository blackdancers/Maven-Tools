package com.tyue.build;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ShowGui {
	private JFrame f;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JButton b4;
	private JButton b5;
	private JButton b6;
	private JTextField tf4;
	private JTextField tf5;
	private JTextField tf6;
	private static String s1;

	public ShowGui() {
		// 直接调用
		startGui();
	}

	public void startGui() {
		f = new BgSet();// 用来设置背景图片
		f.setLayout(new FlowLayout());
		Image icon = Toolkit.getDefaultToolkit().getImage("image/4.jpg");// 设置左上角logo图标
		f.setIconImage(icon);
		// 6个按钮
		b1 = new JButton("开始");
		b2 = new JButton("撤回");
		b3 = new JButton("退出");
		b4 = new JButton("一键提取");
		b5 = new JButton("撤回");
		b6 = new JButton("退出");
		// 6个按钮的大小
		b1.setPreferredSize(new Dimension(89, 39));
		b2.setPreferredSize(new Dimension(89, 39));
		b3.setPreferredSize(new Dimension(89, 39));
		b4.setPreferredSize(new Dimension(89, 39));
		b5.setPreferredSize(new Dimension(89, 39));
		b6.setPreferredSize(new Dimension(89, 39));
		// 6个 文本框的大小以及输入字体的属性
		tf1 = new JTextField("Please input absolute_path", 40);
		tf1.setFont(new Font("宋体", Font.PLAIN, 25));
		tf1.setBounds(200, 15, 550, 126);
		tf2 = new JTextField("Please input keyWords", 40);
		tf2.setFont(new Font("宋体", Font.PLAIN, 25));
		tf2.setBounds(200, 15, 550, 126);
		tf3 = new JTextField("Please input replaceWords", 40);
		tf3.setFont(new Font("宋体", Font.PLAIN, 25));
		tf3.setBounds(200, 15, 550, 126);
		tf4 = new JTextField("Please input absolute_path", 40);
		tf4.setFont(new Font("宋体", Font.PLAIN, 25));
		tf4.setBounds(200, 15, 550, 126);
		tf5 = new JTextField("Please input target_path", 40);
		tf5.setFont(new Font("宋体", Font.PLAIN, 25));
		tf5.setBounds(200, 15, 550, 126);
		tf6 = new JTextField("Please input filetype", 40);
		tf6.setFont(new Font("宋体", Font.PLAIN, 25));
		tf6.setBounds(200, 15, 550, 126);
		// 把按钮和文本框添加上
		f.add(tf1);
		f.add(tf2);
		f.add(tf3);
		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(tf4);
		f.add(tf5);
		f.add(tf6);
		f.add(b4);
		f.add(b5);
		f.add(b6);
		// 调用事件监听函数
		myEvent();
		f.setVisible(true);
	}

	private void myEvent() {
		// 点击右上角×退出
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// 点击第一个按钮的响应事件
		b1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				s1 = tf1.getText();
				// 对文本框内值进行判断，如果什么也没写，当做空处理，以下的类似
				if (s1.equals("Please input path")) {
					s1 = "";
				}
				File file = new File(s1);
				String test[];
				test = file.list();
				RenameFunction.test1 = test;
				String s2 = tf2.getText();
				if (s2.equals("Please input replaceWords")) {
					s2 = "";
				}
				String s3 = tf3.getText();
				if (s3.equals("Please input replaceWords")) {
					s3 = "";
				}
				try {
					// 启动重命名函数
					RenameFunction.sure(s1, s2, s3);
				} catch (Exception e1) {
				}
			}
		});
		// 点击第二个按钮的响应事件
		b2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					try {
						if (!s1.equals("Please input path")) {
							// 启动撤回
							RevokeRename.revoke(s1);
						}
					} catch (Exception e2) {
					}
				} catch (Exception e1) {
				}
			}
		});
		// 点击第三个按钮的响应事件
		b3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0); // 退出
			}
		});
		// 点击第四个按钮的响应事件
		b4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s2 = tf4.getText();
				String s3 = tf5.getText();
				String s4 = tf6.getText();
				if (s2.equals("Please input absolute_path")) {
					s2 = "";
				}
				if (s3.equals("Please input target_path")) {
					s3 = "";
				}
				if (s4.equals("Please input filetype")) {
					s4 = "";
				}
				// 启动文件搜索函数
				SearchFileFunction.startCopy(s2, s3, s4);
			}
		});
		// 点击第五个按钮的响应事件
		b5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String s2 = tf5.getText();
				// 启动撤回函数
				RemoveTargetFile.startDelete(s2);
			}
		});
		// 点击第六个按钮的响应事件
		b6.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0); // 退出
			}
		});
	}
}