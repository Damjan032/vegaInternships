import axios from '../../axios';

export async function getAllEmployeesFromRepository() {
    const {data} = await axios.get('/employees');
    return data;
}

export async function addEmployeeInRepository(employee) {
    const {headers} = await axios.post('/employees', employee);
    return headers;
}

export async function deleteEmployeeFromRepository(employeeId) {
    await axios.delete(`/employees/${employeeId}`);
}

export async function updateEmployeeInRepository(employee) {
    await axios.put(`/employees/${employee.id}`, employee);
}


