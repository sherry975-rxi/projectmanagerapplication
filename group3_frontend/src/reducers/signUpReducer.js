import { INCREASE_SIGN_UP_STEP } from '../actions/actions';

export const signUpReducer = (state = { signUpStep: 1 }, action = {}) => {
    switch (action.type) {
        case INCREASE_SIGN_UP_STEP:
            return {
                ...state,
                signUpStep: action.payload.signUpStep
            };
        default:
            return state;
    }
};

export default signUpReducer;
