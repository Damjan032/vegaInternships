import {combineReducers} from 'redux';
import {clients} from './clientsReducer';
import {countries} from './countriesReducer';
import {employees} from './employeeReducer';
import {projects} from "./projectReducer";
import {fetchPageOfClients} from "./clientPagesReducer";


const rootReducer = combineReducers({
    clients,
    clientPages: fetchPageOfClients,
    countries,
    employees,
    projects
});

export default rootReducer;
