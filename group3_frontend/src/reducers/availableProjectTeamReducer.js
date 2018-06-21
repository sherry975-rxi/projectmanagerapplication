const INITIAL_STATE = {
    availableUsers: []
};

export const availableUsersReducer = (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'AVAILABLE_USER_FETCHED':
            return { ...state, availableUsers: action.availableUsers };
        default:
            return state;
    }
};

export default availableUsersReducer;
