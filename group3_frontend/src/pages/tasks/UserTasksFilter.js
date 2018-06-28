import React, { Component } from 'react';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import './dist/toggle-switch.css';
import './dist/FetchTask.css';
import {
    updateMyAllTasks,
    updateMyFinishedTasks,
    updateMyOngoingTasks,
    updateMyLastMonthFinishedTasks,
    searchList
} from './../../actions/userTasksActions';
import {
    FormGroup,
    FormControl,
    MenuItem,
    DropdownButton,
    ButtonGroup,
    Button,
    ButtonToolbar
} from 'react-bootstrap';
import * as Constants from './../../components/utils/titleConstants';


class UserTasksFilter extends Component {
    constructor(props) {
        super(props);
        this.activeFilter = "",
        this.field = 'Task ID',
        this.option = '1'
        this.state= {
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

    handleClick(eventKey){
        if(eventKey !== -1){
        const selectionIndex = eventKey+1;
        console.log("Print selection index")
        console.log(selectionIndex)
        
        switch (selectionIndex){
                case 1:  
                console.log("entrando no casa")
                this.option = '1';
                this.field = 'Task ID';
                this.setState({
                    selectedOption : this.option,
                    selectedField : this.field
                });
                console.log("selectedOption");
                console.log(this.state.selectedOption);
                console.log("selected option");
                console.log(this.option);
                console.log("selected field");
                console.log(this.field);
                
                break;
                case 2:
                this.option = '2';
                this.field = 'Project ID';
                this.setState({
                    selectedOption : this.option,
                    selectedField : this.field
                });
                console.log("selectedOption");
                console.log(this.state.selectedOption);
                console.log("selected option");
                console.log(this.option);
                console.log("selected field");
                console.log(this.field);
                break;
                case 3:
                this.option = '3';
                this.field = 'Description';
                this.setState({
                    selectedOption : this.option,
                    selectedField : this.field
                });
                console.log(this.option)
                break;
                case 4:
                this.option = '4';
                this.field = 'State';
                this.setState({
                    selectedOption : this.option,
                    selectedField : this.field
                });
                break;
                case 5:
                this.option = '5';
                this.field = 'Start Date "DD/MMM/YYYY"';
                this.setState({
                    selectedOption : this.option,
                    selectedField : this.field
                });
                break;
                case 6:
                this.option = '6';
                this.field = 'Finish Date';
                this.setState({
                    selectedOption : this.option,
                    selectedField : this.field
                });
                break;
                default:
                console.log("penetration test");
                this.option = '1';
                this.field = 'Task ID';
                this.setState({
                    selectedOption : this.option,
                    selectedField : this.field
                });
                
            }
        }
               

    };

    renderSearchForm(){
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
                        {console.log("tesTEEEEEEEE")}
                        {console.log(this.activeFilter)}
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
        console.log("TESTE OLE OLE")
        console.log(choosenField)

        if(event.target.value.toLowerCase()!== -1){
            
            switch (this.activeFilter){
                case "1":
                return this.props.searchList(event, this.props.myAllTasks, choosenField);
                case "2":
                return this.props.searchList(event, this.props.myOngoingTasks, choosenField);
                case "3":
                return this.props.searchList(event, this.props.myFinishedTasks, choosenField);
                case '4':
                return this.props.searchList(event, this.props.lastMonthFinishedTasks, choosenField);
                default:
                return this.props.searchList(event, this.props.myAllTasks, choosenField);
                
                
            }        
        }
    }

    handleChange(key) {
        switch (key) {
            case '1':
                this.activeFilter = "1";
                return this.props.updateMyAllTasks(this.props.userID);
                
            case '2':
                this.activeFilter = "2";
                return this.props.updateMyOngoingTasks(this.props.userID);

            case '3':
                this.activeFilter = "3";
                return this.props.updateMyFinishedTasks(this.props.userID);

            case '4':
                this.activeFilter = "4";
                return this.props.updateMyLastMonthFinishedTasks(this.props.userID);
                
            default:
                return;
        }
    }

    async componentDidMount() {
        this.props.updateMyAllTasks(this.props.userID);
    }

    render() {

        const tabStyle = {
            marginTop: '-20px'
        }
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
                        id="alltasks"
                        name="view3"
                        type="radio"
                        onChange={() => this.handleChange('1')}
                    />
                    <label className="buttonFont" htmlFor="alltasks">
                        All Tasks
                    </label>

                    <input
                        id="onGoing"
                        name="view3"
                        type="radio"
                        onChange={() => this.handleChange('2')}
                    />
                    <label className="buttonFont" htmlFor="onGoing">
                        On Going
                    </label>

                    <input
                        id="finished"
                        name="view3"
                        type="radio"
                        onChange={() => this.handleChange('3')}
                    />
                    <label className="buttonFont" htmlFor="finished">
                        Finished
                    </label>

                    <input
                        id="lastmonthfinished"
                        name="view3"
                        type="radio"
                        onChange={() => this.handleChange('4')}
                    />
                    <label className="buttonFont" htmlFor="lastmonthfinished">
                        Last Month
                    </label>
                    <a></a>

                </div>
                </fieldset>

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
                        {console.log("tesTEEEEEEEE")}
                        {console.log(this.activeFilter)}
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
                
                {console.log("OLLLEEE")}
                {console.log(this.option)}
            </div>
        );
    }
}
const mapStateToProps = state => {
    return {
        myAllTasks: state.userTasks.myAllTasks,
        myFinishedTasks: state.userTasks.myFinishedTasks,
        myOngoingTasks: state.userTasks.myOngoingTasks,
        lastMonthFinishedTasks: state.userTasks.lastMonthFinishedTasks,

    };
};
const mapDispatchToProps = dispatch =>
    bindActionCreators(
        { updateMyAllTasks, updateMyFinishedTasks, updateMyOngoingTasks, updateMyLastMonthFinishedTasks, searchList },
        dispatch
    );
export default connect(
    mapStateToProps,
    mapDispatchToProps
)(UserTasksFilter);
