import {
    addProjectCreator,
    deleteProjectCreator,
    getAllProjectsCreator,
    updateProjectCreator,
} from './projectsActionCreator';
import axios from '../../../axios';

export const getAllProjectsAction = () => async dispatch => {
    const {data} = await axios.get('/projects');
    dispatch(getAllProjectsCreator(data));
};

export const addProjectAction = project => async dispatch => {
    const {data} = await axios.post('/projects', project);

    dispatch(addProjectCreator(data));


};

export const deleteProjectAction = projectId => async dispatch => {
    await axios.delete(`/projects/${projectId}`);
    dispatch(deleteProjectCreator(projectId));
};

export const updateProjectAction = project => async dispatch => {
    await axios.put(`/projects`, project);
    dispatch(updateProjectCreator(project));
};