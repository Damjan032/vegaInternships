export const addClientCreator = client => ({
    type: 'ADD_CLIENT',
    client,
});

export const addClientCreatorFailure = error => ({
    type: 'ADD_CLIENT_FAILURE',
    error
});

export const getAllClientCreator = clients => ({
    type: 'GET_ALL_CLIENTS',
    clients,
});

export const getPageOfClientCreator = page => ({
    type: 'GET_PAGE_OF_CLIENTS',
    page,
});

export const deleteClientCreator = clientId => ({
    type: 'DELETE_CLIENT',
    clientId,
})

export const updateClientActionCreator = client => ({
    type: 'UPDATE_CLIENT',
    client,
})

export const searchClientsActionCreator = clients => ({
    type: 'SEARCH_CLIENTS',
    clients
})

