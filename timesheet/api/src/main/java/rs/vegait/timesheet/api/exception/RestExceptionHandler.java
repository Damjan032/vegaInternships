package rs.vegait.timesheet.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceAlreadyExistsException;
import java.security.InvalidKeyException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)//Foreign key
    public ResponseEntity<ErrorMessages> processSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return new ResponseEntity<>(new ErrorMessages(409, "Conflict", ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessages> processIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorMessages(400, "Bad request", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InstanceAlreadyExistsException.class)
    public ResponseEntity<ErrorMessages> processInstanceAlreadyExistsException(InstanceAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorMessages(409, "Conflict", ex.getMessage()),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidKeyException.class)
    public ResponseEntity<ErrorMessages> processInvalidKeyException(InvalidKeyException ex) {
        return new ResponseEntity<>(new ErrorMessages(400, "Bad request", ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }


}
