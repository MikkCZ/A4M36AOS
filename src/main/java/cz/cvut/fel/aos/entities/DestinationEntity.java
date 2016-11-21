package cz.cvut.fel.aos.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "destination")
@NoArgsConstructor @AllArgsConstructor @Builder
@EqualsAndHashCode(of = "id")
public class DestinationEntity implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "lat")
    private float latitude;

    @Getter
    @Setter
    @Column(name = "lon")
    private float longitude;

    @Getter
    @Setter
    @OneToMany(mappedBy = "from")
    private Set<FlightEntity> from;

}
