import axios from 'axios'


export function logout() {
    return { type: 'LOGOUT' }
}

export function submit() {
    return { type: 'SUCCESSFULL' }
}

export function dispatchError() {
    console.log("OLA")
    return { type: 'ERROR' }
}