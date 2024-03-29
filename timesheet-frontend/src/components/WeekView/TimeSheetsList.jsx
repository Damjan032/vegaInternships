import React from 'react';
import ClientSelection from "../Client/ClientSelection";
import ProjectSelection from "../Project/ProjectSelection";
import CategorySelection from "../Category/CategoriesSelection";

export default function TimeSheetsList(props) {
    const emptyTimeSheet = {
        time: '',
        description: '',
        overtime: '',
        project: '',
        categoryDto: ''
    }
    const {dailyTimeSheets} = props;
    console.log("DailyTimeSheetsList");
    console.log(dailyTimeSheets);

    const inputChanged = (event) => {
        switch (event.target.name) {
            case "client":
                console.log("client");
                break;
            case "project":
                console.log("project");
                break;
            case "categories":
                console.log("categories");
                break;
            default:
                console.log("Ovo ostalo");
                dailyTimeSheets[event.target.id]={
                    ...dailyTimeSheets[event.target.id],
                    [event.target.name]: event.target.value,
                };
                console.log(dailyTimeSheets)
                console.log(event.target.id)
                console.log(dailyTimeSheets[event.target.id])
        }
        console.log(event.target.name)
        console.log(event.target.id)
        console.log(event.target.value)

    };

    function totalHours(daily){
        console.log(daily)
        console.log("EVE SATA");
        return 5;

    }

    return (
        <>
            {dailyTimeSheets.map(timeSheet => (
                <tr key={timeSheet.id}>
                    <td>
                        <ClientSelection onChange={inputChanged} disabled={true}
                                         value={timeSheet.project !== emptyTimeSheet.project ? timeSheet.project.clientDto.id : ''}/>
                    </td>
                    <td>
                        <ProjectSelection onChange={inputChanged}
                                          value={timeSheet.project !== emptyTimeSheet.project ? timeSheet.project.id : ''}/>
                    </td>
                    <td>
                        <CategorySelection id={timeSheet.id} onChange={inputChanged}
                                           value={timeSheet.categoryDto !== emptyTimeSheet.categoryDto ? timeSheet.categoryDto.id : ''}/>
                    </td>
                    <td>
                        <input id={timeSheet.id} name="description" type="text" className="in-text medium"/>
                    </td>
                    <td className="small">
                        <input onChange={inputChanged} id={timeSheet.id}
                                name="time"
                               value={timeSheet.time}
                               type="text" className="in-text xsmall"/>
                    </td>
                    <td className="small">
                        <input onChange={inputChanged} id={timeSheet.id} placeholder={timeSheet.overtime} name="overtime" type="text" className="in-text xsmall"/>
                    </td>
                </tr>
            ))}
        </>
    );
}
