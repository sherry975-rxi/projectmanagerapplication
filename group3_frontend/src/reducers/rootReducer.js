import authenticationReducer from './authenticationReducer';
import { combineReducers } from 'redux';
import { reducer as toastrReducer } from 'react-redux-toastr';
import signUpReducer from './signUpReducer';
import filterReducer from './filterReducer';
import projectTasksReducer from './projectTasksReducer';
import projectsReducer from './projectsReducer';
import metaReducer from './metaReducer';
import UserReducers from './UserReducers';
import usersFilterReducer from './usersFilterReducer';
import userTasksReducer from './userTasksReducer';
import projCollabsWoutTasksReducer from './projCollabsWoutTasksReducer';
import projectTeamReducer from "./projectTeamReducer";

const appReducer = combineReducers({
    authenthication: authenticationReducer,
    toastr: toastrReducer,
    signUp: signUpReducer,
    filterReducer: filterReducer,
    projectTasks: projectTasksReducer,
    projects: projectsReducer,
    meta: metaReducer,
    users: UserReducers,
    usersFilter: usersFilterReducer,
    userTasks: userTasksReducer,
    collabsWoutTasks : projCollabsWoutTasksReducer,
    projectTeam: projectTeamReducer
});
const rootReducer = (state, action) => {
    if (action.type === 'LOGOUT') {
        state = undefined
    }

    return appReducer(state, action)
};

export default rootReducer;
