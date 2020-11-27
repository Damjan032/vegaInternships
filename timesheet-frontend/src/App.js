import React from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Clients from "./pages/Clients";
import Employees from "./pages/Employees";
import Projects from "./pages/Projects"

function App() {
    return (
        <div className="App">
            <Router>
                <Header/>
                <div className="wrapper">
                    <section className="content">
                        <Route path="/clients">
                            <Clients/>
                        </Route>
                        <Route path="/employees">
                            <Employees/>
                        </Route>
                        <Route path="/projects">
                            <Projects/>
                        </Route>
                        {/*<Route path="/"></Route>*/}
                    </section>
                </div>
                <Footer/>
            </Router>
        </div>
    );
}

export default App;
