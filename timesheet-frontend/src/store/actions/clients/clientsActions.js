import {
    addClientCreator,
    deleteClientCreator,
    getAllClientCreator,
    updateClientActionCreator,
} from './clientsActionsCreator';

import {
    addClientInRepository,
    deleteClientFromRepository,
    getAllClientsFromRepository,
    updateClientInRepository
} from "../../repositories/clientsRepository";

export const getAllClientsAction = () => async dispatch => {
    getAllClientsFromRepository().then((clients) => {
        dispatch(getAllClientCreator(clients));
    });
};

export const addClientAction = client => async dispatch => {
    addClientInRepository(client).then((result) => {
        dispatch(addClientCreator({
            id: result.headers.location.split("/clients/")[1],
            ...client
        }));
    });
};

export const deleteClientAction = clientId => async dispatch => {
    deleteClientFromRepository(clientId).then(
        dispatch(deleteClientCreator(clientId))
    )
};

export const updateClientAction = client => async dispatch => {
    updateClientInRepository(client).then(
        dispatch(updateClientActionCreator(client))
    )
};


