import React from 'react';
import { shallow } from 'enzyme';
import SignUpStepThree from '../components/signUpStepThree/SignUpStepThree';

describe('SignUpStepThree', () => {
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

        mockedClass = new SignUpStepThree();
    });

    it('should fetch data with a valid verification code', () => {
        mockedClass.props = {
            validationCodeUrl: 'codeUrl',
            setStepThree: jest.fn()
        };
        mockedClass.state.verificationCode = 'code';

        mockedClass.handleCodeSubmit(eventMock).then(() => {
            const expectedProps = {
                signUpStep: 4
            };
            expect(mockedClass.props.setStepThree).toHaveBeenCalledWith(
                expectedProps
            );

            expect(global.fetch.mock.calls.length).toEqual(1);
            expect(global.fetch.mock.calls[0][0]).toBe('codeUrl');
            expect(global.fetch.mock.calls[0][1]).toEqual({
                body: '{"codeToCheck":"code"}',
                headers: { 'content-type': 'application/json' },
                method: 'POST'
            });

            expect(eventMock.preventDefault).toBeCalled();
        });
    });

    it('should setState when verification code is invalid', () => {
        global.fetch = jest.fn().mockImplementation(() => Promise.reject());
        mockedClass.setState = jest.fn();

        mockedClass.props = {
            validationCodeUrl: 'codeUrl'
        };
        mockedClass.state.verificationCode = 'code';

        mockedClass.handleCodeSubmit(eventMock).then(() => {
            expect(mockedClass.setState).toBeCalledWith({
                hideWrongCode: ''
            });
        });
    });

    it('should render correctly', () => {
        const component = shallow(<SignUpStepThree />);
        expect(component).toMatchSnapshot();
    });

    it('should return true when the length of verificationCode is > 0', () => {
        mockedClass.state.verificationCode = '1234';
        expect(mockedClass.validateCodeForm()).toBeTruthy;
    });

    it('should return false when the length of verificationCode is < 0', () => {
        mockedClass.state.verificationCode = '';
        expect(mockedClass.validateCodeForm()).toBeFalsy;
    });

    it('handle change', () => {
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
});
