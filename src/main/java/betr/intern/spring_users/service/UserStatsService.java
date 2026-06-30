package betr.intern.spring_users.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStatsService {

    private final Map<String,Integer> stats = new ConcurrentHashMap<>();

    public void incrementCount(String username){
        stats.merge(username,1,Integer::sum);
    }

    public Map <String,Integer> getStats(){
        return stats;
    }

    public void resetStats(){
        stats.clear();
    }
}
