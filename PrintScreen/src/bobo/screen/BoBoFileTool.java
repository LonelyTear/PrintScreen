package bobo.screen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class BoBoFileTool {
	
	public static String BASE_PATH ="d:/diffFile/";
	public static String HISTORY_HTML =BASE_PATH+"history/"+"history.html";
	public static File HISTORY_FILE =new File(HISTORY_HTML); 
	
	static{
		
		if(!(HISTORY_FILE.getParentFile().exists())){
			HISTORY_FILE.getParentFile().mkdirs();
			try {
				HISTORY_FILE.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static Boolean concatFileContent(List<File> filesList){
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < filesList.size() ; i++){
			File file = filesList.get(i);
			OutputStreamWriter osw = null;
			String line = null;
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(new  FileInputStream(file),"GBK"));
				while((line = br.readLine()) != null ){
					sb.append(line);
				}
				FileOutputStream fos = new FileOutputStream(HISTORY_FILE);//开始生成批量总历史Html
				osw = new OutputStreamWriter(fos,"GBK");
				osw.write(sb.toString());
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
				if(br != null){
					try {
						br.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		
			
			
		}
		return true;
	}
}
