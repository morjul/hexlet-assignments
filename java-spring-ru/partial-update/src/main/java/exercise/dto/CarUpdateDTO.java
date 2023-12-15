package exercise.dto;

import jakarta.validation.constraints.*;
import org.openapitools.jackson.nullable.JsonNullable;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Setter
@Getter
public class CarUpdateDTO {

    private JsonNullable<String> model;

    private JsonNullable<String> manufacturer;

    private JsonNullable<Integer> enginePower;
}
// END
