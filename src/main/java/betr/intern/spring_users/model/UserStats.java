package betr.intern.spring_users.model;

import java.time.OffsetDateTime;

public record UserStats(int count, OffsetDateTime lastUpdated) {}
