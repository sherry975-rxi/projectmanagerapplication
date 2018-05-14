import React, { Component } from 'react';
import Card from './card'; 
import axios from 'axios';

class UserOngoing extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tasks: []
        }
    }

    async componentDidMount() {

        const request = await axios.get('users/7/tasks/pending');
        const teste = request.data;
        this.setState({ tasks : teste });
                   
    }

    renderOngoingTasks(){
        return this.state.tasks.map((taskItem) =>{
            return(
            <div className="col m4">
                <Card task={taskItem} />
            </div>          
            )
        })
    }

    render() {
        return (
            <div className="container">
            <div className="row">
                {this.renderOngoingTasks()}    
            </div>                                    
            </div>
        );
    }
}

export default UserOngoing;