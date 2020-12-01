import React, {useEffect, useState} from 'react';
import ClientSelection from "../Client/ClientSelection";
import ProjectSelection from "../Project/ProjectSelection";
import CategorySelection from "../Category/CategoriesSelection";
import {useDispatch, useSelector} from "react-redux";

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
        console.log("Opa djurdjo usa sam odje");
        console.log(dailyTimeSheets)
        let dailyPom= []
        dailyTimeSheets.map(daily => {
            if (daily.day === getFormattedDay(currentDay)) {
                console.log("Nasa sam te macak");
                console.log(daily['timeSheets']);
                dailyPom = daily['timeSheets'];
            }
        });
        let numberOfEmpty = 7-dailyPom.length
        for(let i = 0; i<numberOfEmpty;i++){
            dailyPom.push(emptyTimeSheet);
        }
        console.log(dailyPom);
    }


    useEffect(() => {
        setCurrentDaily(setCurrentDailyTimeSheet(dailyTimeSheets))
        /*dispatch(getDailyTimeSheetAction("30a77b80-5ac7-4435-8ee4-068d0eae18e0", getFormattedDay(week[0]),
            getFormattedDay(week[6])))*/

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

                {listTimesSheets.map(i => (
                    <tr key={i}>
                        <td>
                            <ClientSelection/>
                        </td>
                        <td>
                            <ProjectSelection/>
                        </td>
                        <td>
                            <CategorySelection/>
                        </td>
                        <td>
                            <input type="text" className="in-text medium"/>
                        </td>
                        <td className="small">
                            <input name="time"
                                   value={(dailyTimeSheets[i] !== undefined ? dailyTimeSheets[0]['imeSheets'].time : '')}
                                   type="text" className="in-text xsmall"/>
                        </td>
                        <td className="small">
                            <input type="text" className="in-text xsmall"/>
                        </td>
                    </tr>
                ))}
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
