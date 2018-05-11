import React, { Component } from 'react';
import Navbar from './components/navbar/navbar'; 
import Main from './components/main/main';
import ActiveProjects from './components/activeProjects/ActiveProjects';
import AllUsers from './components/Admin/AllUsers';
import UsersList from './components/Admin/UsersList';
import MarkTaskAsFinished from './MarkTaskAsFinished';

class App extends Component {
  render() {

    let subtitle = "Project Management Web-Application"

    return (
      <div className="App">
          {/* <Main title="mySwitch" subTitle= {subtitle}/>/>*/}
          <Navbar/>
          <ActiveProjects />
          <AllUsers />
          <UsersList />
          <MarkTaskAsFinished />

      </div>
    );
  }
}

export default App;
