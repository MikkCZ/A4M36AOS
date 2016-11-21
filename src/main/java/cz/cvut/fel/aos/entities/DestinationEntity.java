package cz.cvut.fel.aos.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
    private String url;

    @Getter
    @Setter
    @Column(name = "lat")
    private float latitude;

    @Getter
    @Setter
    @Column(name = "lon")
    private float longitude;

    @PostPersist
    @PostLoad
    @PostUpdate
    public void updateUrl() {
        this.url = String.format("/destination/%d", id);
    }

}
