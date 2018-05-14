import React, { Component } from 'react';
import {Doughnut, Pie} from 'react-chartjs-2'; 

class Chart extends Component {

    constructor(props) {
        super(props); 
        this.state = { 
            chartData: {
                labels: ['Days spent', 'Remaining Days'],
                datasets: [ 
                    {
                        label:'Days left',
                        data: [ 10,
                            15
                            ], 
                        backgroundColor: [ 
                                'rgba(166, 35, 52, 0.8)',
                                'rgba(254, 180, 0, 0.8)',
                                'rgba(43, 104, 167, 0.8)'   
                            ]                                                     
                    }
                ]
            }
        }
    }

        render() {

            return (
                <div className="chart">
                <Doughnut 
                data={this.state.chartData}
                width={150}
                height={50}
                options={{ 
                    maintainAspectRation: false,
                    cutoutPercentage: 70
                }} />
                </div>
            )
        }
    }

export default Chart; 