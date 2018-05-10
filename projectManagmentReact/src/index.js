import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ActiveProjects from './ActiveProjects';
import AllUsers from './Admin/AllUsers';

import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<ActiveProjects />, document.getElementById('root'));
ReactDOM.render(<AllUsers />, document.getElementById('allUsers'));


registerServiceWorker();
