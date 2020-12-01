import {combineReducers} from 'redux';
import {clients} from './clientsReducer';
import {countries} from './countriesReducer';
import {employees} from './employeeReducer';
import {projects} from "./projectReducer";
import {fetchPageOfClients} from "./clientPagesReducer";
import {fetchPageOfProjects} from "./projectPagesReducer";
import {fetchPageOfEmployee} from "./employeePageReducer";
import {fetchDailyTimeSheet} from "./dailyTimeSheetReducer";
import {fetchCategory} from "./categoriesReducer";


const rootReducer = combineReducers({
    clients,
    clientPages: fetchPageOfClients,
    projectsPage: fetchPageOfProjects,
    employeesPage: fetchPageOfEmployee,
    dailyTimeSheets: fetchDailyTimeSheet,
    categories: fetchCategory,
    countries,
    employees,
    projects
});

export default rootReducer;
