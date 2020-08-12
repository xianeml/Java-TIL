import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.print.attribute.standard.PrinterState;

public class IOTest5 {

	public static void main(String[] args) {
		
		
		// 파일입력/출력
		// 파일의 메타데이터 살펴보기
		// new File("디렉토리");
		// new File("파일명");
		// new File("디렉토리/파일명");
		// new File("디렉토리","파일명");
		
		//File f = new File("C:\\Users\\acorn\\IOTest.java");
		//File f = new File("c:\\Test","IOTest.java");
		File f = new File("c:\\Test","IOTest.java");
		
		System.out.println("파일크기: "+ f.length());
		System.out.println("파일이름: "+ f.getName());
		System.out.println("파일경로: "+ f.getPath());
		System.out.println("파일절대경로: "+ f.getAbsolutePath());
		System.out.println("파일이냐?: "+ f.isFile());
		System.out.println("디렉토리이냐?: "+ f.isDirectory());
		System.out.println("파일이 존재? "+ f.exists());
		System.out.println("실행가능하냐: "+ f.canExecute());
		System.out.println("write 가능?: "+ f.canWrite());
		System.out.println("read 가능?: "+ f.canRead());
		//System.out.println(f.delete());
		
        
	}//end main
}//end class
