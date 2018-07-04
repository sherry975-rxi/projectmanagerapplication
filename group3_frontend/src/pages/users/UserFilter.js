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
import {
    MenuItem,
    DropdownButton
} from 'react-bootstrap';
import * as Constants from './../../components/utils/titleConstants';


class UserFilter extends Component {
    constructor(props) {
        super(props);
        this.activeFilter = ""
        this.option = ''
        this.state = {
            activeKey: '1',
            selectedOption: '2',
            selectedField: 'Email',
        };



    }

    renderDropdownButton(title, i) {

        const styleButton = {
            position: "absolute",
            marginTop: "-18px",
            marginLeft: "-65px"
        }

        return (
            <DropdownButton
                style={styleButton}
                className="buttonFinished"
                bsStyle={title.toLowerCase()}
                title={title}
                key={i}
                id={`dropdown-basic-${i}`}
            >
                {this.renderFields()}
            </DropdownButton>
        );
    }

    renderFields() {


        var items = Constants.USERS
            .filter((element, index) => (index < 5))
            .map((element, index) => {
                return (
                    <MenuItem
                        eventKey={index}
                        key={index}
                        onSelect={(event) => this.handleClick(event)}
                    >
                        {element}
                    </MenuItem>
                );
            });
        return items;

    }

    handleClick(eventKey) {
        if (eventKey !== -1) {
            const selectionIndex = eventKey + 1;

            switch (selectionIndex) {
                case 1:

                    this.option = '1';
                    this.field = 'Name';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });


                    break;
                case 2:
                    this.option = '2';
                    this.field = 'Email';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

                    break;
                case 3:
                    this.option = '3';
                    this.field = 'Profile';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

                    break;
                case 4:
                    this.option = '4';
                    this.field = 'Role';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });
                    break;
                case 5:
                    this.option = '5';
                    this.field = 'State "true" for active or "false" for not active';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });
                    break;
                default:

                    this.option = '2';
                    this.field = 'Email';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

            }
        }


    }

    renderSearchForm() {
        return (
            <div className=" table-striped">
                <div className="filter-list">
                    <form>
                        <fieldset className="form-group">
                            <input
                                type="text"
                                className="form-control form-control-lg"
                                placeholder={`Search by ${this.state.selectedField}`}
                                onChange={(event) => this.filterList(event, this.state.selectedOption)}
                            />

                        </fieldset>
                    </form>
                    {/* <button onClick={e => this.handleChange(e, '2')} >
                    Confirm
                    </button> */}

                </div>
            </div>
        );
    }

    filterList(event, choosenField) {
        if (event.target.value.toLowerCase() !== -1) {
            switch (this.activeFilter) {
                case "1":
                    return this.props.searchList(event, this.props.allUsers, choosenField);
                case "2":
                    return this.props.searchList(event, this.props.emailUsers, choosenField);
                case "3":
                    return this.props.searchList(event, this.props.allCollaborators, choosenField);
                case '4':
                    return this.props.searchList(event, this.props.allDirector, choosenField);
                case '5':
                    return this.props.searchList(event, this.props.allAdministrator, choosenField);
                case '6':
                    return this.props.searchList(event, this.props.allVisitors, choosenField);
                default:
                    return this.props.searchList(event, this.props.allUsers, choosenField);


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

        const divStyle = {
            width: '100%',
            marginTop: '-20px',
            marginBottom: '-40px'

        };

        const tdStyle = {
            paddingRight: '5px',
            width: '615px',
        }
        return (
            <div className="buttonWrapper">
                <fieldset>

                    <div class="switch-toggle switch-candy">
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

                    </div>
                </fieldset>

                {/* <div className=" table-striped">
                    <div className="filter-list">
                        <form>
                        <fieldset className="form-group">
                        <input
                            type="text"
                            className="form-control form-control-lg"
                            placeholder="Search by email"
                            onChange={(event) => this.filterList(event)}
                        />

                        </fieldset>
                        </form>


                    </div>
                </div> */}
                <table style={divStyle}>
                    <tr>
                        <td style={tdStyle}> {this.renderSearchForm()}</td>
                        <td> {this.renderDropdownButton('Search Field', 1)}</td>
                    </tr>
                </table>
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
