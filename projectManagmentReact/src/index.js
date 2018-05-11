import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import $ from 'jquery';
import 'materialize-css';
import 'materialize-css/dist/css/materialize.min.css';
import 'materialize-css/dist/js/materialize.js'; 
import 'materialize-css/dist/js/materialize.min.js'; 
import registerServiceWorker from './registerServiceWorker';
import App from './App';


ReactDOM.render(<App />, document.getElementById('root'));

registerServiceWorker();
