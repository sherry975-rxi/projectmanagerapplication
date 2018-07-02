import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import './dist/toggle-switch.css';
import './dist/FetchTask.css';
import {
    searchList,
    changeTaskFilter,
    getProjectTasksByFilter
} from './../../actions/projectTasksActions';
import {
    MenuItem,
    DropdownButton
} from 'react-bootstrap';
import * as Constants from './../../components/utils/titleConstants';
import {get} from "lodash";
import {TASKS_FILTER} from "../../constants/TasksConstants";


class ProjectTasksSearch extends Component {
    constructor(props) {
        super(props);
        this.option = '1'
        this.state = {
            selectedOption: '1',
            selectedField: 'Task ID',
        };
        this.props.getProjectTasksByFilter(
            this.props.projectId,
            TASKS_FILTER.ALL_TASKS
        );
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

        var items = Constants.TASKS
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
                    this.field = 'Project ID';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

                    break;
                case 3:
                    this.option = '3';
                    this.field = 'Description';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });

                    break;
                case 4:
                    this.option = '4';
                    this.field = 'State';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });
                    break;
                case 5:
                    this.option = '5';
                    this.field = 'Start Date "DD/MMM/YYYY"';
                    this.setState({
                        selectedOption: this.option,
                        selectedField: this.field
                    });
                    break;
                case 6:
                    this.option = '6';
                    this.field = 'Finish Date';
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
                                placeholder={`Search by ${this.state.selectedField}`} /* "Search by Task ID"  */
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
        return this.props.searchList(event, this.props.tasks, choosenField);
    }


    async componentDidMount() {
        this.props.getProjectTasksByFilter(this.props.projectId, this.state.taskFilter);
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

                {/*  <div class="btn-group">
                <FormGroup bsSize="small" onChange={this.handleChange1} >
                            <FormControl
                                id="emailUsers"
                                name="view3"
                                eventKey="0"
                                className="textForm"
                                autoFocus
                                type="text"
                                placeholder="Search Users By Email"
                                value={this.state.emailUsers}

                            />
                </FormGroup>
                <button onClick={e => this.handleChange(e, '2')} >
                    Confirm
                    </button>
                </div> */}
                {/* <div className=" table-striped">
                    <div className="filter-list">
                        <form>
                        <fieldset className="form-group">
                        <input
                            type="text"
                            className="form-control form-control-lg"
                            placeholder="Search by Task ID"
                            onChange={(event) => this.filterList(event)}
                        />

                        </fieldset>
                        </form>
                       <button onClick={e => this.handleChange(e, '2')} >
                    Confirm
                    </button>

                    </div>
                </div> */}

                <table style={divStyle}>
                    <tr>
                        <td style={tdStyle}> {this.renderSearchForm()}</td>
                        <td> {this.renderDropdownButton('Search Field', 0)}</td>
                    </tr>
                </table>

                {/* <div>
                    <DropdownButton
                        className="buttonFinished"
                        title='Select Field'
                        key= "0"
                        id={`dropdown-basic-${i}`}
                    >
                        {this.renderFields()}
                    </DropdownButton>
                </div> */}
            </div>
        );
    }
}


const mapStateToProps = state => {
    return {
        taskFilter: get(state, 'tasks.taskFilter', []),
        tasks: get(state, 'tasks.tasksList', []),
        updatedTasks: get(state, 'tasks.updatedList', [])
    };
};

const mapDispatchToProps = dispatch =>
    bindActionCreators(
        {
            searchList,
            changeTaskFilter,
            getProjectTasksByFilter
        },
        dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(ProjectTasksSearch);
