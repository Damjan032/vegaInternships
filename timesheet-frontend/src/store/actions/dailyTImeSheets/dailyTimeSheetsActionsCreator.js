export const getDailyTimeSheetsCreator = dailyTimeSheets => ({
    type: 'GET_ALL_DAILY_TIME_SHEETS',
    dailyTimeSheets,
});

export const getWeekViewInfoCreator = weekView => ({
    type: 'GET_WEEK_VIEW_INFO',
    weekView,
});

export const setWeekViewInfoCreator = weekView => ({
    type: 'SET_WEEK_VIEW_INFO',
    weekView,
});