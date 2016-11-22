package cz.cvut.fel.aos.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.cvut.fel.aos.entities.jackson.FlightEntityIdResolver;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime created;

    @JsonIdentityInfo(
            generator=ObjectIdGenerators.PropertyGenerator.class,
            property="id",
            resolver = FlightEntityIdResolver.class
    )
    @JsonIdentityReference(alwaysAsId=true)
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
