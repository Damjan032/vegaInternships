import React from 'react';
import {useSelector} from 'react-redux';

export default function EmployeeSelection(props) {
    const employees = useSelector(state => state.employees);
    const {onChange, value} = props;

    return (
        <select value={value} onChange={onChange} name="teamLead">
            <option value={''}>Select a employee</option>
            {employees.map((employee) => (
                <option key={employee.id} value={employee.id}>
                    {employee.name}
                </option>
            ))}
        </select>
    );
}
