import React, {useEffect, useState} from 'react';
import WeekViewBody from "../components/WeekView/WeekViewBody";
import {useDispatch} from 'react-redux';
import {useHistory} from "react-router-dom";
import {getAllClientsAction} from "../store/actions/clients/clientsActions";
import {getAllProjectsAction} from "../store/actions/projects/projectsActions";
import {getAllCategoriesAction} from "../store/actions/categories/categoriesActions";
import {getDailyTimeSheetAction} from "../store/actions/dailyTImeSheets/dailyTimeSheetsActions";

export default function WeekView(props) {
    const history = useHistory();
    const dispatch = useDispatch();
    const [currentDate, setCurrentDate] = useState(new Date(`${props.location.pathname.split('/').slice(-1)[0].slice(0, 4)}-${props.location.pathname.split('/').slice(-1)[0].slice(4, 6)}-${props.location.pathname.split('/').slice(-1)[0].slice(6, 8)}`));
    //const [dailyTimeSheets, setDailTimeSheets] = useState(dispatch(getDailyTimeSheetAction("30a77b80-5ac7-4435-8ee4-068d0eae18e0", getFormattedDay(currentDate[0]),
     //   getFormattedDay(currentDate[6]))));


    function getFormattedDay(day) {
        if (day === undefined) day = new Date();
        let mm = parseInt(String(day.getMonth() + 1).padStart(2, '0'));
        mm = mm < 10 ? 0 + '' + mm : mm;
        let dayNumber = day.getDate() < 10 ? 0 + '' + day.getDate() : day.getDate();
        return day.getFullYear() + "-" + mm + "-" + dayNumber;
    }


    function daysOfWeek() {
        let week = [];
        let current = new Date(`${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${currentDate.getDate()}`)
        current.setDate((currentDate.getDate() - currentDate.getDay() + 1));
        for (let i = 0; i < 7; i++) {
            week.push(
                new Date(current)
            );
            current.setDate(current.getDate() + 1);
        }
        return week;
    }

    Date.prototype.yyyymmdd = function () {
        let mm = this.getMonth() + 1; // getMonth() is zero-based
        let dd = this.getDate();

        return [this.getFullYear(),
            (mm > 9 ? '' : '0') + mm,
            (dd > 9 ? '' : '0') + dd
        ].join('');
    };

    const handleNextOrPrev = (event) => {
        let dayFromNewWeek = currentDate;
        if (event.target.className === "next") dayFromNewWeek.setDate(dayFromNewWeek.getDate() + 7);
        else dayFromNewWeek.setDate(dayFromNewWeek.getDate() - 7);
        setCurrentDate(dayFromNewWeek)
        dispatch(getDailyTimeSheetAction("30a77b80-5ac7-4435-8ee4-068d0eae18e0", getFormattedDay(daysOfWeek()[0]),
            getFormattedDay(daysOfWeek()[6])))
        history.push(`${currentDate.yyyymmdd()}`);
    }
    const handleInWeekDay = (day) => {
        if (day.getDay() !== 0) {
            setCurrentDate(day)
            history.push(`${day.yyyymmdd()}`);
        }
    }

    useEffect(() => {
        dispatch(getAllClientsAction());
        dispatch(getAllProjectsAction());
        dispatch(getAllCategoriesAction());
        dispatch(getDailyTimeSheetAction("30a77b80-5ac7-4435-8ee4-068d0eae18e0", getFormattedDay(daysOfWeek()[0]),
            getFormattedDay(daysOfWeek()[6])))
    }, [dispatch]);


    return (
        <>
            <h2><i className="ico timesheet"/>TimeSheet</h2>
            <WeekViewBody onClickThisWeekDay={handleInWeekDay} onNextOrPrev={handleNextOrPrev} week={daysOfWeek()}
                          currentDay={currentDate}/>
        </>
    );
}
