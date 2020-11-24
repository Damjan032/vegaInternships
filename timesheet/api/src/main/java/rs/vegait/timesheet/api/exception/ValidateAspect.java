package rs.vegait.timesheet.api.exception;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ValidateAspect {

    @Before("execution(public * rs.vegait.timesheet.api.controller.*.*(..)) && args(.., errors)")
    public void beforeController(Errors errors) {
        if (errors != null && errors.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (FieldError fe : errors.getFieldErrors()) {
                errorMessages.add(fe.getField() + " " + fe.getDefaultMessage());
            }
            throw new BadRequestException(new ErrorMessages(400, "Bad Request", errorMessages));
        }
    }
}
