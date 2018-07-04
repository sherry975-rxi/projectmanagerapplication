import { GET_USER_PROJECTS_FULLFIELD, GET_ACTIVE_PROJECTS_FULLFIELD, PROJECT_CHOSEN, PROJECT_COST_LOADED, CALCULATION_METHOD_UPDATED } from '../actions/actions';

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
        case PROJECT_CHOSEN:
            return {
                ...state,
                project: action.payload,
                calculationMethod: action.payload.projectCalculationMethod
            }
        case PROJECT_COST_LOADED:
            return {
                ...state,
                projectCost: action.payload.projectCost,
                calculationMethod: action.payload.calculationMethod
            }
        case CALCULATION_METHOD_UPDATED:
            return {
                ...state,
                calculationMethod: action.payload.calculationMethod
            }
        default:
            return state;
    }
};

export default projectsReducer;
