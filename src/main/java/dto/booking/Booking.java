package dto.booking;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
public class Booking {

    private Integer id;
    private Integer customerId;
    private Integer employeeId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

}