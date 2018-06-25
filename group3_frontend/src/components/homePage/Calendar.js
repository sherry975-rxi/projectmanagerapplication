import React, { Component } from 'react';
import InfiniteCalendar from 'react-infinite-calendar';
import 'react-infinite-calendar/styles.css'; // Make sure to import the default stylesheet


class Calendar extends Component {


    // Render the Calendar


    render(){

        const today = new Date();
        const lastWeek = new Date(today.getFullYear(), today.getMonth(), today.getDate() - 7);
        
        return( <div className="UserDataHomepage">

        <InfiniteCalendar
        width={'100%'}
        height={220}
        selected={today}
        disabledDays={[0,6]}
        minDate={lastWeek}
        theme={{
            selectionColor: '#2A3F54',
            
            textColor: {
              default: '#333',
              active: '#FFF'
            },
            weekdayColor: '#2A3F54',
            headerColor: '#1e2d3a',
            floatingNav: {
              background: '#1e2d3a',
              color: '#FFF',
              chevron: '#1e2d3a'
            }
         }}
      
      />
      </div>


         ) }
    }

export default Calendar;
