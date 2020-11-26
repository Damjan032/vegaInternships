import React from 'react';
import {useSelector} from 'react-redux';
import Employee from "./Employee";


export default function EmployeeList(props) {
  const employees = useSelector(state => state.employees);

  return (
      <div className="accordion-wrap clients">
        {employees.map(employee => (
            <Employee key={employee.id} employee={employee}/>
        ))}
      </div>
  );
}
