import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class Ex10_11 {
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", "홍길동");
		map.put("age", "20");
		map.put("adress", "서울");
		map.put("email", "null");
		
		System.out.println("이름은 : "+map.get("name"));
		System.out.println("나이는 : "+map.get("age"));
		System.out.println("주소는 : "+map.get("name"));
		System.out.println("email은 : "+map.get("email"));
		
		System.out.println("데이터의 크기(길이)는? : "+map.size());
		System.out.println("이름은 : "+map.isEmpty());
		System.out.println("age 키가 있나? : "+map.containsKey("age"));
		System.out.println("홍길동 값이 있나? : "+map.containsValue("홍길동"));
		System.out.println("age 키에 해당하는 데이터 삭제");
		map.remove("age");
		
		System.out.println("전체 데이터 출력: "+map);
		
		Set<String> keys = map.keySet();
		System.out.println(keys);
		for (String key : keys) {
			System.out.println(key+"="+map.get(key));
		}
		Iterator<String> ite = keys.iterator();
		while(ite.hasNext()) {
			String key = ite.next();
			System.out.println(key + "\t" +map.get(key));
		}
	}
}
