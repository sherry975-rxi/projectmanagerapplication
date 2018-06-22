import React from "react";
import { mount, shallow } from "enzyme";
import UserTasks from "./UserTasks";
import AuthService from "../loginPage/AuthService";

describe("UserTasks", () => {
    
    describe('componentDidMount', () => {
        const match = {
              params: {
                userID: '2'   
              }
            };
        
        it('sets the state componentDidMount', async () => {
          AuthService.fetch = jest.fn().mockImplementation(() => ({
            status: 200,
            json: () => new Promise((resolve, reject) => {
              resolve({
                tasks: [
                    {taskId: 1, 
                     project: 2, 
                     description: 'Acually test', 
                     startDate: "2017-09-30T23:00:00.000+0000",
                     estimatedTaskEffort: 3,
                     estimatedTaskStartDate: "2017-09-30T23:00:00.000+0000",
                     taskDeadline: "2018-06-29T23:00:00.000+0000"
                    }
                ]
              })
            })
          }))
    
          const renderedComponent = await shallow(<UserTasks match={match} />)
          await renderedComponent.refreshPage()
          expect(renderedComponent.state('tasks').length).toEqual(1)
        })
        
        it('sets the state componentDidMount on error', async () => {
          AuthService.fetch = jest.fn().mockImplementation(() => ({
            status: 500,
          }))
    
          const renderedComponent = await shallow(<UserTasks match={match} />)
          await renderedComponent.refreshPage()
          expect(renderedComponent.state('message')).toEqual('FORBIDDEN')
        })
      })


      it("always renders a div", () => {
        const divs = userTasks().find("div");
        expect(divs.length).toBeGreaterThan(0);

    });

    describe("the rendered div", () => {
        it("contains everything else that gets rendered", () =>  {
            const divs = userTasks.find("div");

            const wrappingDiv = divs.first();

            expect(wrappingDiv.children()).toEqual(userTasks.children());
        });
    });


});