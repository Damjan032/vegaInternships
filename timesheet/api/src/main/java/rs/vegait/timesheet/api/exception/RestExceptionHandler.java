package rs.vegait.timesheet.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RestExceptionHandler {



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessages> processRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(new BadRequestException(ex.getMessage()).getErrorMessages(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorMessages> processSQLException(SQLException ex) {
        return new ResponseEntity<>(new NotFoundException(ex.getMessage()).getErrorMessages()
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessages> processNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getErrorMessages(), HttpStatus.NOT_FOUND);
    }

}
