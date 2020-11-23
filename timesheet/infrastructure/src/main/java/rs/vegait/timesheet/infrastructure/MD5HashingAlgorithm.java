package rs.vegait.timesheet.infrastructure;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import rs.vegait.timesheet.core.model.HashingAlgorithm;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

@Component
public class MD5HashingAlgorithm implements HashingAlgorithm {

    @SneakyThrows
    public String hash(String text) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }
}
