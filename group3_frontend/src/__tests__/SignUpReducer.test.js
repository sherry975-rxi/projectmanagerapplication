import { INCREASE_SIGN_UP_STEP } from '../actions/actions';
import { signUpReducer } from '../reducers/signUpReducer';

describe('SignUpReducer', () => {
    it('should change the state for action type INCREASE_SIGN_UP_STEP', () => {
        const initialState = {
            signUpStep: 1
        };
        const expectedState = {
            signUpStep: 2
        };
        const action = {
            type: INCREASE_SIGN_UP_STEP,
            payload: { signUpStep: 2 }
        };
        expect(signUpReducer(initialState, action)).toEqual(expectedState);
    });

    it('should return initial state when no action or state is passed', () => {
        const expectedState = {
            signUpStep: 1
        };
        expect(signUpReducer()).toEqual(expectedState);
    });
});
