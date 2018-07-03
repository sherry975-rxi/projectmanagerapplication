import React from 'react';
import { shallow } from 'enzyme';
import ItemsButton from '../components/projectsTable/itemsButton';

describe('ItemsButton', () => {
    it('should render correctly', () => {
        const component = shallow(
            <ItemsButton href="ref" text="component text" />
        );
        expect(component).toMatchSnapshot();
    });
});
