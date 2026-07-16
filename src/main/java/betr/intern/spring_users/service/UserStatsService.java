package betr.intern.spring_users.service;

import betr.intern.spring_users.model.UserStats;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStatsService {

  private final Map<String, UserStats> stats = new ConcurrentHashMap<>();

  public void incrementCount(final String userId) {
    stats.compute(
        userId,
        (key, existingStats) -> {
          if (existingStats == null) {
            return new UserStats(1, OffsetDateTime.now());
          } else {
            return new UserStats(existingStats.count() + 1, OffsetDateTime.now());
          }
        });
  }

  public Map<String, UserStats> getStats() {
    return stats;
  }

  public void resetStats() {
    stats.clear();
  }

  public void removeStatsOlderThan(final OffsetDateTime threshold) {
    stats.entrySet().removeIf(entry -> entry.getValue().lastUpdated().isBefore(threshold));
    // method for deleting all values that take longer than the threshold, before it
  }
}
