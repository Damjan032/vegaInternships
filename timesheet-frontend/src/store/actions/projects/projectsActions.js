import {
    addProjectCreator,
    deleteProjectCreator,
    getAllProjectsCreator,
    getPageOfProjectsCreator,
    updateProjectCreator,
} from './projectsActionsCreator';

import {
    addProjectInRepository,
    deleteProjectFromRepository,
    getAllProjectsFromRepository,
    getPageProjectsFromRepository,
    updateProjectInRepository
} from "../../repositories/projectRepository";

export const getAllProjectsAction = () => async dispatch => {
    getAllProjectsFromRepository().then((projects) => {
        dispatch(getAllProjectsCreator(projects));
    });
};

export const getPageOfProjectsAction = (pageNumber, letter, search) => async dispatch => {
    getPageProjectsFromRepository(pageNumber, letter, search).then((pages) => {

        dispatch(getPageOfProjectsCreator(pages));
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


