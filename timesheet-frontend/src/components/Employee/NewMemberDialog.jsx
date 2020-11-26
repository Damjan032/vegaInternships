import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import {Dialog} from "@material-ui/core";
import {useForm} from "react-hook-form";
import {addEmployeeAction} from "../../store/actions/employees/employeesActions";

export default function NewMemberDialog(props) {
    const [employee, setEmployee] = useState({
        name: '',
        email: '',
        password: null,
        username: '',
        hoursWeekly: '',
        type: 1,
        isActive: true,
    });
    const {handleSubmit, register, errors} = useForm();
    const {onClose, open} = props;

    const dispatch = useDispatch();

    const inputChanged = (event) => {
        let inputValue;

        switch (event.target.name) {
            case "isActive":
                inputValue = (event.target.value === 'true')
                break;
            case "type":
                inputValue = parseInt(event.target.value);
                break;
            case "hoursWeekly":
                inputValue = parseInt(event.target.value);
                break;
            default:
                inputValue = event.target.value;
        }

        setEmployee({
            ...employee,
            [event.target.name]: inputValue,
        });
    };
    const saveEmployee = () => {
        dispatch(addEmployeeAction(employee));
        setEmployee({
            name: '',
            email: '',
            username: '',
            hoursWeekly: '',
            type: 1,
            isActive: true,
        });
        onClose();
    };

    return (
        <Dialog onClose={onClose} open={open}>
            <div className="new-member-wrap">
                <form onSubmit={handleSubmit(saveEmployee)}>
                    <div id="new-member" className="new-member-inner">
                        <h2>Create new team member</h2>
                        <ul className="form">
                            <li>
                                <label>Name:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="name"
                                    value={employee.name || ''}
                                    ref={register({
                                        required: 'Name is required'
                                    })}
                                    onChange={inputChanged}
                                />
                                <span className="input-error">{errors.name && errors.name.message}</span>
                            </li>
                            <li>
                                <label>Hours per week:</label>
                                <input
                                    type="number"
                                    className="in-text"
                                    name="hoursWeekly"
                                    value={employee.hoursWeekly}
                                    ref={register({
                                        required: 'Hours per week are required',
                                        pattern: {
                                            value: /^(\d+\.?\d*|\.\d+)$/i,
                                            message: "Invalid decimal number"
                                        }
                                    })}
                                    onChange={inputChanged}
                                />
                                <span className="input-error">{errors.hoursWeekly && errors.hoursWeekly.message}</span>

                            </li>
                            <li>
                                <label>Username:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="username"
                                    value={employee.username}
                                    ref={register({
                                        required: 'Username is required',
                                        pattern: {
                                            value: /^[A-Za-z0-9.]+$/i,
                                            message: "Username can only contain A-Z, a-z, 0-9 and '.'"
                                        }
                                    })}
                                    onChange={inputChanged}
                                />
                                <span className="input-error">{errors.username && errors.username.message}</span>

                            </li>
                            <li>
                                <label>Email:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="email"
                                    value={employee.email}
                                    ref={register({
                                        required: 'Email is required',
                                        pattern: {
                                            value: /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/i,
                                            message: "Email is invalid."
                                        }
                                    })}
                                    onChange={inputChanged}
                                />
                                <span className="input-error">{errors.email && errors.email.message}</span>

                            </li>
                            <li className="inline">
                                <label>Status:</label>
                                <span className="radio">
                  <label>InisActive:</label>
                  <input
                      type="radio"
                      value={false}
                      name="isActive"
                      id="inisActive"
                      ref={register({required: 'Status is required'})}
                      checked={!employee.isActive}
                      onChange={inputChanged}
                  />
                </span>
                                <span className="radio">
                  <label>Active:</label>
                  <input
                      type="radio"
                      value={true}
                      name="isActive"
                      id="isActive"
                      ref={register({required: 'Status is required'})}
                      checked={employee.isActive}
                      onChange={inputChanged}
                  />
                </span>
                                <span className="input-error">{errors.isActive && errors.isActive.message}</span>

                            </li>
                            <li className="inline">
                                <label>Role:</label>
                                <span className="radio">
                  <label>Admin:</label>
                  <input
                      type="radio"
                      value="0"
                      name="type"
                      id="admin"
                      ref={register({required: 'Role is required'})}
                      checked={employee.type === 0}
                      onChange={inputChanged}
                  />
                </span>
                                <span className="radio">
                  <label>Worker:</label>
                  <input
                      type="radio"
                      value="1"
                      name="type"
                      id="worker"
                      ref={register({required: 'Role is required'})}
                      checked={employee.type === 1}
                      onChange={inputChanged}
                  />
                </span>
                                <span className="input-error">{errors.type && errors.type.message}</span>

                            </li>
                        </ul>
                        <div className="buttons">
                            <div className="inner">
                                <button className="btn green" type="submit">
                                    Invite team member
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </Dialog>
    );
}
