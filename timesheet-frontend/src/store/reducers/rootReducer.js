import {combineReducers} from 'redux';
import {clients} from './clientsReducer';
import {countries} from './countriesReducer';
import {employees} from './employeeReducer';


const rootReducer = combineReducers({
    clients,
    countries,
    employees
});

export default rootReducer;
