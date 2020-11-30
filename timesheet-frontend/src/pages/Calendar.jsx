import React, {useEffect, useState} from 'react';
import TableOfDays from "../components/Calendar/TableOfDays";
import {getDailyTimeSheet} from "../store/actions/dailyTImeSheets/dailyTimeSheetsActions";
import {useDispatch, useSelector} from "react-redux";


export default function Calendar() {
    const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];
    const dispatch = useDispatch();

    const dailyTimeSheets = useSelector(state => state.dailyTimeSheets);

    const [today] = useState(new Date());
    const [month, setMonth] = useState(11);
    const [year, setYear] = useState(2020);

    function setNextMonth() {
        if (month === 12) {
            setMonth(1);
            setYear(year + 1);
        } else
            setMonth(month + 1);
    }

    function setPrevMonth() {
        if (month === 1) {
            setMonth(12);
            setYear(year - 1);
        } else setMonth(month - 1);
    }

    async function handleClick(event) {
        console.log(event.target.className)
        if (event.target.className === 'next') {
            setNextMonth();
        }
        if (event.target.className === 'prev') {
            setPrevMonth();
        }
        console.log(dailyTimeSheets)
    }

    useEffect(() => {
        dispatch(getDailyTimeSheet("30a77b80-5ac7-4435-8ee4-068d0eae18e0", "1900-10-10", "2018-11-11"));
        setMonth(parseInt(String(today.getMonth() + 1).padStart(2, '0')));
        setYear(today.getFullYear());
    }, [dispatch]);

    return (
        <>
            <h2><i className="ico timesheet"></i>TimeSheet</h2>
            <div className="grey-box-wrap">
                <div className="top">
                    <a href="javascript:" onClick={handleClick} className="prev"><i
                        className="zmdi zmdi-chevron-left"></i>previous month</a>
                    <span className="center">{monthNames[month - 1]}, {year}</span>
                    <a href="javascript:" onClick={handleClick} className="next">next month<i
                        className="zmdi zmdi-chevron-right"></i></a>
                </div>
                <div className="bottom">

                </div>
            </div>
            <TableOfDays month={month - 1} year={year} today={today}/>
            <div className="total">
                <span>Total hours: <em>90</em></span>
            </div>
        </>
    );
}
