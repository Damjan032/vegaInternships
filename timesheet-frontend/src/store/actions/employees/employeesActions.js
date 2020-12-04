import {
    addEmployeeCreator,
    deleteEmployeeCreator,
    getAllEmployeesCreator,
    getPageOfEmployeesCreator,
    updateEmployeeCreator,
} from './employeesActionCreator';

import {
    addEmployeeInRepository,
    deleteEmployeeFromRepository,
    getAllEmployeesFromRepository,
    getPageEmployeesFromRepository, repeatPasswordInRepository, resetPasswordInRepository,
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

export const getPageOfEmployeesAction = (pageNumber) => async dispatch => {
    getPageEmployeesFromRepository(pageNumber).then((pages) => {
        dispatch(getPageOfEmployeesCreator(pages));
    });
};

export const updateEmployeeAction = employee => async dispatch => {
    updateEmployeeInRepository(employee).then(
        dispatch(updateEmployeeCreator(employee))
    )
};

export const resetPasswordAction = (employeeId,passwords)  => {
    resetPasswordInRepository(employeeId, passwords).then(r => console.log("done"));
};


