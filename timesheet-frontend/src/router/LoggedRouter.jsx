import React from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import Header from "../components/Header";
import TimeSheet from "../pages/TimeSheet";
import WeekView from "../pages/WeekView";
import Clients from "../pages/Clients";
import Employees from "../pages/Employees";
import Projects from "../components/Project/Project";
import Reports from "../pages/Reports";
import Footer from "../components/Footer";

function LoggedRouter() {
    return (
        <Route>
            <Header/>
            <div className="wrapper">
                <section className="content">
                    <Route path="/index">
                        <TimeSheet/>
                    </Route>
                    <Route
                        path='/weekView'
                        render={props => <WeekView {...props} />}
                    />
                    <Route path="/clients">
                        <Clients/>
                    </Route>
                    <Route path="/employees">
                        <Employees/>
                    </Route>
                    <Route path="/projects">
                        <Projects/>
                    </Route>
                    <Route path="/calendar">
                        <TimeSheet/>
                    </Route>
                    <Route path="/reports">
                        <Reports/>
                    </Route>

                </section>
            </div>
            <Footer/>
        </Route>
    );
}

export default LoggedRouter;
