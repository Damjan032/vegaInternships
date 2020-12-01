export function fetchCategory(state = [], action) {
    switch (action.type) {
        case 'GET_ALL_CATEGORIES':
            return action.categories;

        default:
            return state;
    }
}
