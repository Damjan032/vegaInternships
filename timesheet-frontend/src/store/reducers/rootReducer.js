import {combineReducers} from 'redux';
import {clients} from './clientsReducer';
import {countries} from './countriesReducer';
import {employees} from './employeeReducer';
import {projects} from "./projectReducer";


const rootReducer = combineReducers({
    clients,
    countries,
    employees,
    projects
});

export default rootReducer;
