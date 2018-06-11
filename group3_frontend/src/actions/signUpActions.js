import { INCREASE_SIGN_UP_STEP } from './actions';

export const increaseSignUpStepAction = payload => {
    return {
        type: INCREASE_SIGN_UP_STEP,
        payload
    };
};
