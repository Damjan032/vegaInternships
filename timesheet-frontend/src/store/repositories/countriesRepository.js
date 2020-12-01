import axios from '../../axios';

export async function getAllCountriesFromRepository() {
    const {data} = await axios.get('/countries');
    return {data};
}
