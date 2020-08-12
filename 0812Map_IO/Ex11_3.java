import java.io.IOException;
import java.io.InputStreamReader;

public class Ex11_3 {

	public static void main(String[] args) {
		
		InputStreamReader reader = null;
		try {
			System.out.println("데이터를 입력하시오");
			reader = new InputStreamReader(System.in);
			int n = reader.read();
			System.out.println("입력값: "+(char)n);
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(reader != null)reader.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
