import {getAllCountries} from './countriesActionCreator';
import {getAllCountriesFromRepository} from "../../repositories/countriesRepository";

export const getAllCountriesAction = () => async (dispatch) => {
    getAllCountriesFromRepository().then((result) => {
        dispatch(getAllCountries(result.data));
    });

};
