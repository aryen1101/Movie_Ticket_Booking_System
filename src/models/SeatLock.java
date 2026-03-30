package models;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SeatLock {

    private final String userId;
    private final Instant lockedTimestamp;

    public SeatLock(String userId) {
        this.userId = userId;
        this.lockedTimestamp = Instant.now();
    }

    public String getUserId() {
        return userId;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(lockedTimestamp.plus(5, ChronoUnit.MINUTES));
    }

}
