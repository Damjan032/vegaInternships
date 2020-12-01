export function fetchWeekView(state = {week:[], day:new Date()}, action) {
    switch (action.type) {
        case 'GET_WEEK_VIEW_INFO':
            return action.weekView;
        case 'SET_WEEK_VIEW_INFO':
            state = action.weekView;
            return state;

        default:
            return state;
    }
}
