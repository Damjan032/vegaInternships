import axios from '../../axios';

export async function getAllClientsFromRepository() {
    const {data} = await axios.get('/clients');
    return data;
}

export async function addClientInRepository(client) {
    const {headers} = await axios.post('/clients', client);
    return headers;
}

export async function deleteClientFromRepository(clientId) {
    await axios.delete(`/clients/${clientId}`);
}

export async function updateClientInRepository(client) {
    await axios.put(`/clients/${client.id}`, client);
}


