import React, {useEffect} from 'react';
import ListOfDays from "./ListOfDays";
import {useDispatch} from "react-redux";
import {getDailyTimeSheetAction} from "../../store/actions/dailyTImeSheets/dailyTimeSheetsActions";


export default function TableOfDays(props) {
    const {year, today, listOfWeeks} = props;
    const dispatch = useDispatch();
    dispatch(getDailyTimeSheetAction("30a77b80-5ac7-4435-8ee4-068d0eae18e0", getFormattedDay(listOfWeeks[0][0]),
        getFormattedDay(listOfWeeks[listOfWeeks.length - 1][6])));


    function getFormattedDay(day) {
        if (day === undefined) day = new Date();
        let mm = parseInt(String(day.getMonth() + 1).padStart(2, '0'));
        mm = mm < 10 ? 0 + '' + mm : mm;
        let dayNumber = day.getDate() < 10 ? 0 + '' + day.getDate() : day.getDate();
        return day.getFullYear() + "-" + mm + "-" + dayNumber;
    }


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
                <ListOfDays listOfWeeks={listOfWeeks} year={year} today={today}/>
                </tbody>
            </table>
        </>
    );
}
