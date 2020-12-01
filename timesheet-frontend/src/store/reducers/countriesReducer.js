export function countries(state = [], action) {
    switch (action.type) {
        case 'GET_ALL_COUNTRIES':
            return action.countries;

        default:
            return state;
    }
}
