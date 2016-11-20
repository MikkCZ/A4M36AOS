package cz.cvut.fel.aos.resource.params.pagination;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Pagination {

    private final int base;
    private final int offset;

    public static Optional<Pagination> fromHeaders(Integer xBase, Integer xOffset) {
        if (xBase == null || xOffset == null) {
            return Optional.empty();
        } else {
            return Optional.of(new Pagination(xBase, xOffset));
        }
    }
}
