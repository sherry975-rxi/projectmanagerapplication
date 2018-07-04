import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import '../tasks/dist/toggle-switch.css'
import '../tasks/dist/FetchTask.css'

import { getAllRequests, getOpenedRequests, getClosedRequests, searchList } from '../../actions/requestsActions'
import {
    MenuItem,
    DropdownButton
} from 'react-bootstrap';
import * as Constants from './../../components/utils/titleConstants';


class RequestsFilter extends Component {
    constructor(props) {
        super(props);
        this.option = '1'
        this.state = {
            selectedOption: '1',
            selectedField: 'Task ID',
        };
        this.renderSearchForm = this.renderSearchForm.bind(this);
        this.handleClick = this.handleClick.bind(this);

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
        /* var size = 3;
        var items = list.slice(0, size).map(i => {
             return <myview item={i} key={i.id} />
        }) */

        var items = Constants.REQUESTS
            .filter((element, index) => (index < 4))
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
        /*  return Constants.TASKS.map((element, index) => (
             <MenuItem
                 eventKey={index}
                 key={index}
                 onSelect={(event) => this.handleClick(event)}
             >
                 {element}
             </MenuItem>
     )); */
    }

    handleClick(eventKey) {
        if (eventKey !== -1) {
            const selectionIndex = eventKey + 1;
            switch (selectionIndex) {
                case 1:
                    this.option = '1';
                    this.field = 'Task ID';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });


                    break;
                case 2:
                    this.option = '2';
                    this.field = 'Task Description';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

                    break;
                case 3:
                    this.option = '3';
                    this.field = 'Collaborator Name';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

                    break;
                case 4:
                    this.option = '4';
                    this.field = 'Request Type';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });
                    break;
                case 5:
                    this.option = '5';
                    this.field = 'Approval Date "DD/MMM/YYYY"';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });
                    break;
                case 6:
                    this.option = '6';
                    this.field = 'Rejection Date "DD/MMM/YYYY"';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });
                    break;
                default:

                    this.option = '1';
                    this.field = 'Task ID';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

            }
        }


    };

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


                </div>
            </div>
        );
    }

    filterList(event, choosenField) {

        if (event.target.value.toLowerCase() !== -1) {

            switch (this.activeFilter) {
                case "1":
                    return this.props.searchList(event, this.props.allRequests, choosenField);
                case "2":
                    return this.props.searchList(event, this.props.openedRequests, choosenField);
                case "3":
                    return this.props.searchList(event, this.props.closedRequests, choosenField);
                default:
                    return this.props.searchList(event, this.props.allRequests, choosenField);


            }
        }
    }

    handleChange(key) {
        switch (key) {
            case '1':
                this.activeFilter = "1";
                return this.props.getAllRequests(this.props.projectId);

            case '2':
                this.activeFilter = "2";
                return this.props.getOpenedRequests(this.props.projectId);

            case '3':
                this.activeFilter = "3";
                return this.props.getClosedRequests(this.props.projectId);

            default:
                return;
        }
    }

    async componentDidMount() {
        console.log("Teste props project")
        console.log(this.props.projectId)
        this.props.getOpenedRequests(this.props.projectId)
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

                    <div className="switch-toggle switch-candy">
                       
                        <input
                            id="openedRequests"
                            name="view3"
                            type="radio"
                            onChange={() => this.handleChange('2')}
                        />
                        <label className="buttonFont" htmlFor="openedRequests">
                            Opened Requests
                    </label>

                        <input
                            id="closedRequests"
                            name="view3"
                            type="radio"
                            onChange={() => this.handleChange('3')}
                        />
                        <label className="buttonFont" htmlFor="closedRequests">
                            Closed Requests
                    </label>

                        <input
                            id="allRequests"
                            name="view3"
                            type="radio"
                            onChange={() => this.handleChange('1')}
                        />
                        <label className="buttonFont" htmlFor="allRequests">
                            All Requests
                    </label>

                        <a></a>

                    </div>
                </fieldset>

                <table style={divStyle}>
                    <tr>
                        <td style={tdStyle}> {this.renderSearchForm()}</td>
                        <td> {this.renderDropdownButton('Search Field', 0)}</td>
                    </tr>
                </table>

            </div>
        );
    }
}
const mapStateToProps = state => {
    return {
        allRequests : state.requests.allRequests,
        openedRequests : state.requests.openedRequests,
        closedRequests : state.requests.closedRequests,
        searchList : state.requests.searchList,
        project : state.projects.project,
       


    };
};
const mapDispatchToProps = dispatch =>
    bindActionCreators(
        { getAllRequests, getOpenedRequests, getClosedRequests, searchList },
        dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(RequestsFilter);
