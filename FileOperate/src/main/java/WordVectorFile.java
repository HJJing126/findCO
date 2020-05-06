

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class WordVectorFile {
	
	static HashMap<String,String> vectorMap = new HashMap<String, String>();
	
	
	
	
	public WordVectorFile(){
		
	}
	
	public static void main(String[] args) throws IOException{
		//FirstAdjustFile("/Users/huangjingjing06/Desktop/testori");
		//System.out.println("Finish ---- FirstAdjustFile");
		loadWorVec(args[0]);//词向量字典
		System.out.println("Finish ---- loadWorVec");
		transFileVec(args[1]);//要向量化的数据集
		System.out.println("Finish ---- transFileVec");
		
	}
	
	
	/**
	 * 将单词中的空格变为-，将分割符变为空格
	 * @param filename
	 * @throws IOException
	 */
	public static void FirstAdjustFile(String filename)throws IOException {
		FileInputStream filein = new FileInputStream(filename);
		BufferedReader buffers = new BufferedReader(new InputStreamReader(filein));
		String line = null;
		
		//写文件
		File fileout =new File("/Users/huangjingjing06/Desktop/playerword");
		if(!fileout.exists()) {
			fileout.createNewFile();
			if(fileout.createNewFile()){
				fileout.setExecutable(true);//设置可执行权限  
				fileout.setReadable(true);//设置可读权限  
				fileout.setWritable(true);//设置可写权限  
			}
				
		}			
		FileWriter fileWritter = new FileWriter(fileout.getName(), true);		
		
		
		while((line = buffers.readLine()) != null) {
			StringBuilder newLine = new StringBuilder();
			String[]      msg     = line.split(",");
			int i = 0;
			for(i = 0 ; i < msg.length-1; i++){
				String s = "null";
				if(!msg[i].equals(" ")) {
				  s  = msg[i].replaceAll(" ", "-");
				}
				newLine.append(s).append(" ");
				
			}
			String s  = msg[i].replaceAll(" ", "-");
			newLine.append(s).append("\r\n");
			System.out.println("now :"+newLine);
			fileWritter.write(newLine.toString());
			fileWritter.flush();
		
		}
		
		fileWritter.close();
		filein.close();

		
	}
	
	public static void loadWorVec(String filename) throws IOException {
		FileInputStream filein = new FileInputStream(filename);
		BufferedReader buffers = new BufferedReader(new InputStreamReader(filein));
		String line = null;
		
		//记录所有词的vector
		while((line = buffers.readLine()) != null) {
			StringBuilder newLine = new StringBuilder();
			String[]      msg     = line.split(" ");
			StringBuilder s = new StringBuilder();
			String word = msg[0];
			int i=1;
			for(i=1;i<msg.length-1;i++) {
				s.append(msg[i]).append(",");
			}
			s.append(msg[i]);
			vectorMap.put(word,s.toString());
			System.out.println("word: "+ word+"\n vector: "+s.toString());
		}
		filein.close();
		
		
		
	}
	public static void transFileVec(String filename) throws IOException{
		FileInputStream filein = new FileInputStream(filename);
		BufferedReader buffers = new BufferedReader(new InputStreamReader(filein));
		String line = buffers.readLine();//抛弃第一行
		
		//写文件
		File fileout =new File(filename+"-vector");
		if(!fileout.exists()) {
			fileout.createNewFile();
			if(fileout.createNewFile()){
				fileout.setExecutable(true);//设置可执行权限  
				fileout.setReadable(true);//设置可读权限  
				fileout.setWritable(true);//设置可写权限  
			}
				
		}			
		FileWriter fileWritter = new FileWriter(fileout.getName(), true);		
		
		
		
		while((line = buffers.readLine()) != null) {
			StringBuilder newLine = new StringBuilder();
			String[]      msg     = line.split(" ");
			for(int i=0;i<msg.length;i++) {
				if(!vectorMap.containsKey(msg[i])) {
					System.out.println("ERROR :   String Wrong   "+msg[i]);
				}else {
					String vec = vectorMap.get(msg[i]);
					if(i<msg.length-1) {
						newLine.append(vec).append(",");
					}else {
						newLine.append(vec);
					}
				}
				
			}
			fileWritter.write(newLine.toString()+"\r\n");
			fileWritter.flush();
		    
				
		}
		fileWritter.close();
		filein.close();
		
	}

}
