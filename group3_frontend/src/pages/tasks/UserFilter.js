import React, { Component } from "react";
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import './dist/toggle-switch.css'
import './dist/FetchTask.css'
import { updateAllUsers, updateEmail, updateCollaborators, updateDirector, updateAdministrator } from './../../actions/UserActions';


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
                return (this.props.updateAllUsers());
            case ("2"):
                return (this.props.updateEmail((this.props.userID)));
            case ("3"):
                return (this.props.updateCollaborators());
            case ("4"):
                return (this.props.updateDirector());
            case ("5"):
                return (this.props.updateAdministrator());

        }
    }

    async componentDidMount() {
        this.props.updateAllUsers()
    }


    render() {
        return (
            <div className="buttonWrapper">
                <div class="switch-toggle switch-ios">
                    <input id="allUsers" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "1")} />
                    <label class="buttonFont" for="alltasks" eventKey="1" >All Users</label>

                    <input id="emailUsers" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "2")} />
                    <label class="buttonFont" for="onGoing" eventKey="1" >Email</label>

                    <input id="allCollaborators" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "3")} />
                    <label class="buttonFont" for="finished" eventKey="1" >Collaborators</label>

                    <input id="allDirector" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "4")} />
                    <label class="buttonFont" for="finished" eventKey="1" >Director</label>

                    <input id="allAdministrator" name="view3" type="radio" eventKey="1" onChange={(e) => this.handleChange(e, "5")} />
                    <label class="buttonFont" for="finished" eventKey="1" >Administrator</label>


                    <a></a>
                </div>
            </div>



        )
    }

}

const mapDispatchToProps = dispatch => bindActionCreators({ updateAllUsers, updateEmail, updateCollaborators, updateDirector, updateAdministrator}, dispatch)
export default connect(null, mapDispatchToProps)(UserFilter);
