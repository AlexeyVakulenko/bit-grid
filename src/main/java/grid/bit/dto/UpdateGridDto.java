package grid.bit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonDeserialize
@JsonSerialize
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGridDto {
    private String name;
}
