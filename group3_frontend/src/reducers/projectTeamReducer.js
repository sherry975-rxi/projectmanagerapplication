import AuthService from "../pages/loginPage/AuthService";

const INITIAL_STATE = {
    projectTeam: []

}

export const projectTeamReducer = (action, state = INITIAL_STATE) => {
    switch (action.type) {
        case 'PROJECT_TEAM_FETCHED':
            return { ...state, projectTeam: action.projectTeam }
        default:
            return state;
    }
};

export default projectTeamReducer;