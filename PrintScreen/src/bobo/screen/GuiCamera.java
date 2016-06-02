package bobo.screen;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

/*******************************************************************************
 * 该JavaBean可以直接在其他Java应用程序中调用，实现屏幕的"拍照" This JavaBean is used to snapshot the
 * GUI in a Java application! You can embeded it in to your java application
 * source code, and us it to snapshot the right GUI of the application
 * 
 * @see javax.ImageIO
 * @version 1.0
 * @author other
 * 
 ******************************************************************************/

public class GuiCamera {

	private String fileName; // 文件的前缀

	private String defaultName = "GuiCamera";

	private String imageFormat; // 图像文件的格式

	private String defaultImageFormat = "png";

	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	static public  SimpleDateFormat sdf2second = new SimpleDateFormat("yyyyMMddHHmmss");
	
	static public SimpleDateFormat sdf2day = new SimpleDateFormat("yyyyMMdd");
	/***************************************************************************
	 * 默认的文件前缀为GuiCamera，文件格式为PNG格式 The default construct will use the default
	 * Image file surname "GuiCamera", and default image format "png"
	 **************************************************************************/
	public GuiCamera() {
		fileName = defaultName;
		imageFormat = defaultImageFormat;

	}

	/***************************************************************************
	 * @param s
	 *            the surname of the snapshot file
	 * @param format
	 *            the format of the image file, it can be "jpg" or "png" or "bmp"
	 *            本构造支持JPG和PNG或BMP文件的存储
	 **************************************************************************/
	public GuiCamera(String s, String format) {

		fileName = s;
		imageFormat = format;
	}

	/***************************************************************************
	 * 对屏幕进行拍照 snapShot the Gui once
	 **************************************************************************/
	public void snapShot(String message) throws Exception{

		try {
			// 拷贝屏幕到一个BufferedImage对象screenshot
			BufferedImage screenshot = (new Robot())
					.createScreenCapture(new Rectangle(0, 0,
							(int) d.getWidth(), (int) d.getHeight()));
			// 根据文件前缀变量和文件格式变量，自动生成文件名
			String name = fileName + String.valueOf(sdf2second.format(new Date())) +"_"+message+ "."
					+ imageFormat;
			File f = new File(name);
			File parent = f.getParentFile();
			if(!parent.exists()){
				parent.mkdirs();
			}
			f.createNewFile();
			System.out.print("Save File " + name);
			// 将screenshot对象写入图像文件
			ImageIO.write(screenshot, imageFormat, f);
			System.out.print("..Finished!\n");
		}catch(IOException fnfe){
			throw new Exception("截图失败，请不要输入 \\ / : * ? \" < > | 之类特殊字符");
		}catch (Exception ex) {
			throw new Exception("截图失败");
		}
	}

	public static String printScreen(String message) throws Exception{
		GuiCamera cam = new GuiCamera("d:/diffFile/"+sdf2day.format(new Date())+"/picture/", "png");//
		cam.snapShot(message);
		return "success";
	}
	
	
	public static void main(String[] args) {
		
//		GuiCamera cam = new GuiCamera("d:/diffFile/"+sdf2day.format(new Date())+"/picture/", "png");//
//		cam.snapShot("message");
	}
}