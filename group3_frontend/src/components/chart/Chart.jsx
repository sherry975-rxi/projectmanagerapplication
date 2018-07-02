import React, { Component } from 'react';
import { Bar } from 'react-chartjs-2';
import './Chart.css'

class Chart extends Component {


    render() {

        let chartData = {
            datasets: [
                {
                    label: 'Budget',
                    data: [this.props.budget
                    ],
                    backgroundColor: [
                        'rgb(230, 190, 80)',

                    ]
                }, {
                    label: 'Cost',
                    data: [this.props.cost
                    ],
                    backgroundColor: [
                        'rgb(135, 190, 178)',

                    ]
                }

            ]
        }


        return (
            <div className="chartBar" styles={{ width: '500px' }}>
                <Bar
                    align="center"
                    data={chartData}
                    width={100}
                    height={50}
                    options={{
                        maintainAspectRation: false,
                        cutoutPercentage: 85,
                        scales: { xAxes: [{ barPercentage: 0.2 }] }
                    }} />
            </div >


        )
    }
}

export default Chart;