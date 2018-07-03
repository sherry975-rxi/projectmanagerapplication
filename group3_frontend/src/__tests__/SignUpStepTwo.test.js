import React from 'react';
import { shallow } from 'enzyme';
import SignUpStepTwo from '../components/signUpStepTwo/SignUpStepTwo';

describe('SignUpStepTwo', () => {
    const fetchResult = {
        json: jest.fn().mockReturnValue({ href: 'responseUrl' })
    };
    global.fetch = jest
        .fn()
        .mockImplementation(() => Promise.resolve(fetchResult));

    let mockedClass;

    afterEach(() => {
        global.fetch.mockClear();
    });

    beforeEach(() => {
        mockedClass = new SignUpStepTwo();
    });

    it('should fetch data with smsValidation validation type', () => {
        const eventSmsMock = {
            target: {
                id: 'smsValidation'
            }
        };

        mockedClass.props = {
            validationUrl: {
                smsValidation: 'smsValidationUrl'
            },
            setStepTwo: jest.fn()
        };
        mockedClass.handleSignUpValidation(eventSmsMock).then(() => {
            const expectedProps = {
                signUpStep: 3,
                verificationUrl: 'responseUrl'
            };
            expect(mockedClass.props.setStepTwo).toHaveBeenCalledWith(
                expectedProps
            );
        });
        expect(global.fetch).toHaveBeenCalledWith(
            mockedClass.props.validationUrl.smsValidation
        );
    });

    it('should fetch data with emailValidation validation type', () => {
        const eventSmsMock = {
            target: {
                id: 'emailValidation'
            }
        };

        mockedClass.props = {
            validationUrl: {
                emailValidation: 'emailValidationUrl'
            },
            setStepTwo: jest.fn()
        };
        mockedClass.handleSignUpValidation(eventSmsMock).then(() => {
            const expectedProps = {
                signUpStep: 3,
                verificationUrl: 'responseUrl'
            };
            expect(mockedClass.props.setStepTwo).toHaveBeenCalledWith(
                expectedProps
            );
        });
        expect(global.fetch).toHaveBeenCalledWith(
            mockedClass.props.validationUrl.emailValidation
        );
    });

    it('should render correctly', () => {
        const component = shallow(<SignUpStepTwo />);
        expect(component).toMatchSnapshot();
    });

    it('should not fetch data when receives a invalid validation type', () => {
        const eventSmsMock = {
            target: {
                id: 'invalid'
            }
        };

        mockedClass.handleSignUpValidation(eventSmsMock);

        expect(global.fetch).not.toHaveBeenCalled();
    });
});
