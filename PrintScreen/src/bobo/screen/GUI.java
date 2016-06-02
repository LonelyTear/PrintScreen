package bobo.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 截图工具界面
 * @author BoBo
 *
 */
public class GUI {
	static String NEWLINE = "@_@";
	static JFrame frame = new JFrame("全屏截图&注释工具  "+BoBoFileTool.BASE_PATH);
	static JPanel mainPanel = new JPanel();
	static TextArea textArea = new TextArea(2, 100);

	static JButton but1 = new JButton("Ctrl+Enter截图");
	static JButton but2 = new JButton("查看今天增量");
	static JButton but3 = new JButton("生成当天HTML");
	static JButton but4 = new JButton("打开当天HTML");
	static JButton but5 = new JButton("打开图片目录");
	static JButton but6 = new JButton("合成所有历史HTML");
	static JTextArea retInfoArea = new JTextArea();
	static {
		textArea.setBackground(new Color(236,236,236));
		retInfoArea.setEditable(false);
		retInfoArea.setBackground(new Color(129, 196, 236));
		retInfoArea.setText("将按上排按钮从左到右依次点击一遍 , 即可明白如何使用 , 生成的目录地址为 "+BoBoFileTool.BASE_PATH);
		textArea.setFont(new Font("TimesRoman",Font.BOLD,16));
//		retInfoArea.setFont(new Font("TimesRoman",Font.BOLD,20));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent e) {
				System.out.println("icon");
//				frame.setState(JFrame.NORMAL); //最小化之后马上最大化
//				frame.show();					//还原窗口
			}
		});

		textArea.addKeyListener(new KeyAdapter() { //全屏截图
			@Override
			public void keyPressed(KeyEvent  e) {
				if((e.isControlDown())&&(e.getKeyCode() == KeyEvent.VK_ENTER)){
					frame.setExtendedState(JFrame.ICONIFIED);
					try{
						GuiCamera.printScreen(encodeStr(textArea.getText()));
					}catch(Exception e1){
						frame.setState(JFrame.NORMAL); //最小化之后马上最大化
						frame.setVisible(true);					//还原窗口
						retInfoArea.setText("");
						JOptionPane.showMessageDialog(null, e1.getMessage(), "截图失败", JOptionPane.ERROR_MESSAGE); 
						textArea.requestFocusInWindow();   //textArea获取焦点
						return;
					}
					retInfoArea.setText("已进行全屏截图");
				}
			}
		});
		
		but1.addActionListener(new ActionListener() { //全屏截图
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setExtendedState(JFrame.ICONIFIED);
				try{
					GuiCamera.printScreen(encodeStr(textArea.getText()));
				}catch(Exception e1){
					frame.setState(JFrame.NORMAL); //最小化之后马上最大化
					frame.setVisible(true);
					retInfoArea.setText("");
					JOptionPane.showMessageDialog(null, e1.getMessage(), "截图失败", JOptionPane.ERROR_MESSAGE); 
					textArea.requestFocusInWindow();   //textArea获取焦点
					return;
				}
				retInfoArea.setText("已进行全屏截图");
			}
		});
		
		but2.addActionListener(new ActionListener() {	//查看今天增量记录
			@Override
			public void actionPerformed(ActionEvent e) {
				File dest = new File(BoBoFileTool.BASE_PATH+GuiCamera.sdf2day.format(new Date())+"/html/"+
						GuiCamera.sdf2day.format(new Date())+".html");
				File parent = dest.getParentFile();
				File picture = new File(parent.getParent()+"/picture");
				try {
					StringBuilder strBuilder = new StringBuilder();
					if(picture.exists()){
						File[] files = picture.listFiles();
						for(int i = 0 ; i<files.length;i++ ){
							strBuilder.append(i+":\t"+files[i].getName().substring(15, files[i].getName().length()-4)).append("\n");
						}
						retInfoArea.setText(strBuilder.toString());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		
		but3.addActionListener(new ActionListener() {	//生成增量Html
			@Override
			public void actionPerformed(ActionEvent e) {
				File dest = new File(BoBoFileTool.BASE_PATH+GuiCamera.sdf2day.format(new Date())+"/html/"+
						GuiCamera.sdf2day.format(new Date())+".html");
				File parent = dest.getParentFile();
				File picture = new File(parent.getParent()+"/picture");
				OutputStreamWriter osw = null;
				if(!parent.exists()){
					parent.mkdirs();
				}
				try {
					FileOutputStream fos = new FileOutputStream(dest);//开始生成目标Html
					osw = new OutputStreamWriter(fos,"gbk");
					osw.write("<!DOCTYPE html>"+
								"<html>"+
								"<head>"+
								"<title>"+GuiCamera.sdf2day.format(new Date())+"</title>"+
								"</head>"+
								"<body>");
					osw.write("<font size='5' color='red'>"+GuiCamera.sdf2day.format(new Date())+"</font>");
					osw.write("<ol>");
					if(picture.exists()){
						for(File file :picture.listFiles()){
							osw.write("<li>");
							String fileNameBR = decodeStr(file.getName());//把\n解码成<br/>
							osw.write("<a href='"+picture+"/"+file.getName()+"'>"+fileNameBR.substring(15, fileNameBR.length()-3)+"</a>");
							osw.write("</li>");
						}
					}
					osw.write("</ol>");
					osw.write( 	"</body>"+
								"</html>");
								
				} catch (IOException e1) {
					e1.printStackTrace();
				}finally{
					if(osw != null){
						try {
							osw.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				retInfoArea.setText("已生成Html增量文件");
			}
		});
		
		but4.addActionListener(new ActionListener() { //打开当天增量网页
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					File dest = new File(BoBoFileTool.BASE_PATH+GuiCamera.sdf2day.format(new Date())+"/html/"+
							GuiCamera.sdf2day.format(new Date())+".html");
					Runtime.getRuntime().exec("cmd.exe /c start iexplore "+dest.getAbsolutePath());
				}catch (Exception e1){
					e1.printStackTrace();
				}
				retInfoArea.setText("已打开当天增量网页");
			}
		});
		
		but5.addActionListener(new ActionListener() { //打开网页
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					File dest = new File(BoBoFileTool.BASE_PATH+GuiCamera.sdf2day.format(new Date())+"/picture");
					Runtime.getRuntime().exec("cmd /c start explorer "+dest.getAbsolutePath());
				}catch (Exception e1){
					e1.printStackTrace();
					return;
				}
				retInfoArea.setText("已打开图片目录");
			}
		});
		
		but6.addActionListener(new ActionListener() { //合成所有历史HTML
			public void actionPerformed(ActionEvent e) {
				List<File> files = new ArrayList<File>();
				try{
					File dest = new File(BoBoFileTool.BASE_PATH);
					for(File yearFile :dest.listFiles()){
						if( (yearFile.getName().length() == 8)&&(yearFile.isDirectory()) ){
							for(File htmlDir :yearFile.listFiles()){
								if( (htmlDir.getName().length() == 4) && (htmlDir.isDirectory())&&(htmlDir.getName().endsWith("html")) ){
									for(File htmlFile :htmlDir.listFiles()){
										files.add(htmlFile);
									}
								}
							}
						}
					}
					BoBoFileTool.concatFileContent(files);
					Runtime.getRuntime().exec("cmd.exe /c start iexplore "+BoBoFileTool.HISTORY_FILE.getAbsolutePath());
				}catch (Exception e1){
					e1.printStackTrace();
				}
				retInfoArea.setText("已打开所有历史HTML");
			}
		});
	}

	public static String encodeStr(String input) throws Exception{
		String str = input.replace("\r\n", NEWLINE);
		if(str.length() < 15)
			throw new Exception("在文本域中至少输入3个字");
		return str;
	}
	
	public static String decodeStr(String str){
		return str.replace("@_@", "<br/>");
	}
	
	
	public static void main(String[] args) {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(new Color(205,228,247));
//		textArea.setRows(100);
//		textArea.setColumns(100);
		southPanel.add(but1);
		southPanel.add(but2);
		southPanel.add(but3);
		southPanel.add(but4);
		southPanel.add(but5);
		southPanel.add(but6);
		mainPanel.setLayout(new GridLayout(3, 1));
		
		
		mainPanel.add(textArea);
		mainPanel.add(southPanel);
		
		JScrollPane scrollPane=new JScrollPane(retInfoArea);
		mainPanel.add(scrollPane);
		frame.add(mainPanel);
		frame.setSize(new Dimension(800, 200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
//		frame.pack();
		frame.setVisible(true);
	}
}
