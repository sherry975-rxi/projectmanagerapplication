import React from 'react';
import ReactDOM from 'react-dom';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap-theme.css';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import { BrowserRouter } from 'react-router-dom';
import rootReducer from './reducers/rootReducer';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import ReduxThunk from 'redux-thunk';

const devTools = window.__REDUX_DEVTOOLS_EXTENSION__ 
                && window.__REDUX_DEVTOOLS_EXTENSION__()
    
const createStoreWithMiddleware = applyMiddleware(ReduxThunk)(createStore)(rootReducer, devTools)

ReactDOM.render(
    <Provider
        store={createStoreWithMiddleware}
    >
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);
registerServiceWorker();
