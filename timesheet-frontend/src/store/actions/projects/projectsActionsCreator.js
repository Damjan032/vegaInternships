export const addProjectCreator = (project) => ({
    type: 'ADD_PROJECT',
    project,
});

export const getAllProjectsCreator = (projects) => ({
    type: 'GET_ALL_PROJECTS',
    projects,
});

export const deleteProjectCreator = (projectId) => ({
    type: 'DELETE_PROJECT',
    projectId,
})

export const getPageOfProjectsCreator = page => ({
    type: 'GET_PAGE_OF_PROJECTS',
    page,
});

export const updateProjectCreator = (projects) => ({
    type: 'UPDATE_PROJECT',
    projects,
})