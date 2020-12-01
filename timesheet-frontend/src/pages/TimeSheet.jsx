import React, {useEffect, useState} from 'react';
import TableOfDays from "../components/Calendar/TableOfDays";
import {getDailyTimeSheetAction} from "../store/actions/dailyTImeSheets/dailyTimeSheetsActions";
import {useDispatch} from "react-redux";


export default function TimeSheet() {
    const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];
    const dispatch = useDispatch();

    const [today] = useState(new Date());
    const [month, setMonth] = useState(parseInt(String(new Date().getMonth() + 1).padStart(2, '0')));
    const [year, setYear] = useState(new Date().getFullYear());
    const [listOfDays] = useState(getDaysInMonth())

    function setNextMonth() {
        if (month === 12) {
            setMonth(1);
            setYear(year + 1);
        } else {
            setMonth(month + 1)
        }
    }

    function getDaysInMonth() {
        let date = new Date(year, month - 1, 1);
        let days = [];
        while (date.getUTCDay() !== 0) {
            date.setDate(date.getDate() - 1);
            days.unshift(new Date(date));
        }
        date = new Date(year, month - 1, 1);
        while (date.getMonth() === month - 1) {
            days.push(new Date(date));
            date.setDate(date.getDate() + 1);
        }
        while (date.getUTCDay() !== 0) {
            days.push(new Date(date));
            date.setDate(date.getDate() + 1);
        }
        return days;
    }

    function chunkArray(arr, n) {
        let chunkLength = Math.max(arr.length / n, 1);
        let chunks = [];
        for (let i = 0; i < n; i++) {
            if (chunkLength * (i + 1) <= arr.length) chunks.push(arr.slice(chunkLength * i, chunkLength * (i + 1)));
        }
        return chunks;
    }

    function getFormattedDay(day) {
        if (day === undefined) day = new Date();
        let mm = parseInt(String(day.getMonth() + 1).padStart(2, '0'));
        mm = mm < 10 ? 0 + '' + mm : mm;
        let dayNumber = day.getDate() < 10 ? 0 + '' + day.getDate() : day.getDate();
        return day.getFullYear() + "-" + mm + "-" + dayNumber;
    }

    function setPrevMonth() {
        if (month === 1) {
            setMonth(12);
            setYear(year - 1);
        } else setMonth(month - 1);
    }

    function handleClick(event) {
        if (event.target.className === 'next') {
            setNextMonth();
        }
        if (event.target.className === 'prev') {
            setPrevMonth();
        }
    }


    useEffect(() => {
        dispatch(getDailyTimeSheetAction("30a77b80-5ac7-4435-8ee4-068d0eae18e0", getFormattedDay(listOfDays[0]),
            getFormattedDay(listOfDays[listOfDays.length - 1])));
    }, [dispatch]);

    return (
        <>
            <h2><i className="ico timesheet"/>TimeSheet</h2>
            <div className="grey-box-wrap">
                <div className="top">
                    <a href="javascript:" onClick={handleClick} className="prev"><i
                        className="zmdi zmdi-chevron-left"/>previous month</a>
                    <span className="center">{monthNames[month - 1]}, {year}</span>
                    <a href="javascript:" onClick={handleClick} className="next">next month<i
                        className="zmdi zmdi-chevron-right"/></a>
                </div>
                <div className="bottom">

                </div>
            </div>
            <TableOfDays listOfWeeks={chunkArray(getDaysInMonth(), getDaysInMonth().length / 7)} year={year}
                         today={today}/>
            <div className="total">
                <span>Total hours: <em>90</em></span>
            </div>
        </>
    );
}
