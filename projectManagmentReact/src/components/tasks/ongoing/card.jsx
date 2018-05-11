import React, { Component } from 'react';
import Chart from './chart'; 

class Card extends Component {

    constructor(props) {
        super(props)

    }    

  render() {

    var task = this.props.task; 

    return (

      <div className="line">
          <div className="card white z-depth-0">
          <Chart />
            <div className="card-content white-text">
              <span className="card-title blue-grey-text text-darken-2">Description:</span>
              <span className=" blue-grey-text text-darken-2"><p>{this.props.task.description}</p></span>
            </div>
            <div className="card-action">
            <p><b>Project ID: </b>{this.props.task.project}</p>
            <p><b>Start-Date:</b>{this.props.task.startDate}</p>
           
            <button type="button"><span className=" blue-grey-text text-darken-2 left"><b>Details</b></span> </button>
          </div>
        </div>
      </div>

    );
  }
}

export default Card;