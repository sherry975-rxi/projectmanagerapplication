import React, { Component } from "react";
import MediumButton from '../../../src/components/button/mediumButton'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import {changeToFinished, changeToOnGoing, changeToStandBy, changeToNotStarted, changeToAllTasks}  from '../../actions/filterActions';
import './dist/toggle-switch.css'
import './dist/FetchTask.css'


class FetchTaskButton extends Component{


    constructor(props) {
        super(props);
        this.state = {
            activeKey: '1'        
        };
    }

    handleChange(event,key) {
        
        switch(key){
            case("1"):
                 return (this.props.changeToAllTasks());
            case("2"):
                 return (this.props.changeToOnGoing());
            case("3"):
                 return (this.props.changeToFinished());
            case("4"):
                 return (<div>{this.props.changeToNotStarted()}</div>);
            case("5"):
                 return (<div>{this.props.changeToStandBy()}</div>);

        }
    }


    render(){
        return(
            <div className="buttonWrapper">
             <div class="switch-toggle switch-ios">
                        
                         
                <input id="alltasks" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "1")} />
                <label class="buttonFont" for="alltasks" eventKey="1" >All Tasks</label>

                <input id="onGoing" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "2")} />
                <label class="buttonFont" for="onGoing" eventKey="1" >On Going</label>

                <input id="finished" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "3")} />
                <label class="buttonFont" for="finished" eventKey="1" >Finished</label>

                <input id="notStarted" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "4")} />
                <label class="buttonFont" for="notStarted" eventKey="1" >Not Started</label>

                <input id="standBy" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "5")} />
                <label class="buttonFont" for="standBy" eventKey="1" >Stand By</label>

                <a></a>
            </div>
        </div>
            
                   
     
        )
    }

}

const mapDispatchToProps = dispatch => bindActionCreators({ changeToFinished, changeToNotStarted, changeToOnGoing, changeToStandBy, changeToAllTasks}, dispatch)
export default connect(null, mapDispatchToProps)(FetchTaskButton);
