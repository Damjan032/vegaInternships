import React, {useEffect, useState} from 'react';
import EmployeeSelection from "../components/Employee/EmpoyeeSelection";
import {getAllClientsAction} from "../store/actions/clients/clientsActions";
import {getAllProjectsAction} from "../store/actions/projects/projectsActions";
import {getAllCategoriesAction} from "../store/actions/categories/categoriesActions";
import {getDailyTimeSheetAction} from "../store/actions/dailyTImeSheets/dailyTimeSheetsActions";

import {useDispatch, useSelector} from 'react-redux';
import {getAllEmployeesAction} from "../store/actions/employees/employeesActions";
import CategorySelection from "../components/Category/CategoriesSelection";
import ClientSelection from "../components/Client/ClientSelection";
import ProjectSelection from "../components/Project/ProjectSelection";
import ReportsBody from "../components/Reports/ReportsBody";


export default function Reports() {

    const dailyTimeSheets = useSelector(state => state.dailyTimeSheets);

    const dispatch = useDispatch();
    const [searchParams, setSearchParams] = useState({
        teamLead: '',
        project: '',
        client: '',
        categories: '',
        startDate: '',
        endDate: '',
    })
    const [filtered, setFiltered] = useState(initFilteringSet());

    const onSearchParamsChange = (event) => {
        setSearchParams({
            ...searchParams,
            [event.target.name]: event.target.value,
        });
    }


    const handleSearch = () => {
        let filterPom = initFilteringSet();
        if (searchParams.project !== '')
            filterPom = filterPom.filter(timeSheet => timeSheet.project.id === searchParams.project)
        if (searchParams.categories !== '')
            filterPom = filterPom.filter(timeSheet => timeSheet.categoryDto.id === searchParams.categories)
        if (searchParams.client !== '')
            filterPom = filterPom.filter(timeSheet => timeSheet.project.clientDto.id === searchParams.client)
        if (searchParams.teamLead !== '')
            filterPom = filterPom.filter(timeSheet => timeSheet.project.teamLead.id === searchParams.teamLead)
        if (searchParams.startDate !== '')
            filterPom = filterPom.filter(timeSheet => timeSheet.day >= searchParams.startDate)
        if (searchParams.endDate !== '')
            filterPom = filterPom.filter(timeSheet => timeSheet.day <= searchParams.endDate)
        setFiltered(filterPom)

    }
    const handleRestart = () => {
        setFiltered(initFilteringSet())
    }

    function initFilteringSet() {
        let filteredPom = []
        dailyTimeSheets.map(daily =>
            daily['timeSheets'].map(timeSheet => {
                filteredPom.push({...timeSheet, day: daily.day});
            })
        )
        return filteredPom;
    }

    function countTotalHours() {
        let hours = 0;
        filtered.map(timeSheet => {
            hours += timeSheet.time + timeSheet.overtime;
        })
        return hours;

    }

    useEffect(() => {
        dispatch(getAllClientsAction());
        dispatch(getAllProjectsAction());
        dispatch(getAllCategoriesAction());
        dispatch(getAllEmployeesAction())
        dispatch(getDailyTimeSheetAction("30a77b80-5ac7-4435-8ee4-068d0eae18e0", '1900-01-01',
            '2200-01-01'));
    }, [dispatch]);

    return (
        <>
            <h2><i className="ico report"></i>Reports {filtered.length}</h2>
            <div className="grey-box-wrap reports">
                <ul className="form">
                    <li>
                        <label>Team member:</label>
                        <EmployeeSelection onChange={onSearchParamsChange}/>
                    </li>
                    <li>
                        <label>Category:</label>
                        <CategorySelection onChange={onSearchParamsChange}/>
                    </li>
                </ul>
                <ul className="form">
                    <li>
                        <label>Client:</label>
                        <ClientSelection onChange={onSearchParamsChange}/>
                    </li>
                    <li>
                        <label>Start date:</label>
                        <input onChange={onSearchParamsChange} className="in-text datepicker" type="date" id="start"
                               name="startDate"
                               defaultValue="2020-01-01"/>
                    </li>
                </ul>
                <ul className="form last">
                    <li>
                        <label>Project:</label>
                        <ProjectSelection onChange={onSearchParamsChange}/>
                    </li>
                    <li>
                        <label>End date:</label>
                        <input onChange={onSearchParamsChange} className="in-text datepicker" type="date" id="start"
                               name="endDate"
                               defaultValue="2022-01-01"/>
                    </li>
                    <li>
                        <a href="javascript:" className="btn orange right" onClick={handleRestart}>Reset</a>
                        <a href="javascript:" className="btn green right" onClick={handleSearch}>Search</a>
                    </li>
                </ul>
            </div>

            <table className="default-table">
                <tbody>
                <tr>
                    <th>
                        Date
                    </th>
                    <th>
                        Team member
                    </th>
                    <th>
                        Projects
                    </th>
                    <th>Categories</th>
                    <th>Description</th>
                    <th className="small">Time</th>
                </tr>
                <ReportsBody filtered={filtered}/>
                </tbody>
            </table>
            <div className="total">
                <span>Report total: <em>{countTotalHours()}</em></span>
            </div>
            <div className="grey-box-wrap reports">
                <div className="btns-inner">
                    <a href="javascript:" className="btn white">
                        <span>Print report</span>
                    </a>
                    <a href="javascript:" className="btn white">
                        <span>Create PDF</span>
                    </a>
                    <a href="javascript:" className="btn white">
                        <span>Export to excel</span>
                    </a>
                </div>
            </div>
        </>
    );
}
