import {
    addEmployeeCreator,
    deleteEmployeeCreator,
    getAllEmployeesCreator,
    updateEmployeeCreator,
} from './employeesActionCreator';

import {
    addEmployeeInRepository,
    deleteEmployeeFromRepository,
    getAllEmployeesFromRepository,
    updateEmployeeInRepository
} from "../../repositories/employeeRepository";

export const getAllEmployeesAction = () => async dispatch => {
    getAllEmployeesFromRepository().then((employees) => {
        dispatch(getAllEmployeesCreator(employees));
    });
};

export const addEmployeeAction = employee => async dispatch => {
    addEmployeeInRepository(employee).then((headers) => {
        dispatch(addEmployeeCreator({
            id: headers.location.split("/employees/")[1],
            ...employee
        }));
    });
};

export const deleteEmployeeAction = employeeId => async dispatch => {
    deleteEmployeeFromRepository(employeeId).then(
        dispatch(deleteEmployeeCreator(employeeId))
    )
};

export const updateEmployeeAction = employee => async dispatch => {
    updateEmployeeInRepository(employee).then(
        dispatch(updateEmployeeCreator(employee))
    )
};


