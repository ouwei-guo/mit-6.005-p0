package twitter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ouwei
 */
public class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        // sort in ascending order
        //list.sort((o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        
        // sort in descending order
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        
        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            //System.out.println(entry.getKey()+": "+entry.getValue());
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}