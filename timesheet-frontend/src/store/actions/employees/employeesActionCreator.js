export const addEmployeeCreator = (employee) => ({
    type: 'ADD_EMPLOYEE',
    employee,
});

export const getAllEmployeesCreator = (employees) => ({
    type: 'GET_ALL_EMPLOYEES',
    employees,
});

export const deleteEmployeeCreator = (employeeId) => ({
    type: 'DELETE_EMPLOYEE',
    employeeId,
})

export const updateEmployeeCreator = (employees) => ({
    type: 'UPDATE_EMPLOYEE',
    employees,
})