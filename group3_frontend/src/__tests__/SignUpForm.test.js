import React from 'react';
import { shallow } from 'enzyme';
import SignUpForm from '../components/signUpForm/SignUpForm';

describe('SignUpForm', () => {
    const fetchResult = {
        json: jest.fn().mockReturnValue({ href: 'responseUrl' })
    };

    let mockedClass;
    let eventMock = { preventDefault: jest.fn() };

    afterEach(() => {
        global.fetch.mockClear();
    });

    beforeEach(() => {
        global.fetch = jest
            .fn()
            .mockImplementation(() => Promise.resolve(fetchResult));

        mockedClass = new SignUpForm();
        mockedClass.setState = jest.fn().mockImplementation(val => {
            return val;
        });
    });

    it('should render correctly', () => {
        const component = shallow(<SignUpForm incrementStep="incrementStep" />);
        expect(component).toMatchSnapshot();
    });

    it('should post user details correctly', () => {
        mockedClass.state = {
            name: 'name',
            email: 'email',
            userFunction: 'userFunction',
            phone: 'phone',
            password: 'password',
            securityQuestion: 'securityQuestion',
            securityAnswer: 'securityAnswer',
            street: 'street',
            zipCode: 'zipCode',
            city: 'city',
            district: 'district',
            country: 'country',
            idNumber: 'idNumber'
        };

        mockedClass.props = {
            incrementStep: jest.fn()
        };

        const expectedFetchOptions = {
            body:
                '{"name":"name","email":"email","function":"userFunction","phone":"phone","password":"password","question":"securityQuestion","answer":"securityAnswer","street":"street","zipCode":"zipCode","city":"city","district":"district","country":"country","idNumber":"idNumber"}',
            headers: { 'content-type': 'application/json' },
            method: 'POST'
        };

        const fetchResult = { href: 'responseUrl' };

        mockedClass.handleSubmit(eventMock).then(() => {
            expect(global.fetch.mock.calls.length).toEqual(1);
            expect(global.fetch.mock.calls[0][0]).toBe('/account/register');
            expect(global.fetch.mock.calls[0][1]).toEqual(expectedFetchOptions);
            expect(mockedClass.props.incrementStep).toHaveBeenCalledWith(
                fetchResult
            );
        });
        expect(eventMock.preventDefault).toBeCalled();
    });

    it('should catch error when post user details fail', () => {
        global.fetch = jest.fn().mockImplementation(() => Promise.reject());

        mockedClass.props = {
            incrementStep: '1'
        };

        mockedClass.handleSubmit(eventMock).then(() => {
            expect(mockedClass.setState).toBeCalledWith({
                hideExistingEmail: ''
            });
        });
    });

    it('should setState when handleChange is called', () => {
        eventMock = {
            target: {
                id: 'id',
                value: 'value'
            }
        };
        mockedClass.setState = jest.fn();
        mockedClass.handleChange(eventMock);
        expect(mockedClass.setState).toHaveBeenLastCalledWith({ id: 'value' });
    });

    it('should setState when checkBoxTerms is checked', () => {
        eventMock = {
            ...eventMock,
            target: {
                checked: 'checked'
            }
        };

        mockedClass.setState = jest.fn();
        mockedClass.handleChangeCheckboxTerms(eventMock);
        expect(mockedClass.setState).toHaveBeenLastCalledWith({
            aceptTerms: 'checked'
        });
    });

    it('should setState (contry) according the val received', () => {
        const val = 'Portugal';

        mockedClass.setState = jest.fn();
        mockedClass.selectCountry(val);
        expect(mockedClass.setState).toHaveBeenLastCalledWith({
            country: 'Portugal'
        });
    });

    it('should setState (region) according the val received', () => {
        const val = 'South';

        mockedClass.setState = jest.fn();
        mockedClass.selectRegion(val);
        expect(mockedClass.setState).toHaveBeenLastCalledWith({
            district: 'South'
        });
    });

    it('should return true when the length of all state fields is > 0 and aceaceptTerms is true ', () => {
        mockedClass.state = {
            name: 'name',
            email: 'email',
            userFunction: 'userFunction',
            phone: 'phone',
            password: 'password',
            securityQuestion: 'securityQuestion',
            securityAnswer: 'securityAnswer',
            street: 'street',
            zipCode: 'zipCode',
            city: 'city',
            district: 'district',
            country: 'country',
            idNumber: 'idNumber',
            aceptTerms: true
        };
        expect(mockedClass.validateForm()).toBeTruthy;
    });

    it('should return false when the length of one state field is <0 and/or aceaceptTerms is false ', () => {
        mockedClass.state = {
            name: 'name',
            email: 'email',
            userFunction: 'userFunction',
            phone: 'phone',
            password: 'password',
            securityQuestion: 'securityQuestion',
            securityAnswer: 'securityAnswer',
            street: 'street',
            zipCode: 'zipCode',
            city: 'city',
            district: 'district',
            country: 'country',
            idNumber: '',
            aceptTerms: false
        };
        expect(mockedClass.validateForm()).toBeFalsy;
    });
});
