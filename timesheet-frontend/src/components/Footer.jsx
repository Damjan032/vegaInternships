import React from 'react';

export default function Footer() {
    return (
        <footer className="footer">
            <div className="wrapper">
                <ul>
                    <li>
                        <span>Copyright. VegaITSourcing All rights reserved</span>
                    </li>
                </ul>
                <ul className="right">
                    <li>
                        {/* eslint-disable-next-line jsx-a11y/anchor-is-valid,no-script-url */}
                        <a href="javascript:">Terms of service</a>
                    </li>
                    <li>
                        {/* eslint-disable-next-line jsx-a11y/anchor-is-valid,no-script-url */}
                        <a href="javascript:" className="last">Privacy policy</a>
                    </li>
                </ul>
            </div>
        </footer>
    );
}
