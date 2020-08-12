package workshop07_7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class MapTest_Sum {

	public static void main(String[] args) {
		HashMap<String, Integer> map =new HashMap<>();
		int sum = 0;
		for(int i = 1; i<=10; i++) {
			map.put(i+"", i);
		}
		
		Set<String> keys = map.keySet();
		for(String key : keys) {
			sum += map.get(key);
		}
		System.out.println("==============="+sum);
		Iterator<String> itr = keys.iterator();
		sum = 0;
		while(itr.hasNext()) {
			String key = itr.next();
			sum += map.get(key);
		}
		System.out.println("============"+sum);
	}

}
