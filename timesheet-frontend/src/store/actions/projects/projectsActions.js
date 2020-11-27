import {
    addProjectCreator,
    deleteProjectCreator,
    getAllProjectsCreator,
    updateProjectCreator,
} from './projectsActionsCreator';

import {
    addProjectInRepository,
    deleteProjectFromRepository,
    getAllProjectsFromRepository,
    updateProjectInRepository
} from "../../repositories/projectRepository";

export const getAllProjectsAction = () => async dispatch => {
    getAllProjectsFromRepository().then((projects) => {
        dispatch(getAllProjectsCreator(projects));
    });
};


export const addProjectAction = project => async dispatch => {
    addProjectInRepository(project).then((headers) => {
        dispatch(addProjectCreator({
            id: headers.location.split("/projects/")[1],
            ...project
        }));
    });
};

export const deleteProjectAction = projectId => async dispatch => {
    deleteProjectFromRepository(projectId).then(
        dispatch(deleteProjectCreator(projectId))
    )
};

export const updateProjectAction = project => async dispatch => {
    updateProjectInRepository(project).then(
        dispatch(updateProjectCreator(project))
    )
};


