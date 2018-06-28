import authenticationReducer from './authenticationReducer';
import { combineReducers } from 'redux';
import { reducer as toastrReducer } from 'react-redux-toastr';
import signUpReducer from './signUpReducer';
import filterReducer from './filterReducer';
import projectsReducer from './projectsReducer';
import tasksReducer from './tasksReducer';
import metaReducer from './metaReducer';
import UserReducers from './UserReducers';
import usersFilterReducer from './usersFilterReducer';
import createTaskReducer from './createTaskReducer';
import userTasksReducer from './userTasksReducer';
import projCollabsWoutTasksReducer from './projCollabsWoutTasksReducer';
import projectTeamReducer from './projectTeamReducer';
import availableUsersReducer from './availableProjectTeamReducer';

const appReducer = combineReducers({
    authenthication: authenticationReducer,
    toastr: toastrReducer,
    signUp: signUpReducer,
    filterReducer: filterReducer,
    projects: projectsReducer,
    meta: metaReducer,
    users: UserReducers,
    usersFilter: usersFilterReducer,
    userTasks: userTasksReducer,
    collabsWoutTasks: projCollabsWoutTasksReducer,
    projectTeam: projectTeamReducer,
    availableUsers: availableUsersReducer,
    createTask: createTaskReducer,
    tasks: tasksReducer
});
const rootReducer = (state, action) => {
    if (action.type === 'LOGOUT') {
        state = undefined;
    }

    return appReducer(state, action);
};

export default rootReducer;
