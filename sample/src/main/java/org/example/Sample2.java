package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Sample2 {
    public static void main(String[] args) {
//        String s = "Suraj Nagre";
//        HashSet<String> hs = new HashSet<>();
//        hs.add(s);
//        List<String> list = new ArrayList<>(hs);
//        List result = list.stream().filter(a -> a.contains(hs.iterator().next())).toList();
//        System.out.println(result);

        String s = "Suraj Nagre";
        String s1 = Arrays.toString(s.split(" "));
        String[] arr = s1.split("");
        String res = Arrays.stream(arr).distinct().collect(Collectors.joining());
        System.out.println(res);

        //map -> key int, value -employee<id, name, salary>

//        Map<Integer, Employee> map = new HashMap<>();
//        List<Employee> result = map.entrySet().stream().filter(Map.Entry.getValue().CompairingBy(Employee::getSalary).limit(3)).toList;
    }
}
