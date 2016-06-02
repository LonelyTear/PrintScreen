package other;

import java.io.File;
import java.util.Scanner;
/**
 * 主要用于把<>"之类的特殊字符转换成js里面的&amp;&gt;&lt;等形式
 */
public class GenerateJSVariable {

	public static void main(String[] args) throws Exception{
		File f = new File("src/input.txt");
		Scanner s = new Scanner(f);
		StringBuffer sb  = new StringBuffer();
		sb.append("var variable =");
		while(s.hasNext()){
			String line = "@#";
			line += s.next();
			line += "#@";
			line = line.replace("\"", "&quot;");
			line = line.replace("@#","\"");
			line = line.replace("#@","\"");
			line = line.replace("<", "&lt;");
			line = line.replace(">", "&gt;");
			line +="+";
			sb.append(line+"\n");
			
		}
		String str = sb.toString();
		str = str.substring(0, str.length()-2);
		str +=";";
		System.out.println(str);
		
	}
}
