import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IOTest_In_Out {

	public static void main(String[] args) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			File readFile = new File("c:\\Test", "a.jpg");
			File writeFile = new File("c:\\Test", "b.jpg");
			int size = (int)readFile.length();
			byte[] readByte = new byte[size];
			fis = new FileInputStream(readFile);
			fos = new FileOutputStream(writeFile);
			int n = fis.read(readByte);
			fos.write(readByte);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				fis.close();
				fos.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

	}

}
