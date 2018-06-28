import decode from 'jwt-decode';
import axios from 'axios';
export default class AuthService {
    //HTTP login request to server/login
    login = (email, password) => {
        return axios
            .post('/login', { email: email, password: password })
            .then(res => {
                this.setToken(res); // Setting the token in localStorage
                this.setUser(res);
                return Promise.resolve(res);
            });
    };

    //Verifies if the user is logged in
    loggedIn() {
        const token = this.getToken();
        return !!token && !this.isTokenExpired(token);
    }

    //Verifies if the token is experided
    isTokenExpired(token) {
        try {
            const decoded = decode(token);
            return decoded.exp < Date.now() / 1000;
        } catch (err) {
            return false;
        }
    }

    setToken(res) {
        const idToken = res.headers.authorization;
        localStorage.setItem('id_token', idToken);
    }

    setUser(res) {
        localStorage.setItem('id_user', res.data.idNumber);
    }

    getUserId() {
        return localStorage.getItem('id_user');
    }

    //Gets the user token from localStorage
    getToken() {
        return localStorage.getItem('id_token');
    }

    //Removes the user token and profile data from localStorage
    logout() {
        localStorage.removeItem('id_token');
    }

    // Using jwt-decode npm package to decode the token
    getProfile = () => {
        return decode(this.getToken());
    };

    //Fetch that sends a header with the authorization token storen in the localStorage
    fetch(url, options) {
        const headers = {
            Accept: 'application/json',
            'Content-Type': 'application/json'
        };

        if (this.loggedIn()) {
            headers['Authorization'] = this.getToken();
        }

        return fetch(url, {
            headers,
            ...options
        }).then(response => {
            localStorage.setItem(
                'id_token',
                response.headers.get('Authorization')
            );
            return response.json();
        });
    }


    //Fetch that sends a header with the authorization token storen in the localStorage
    fetchRaw(url, options) {
        const headers = {
            Accept: 'application/json',
            'Content-Type': 'application/json'
        };

        if (this.loggedIn()) {
            headers['Authorization'] = this.getToken();
        }

        return fetch(url, {
            headers,
            ...options
        }).then(response => {
            localStorage.setItem(
                'id_token',
                response.headers.get('Authorization')
            );
            return response
        });
    }

    //Verifies if the HTTP response is valid (within 200)
    checkStatus(response) {
        if (response.status >= 200 && response.status < 300) {
            return response;
        } else {
            var error = new Error(response.statusText);
            error.response = response;
            throw error;
        }
    }

    fetchWithoutContentType(url, options) {
        const headers = {
            Accept: 'application/json'
        };

        if (this.loggedIn()) {
            headers['Authorization'] = this.getToken();
        }

        return fetch(url, {
            headers,
            ...options
        }).then(response => {
            localStorage.setItem(
                'id_token',
                response.headers.get('Authorization')
            );
            return response
        });
    }
}
