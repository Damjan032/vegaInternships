import React from 'react';


export default function ListOfDays(props) {
    const {month,year, today} = props;
    const todayMonth = parseInt(String(today.getMonth()).padStart(2, '0'))
    const todayNumberOdDay = today.getDate();
    function getDaysInMonth(month, year) {
        let date = new Date(year, month, 1);
        let days = [];
        while (date.getUTCDay() !== 0) {
            date.setDate(date.getDate() - 1);
            days.unshift(new Date(date));
        }
        date = new Date(year, month, 1);
        while (date.getMonth() === month) {
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
        for (var i = 0; i < n; i++) {
            if (chunkLength * (i + 1) <= arr.length) chunks.push(arr.slice(chunkLength * i, chunkLength * (i + 1)));
        }
        return chunks;
    }

    return (
        <>
            {chunkArray(getDaysInMonth(month, year), getDaysInMonth(month, year).length / 7).map(week => (
                <tr>
                    {week.map(day => (
                        <td className={(today<=day) ? "disable": day.getMonth() < month ? "positive previous": "positive"}>
                            <div className="date">
                                <span>{day.getDate()}</span>
                            </div>
                            <div className="hours">
                                <a href="days.html">
                                    Hours: <span>7.5</span>
                                </a>
                            </div>
                        </td>
                    ))}

                </tr>
            ))}

        </>
    );
}
