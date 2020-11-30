import axios from '../../axios';

export async function getAllProjectsFromRepository() {
    const {data} = await axios.get('/projects');
    return data;
}

export async function addProjectInRepository(project) {
    const {headers} = await axios.post('/projects', project);
    return headers;
}

export async function deleteProjectFromRepository(projectId) {
    await axios.delete(`/projects/${projectId}`);
}

export async function updateProjectInRepository(project) {
    await axios.put(`/projects/${project.id}`, project);
}

export async function getPageProjectsFromRepository(pageNumber, letter, search) {
    let letterParam = '';
    if (letter !== '' && letter !== undefined && letter !== 'cancel') {
        letterParam = `&firstLetter=${letter}`;
    }
    let searchParam = ''
    if (search !== '' && search !== undefined) {
        searchParam = `&searchString=${search}`;
    }
    const {data} = await axios.get(`/projects/page?pageNumber=${pageNumber}&pageSize=2${letterParam}${searchParam}`);
    return data;
}


