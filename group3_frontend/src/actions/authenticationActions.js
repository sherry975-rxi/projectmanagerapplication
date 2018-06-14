export function logout() {
    return { type: 'LOGOUT' };
}

export function submit() {
    return { type: 'SUCCESSFULL' };
}

export function authorize(user) {
    return { type: 'AUTHORIZED',
             user: user };
}

export function dispatchError() {
    return { type: 'ERROR' };
}
