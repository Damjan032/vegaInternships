package rs.vegait.timesheet.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private ErrorMessages errorMessages;

    public NotFoundException(String msg) {
        this.errorMessages = new ErrorMessages(404, "Not Found", msg);
    }

}
