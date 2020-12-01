export function fetchDailyTimeSheet(state = [], action) {
    switch (action.type) {
        case 'GET_ALL_DAILY_TIME_SHEETS':
            return action.dailyTimeSheets;

        default:
            return state;
    }
}
