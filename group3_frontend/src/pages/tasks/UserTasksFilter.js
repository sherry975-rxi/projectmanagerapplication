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
} from 'react-bootstrap';

class UserTasksFilter extends Component {
    constructor(props) {
        super(props);
        this.activeFilter = ""

    }

    filterList(event) {

        if(event.target.value.toLowerCase()!== -1){
            
            switch (this.activeFilter){
                case "1":
                return this.props.searchList(event, this.props.myAllTasks);
                case "2":
                return this.props.searchList(event, this.props.myOngoingTasks);
                case "3":
                return this.props.searchList(event, this.props.myFinishedTasks);
                case '4':
                return this.props.searchList(event, this.props.lastMonthFinishedTasks);
                default:
                return this.props.searchList(event, this.props.myAllTasks);
                
                
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
        return (
            <div className="buttonWrapper">
                <div className="switch-toggle switch-ios">
                    <input
                        id="Test"
                        name="view3"
                        type="radio"
                        onChange={<div></div>}
                    />
                    <label className="buttonFont" htmlFor="">
                        <b>Filter by:</b>
                    </label>
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
                        Finished Last Month
                    </label>
                </div>
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
                 <div className=" table-striped">
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
