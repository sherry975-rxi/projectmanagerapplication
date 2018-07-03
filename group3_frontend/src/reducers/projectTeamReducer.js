const INITIAL_STATE = {
    projectTeam: [],
    teamLoading: true
};

export const projectTeamReducer = (state = INITIAL_STATE, action) => {
    switch (action.type) {
        case 'PROJECT_TEAM_FETCHED':
            return { ...state, teamLoading: false, projectTeam: action.projectTeam };
        case 'TEAM_LOADING':
            return { ...state, teamLoading: true };
        default:
            return state;
    }
};

export default projectTeamReducer;