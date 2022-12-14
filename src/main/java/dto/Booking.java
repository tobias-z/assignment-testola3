package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Booking {

    private Integer id;
    private Integer customerId;
    private Integer employeeId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

}