import React, { Component } from 'react';
import '../tasks/MarkTaskAsFinished';
import '../reports/Reports';
import AuthService from '../loginPage/AuthService';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import AccordionMenuRequests from '../../components/AccordianMenuRequests/AccordionMenuRequests.jsx'
import RequestsFilter from './RequestsFilter'
import LoadingComponent from '../../components/loading/LoadingComponent'

class Requests extends Component {
    constructor(props) {
        super(props);

        this.AuthService = new AuthService();
        this.renderRequests = this.renderRequests.bind(this);

    }

    //TODO: Add sort by ascending or descending order to these tables



    renderRequests() {


        if (this.props.itemIsLoading) {
            return (<LoadingComponent />)
        }

        else if (this.props.error) {
            return <Redirect to="/login" />;
        }

        else if (this.props.filter === 'allRequests') {
            console.log("TESTE filtro all");
            console.log(this.props.allRequests);

            return <AccordionMenuRequests list={this.props.allRequests} />;
        }

        else if (this.props.filter === 'openedRequests') {
            console.log("TESTE filtro open");
            console.log(this.props.openedRequests);
            return <AccordionMenuRequests list={this.props.openedRequests} />;
        }
        else if (this.props.filter === 'closedRequests') {
            console.log("TESTE filtro close");
            console.log(this.props.openedRequests);
            return <AccordionMenuRequests list={this.props.closedRequests} />;
        }
        else if (this.props.filter === 'searchList') {
            return <AccordionMenuRequests list={this.props.searchList} />
        }
        else {
            return <AccordionMenuRequests list={this.props.allRequests} />;
        }
    }


    render() {

        return (
            <div className=" table-striped">
                <h2>Requests</h2>

                <RequestsFilter projectId={this.props.match.params.projectID} />
                <h3>
                </h3>
                &nbsp;
                        {this.renderRequests()}

            </div>
        );
    }
}


const mapStateToProps = state => {
    return {
        filter: state.requestsFilter.filterType,
        itemIsLoading: state.requests.itemIsLoading,
        allRequests: state.requests.allRequests,
        openedRequests: state.requests.openedRequests,
        closedRequests: state.requests.closedRequests,
        searchList: state.requests.searchList,
        error: state.requests.error

    };
};

export default connect(
    mapStateToProps,
    null
)(Requests);
