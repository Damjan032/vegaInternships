export function employees(state = [], action) {
    switch (action.type) {
        case 'GET_ALL_EMPLOYEES':
            return action.employees;

        case 'ADD_EMPLOYEE':
            return [action.employee, ...state];

        case 'DELETE_EMPLOYEE':
            return state.filter((item) => item.id !== action.employeeId);

        case 'UPDATE_EMPLOYEE':
            console.log(action);
            state = [
                ...state.filter(c => c.id !== action.employees.id),
                action.employees
            ];
            return state;

        default:
            return state;
    }
}
