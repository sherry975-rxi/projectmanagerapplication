import React from 'react';
import ReactDOM from 'react-dom';
import ActiveProjects from './ActiveProjects';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<ActiveProjects />, div);
  ReactDOM.unmountComponentAtNode(div);
});
