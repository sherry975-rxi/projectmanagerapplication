import React from 'react';
import { shallow } from 'enzyme';
import StatsWidgetsContainer from '../components/StatsWidgetsContainer/StatsWidgetsContainer';

describe('StatsWidgetsContainer', () => {
    it('should render correctly', () => {
        const dataArray = [
            { value: '1', title: 'task time', unit: 'hours' },
            { value: '2', title: 'project cost', unit: 'euros' }
        ];
        const component = shallow(<StatsWidgetsContainer data={dataArray} />);
        expect(component).toMatchSnapshot();
    });
});
