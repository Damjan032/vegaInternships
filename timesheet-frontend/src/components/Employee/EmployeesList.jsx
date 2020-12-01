import React from 'react';
import Employee from "./Employee";


export default function EmployeeList(props) {
    const {employees, onDelete} = props;

    return (
        <div className="accordion-wrap clients">
            {employees.map(employee => (
                <Employee onDeleteOrUpdate={onDelete} key={employee.id} employee={employee}/>
            ))}
        </div>
    );
}
