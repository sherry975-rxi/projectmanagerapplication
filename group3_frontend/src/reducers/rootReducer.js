import authenticationReducer from './authenticationReducer';
import { combineReducers } from 'redux';
import { reducer as toastrReducer } from 'react-redux-toastr';
import signUpReducer from './signUpReducer';
import filterReducer from './filterReducer';

const rootReducer = combineReducers({
    authenthication: authenticationReducer,
    toastr: toastrReducer,
    signUp: signUpReducer,
    filterReducer: filterReducer
});

export default rootReducer;
