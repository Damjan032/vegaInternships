package rs.vegait.timesheet.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PageDto<T> {
    @NotBlank
    private Iterable<T> iterable;
    @NotBlank
    private int pageNumber;

    @NotBlank
    private int pageSize;

    @NotBlank
    private int totalSize;

    public PageDto(Iterable<T> items, int pageNumber, int pageSize, int totalSize) {
        this.iterable = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

}
