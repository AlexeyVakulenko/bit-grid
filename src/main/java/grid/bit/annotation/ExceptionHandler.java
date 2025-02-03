package grid.bit.annotation;

import grid.bit.dto.ErrorResponse;
import grid.bit.exception.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @Around("@annotation(ExceptionHandling)")
    public Object handle(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (ConstraintViolationException | ValidationException e) {
            log.error("Validation failed {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed", e));
        }
    }
}
