import {
    addClientCreator,
    deleteClientCreator,
    getAllClientCreator,
    getPageOfClientCreator,
    updateClientActionCreator,
} from './clientsActionsCreator';

import {
    addClientInRepository,
    deleteClientFromRepository,
    getAllClientsFromRepository,
    getPageClientsFromRepository,
    updateClientInRepository
} from "../../repositories/clientsRepository";

export const getAllClientsAction = () => async dispatch => {
    getAllClientsFromRepository().then((clients) => {
        dispatch(getAllClientCreator(clients));
    });
};

export const getPageOfClientsAction = (pageNumber, letter, search) => async dispatch => {
    getPageClientsFromRepository(pageNumber, letter, search).then((pages) => {
        dispatch(getPageOfClientCreator(pages));
    });
};

export const addClientAction = client => async dispatch => {
    addClientInRepository(client).then((result) => {
        dispatch(addClientCreator({
            id: result.location.split("/clients/")[1],
            ...client
        }));
        getPageClientsFromRepository(1);
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


