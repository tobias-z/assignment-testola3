package dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Employee {

    private Integer id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;

}
