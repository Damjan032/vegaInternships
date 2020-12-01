import {getAllDailyTimeSheetsFromEmployeeBetweenTwoDaysRepository} from "../../repositories/dailyTimeSheetRepository";
import {getDailyTimeSheetsCreator} from "./dailyTimeSheetsActionsCreator";

export const getDailyTimeSheetAction = (employeeId, dateFrom, dateTo) => async dispatch =>  {
    getAllDailyTimeSheetsFromEmployeeBetweenTwoDaysRepository(employeeId, dateFrom, dateTo).then((dailyTimeSheets) => {
        dispatch(getDailyTimeSheetsCreator(dailyTimeSheets));
    });
};

