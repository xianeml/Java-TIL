package workshop07_7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MapTest {
	public static void main(String[] args) {
		//HashMap 생성
		HashMap<String, Mobile> map =
				new HashMap<String, Mobile>();
		double sum = 0.0;
		
		Mobile m1 = new Mobile("LU6800", "Optimus 2X", 800000.0);
		Mobile m2 = new Mobile("SU6600", "Optimus Black", 900000.0);
		Mobile m3 = new Mobile("KU5700", "Optimus Big", 700000.0);
		Mobile m4 = new Mobile("SU7600", "Optimus Mach", 950000.0);
		map.put(m1.getCode(), m1);
		map.put(m2.getCode(), m2);
		map.put(m3.getCode(), m3);
		map.put(m4.getCode(), m4);
		
		Set<String> keys = map.keySet();
		for(String key : keys) {
			Mobile p = map.get(key);
			System.out.println(p.printInfo());
			sum += p.getPrice();
		}System.out.println("합계 : "+sum);
		
		double sum2 = 0.0;
		for(String key : keys) {
			Mobile p = map.get(key);
			p.setPrice(p.getPrice() + (p.getPrice() * 0.1));
			System.out.println(p.printInfo());
			sum2 += p.getPrice();
		}System.out.println("합계 : "+sum2);
		
	}
}
