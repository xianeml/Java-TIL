import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.print.attribute.standard.PrinterState;

public class IOTest4 {

	public static void main(String[] args) {
		//표준입력==>키보드로 입력
		
		System.out.println("데이터 입력:");
		InputStream is = System.in; //표준입력
		InputStreamReader reader = null;
		BufferedReader buffer = null;
		 try {
		  //필터사용1: byte->char로 변경
	      reader = new InputStreamReader(is);		
	     
	      //필터사용2: 한 줄(line) 읽기
	      buffer = new BufferedReader(reader);
	      String mesg = buffer.readLine();
	      System.out.println(mesg); //표준출력
//	      PrintStream xxx = System.out;
//	      xxx.println("asdfs");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				buffer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}//end main
}//end class
