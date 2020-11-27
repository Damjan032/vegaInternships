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

export async function getPageClientsFromRepository(pageNumber, letter, search) {
    let letterParam = ''
    if(letter!=='' && letter!==undefined && letter!=='cancel'){
        letterParam = `&firstLetter=${letter}`;
    }
    let searchParam = ''
    if(search!=='' && search!==undefined){
        searchParam = `&searchString=${search}`;
    }
    console.log("getPageClientsFromRepository")
    console.log(searchParam)
    const {data} = await axios.get(`/clients/page?pageNumber=${pageNumber}&pageSize=5${letterParam}${searchParam}`);
    return data;
}


