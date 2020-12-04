import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import TimeSheetsList from "./TimeSheetsList";

export default function WeekViewBody(props) {
    const emptyTimeSheet = {
        time: '',
        description: '',
        overtime: '',
        project: '',
        categoryDto: ''
    }
    const {week, currentDay, onNextOrPrev, onClickThisWeekDay} = props;

    const dispatch = useDispatch();
    const monthNames = ["January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    ];
    const dailyTimeSheets = useSelector(state => state.dailyTimeSheets);
    const listTimesSheets = [1, 2, 3, 4, 5, 6, 7];
    //const [dailyTimeSheets, setDailTimeSheets] = useState();
    const nameOfWeekDays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    const [currentDaily, setCurrentDaily] = useState([1, 2, 3, 4, 5, 6, 7]);
    console.log(currentDaily)

    function getFormattedDay(day) {
        if (day === undefined) day = new Date();
        let mm = parseInt(String(day.getMonth() + 1).padStart(2, '0'));
        mm = mm < 10 ? 0 + '' + mm : mm;
        let dayNumber = day.getDate() < 10 ? 0 + '' + day.getDate() : day.getDate();
        return day.getFullYear() + "-" + mm + "-" + dayNumber;
    }

    function setCurrentDailyTimeSheet() {
        let dailyPom = []
        dailyTimeSheets.map(daily => {
            if (daily.day === getFormattedDay(currentDay)) {
                dailyPom = daily['timeSheets'];
            }
        });
        let numberOfEmpty = 7 - dailyPom.length
        for (let i = 0; i < numberOfEmpty; i++) {
            dailyPom.push(emptyTimeSheet);
        }
        let dailyWithId=[]
        for (let i = 0; i < 7; i++) {
            dailyWithId.push({
                ...dailyPom[i],
                id:i
            });
        }
        return dailyWithId;
    }


    useEffect(() => {
        setCurrentDaily(setCurrentDailyTimeSheet(dailyTimeSheets))

    }, [dispatch]);

    return (
        <>
            <div className="grey-box-wrap">
                <div className="top">
                    <a onClick={onNextOrPrev} href="javascript:" className="prev"><i
                        className="zmdi zmdi-chevron-left"/>previous week</a>
                    <span
                        className="center">{monthNames[week[0].getMonth()]} {week[0].getDate()} - {monthNames[week[6].getMonth()]}
                        {week[6].getDate()}, {week[6].getFullYear()}&nbsp;&nbsp;
                        (week {Math.ceil(week[6].getDate() / 7)})</span>
                    <a onClick={onNextOrPrev} href="javascript:" className="next">next week<i
                        className="zmdi zmdi-chevron-right"/></a>
                </div>
                <div className="bottom">
                    <ul className="days">
                        {week.map(day => (
                            <li key={day} onClick={onClickThisWeekDay.bind(this, day)}
                                className={day.getDay() === currentDay.getDay() ? "active" : ""}>
                                <a href="javascript:">
                                    <b>{monthNames[day.getMonth()]} {day.getDate()}</b>
                                    <span>{nameOfWeekDays[day.getDay()]}</span>
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
            <table className="default-table">
                <tbody>
                <tr>
                    <th>
                        Client <em>*</em>
                    </th>
                    <th>
                        Project <em>*</em>
                    </th>
                    <th>
                        Category <em>*</em>
                    </th>
                    <th>Description</th>
                    <th className="small">
                        Time <em>*</em>
                    </th>
                    <th className="small">Overtime</th>
                </tr>

                <TimeSheetsList dailyTimeSheets={setCurrentDailyTimeSheet()}/>
                </tbody>
            </table>
            <div className="total">
                <a href="/index"><i></i>back to monthly view</a>
                <span>
                    <button className="btn green" type="submit">Save</button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    Total hours: <em>7.5</em></span>
            </div>
        </>
    );
}
