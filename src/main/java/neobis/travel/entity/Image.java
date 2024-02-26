package neobis.travel.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "images")
@Entity
public class Image {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trips_gen")
    @SequenceGenerator(name = "trips_gen",
            sequenceName = "trips_seq",
            allocationSize = 1,
            initialValue = 1)
    private  Long id;
    @Column(name = "url_image")
    private String url;
}