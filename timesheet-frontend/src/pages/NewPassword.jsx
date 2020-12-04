import React, {useState} from "react";
import {useDispatch} from "react-redux";
import {resetPasswordAction} from "../store/actions/employees/employeesActions";

export default function NewPassword(props) {
    const queryId = require('query-string');
    const [passwords, setPasswords] = useState({
        password: '',
        repeatedPassword: ''
    });
    const [employeeId] = useState(queryId.parse(props.location.search).id);


    const inputChanged = (event) =>{
        setPasswords({
            ...passwords,
            [event.target.name]: event.target.value,
        });
    }

    const handleSubmit = () => {
        console.log("Submit")
        if (passwords.password !== passwords.repeatedPassword) {
            alert("Passwords don't match");
        } else if ( passwords.password.length<6){
            alert("Passwords must have more than 6 characters.");
        } else {
            resetPasswordAction(employeeId,passwords)
        }
        setPasswords({
            password: '',
            repeatedPassword: ''
        });

        console.log(employeeId)
        console.log(passwords)
    }
    return (
        <>
            <div className="wrapper centered">
                <div className="logo-wrap">
                    <a href="index.html" className="inner">
                        <img src="assets/img/logo-large.png" alt={"vega"}/>
                    </a>
                </div>
                <div className="centered-content-wrap">
                    <div className="centered-block">
                        <h1>Reset password</h1>
                        <ul>
                            <li>
                                <input value={passwords.password}
                                       type="password" name="password" placeholder="New password" className="in-pass large" onChange={inputChanged}/>
                            </li>
                            <li>
                                <input type="password" name="repeatedPassword" placeholder="Repeat password" className="in-pass large"
                                       value={passwords.repeatedPassword} onChange={inputChanged}
                                />
                            </li>
                            <li className="last">
                                    <span className="right">
							<a href="javascript:;" className="btn orange" onClick ={handleSubmit}>Reset password</a>
						</span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </>
    )
}