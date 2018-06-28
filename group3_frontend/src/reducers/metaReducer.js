import {
    PROJECT_TABLE_DETAILS_TOOGLE,
    TOTAL_TIME_SPENT_TASKS_LAST_MONTH
} from '../actions/actions';

export const metaReducer = (state = {}, action = {}) => {
    switch (action.type) {
        case PROJECT_TABLE_DETAILS_TOOGLE:
            return {
                ...state,
                projectsTableOpenIndex: action.payload.index
            };
        case TOTAL_TIME_SPENT_TASKS_LAST_MONTH:
            return {
                ...state,
                totalTimeSpentOnTasksLastMonth: action.payload
            };
        default:
            return state;
    }
};

export default metaReducer;
