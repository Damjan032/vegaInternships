import axios from '../../axios';

export async function getAllDailyTimeSheetsFromEmployeeBetweenTwoDaysRepository(employeeId, dateFrom, dateTo) {
    const {data} = await axios.get(`/timesheets?employeeId=${employeeId}&dateFrom=${dateFrom}&dateTo=${dateTo}`);
    return data;
}



