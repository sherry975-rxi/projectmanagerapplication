import React from 'react';
import { shallow } from 'enzyme';
import LoadingComponent from '../components/loading/LoadingComponent';

describe('LoadingComponent', () => {
    it('should render correctly', () => {
        const component = shallow(<LoadingComponent />);
        expect(component).toMatchSnapshot();
    });
});
