import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PersonMapTest2 {

	public static void main(String[] args) {
		
		ArrayList<Person> list1 = new ArrayList<Person>();
		list1.add(new Person("홍길동", 20, "서울"));
		list1.add(new Person("홍길동2", 30, "서울2"));
		list1.add(new Person("홍길동3", 40, "서울3"));
		
		ArrayList<Person> list2 = new ArrayList<Person>();
		list1.add(new Person("이순신", 20, "경기"));
		list1.add(new Person("이순신2", 30, "경기2"));
		list1.add(new Person("이순신3", 40, "경기3"));
		
		HashMap<String, ArrayList<Person>> map =
				new HashMap<String, ArrayList<Person>>();
		map.put("one", list1);
		map.put("two", list2);
		
		Set<String> keys = map.keySet();
		for(String key : keys) {
			ArrayList<Person> x = map.get(key);
			for(Person p : x) {
				System.out.println(p.getName()+"\t"+p.getAge()+"\t"+p.getAddress());
			}
		}
		
	}

}
