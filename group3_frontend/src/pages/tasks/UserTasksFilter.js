import React, { Component } from "react";
import MediumButton from '../../../src/components/button/mediumButton'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import './dist/toggle-switch.css'
import './dist/FetchTask.css'
import { updateAllTasks, updateFinishedTasks, updateNotStartedTasks, updateOngoingTasks } from './../../actions/userTasksActions';


class UserTasksFilter extends Component {

    constructor(props) {
        super(props);

        this.state = {
            activeKey: '1'
        };
    }

    handleChange(event, key) {

        switch (key) {
            case ("1"):
                return (this.props.updateAllTasks((this.props.userID)));
            case ("2"):
                return (this.props.updateOngoingTasks((this.props.userID)));
            case ("3"):
                return (this.props.updateFinishedTasks((this.props.userID)));

        }
    }

    async componentDidMount() {
        this.props.updateAllTasks(this.props.userID)
    }


    render() {
        return (
            <div className="buttonWrapper">
                <div class="switch-toggle switch-ios">
                    <input id="alltasks" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "1")} />
                    <label class="buttonFont" for="alltasks" eventKey="1" >All Tasks</label>

                    <input id="onGoing" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "2")} />
                    <label class="buttonFont" for="onGoing" eventKey="1" >On Going</label>

                    <input id="finished" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "3")} />
                    <label class="buttonFont" for="finished" eventKey="1" >Finished</label>


                    <a></a>
                </div>
            </div>



        )
    }

}

const mapDispatchToProps = dispatch => bindActionCreators({ updateAllTasks, updateFinishedTasks, updateOngoingTasks }, dispatch)
export default connect(null, mapDispatchToProps)(UserTasksFilter);
