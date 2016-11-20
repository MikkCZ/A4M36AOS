package cz.cvut.fel.aos.resource.pages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Page<ENTITY> {

    private List<ENTITY> results;
    private Long count;
}
