import React from 'react';

import {useDispatch} from 'react-redux';


export default function ReportsBody(props) {
    const {filtered} = props
    // const dailyTimeSheets = useSelector(state => state.dailyTimeSheets);
    const dispatch = useDispatch();
    console.log("DAILY")
    console.log(filtered)

    return (
        <>


            {filtered.map((daily) => (
                <tr key={1 + (Math.random() * (10000 - 1))}>
                    <td>
                        {daily.day}
                    </td>
                    <td>
                        {daily.project.teamLead.name}
                    </td>
                    <td>
                        {daily.project.name}
                    </td>
                    <td>
                        {daily.categoryDto.name}
                    </td>
                    <td>
                        {daily.description}
                    </td>
                    <td className="small">
                        {daily.time + daily.overtime}
                    </td>
                </tr>

            ))}
        </>
    );
}
