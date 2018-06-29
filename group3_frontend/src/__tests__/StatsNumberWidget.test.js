import React from 'react';
import { shallow } from 'enzyme';
import StatsNumberWidget from '../components/StatsNumberWidget/StatsNumberWidget';

describe('StatsNumberWidget', () => {
    it('should render correctly', () => {
        const component = shallow(
            <StatsNumberWidget value="1" title="title" unit="unit" />
        );
        expect(component).toMatchSnapshot();
    });
});
