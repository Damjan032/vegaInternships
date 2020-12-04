import React from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Clients from "./pages/Clients";
import Employees from "./pages/Employees";
import Projects from "./pages/Projects"
import TimeSheet from "./pages/TimeSheet";
import WeekView from "./pages/WeekView";
import Reports from "./pages/Reports";
import NewPassword from "./pages/NewPassword";
import LoggedRouter from "./router/LoggedRouter";

function App() {
    return (
        <div className="App">
            <Router>
                <Switch>
                    <Route
                        path='/newPassword'
                        render={props => <NewPassword {...props} />}
                    />
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
                </Switch>
            </Router>
        </div>
    );
}

export default App;
