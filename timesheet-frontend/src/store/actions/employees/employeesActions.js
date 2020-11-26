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
    console.log(employee)
    await axios.post('/employees', employee).then((response) => {
        console.log(response.data.split("/employees/")[1])
        console.log(employee)
        let employeeNew = {...employee,id:response.data.split("/clients/")[1]}

        console.log(employeeNew);
        dispatch(addEmployeeCreator(employeeNew));
    }).catch(error => {
        console.log(error);
    });
    //dispatch(addEmployeeCreator(data));


};

export const deleteEmployeeAction = employeeId => async dispatch => {
    await axios.delete(`/employees/${employeeId}`);
    dispatch(deleteEmployeeCreator(employeeId));
};

export const updateEmployeeAction = employee => async dispatch => {
    console.log(employee)
    await axios.put(`/employees/${employee.id}`, employee);
    dispatch(updateEmployeeCreator(employee));
};