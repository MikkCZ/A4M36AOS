package cz.cvut.fel.aos.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Entity(name = "flight")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class FlightEntity implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Getter
    @Setter
    @Column(name = "dateOfDeparture")
    private Instant departure;

    // TODO id in (de)serialization only
    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.PERSIST)
    private DestinationEntity from;

    // TODO id in (de)serialization only
    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.PERSIST)
    private DestinationEntity to;

    @Getter
    @Setter
    @Column(name = "distance")
    private float distance;

    @Getter
    @Setter
    @Column(name = "price")
    private float price;

    @Getter
    @Setter
    @Column(name = "seats")
    private int seats;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Getter
    @Setter
    @OneToMany(mappedBy = "flight")
    private Set<ReservationEntity> reservations;

    @Getter
    private String url;

    @PostPersist
    @PostLoad
    @PostUpdate
    public void updateUrl() {
        this.url = String.format("/destination/%d", id);
    }

}
