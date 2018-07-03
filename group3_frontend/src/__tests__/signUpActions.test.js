import { INCREASE_SIGN_UP_STEP } from '../actions/actions';
import { increaseSignUpStepAction } from '../actions/signUpActions';

describe('signUpActions', () => {
    it('should return a valid INCREASE_SIGN_UP_STEP action', () => {
        const payload = 'payload';
        const expected = {
            type: INCREASE_SIGN_UP_STEP,
            payload
        };
        expect(increaseSignUpStepAction(payload)).toEqual(expected);
    });
});
