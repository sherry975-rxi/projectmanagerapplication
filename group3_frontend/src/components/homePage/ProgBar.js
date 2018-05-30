import React, { Component } from "react";
import { Line, Circle } from 'rc-progress';

class ProgBar extends Component{

    constructor(props) {
        super(props);
        this.match;
        this.state = {
            percent: 0,
            color: '#25c4a9',
            trailWidth: 4,
            trailColor: '#aaa',
            strokeLinecap:"square",
            strokeWidth: '2'
        

        };
        this.increase = this.increase.bind(this);
    }

    componentDidMount() {
        this.increase();
        
        }

        
      
    


    increase() {
        const percent = this.state.percent + 1;
        if (percent >= this.props.limit) {
          clearTimeout(this.tm);
          return;
        }
        this.setState(
            { percent
            });
        this.tm = setTimeout(this.increase, 10);
      }

      render(){
          return(
            <Line percent={this.state.percent} strokeWidth={this.state.strokeWidth} trailColor={this.state.trailColor} trailWidth={this.state.trailWidth} strokeLinecap={this.state.strokeLinecap} strokeColor={this.state.color} />


          )}
}
export default ProgBar