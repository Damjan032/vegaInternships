export function employees(state = [], action) {
    switch (action.type) {
        case 'GET_ALL_EMPLOYEES':
            return action.employees;

        case 'ADD_EMPLOYEE':
            return [...state, action.employee];

        case 'DELETE_EMPLOYEE':
            return state.filter((item) => item.id !== action.employeeId);

        case 'UPDATE_EMPLOYEE':
            state = [
                ...state.filter(c => c.id !== action.employee.id),
                action.employee
            ];

            return state;

        default:
            return state;
    }
}
