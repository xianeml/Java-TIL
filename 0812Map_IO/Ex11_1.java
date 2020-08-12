import java.io.IOException;
import java.io.InputStream;

public class Ex11_1 {

	public static void main(String[] args) {
		//표준입력 ==> 키보드로 입력 byte단위 한 글자 입력
		System.out.println("데이터를 입력하시오");
		InputStream is = System.in;
		try {
			int n = is.read();
			System.out.println((char)n);
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(is!=null) is.close();
			}catch (IOException e) {
				e.getStackTrace();
			}
		}
		
	}

}
