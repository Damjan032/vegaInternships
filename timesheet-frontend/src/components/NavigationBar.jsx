import React from 'react';
import {NavLink} from 'react-router-dom';

const tabs = [
    {
        name: "TimeSheet",
        path: "/index"
    },
    {
        name: "Clients",
        path: "/clients"
    },
    {
        name: "Projects",
        path: "/projects"
    },
    {
        name: "Categories",
        path: "/categories"
    },
    {
        name: "TeamMembers",
        path: "/employees"
    },
    {
        name: "Reports",
        path: "/reports"
    },
]

export default function NavigationBar() {
    return (
        <nav>
            <ul className="menu">
                {tabs.map(item => (
                    <li key={item.name}>
                        <NavLink
                            to={item.path}
                            className={'btn nav'}
                        >
                            {item.name}
                        </NavLink>
                    </li>
                ))}
            </ul>
            <div className="mobile-menu">
                {/* eslint-disable-next-line jsx-a11y/anchor-is-valid,no-script-url */}
                <a href="#" className="menu-btn">
                    <i className="zmdi zmdi-menu"/>
                </a>
                <ul>
                    {tabs.map(item => (
                        <li key={item.name}>
                            <NavLink
                                to={item.path}
                            >
                                {item.name}
                            </NavLink>
                        </li>
                    ))}
                </ul>
                <span className="line"/>
            </div>
        </nav>
    );
}
