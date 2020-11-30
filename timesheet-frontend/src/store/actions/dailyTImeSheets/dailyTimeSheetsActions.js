import {getAllDailyTimeSheetsFromEmployeeBetweenToDaysRepository} from "../../repositories/dailyTimeSheetRepository";
import {getDailyTimeSheetsCreator} from "./dailyTimeSheetsActionsCreator";

export const getDailyTimeSheet = (employeeId, dateFrom, dateTo) => async dispatch => {
    getAllDailyTimeSheetsFromEmployeeBetweenToDaysRepository(employeeId, dateFrom, dateTo).then((dailyTimeSheets) => {
        dispatch(getDailyTimeSheetsCreator(dailyTimeSheets));
    });
};
