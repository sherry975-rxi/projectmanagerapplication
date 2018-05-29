import authenticationReducer from './authentication/authentication'
import { combineReducers } from 'redux';


const rootReducer = combineReducers({
    authenthication: authenticationReducer
})

export default rootReducer
