package rs.vegait.timesheet.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ErrorMessages {
    private Integer code;
    private String status;
    private List<String> messages;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date timestamp;

    public ErrorMessages(Integer code, String status, List<String> messages) {
        this.code = code;
        this.status = status;
        this.messages = messages;
        this.timestamp = new Date();
    }
}
