import axios from 'axios'

export function login(values) {
    return submit(values, '/login')
}

export function logout() {
    return { type: 'FAIL' }
}

export function submit() {
    return { type: 'SUCCESSFULL' }
}