package grid.bit.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ErrorResponse {
    private final String message;
    private final Exception e;
}
