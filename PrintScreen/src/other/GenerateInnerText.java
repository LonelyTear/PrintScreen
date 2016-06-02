package other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
/**
 * 主要用于把<>"之类的特殊字符转换成innerHtml里面的&amp;&gt;&lt;<br/>等形式
 */
public class GenerateInnerText {

	public static void main(String[] args) throws Exception{
		File f = new File("src/input.txt");
		BufferedReader br = new BufferedReader(new FileReader(f));
		String s = null;
		StringBuffer sb  = new StringBuffer();
		while( (s = br.readLine() ) != null){
			int count = s.split("\t").length;
			String pre = "";
			for(int i = 0 ; i < count ; i++){
				pre +="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
			String line = pre+s.trim();
			line = line.replace("\"", "&quot;");
			line = line.replace("<", "&lt;");
			line = line.replace(">", "&gt;");
			sb.append(line+"<br/>\n");
		}
		String str = sb.toString();
		str = str.substring(0, str.length()-1);
		System.out.println(str);
		
	}
	
	public void method1() throws Exception{
		File f = new File("src/input.txt");
		Scanner s = new Scanner(f);
		StringBuffer sb  = new StringBuffer();
		while(s.hasNext()){
			String line = s.next();
			line = line.replace("\"", "&quot;");
			line = line.replace("<", "&lt;");
			line = line.replace(">", "&gt;");
			sb.append(line+"<br/>\n");
		}
		String str = sb.toString();
		str = str.substring(0, str.length()-1);
		System.out.println(str);
	}
}
