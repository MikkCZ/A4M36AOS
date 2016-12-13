package cz.cvut.fel.aos.resource.params;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StringDto implements Serializable {

    @Getter
    @Setter
    private String value;
}
