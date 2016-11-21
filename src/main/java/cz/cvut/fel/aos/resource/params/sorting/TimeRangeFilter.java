package cz.cvut.fel.aos.resource.params.sorting;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class TimeRangeFilter {

    private final ZonedDateTime from;
    private final ZonedDateTime to;

    public static Optional<TimeRangeFilter> fromXFilter(String xFilterHeader) {
        if (xFilterHeader != null) {
            String[] split = xFilterHeader.split(",");
            if (split.length == 2) {
                return Optional.of(
                        new TimeRangeFilter(
                                ZonedDateTime.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse( split[0].split("=")[1] )),
                                ZonedDateTime.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse( split[1].split("=")[1] ))
                        )
                );
            }
        }
        return Optional.empty();
    }
}
