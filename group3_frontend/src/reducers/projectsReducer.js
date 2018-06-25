import { GET_USER_PROJECTS_FULLFIELD, GET_ACTIVE_PROJECTS_FULLFIELD } from '../actions/actions';

export const projectsReducer = (state = {}, action = {}) => {
    switch (action.type) {
        case GET_USER_PROJECTS_FULLFIELD:
            return {
                ...state,
                userProjects: action.payload,
                error: action.payload.error
            };
        case GET_ACTIVE_PROJECTS_FULLFIELD:
            return {
                ...state,
                activeProjects: action.payload,
                error: action.payload.error
            };
        case 'PROJECT_CHOSEN':
            return {
                ...state,
                projectManager: action.projectManager
            }
        default:
            return state;
    }
};

export default projectsReducer;
