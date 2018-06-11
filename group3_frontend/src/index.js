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

const createStoreWithMiddleware = applyMiddleware(ReduxThunk)(createStore);
let devTools;

const devToolsExtension = window.devToolsExtension();
if (typeof devToolsExtension === 'function') {
    devTools = devToolsExtension;
}

ReactDOM.render(
    <Provider store={createStoreWithMiddleware(rootReducer, devTools)}>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </Provider>,
    document.getElementById('root')
);
registerServiceWorker();
