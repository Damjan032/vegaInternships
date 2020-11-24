package rs.vegait.timesheet.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotFoundException extends RuntimeException {
    private ErrorMessages errorMessages;

    public NotFoundException(String msg) {
        List<String> msgs = new ArrayList<>();
        msgs.add(msg);
        this.errorMessages = new ErrorMessages(404, "Not Found", msgs);
    }

    public NotFoundException(List<String> msgs) {
        this.errorMessages = new ErrorMessages(404, "Not Found", msgs);
    }
}
