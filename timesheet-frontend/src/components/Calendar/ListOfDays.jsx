import React from 'react';
import {useSelector} from "react-redux";
import {useHistory} from "react-router-dom";


export default function ListOfDays(props) {

    const history = useHistory();
    const {today, listOfWeeks} = props;

    const dailyTimeSheets = useSelector(state => state.dailyTimeSheets);


    function getFormattedDay(day) {
        if (day === undefined) day = new Date();
        let mm = parseInt(String(day.getMonth() + 1).padStart(2, '0'));
        mm = mm < 10 ? 0 + '' + mm : mm;
        let dayNumber = day.getDate() < 10 ? 0 + '' + day.getDate() : day.getDate();
        return day.getFullYear() + "-" + mm + "-" + dayNumber;
    }

    function dailyTime(day) {
        let time = 0;
        dailyTimeSheets.map(dailyTimeSheet => {
            if (dailyTimeSheet.day === getFormattedDay(day)) {
                dailyTimeSheet["timeSheets"].map(timeSheet => {
                    time += timeSheet.time + timeSheet.overtime;
                })
            }
        })
        return time;
    }

    Date.prototype.yyyymmdd = function () {
        let mm = this.getMonth() + 1; // getMonth() is zero-based
        let dd = this.getDate();

        return [this.getFullYear(),
            (mm > 9 ? '' : '0') + mm,
            (dd > 9 ? '' : '0') + dd
        ].join('');
    };
    const handleClick = (day) => {
        history.push(`weekView/${day.yyyymmdd()}`);
    };

    return (
        <>
            {listOfWeeks.map(week => (
                <tr>
                    {week.map(day => (
                        <td className={(today <= day) ? "disable" : dailyTime(day) < 7.5 ? "negative" :
                            day.getMonth() < today.getMonth() ?
                                "positive previous" : "positive"}>
                            <div className="date">
                                {/*// <td className={(today<=day) ? "disable":*/}
                                {/*//     day.getMonth() < today.getMonth() ?*/}
                                {/*//      "positive previous": dailyTime(day)<7.5? "negative" : "positive"}>*/}
                                {/*    <div className="date" >*/}
                                <span>{day.getDate()}</span>
                            </div>
                            <div className="hours">
                                <a onClick={handleClick.bind(this, day)}>
                                    Hours: <span>{dailyTime(day)}</span>
                                </a>
                            </div>
                        </td>
                    ))}

                </tr>
            ))}

        </>
    );
}
