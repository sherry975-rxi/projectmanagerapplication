const INITIAL_STATE = {
    projectTeam: []
};

export const projectTeamReducer = (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'PROJECT_TEAM_FETCHED':
            return { ...state, projectTeam: action.projectTeam };
        default:
            return state;
    }
};

export default projectTeamReducer;