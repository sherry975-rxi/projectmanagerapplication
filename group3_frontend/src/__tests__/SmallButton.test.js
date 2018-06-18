import React from 'react';
import { shallow } from 'enzyme';
import SmallButton from "../components/button/SmallButton"


describe('SmallButton', () => {
    it('should be defined', () => {
        expect(SmallButton).toBeDefined();
    });
    it('should render correctly', () => {
        const tree = shallow(
            <SmallButton name='button test' />

        );
        expect(tree).toMatchSnapshot();
    });

})