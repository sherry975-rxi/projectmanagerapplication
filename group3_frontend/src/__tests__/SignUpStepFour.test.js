import React from 'react';
import { shallow } from 'enzyme';
import SignUpStepFour from '../components/signUpStepFour/SignUpStepFour';

describe('SignUpStepFour', () => {
    it('should render correctly', () => {
        const component = shallow(<SignUpStepFour />);
        expect(component).toMatchSnapshot();
    });
});
