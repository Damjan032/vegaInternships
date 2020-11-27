export function projects(state = [], action) {
    switch (action.type) {
        case 'GET_ALL_PROJECTS':
            return action.projects;

        case 'ADD_PROJECT':
            return [...state, action.project];

        case 'DELETE_PROJECT':
            return state.filter((item) => item.id !== action.projectId);

        case 'UPDATE_PROJECT':
            state = [
                ...state.filter(c => c.id !== action.projects.id),
                action.projects
            ];

            return state;

        default:
            return state;
    }
}
