import React from 'react';
import { shallow } from 'enzyme';
import SideButton from '../components/sideBar/SideButton';

describe('SideButton', () => {
    it('should render correctly', () => {
        const component = shallow(
            <SideButton to="/mytest" text="Button text" />
        );
        expect(component).toMatchSnapshot();
    });
});
