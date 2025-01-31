package grid.bit.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonDeserialize
@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGridDto {
    @NotBlank
    @Size(min = 1, max = 200)
    private String name;
    @Positive
    @Max(100000)
    private int cellSize;
}
