import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class IOTest8 {

	public static void main(String[] args) {
		File f = new File("C:\\Test\\xyz.txt");
		PrintWriter out = null;
		try {
			FileWriter writer = new FileWriter(f,true);
			//필터
			out = new PrintWriter(writer);
			out.print("happy");
			//out.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
	}

}
