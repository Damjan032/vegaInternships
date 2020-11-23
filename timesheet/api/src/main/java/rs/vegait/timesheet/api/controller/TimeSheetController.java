package rs.vegait.timesheet.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.vegait.timesheet.api.dto.DailyTimeSheetDto;
import rs.vegait.timesheet.api.dto.TimeSheetDto;
import rs.vegait.timesheet.api.factory.DailyTimeSheetFactory;
import rs.vegait.timesheet.api.factory.TimeSheetFactory;
import rs.vegait.timesheet.core.model.employee.Employee;
import rs.vegait.timesheet.core.model.timesheet.DailyTimeSheet;
import rs.vegait.timesheet.core.model.timesheet.TimeSheet;
import rs.vegait.timesheet.core.repository.DailyTimeSheetRepository;
import rs.vegait.timesheet.core.repository.EmployeeRepository;
import rs.vegait.timesheet.core.service.DailyTimeSheetService;
import rs.vegait.timesheet.core.service.TimeSheetService;

import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/timesheets")
public class TimeSheetController {
    private final TimeSheetService timeSheetService;
    private final TimeSheetFactory timeSheetFactory;
    private final EmployeeRepository employeeRepository;
    private final DailyTimeSheetService dailyTimeSheetService;
    private final DailyTimeSheetFactory dailyTimeSheetFactory;
    private final DailyTimeSheetRepository dailyTimeSheetRepository;

    public TimeSheetController(TimeSheetService timeSheetService, TimeSheetFactory timeSheetFactory, EmployeeRepository employeeRepository, DailyTimeSheetService dailyTimeSheetService, DailyTimeSheetFactory dailyTimeSheetFactory, DailyTimeSheetRepository dailyTimeSheetRepository) {
        this.timeSheetService = timeSheetService;
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
        return new ResponseEntity<>(this.dailyTimeSheetFactory.toListDto(
                this.dailyTimeSheetService.findTimeSheetSet(employeeId,
                        sdf.parse(dateFrom), sdf.parse(dateTo))
        ), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeSheetDto> addTimeSheet(@RequestBody TimeSheetDto timeSheetDto,
                                                  @PathParam("employeeId") String employeeId,
                                                  @PathParam("date") String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Optional<Employee> employee = this.employeeRepository.findById(UUID.fromString(employeeId));
        Optional<DailyTimeSheet> dailyTimeSheet = this.dailyTimeSheetService.findByEmployeeAndData( employeeId,  date);

        if(!dailyTimeSheet.isPresent()){
            UUID uuid = UUID.randomUUID();
            this.dailyTimeSheetService.create(new DailyTimeSheet(uuid, employee.get(), sdf.parse(date)));
            dailyTimeSheet = this.dailyTimeSheetRepository.findById(uuid);
        }
        TimeSheet timeSheet = this.timeSheetFactory.fromDto(UUID.randomUUID(),timeSheetDto,dailyTimeSheet.get());
        this.timeSheetService.create(timeSheet);

        return new ResponseEntity<>(this.timeSheetFactory.toDto(timeSheet), HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TimeSheetDto> updateTimeSheet(@RequestBody TimeSheetDto timeSheetDto,
                                                     @PathParam("employeeId") String employeeId,
                                                     @PathParam("date") String date) throws Exception {
        Optional<DailyTimeSheet> dailyTimeSheet = this.dailyTimeSheetService.findByEmployeeAndData( employeeId,  date);
        if(!dailyTimeSheet.isPresent()){
            throw new RuntimeException("Not exists dailyTimeSheet");
        }

        TimeSheet timeSheet = this.timeSheetFactory.fromDto(UUID.randomUUID(),timeSheetDto,dailyTimeSheet.get());
        this.timeSheetService.update(timeSheet);
        return new ResponseEntity<>(this.timeSheetFactory.toDto(timeSheet), HttpStatus.OK);
    }


}
