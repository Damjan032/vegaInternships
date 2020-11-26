import {getAllCountries} from './countriesActionCreator';
import axios from '../../../axios';

export const getAllCountriesAction = () => async (dispatch) => {
    const {data} = await axios.get('/countries');
    dispatch(getAllCountries(data));
};
