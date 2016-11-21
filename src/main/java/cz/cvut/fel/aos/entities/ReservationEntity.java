package cz.cvut.fel.aos.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Entity(name = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class ReservationEntity implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Getter
    @Setter
    @Column(name = "seats")
    private int seats;

    @Getter
    @Setter
    @Column(name = "password")
    @Size(max = 255)
    private String password;

    @Getter
    @Setter
    @Column(name = "created")
    private Instant created;

    @Getter
    @Setter
    @ManyToOne(cascade = CascadeType.PERSIST)
    private FlightEntity flight;

    @Getter
    @Setter
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private ReservationState state;

}
