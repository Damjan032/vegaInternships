package rs.vegait.timesheet.core.repository;

import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Category;

import java.sql.SQLException;
import java.util.Optional;

public interface CategoryRepository extends CoreRepository<Category> {
    Page<Category> findBy(String searchString, char firstLetter, int pageNumber, int pageSize) throws SQLException;

    Optional<Category> findByName(String name) throws SQLException;
}
