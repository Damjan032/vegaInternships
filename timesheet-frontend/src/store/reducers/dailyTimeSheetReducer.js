export function fetchDailyTimeSheet(state = [], action) {
    console.log("ACTIOOON");
    console.log(action)
    switch (action.type) {
        case 'GET_ALL_DAILY_TIME_SHEETS':
            return action.dailyTimeSheets;

        default:
            return state;
    }
}
