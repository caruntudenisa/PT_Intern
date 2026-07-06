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

  private static final Logger logger =
      LoggerFactory.getLogger(UserStatsScheduler.class); // initialized logger for sending messages
  private final UserStatsService userStatsService;

  public UserStatsScheduler(final UserStatsService userStatsService) {
    this.userStatsService = userStatsService;
  }

  @Scheduled(cron = "0 * * * * *")
  // cron expression used to make it run in every second of the starting minute
  // puteam face si cu fixed rate = 60000 pointing the ms that i have
  // the structure of cron: s,m,h,d,m,dayofweek

  public void logCurrentStats() {
    logger.info("report");
    final Map<Long, UserStats> stats = userStatsService.getStats();

    if (stats.isEmpty()) {
      logger.info("no stats."); // for users that were never searched
      return;
    }
    stats.forEach(
        (userId, userStats) -> {
          System.out.println(
              "user with id="
                  + userId
                  + " has been searched for "
                  + userStats.count()
                  + " times"); // it will show how many times it has been shown
        });
  }

  @Scheduled(cron = "0 */5 * * * *")
  public void cleanOldStats() {
    logger.info("cleaning stats");
    final OffsetDateTime twoMinutesAgo =
        OffsetDateTime.now()
            .minusMinutes(2); // i will delete all time records that are older than that

    userStatsService.removeStatsOlderThan(twoMinutesAgo);
    logger.info("stats older than 2 minutes have been removed.");
  }
}
