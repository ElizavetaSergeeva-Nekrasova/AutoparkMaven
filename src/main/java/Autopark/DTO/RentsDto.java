package Autopark.DTO;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class RentsDto {
    private Long id;
    private Long vehicleId;
    private Date date;
    private Double cost;
}