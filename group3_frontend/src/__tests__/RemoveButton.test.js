import React from 'react';
import { shallow } from 'enzyme';
import RemoveButton from '../components/button/removeButton';

describe('RemoveButton tests', function() {

    it('should have a button with a specific className and a specific icon', function() {
        expect( shallow(<RemoveButton/>).contains(<button type="submit" className="remove-Button"><span className="glyphicon glyphicon-remove" role="button" aria-hidden="true"></span></button>)).toBe(true);
    })

})