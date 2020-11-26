package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.vegait.timesheet.api.dto.DailyTimeSheetDto;
import rs.vegait.timesheet.api.factory.DailyTimeSheetFactory;
import rs.vegait.timesheet.api.factory.TimeSheetFactory;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;
import rs.vegait.timesheet.core.repository.EmployeeRepository;
import rs.vegait.timesheet.core.service.DailyTimeSheetService;

import javax.websocket.server.PathParam;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/timesheets")
public class TimeSheetController {
    private final TimeSheetFactory timeSheetFactory;
    private final EmployeeRepository employeeRepository;
    private final DailyTimeSheetService dailyTimeSheetService;
    private final DailyTimeSheetFactory dailyTimeSheetFactory;
    private final DailyTimeSheetRepository dailyTimeSheetRepository;

    public TimeSheetController(TimeSheetFactory timeSheetFactory, EmployeeRepository employeeRepository, DailyTimeSheetService dailyTimeSheetService, DailyTimeSheetFactory dailyTimeSheetFactory, DailyTimeSheetRepository dailyTimeSheetRepository) {
        this.timeSheetFactory = timeSheetFactory;
        this.employeeRepository = employeeRepository;
        this.dailyTimeSheetService = dailyTimeSheetService;
        this.dailyTimeSheetFactory = dailyTimeSheetFactory;
        this.dailyTimeSheetRepository = dailyTimeSheetRepository;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<DailyTimeSheetDto>> getForEmployee(@PathParam("employeeId") String employeeId,
                                                                      @PathParam("dateFrom") String dateFrom,
                                                                      @PathParam("dateTo") String dateTo) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Iterable<DailyTimeSheet> dailyTimeSheets = this.dailyTimeSheetService.findTimeSheetSet(employeeId,
                sdf.parse(dateFrom), sdf.parse(dateTo));

        Iterable<DailyTimeSheetDto> iterable = this.dailyTimeSheetFactory.toListDto(dailyTimeSheets);
        return new ResponseEntity<>(iterable, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DailyTimeSheetDto> addTimeSheet(@RequestBody DailyTimeSheetDto dailyTimeSheetDto,
                                                          @PathParam("employeeId") String employeeId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Optional<Employee> employee = this.employeeRepository.findById(UUID.fromString(employeeId));
        if (employee.isPresent()) {
            UUID uuid = UUID.randomUUID();
            Iterable<TimeSheet> timeSheets = this.timeSheetFactory.toList(dailyTimeSheetDto.getTimeSheets());
            DailyTimeSheet dailyTimeSheet =
                    new DailyTimeSheet(uuid, employee.get(), sdf.parse(dailyTimeSheetDto.getDay()),
                            timeSheets);
            this.dailyTimeSheetService.createOrUpdate(dailyTimeSheet,
                    employee.get());

        } else throw new InvalidKeyException("Invalid employeeId");
        Optional<DailyTimeSheet> dailyTimeSheet = this.dailyTimeSheetService.findByEmployeeAndData(employeeId, sdf.parse(dailyTimeSheetDto.getDay()));
        return new ResponseEntity<>(this.dailyTimeSheetFactory.toDto(dailyTimeSheet.get()), HttpStatus.OK);
    }


}
