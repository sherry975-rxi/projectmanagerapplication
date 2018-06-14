import { PROJECT_TABLE_DETAILS_TOOGLE } from '../actions/actions';

export const metaReducer = (state = {}, action = {}) => {
    switch (action.type) {
        case PROJECT_TABLE_DETAILS_TOOGLE:
            return {
                ...state,
                projectsTableOpenIndex: action.payload.index
            };
        default:
            return state;
    }
};

export default metaReducer;
