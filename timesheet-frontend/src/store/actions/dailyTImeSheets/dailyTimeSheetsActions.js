import {getAllDailyTimeSheetsFromEmployeeBetweenTwoDaysRepository} from "../../repositories/dailyTimeSheetRepository";
import {getDailyTimeSheetsCreator} from "./dailyTimeSheetsActionsCreator";

export const getDailyTimeSheetAction = (employeeId, dateFrom, dateTo) => async dispatch =>  {
    console.log("OVDEEEE");
    console.log(dateFrom);
    getAllDailyTimeSheetsFromEmployeeBetweenTwoDaysRepository(employeeId, dateFrom, dateTo).then((dailyTimeSheets) => {
        dispatch(getDailyTimeSheetsCreator(dailyTimeSheets));
    });
};
