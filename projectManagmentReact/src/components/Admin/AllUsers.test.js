import React from 'react';
import ReactDOM from 'react-dom';
import AllUsers from './AllUsers';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<AllUsers />, div);
  ReactDOM.unmountComponentAtNode(div);
});