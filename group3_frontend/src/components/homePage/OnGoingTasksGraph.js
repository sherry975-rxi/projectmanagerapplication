import React, { Component } from "react";
import "./tasksGraph.css";
import SkillBar from 'react-skillbars';
import ProgressBar from 'progressbar.js';




class OnGoingTasksGraph extends Component {


    constructor(props) {
        super(props);
        this.state = {
            skills: [],
            
            colors: {
                "bar": "#3498db",
                "title": {
                  "text": "#fff",
                  "font-family" : "Times New Roman",
                  "background": "#2980b9"
                }
              }

        };
    }

    componentDidMount(){
        this.setState(prevState => ({
            skills: [...prevState.skills,{"type" : "Java", "level" : "85"}]
        }))
         
    }

    

    render(){

          return(
              <div className="graphContainer">
              <SkillBar skills={this.state.skills} colors={this.state.colors}/>
            
              </div>
          )
    }
}



  export default OnGoingTasksGraph