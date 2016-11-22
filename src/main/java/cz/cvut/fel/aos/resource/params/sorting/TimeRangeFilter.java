package cz.cvut.fel.aos.resource.params.sorting;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
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
                                ZonedDateTime.parse( split[0].split("=")[1] ),
                                ZonedDateTime.parse( split[1].split("=")[1] )
                        )
                );
            }
        }
        return Optional.empty();
    }
}
