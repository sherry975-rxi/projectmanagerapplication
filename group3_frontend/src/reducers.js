import authenticationReducer from './authentication/authentication'
import { combineReducers } from 'redux';
import { reducer as toastrReducer } from 'react-redux-toastr'


const rootReducer = combineReducers({
    authenthication: authenticationReducer,
    toastr: toastrReducer
})

export default rootReducer
