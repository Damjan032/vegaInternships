export function fetchPageOfEmployee(state = {items: [], pageNumber: 1, pageSize: 5, totalSize: 100}, action) {
    switch (action.type) {

        case 'GET_PAGE_OF_EMPLOYEES':
            return action.page;


        default:
            return state;
    }
}
