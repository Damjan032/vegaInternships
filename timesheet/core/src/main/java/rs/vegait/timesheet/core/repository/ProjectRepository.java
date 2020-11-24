package rs.vegait.timesheet.core.repository;


import rs.vegait.timesheet.core.model.Page;
import rs.vegait.timesheet.core.model.project.Project;

import java.sql.SQLException;
import java.util.Optional;

public interface ProjectRepository extends CoreRepository<Project> {
    Optional<Project> findByName(String name) throws Exception;

    Page<Project> findBy(String s, char c, int pageNumber, int pageSize) throws Exception;
}
