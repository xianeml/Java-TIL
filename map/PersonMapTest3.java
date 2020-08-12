import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PersonMapTest3 {
	public static void main(String[] args) {
		
		HashMap<String, Person> map = new HashMap<>();
		map.put("one", new Person("홍길동", 20, "서울"));
		map.put("two", new Person("이순신", 30, "경기"));
		map.put("three", new Person("유관순", 40, "서울"));
		HashMap<String, Person> map2 = new HashMap<>();
		map2.put("one", new Person("홍길동2", 20, "서울2"));
		map2.put("two", new Person("이순신2", 30, "경기2"));
		map2.put("three", new Person("유관순2", 40, "서울2"));
		
		ArrayList<HashMap<String, Person>> list = new ArrayList<HashMap<String, Person>>();
		list.add(map);
		list.add(map2);
		
		for (HashMap<String, Person> x : list) {
			Set<String> keys = x.keySet();
			for (String key : keys) {
				Person p = x.get(key);
				System.out.println(p.getName());
			}
		}
	}
}

