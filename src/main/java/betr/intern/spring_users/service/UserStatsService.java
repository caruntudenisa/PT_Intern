package betr.intern.spring_users.service;

import org.yaml.snakeyaml.events.Event;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStatsService {


  private final Map<Long, Integer> stats = new ConcurrentHashMap<>();


  public void incrementCount(final Long userId) {
    stats.merge(userId, 1, Integer::sum);
  }

  public Map<Long, Integer> getStats() {
    return stats;
  }

  public void resetStats() {
    stats.clear();
  }

}
