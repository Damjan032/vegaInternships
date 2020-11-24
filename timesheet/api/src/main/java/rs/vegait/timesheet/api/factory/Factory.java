package rs.vegait.timesheet.api.factory;

import rs.vegait.timesheet.api.dto.PageDto;
import rs.vegait.timesheet.core.model.Page;

import java.util.List;
import java.util.UUID;

public interface Factory<TDto, TEntity> {

    TEntity createFromDto(UUID id, TDto tDto);

    TDto toDto(TEntity entity);

    List<TDto> toListDto(Iterable<TEntity> iterable);

    PageDto<TDto> toDtoPage(Page<TEntity> page);
}
