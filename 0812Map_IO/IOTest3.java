import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOTest3 {

	public static void main(String[] args) {
		//표준입력==>키보드로 입력 한글입력가능 (byte->char로 변환)InputStreamReader사용
		//read()함수 사용		
		System.out.println("데이터 입력:");
		InputStream is = System.in;
		InputStreamReader reader = null;
		 try {
		  reader = new InputStreamReader(is);	 //필터사용: byte->char로 변경	
	      int n = reader.read();
	      System.out.println((char)n);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}//end main
}//end class
