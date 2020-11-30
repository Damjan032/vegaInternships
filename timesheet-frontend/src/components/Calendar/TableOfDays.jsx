import React from 'react';
import ListOfDays from "./ListOfDays";


export default function TableOfDays(props) {
    const {month,year, today} = props;
    return (
        <>
            <table className="month-table">
                <tbody>
                <tr className="head">
                    <th><span>monday</span></th>
                    <th>tuesday</th>
                    <th>wednesday</th>
                    <th>thursday</th>
                    <th>friday</th>
                    <th>saturday</th>
                    <th>sunday</th>
                </tr>
                <tr className="mobile-head">
                    <th>mon</th>
                    <th>tue</th>
                    <th>wed</th>
                    <th>thu</th>
                    <th>fri</th>
                    <th>sat</th>
                    <th>sun</th>
                </tr>
               <ListOfDays month={month} year={year} today={today}/>
                </tbody>
            </table>
        </>
    );
}
