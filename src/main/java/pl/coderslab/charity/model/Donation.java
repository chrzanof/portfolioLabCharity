package pl.coderslab.charity.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "donation")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer quantity;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "donation_category", joinColumns = @JoinColumn(name = "donation_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categories;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="institution_id")
    Institution institution;

    String street;
    String city;

    @Length(max = 6)
    String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate pickUpDate;

    LocalTime pickUpTime;

    String pickUpComment;


}
