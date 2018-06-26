import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import '../tasks/dist/toggle-switch.css';
import '../tasks/dist/FetchTask.css';
import {
    updateAllUsers,
    updateEmail,
    updateCollaborators,
    updateDirector,
    updateAdministrator,
    searchList,
    updateVisitors
} from './../../actions/UserActions';

class UserFilter extends Component {
    constructor(props) {
        super(props);
        this.activeFilter = ""
        this.state = {
            activeKey: '1'
        };
    }

    filterList(event) {

        if(event.target.value.toLowerCase()!== -1){
            
            switch (this.activeFilter){
                case "1":
                return this.props.searchList(event, this.props.allUsers);
                case "2":
                return this.props.searchList(event, this.props.emailUsers);
                case "3":
                return this.props.searchList(event, this.props.allCollaborators);
                case '4':
                return this.props.searchList(event, this.props.allDirector);
                case '5':
                return this.props.searchList(event, this.props.allAdministrator);
                case '6':
                return this.props.searchList(event, this.props.allVisitors);
                default:
                return this.props.searchList(event, this.props.allUsers);
                
                
            }        
        }
    }

    handleChange(event, key) {
        switch (key) {
            case '1':
                this.activeFilter = "1";
                return this.props.updateAllUsers();

            case '2':
                this.activeFilter = "2";
                return this.props.updateEmail(this.props.userID);

            case '3':
                this.activeFilter = "3";
                return this.props.updateCollaborators();

            case '4':
             this.activeFilter = "4";
                return this.props.updateDirector();

            case '5':
                this.activeFilter = "5";
                return this.props.updateAdministrator();

            case '6':
                this.activeFilter = "6";
                return this.props.updateVisitors();
            default:
                return;
        }
    }

    async componentDidMount() {
        this.props.updateAllUsers();
    }

    render() {
        return (
            <div className="buttonWrapper">
                                        <fieldset>

                <div class="switch-toggle switch-ios">
                    {/* <input
                        id="Test"
                        name="view3"
                        type="radio"
                        onChange={<div></div>}
                    />
                    <label className="buttonFont" htmlFor="">
                        <b>Filter by:</b>
                    </label> */}
                    <input
                        id="allUsers"
                        name="view3"
                        type="radio"
                        eventKey="1"
                        onChange={e => this.handleChange(e, '1')}
                    />
                    <label class="buttonFont" for="allUsers" eventKey="1">
                        All Users
                    </label>

                    <input
                        id="allVisitors"
                        name="view3"
                        type="radio"
                        eventKey="1"
                        onChange={e => this.handleChange(e, '6')}
                    />
                    <label class="buttonFont" for="allVisitors" eventKey="1">
                        Visitors
                    </label>

                    <input
                        id="allCollaborators"
                        name="view3"
                        type="radio"
                        eventKey="1"
                        onChange={e => this.handleChange(e, '3')}
                    />
                    <label
                        class="buttonFont"
                        for="allCollaborators"
                        eventKey="1"
                    >
                        Collaborator
                    </label>

                    <input
                        id="allDirector"
                        name="view3"
                        type="radio"
                        eventKey="1"
                        onChange={e => this.handleChange(e, '4')}
                    />
                    <label class="buttonFont" for="allDirector" eventKey="1">
                        Director
                    </label>

                    <input
                        id="allAdministrator"
                        name="view3"
                        type="radio"
                        eventKey="1"
                        onChange={e => this.handleChange(e, '5')}
                    />
                    <label
                        class="buttonFont"
                        for="allAdministrator"
                        eventKey="1"
                    >
                        Admin
                    </label>
                    <a></a>

                </div>
                </fieldset>

                <div className=" table-striped">
                    <div className="filter-list">
                        <form>
                        <fieldset className="form-group">
                        <input 
                            type="text" 
                            className="form-control form-control-lg" 
                            placeholder="Search by email" 
                            onChange={(event) => this.filterList(event)}
                        />
                        {console.log("tesTEEEEEEEE")}
                        {console.log(this.activeFilter)}
                        </fieldset>
                        </form>
                       {/* <button onClick={e => this.handleChange(e, '2')} >
                    Confirm
                    </button> */}
                        
                    </div>
                </div>
            </div>
        );
    }
}

const mapStateToProps = state => {
    return {
        allAdministrator: state.users.allAdministrator,
        allDirector: state.users.allDirector,
        allCollaborators: state.users.allCollaborators,
        emailUsers: state.users.emailUsers,
        allUsers: state.users.allUsers,
        allVisitors: state.users.allVisitors
    };
};

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            updateAllUsers,
            updateEmail,
            updateCollaborators,
            updateDirector,
            updateAdministrator,
            searchList,
            updateVisitors
        },
        dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(UserFilter);
