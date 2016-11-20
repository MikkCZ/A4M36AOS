package cz.cvut.fel.aos.resource.params.sorting;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderBy {

    private final String field;
    private final Order order;

    public static Optional<OrderBy> fromXOrder(String xOrderHeader) {
        if (xOrderHeader != null) {
            String[] split = xOrderHeader.split(":");
            if (split.length == 2) {
                return Optional.of(new OrderBy(split[0], Order.valueOf(split[1].toUpperCase())));
            }
        }
        return Optional.empty();
    }
}
