import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ActiveProjects from './ActiveProjects';

import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<ActiveProjects />, document.getElementById('root'));

registerServiceWorker();
