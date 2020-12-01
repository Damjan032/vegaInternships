import React, {useEffect, useState} from 'react';
import ClientSelection from "../Client/ClientSelection";
import ProjectSelection from "../Project/ProjectSelection";
import CategorySelection from "../Category/CategoriesSelection";
import {useDispatch, useSelector} from "react-redux";

export default function DailyTimeSheetsList(props) {
    const emptyTimeSheet = {
        time: '',
        description: '',
        overtime: '',
        project: '',
        categoryDto: ''
    }
    const {dailyTimeSheets} = props;


    return (
        <>
                {dailyTimeSheets.map(i => (
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
        </>
    );
}
