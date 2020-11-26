import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import NavigationBar from "./NavigationBar";

export default function Header() {
    const [isMouseOverAccount, setIsMouseOverAccount] = useState(false);

    return (
        <header className="header">
            <div className="top-bar"/>
            <div className="wrapper">
                <Link className="logo" to="/">
                    <img src={process.env.PUBLIC_URL + '/assets/img/logo.png'} alt="VegaITSourcing Timesheet"/>
                </Link>
                <ul className="user right">
                    <li
                        onMouseOver={() => setIsMouseOverAccount(true)}
                        onMouseOut={() => setIsMouseOverAccount(false)}
                    >
                        {/* eslint-disable-next-line jsx-a11y/anchor-is-valid,no-script-url */}
                        <a href="javascript:">Sladjana Miljanovic</a>
                        <div className="invisible"
                             style={{display: isMouseOverAccount ? 'block' : 'none'}}
                        />
                        <div className="user-menu"
                             style={{display: isMouseOverAccount ? 'block' : 'none'}}
                        >
                            <ul>
                                <li>
                                    {/* eslint-disable-next-line no-script-url,jsx-a11y/anchor-is-valid */}
                                    <a href="javascript:" className="link">Change password</a>
                                </li>
                                <li>
                                    {/* eslint-disable-next-line no-script-url,jsx-a11y/anchor-is-valid */}
                                    <a href="javascript:" className="link">Settings</a>
                                </li>
                                <li>
                                    {/* eslint-disable-next-line no-script-url,jsx-a11y/anchor-is-valid */}
                                    <a href="javascript:" className="link">Export all data</a>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li className="last">
                        {/* eslint-disable-next-line no-script-url,jsx-a11y/anchor-is-valid */}
                        <a href="javascript:">Logout</a>
                    </li>
                </ul>
                <NavigationBar/>
            </div>
        </header>
    );
}
