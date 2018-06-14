import { GET_USER_PROJECTS_FULLFIELD, GET_ACTIVE_PROJECTS_FULLFIELD } from '../actions/actions';

export const projectsReducer = (state = {}, action = {}) => {
    switch (action.type) {
        case GET_USER_PROJECTS_FULLFIELD:
            return {
                ...state,
                userProjects: action.payload
            };
        case GET_ACTIVE_PROJECTS_FULLFIELD:
            return {
                ...state,
                activeProjects: action.payload
            };
        default:
            return state;
    }
};

export default projectsReducer;
