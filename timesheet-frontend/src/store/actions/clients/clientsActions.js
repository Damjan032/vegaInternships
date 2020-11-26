import {
    addClientCreator,
    deleteClientCreator,
    getAllClientCreator,
    updateClientActionCreator,
} from './clientsActionsCreator';
import axios from '../../../axios';

export const getAllClientsAction = () => async dispatch => {
    const {data} = await axios.get('/clients');
    dispatch(getAllClientCreator(data));
};

export const addClientAction = client => async dispatch => {
    await axios.post('/clients', client).then((response) => {
        console.log(response.headers.location.split("/clients/")[1])
        console.log(response)
        let clientNew = {id:response.headers.location.split("/clients/")[1],
            city: client.city,
            country: client.country,
            name: client.name,
            street: client.street,
            zipCode:client.zipCode}
        console.log(clientNew);
        dispatch(addClientCreator(clientNew));
    }).catch(error => {
        console.log(error);
    });

};

export const deleteClientAction = clientId => async dispatch => {
    await axios.delete(`/clients/${clientId}`);
    dispatch(deleteClientCreator(clientId));
};

export const updateClientAction = client => async dispatch => {
    await axios.put(`/clients/${client.id}`, client);
    dispatch(updateClientActionCreator(client));
};


