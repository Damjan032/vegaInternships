import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import {deleteEmployeeAction, updateEmployeeAction} from '../../store/actions/employees/employeesActions';
import {useForm} from "react-hook-form";

export default function Employee(props) {

  const [employee, setEmployee] = useState({
    id: props.employee.id,
    name: props.employee.name,
    username: props.employee.username,
    email: props.employee.email,
    hoursPerWeek: props.employee.hoursPerWeek,
    role: props.employee.role,
    status: props.employee.status,
    accepted:props.employee.accepted
  });

  const {handleSubmit} = useForm();
  const [isEditing, setIsEditing] = useState(false);

  const dispatch = useDispatch();

  function deleteEmployee(id) {
    dispatch(deleteEmployeeAction(id));
  }

  function updateEmployee() {
    dispatch(updateEmployeeAction(employee));
  }

  function inputChanged(event) {
    setEmployee({
      ...employee,
      [event.target.name]: event.target.value,
    });
    if(event.target.name==="isActive"){
      setEmployee({
        ...employee,
        status: event.target.value==="true"?"ACTIVE": "INACTIVE"  ,
      });
    }
    if(event.target.name==="type"){
      setEmployee({
        ...employee,
        role: event.target.value==="WORKER"?"WORKER": "ADMIN"  ,
      });
    }
  }

  return (
      <div className="item">
        <div className="heading" onClick={() => setIsEditing(!isEditing)}>
          <span>{employee.name}</span>
          <i>+</i>
        </div>
        {isEditing && <div className="details">
          <form onSubmit={handleSubmit(updateEmployee)}>
            <ul className="form">
              <li>
                <label>Name:</label>
                <input
                    type="text"
                    className="in-text"
                    name="name"
                    value={employee.name}
                    onChange={inputChanged}
                />
              </li>
              <li>
                <label>Hours per week:</label>
                <input
                    type="text"
                    className="in-text"
                    name="hoursPerWeek"
                    value={employee.hoursPerWeek}
                    onChange={inputChanged}
                />
              </li>
            </ul>
            <ul className="form">
              <li>
                <label>Username:</label>
                <input
                    type="text"
                    className="in-text"
                    name="username"
                    value={employee.username}
                    onChange={inputChanged}
                />
              </li>
              <li>
                <label>Email:</label>
                <input
                    type="text"
                    className="in-text"
                    name="email"
                    value={employee.email}
                    onChange={inputChanged}
                />
              </li>
            </ul>
            <ul className="form last">
              <li>
                <label>Status:</label>
                <span className="radio">
                <label className="myMargin">Inactive:</label>
                <input
                    type="radio"
                    value={false}
                    name="isActive"
                    id="inactive"
                    checked={employee.status!=="ACTIVE"}
                    onChange={inputChanged}
                />
              </span>
                <span className="radio">
                <label className="myMargin">Active:</label>
                <input
                    type="radio"
                    value={true}
                    name="isActive"
                    id="active"
                    checked={employee.status==="ACTIVE"}
                    onChange={inputChanged}
                />
              </span>
              </li>
              <li>
                <label>Type:</label>
                <span className="radio">
                <label className="myMargin">Admin:</label>
                <input
                    type="radio"
                    value="ADMIN"
                    name="type"
                    id="admin"
                    checked={employee.role==="ADMIN"}
                    onChange={inputChanged}
                />
              </span>
                <span className="radio">
                <label className="myMargin">Worker:</label>
                <input
                    type="radio"
                    value="WORKER"
                    name="type"
                    id="worker"
                    checked={employee.role==="WORKER"}
                    onChange={inputChanged}
                />
              </span>
              </li>
            </ul>
            <div className="buttons">
              <div className="inner">
                <button
                    className="btn green"
                    type="submit"
                >Save
                </button>
                <button
                    className="btn red"
                    type="button"
                    onClick={() => deleteEmployee(employee.id)}
                >Delete
                </button>
              </div>
            </div>
          </form>

        </div>}
      </div>
  );
}
