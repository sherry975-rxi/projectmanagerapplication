import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import ActiveProjects from './ActiveProjects';

import registerServiceWorker from './registerServiceWorker';
import MarkTaskAsFinished from "./MarkTaskAsFinished";

ReactDOM.render(<ActiveProjects />, document.getElementById('root'));
ReactDOM.render(<MarkTaskAsFinished />, document.getElementById('markTaskAsFinished'));

registerServiceWorker();
