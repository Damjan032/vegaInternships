import React from "react";
import {useSelector} from "react-redux";

export default function TotalHours() {
    const dailyTimeSheets = useSelector(state => state.dailyTimeSheets);

    function totalHours(){
        let time = 0;
        dailyTimeSheets.map(daily =>{
            daily['timeSheets'].map(timeSheets =>{
              time += timeSheets.time+timeSheets.overtime
            })
        })

        return time;
    }
    return (
        <>
            <div className="total">
                <span>Total hours: <em>{totalHours()}</em></span>
            </div>
        </>
    )
}