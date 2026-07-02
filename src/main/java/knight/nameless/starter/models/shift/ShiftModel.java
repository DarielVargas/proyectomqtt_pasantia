package knight.nameless.starter.models.shift;

import java.time.Duration;
import java.util.List;

public interface ShiftModel {

    int getId();

    Duration getStartTime();

    Duration getEndTime();

    List<Integer> getDaysOfWeek();

}
