import React, { Component } from 'react';
import Navbar from './components/navbar/navbar'; 
import Main from './components/main/main';
import ActiveProjects from './components/activeProjects/ActiveProjects';
import AllUsers from './components/Admin/AllUsers';
import UsersList from './components/Admin/UsersList';
import UserOngoing from './components/tasks/ongoing/UserOngoing'; 


class App extends Component {
  render() {

    let subtitle = "Project Management Web-Application"
 
    return (
     
      
      <div className="App">
          {/* <Main title="mySwitch" subTitle= {subtitle}/>/>*/}
        
          <Navbar />
          <UserOngoing />
          <ActiveProjects />
          <AllUsers />
          <UsersList />

      </div>
    );
  }
}

export default App;
