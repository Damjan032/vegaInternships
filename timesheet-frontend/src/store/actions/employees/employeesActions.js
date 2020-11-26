import {
    addEmployeeCreator,
    deleteEmployeeCreator,
    getAllEmployeesCreator,
    updateEmployeeCreator,
} from './employeesActionCreator';
import axios from '../../../axios';

export const getAllEmployeesAction = () => async dispatch => {
    const {data} = await axios.get('/employees');
    dispatch(getAllEmployeesCreator(data));
};

export const addEmployeeAction = employee => async dispatch => {
    const {data} = await axios.post('/employees', employee);

    dispatch(addEmployeeCreator(data));


};

export const deleteEmployeeAction = employeeId => async dispatch => {
    await axios.delete(`/employees/${employeeId}`);
    dispatch(deleteEmployeeCreator(employeeId));
};

export const updateEmployeeAction = employee => async dispatch => {
    await axios.put(`/employees`, employee);
    dispatch(updateEmployeeCreator(employee));
};