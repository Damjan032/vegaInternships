import axios from '../../axios';

export async function getAllDailyTimeSheetsFromEmployeeBetweenToDaysRepository(employeeId,dateFrom, dateTo) {
    //1900-10-10
    const {data} = await axios.get(`/timesheets?employeeId=${employeeId}&dateFrom=${dateFrom}&dateTo=${dateTo}`);
    return data;
}



