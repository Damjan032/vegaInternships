import React, {useEffect, useState} from 'react';
import NewMemberDialog from "../components/Employee/NewMemberDialog"
import {useDispatch, useSelector} from 'react-redux';
import {getPageOfEmployeesAction} from "../store/actions/employees/employeesActions";
import EmployeesList from "../components/Employee/EmployeesList";
import PaginationBar from "../components/PaginationBar";

export default function Employees() {
    const [isDialogVisible, setIsDialogVisible] = useState(false);
    const pageOfEmployees = useSelector(state => state.employeesPage);
    const dispatch = useDispatch();

    const handleAddOrDelete = async () => {
        await dispatch(getPageOfEmployeesAction(pageOfEmployees.pageNumber));
        // setTimeout(dispatch(getPageOfEmployeesAction(pageOfEmployees.pageNumber)), 2000);
    };

    const handlePropagationClick = (numberOfPage) => {
        if (numberOfPage === "PREV") {
            dispatch(getPageOfEmployeesAction(--pageOfEmployees.pageNumber));
        } else if (numberOfPage === "NEXT") {
            dispatch(getPageOfEmployeesAction(++pageOfEmployees.pageNumber));
        } else {
            dispatch(getPageOfEmployeesAction(numberOfPage));
            pageOfEmployees.pageNumber = numberOfPage;
        }
    };


    function dialogClosed() {
        setIsDialogVisible(false);
    }

    useEffect(() => {
        dispatch(getPageOfEmployeesAction(1));
    }, [dispatch]);

    return (
        <>
            <h2>
                <i className="ico team-member"/>Team members
            </h2>
            <div className="grey-box-wrap reports ico-member">
                {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                <a href="#"
                   className="link new-member-popup"
                   onClick={() => setIsDialogVisible(true)}
                >
                    Create new member
                </a>
                <NewMemberDialog onAdd={handleAddOrDelete} open={isDialogVisible} onClose={dialogClosed}/>
            </div>

            <EmployeesList employees={pageOfEmployees.items} onDelete={handleAddOrDelete}/>

            <PaginationBar onClick={handlePropagationClick} pageNumber={pageOfEmployees.pageNumber}
                           totalSize={pageOfEmployees.totalSize} pageSize={pageOfEmployees.pageSize}/>
        </>
    );
}
