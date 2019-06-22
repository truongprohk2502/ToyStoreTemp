package code.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String address;
}
