import React, { Component } from "react";
import MediumButton from '../../../src/components/button/mediumButton'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import {changeToFinished, changeToOnGoing, changeToStandBy, changeToNotStarted, changeToAllTasks}  from '../../actions/filterActions';
import './dist/toggle-switch.css'


class FetchTaskButton extends Component{


    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1'        
        };
    }

    handleChange(event,key) {
        
        console.log(key)
        switch(key){
            case("1"):
                 return (<div>{this.props.changeToAllTasks()}</div>);
            case("2"):
                 return (<div>{this.props.changeToOnGoing()}</div>);
            case("3"):
                 return (<div>{this.props.changeToFinished()}</div>);
            case("4"):
                 return (<div>{this.props.changeToNotStarted()}</div>);
            case("5"):
                 return (<div>{this.props.changeToStandBy()}</div>);

        }
    }


    render(){
        return(
             <div class="switch-toggle switch-candy">
                        
                      
                       
                        <input id="alltasks" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "1")} />
                        <label for="alltasks" eventKey="1" >All Tasks</label>

                        <input id="onGoing" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "2")} />
                        <label for="onGoing" eventKey="1" >On Going</label>

                         <input id="finished" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "3")} />
                        <label for="finished" eventKey="1" >Finished</label>

                       <input id="notStarted" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "4")} />
                        <label for="notStarted" eventKey="1" >Not Started</label>

                         <input id="standBy" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "5")} />
                        <label for="standBy" eventKey="1" >Stand By</label>

                        <a></a>
                        </div>
            
                   
     
        )
    }

}

const mapDispatchToProps = dispatch => bindActionCreators({ changeToFinished, changeToNotStarted, changeToOnGoing, changeToStandBy, changeToAllTasks}, dispatch)
export default connect(null, mapDispatchToProps)(FetchTaskButton);
