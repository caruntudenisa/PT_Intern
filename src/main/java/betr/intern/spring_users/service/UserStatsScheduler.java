package betr.intern.spring_users.service;

import betr.intern.spring_users.model.UserStats;
import java.time.OffsetDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserStatsScheduler {

  private static final Logger logger = LoggerFactory.getLogger(UserStatsScheduler.class);
  private final UserStatsService userStatsService;

  public UserStatsScheduler(final UserStatsService userStatsService) {
    this.userStatsService = userStatsService;
  }

  @Scheduled(cron = "0 * * * * *")
  public void logCurrentStats() {
    final Map<Long, UserStats> stats = userStatsService.getStats();

    if (stats.isEmpty()) {
      logger.info("no stats.");
      return;
    }
    printStats(stats);
  }

  private void printStats(final Map<Long, UserStats> stats) {
    stats.forEach(
        (userId, userStats) -> {
          logger.info("user with id={} has been searched for {} times", userId, userStats.count());
        });
  }

  @Scheduled(cron = "0 */5 * * * *")
  public void cleanOldStats() {
    logger.info("cleaning stats");
    final OffsetDateTime twoMinutesAgo = OffsetDateTime.now().minusMinutes(2);

    userStatsService.removeStatsOlderThan(twoMinutesAgo);
    logger.info("stats older than 2 minutes have been removed.");
  }
}
