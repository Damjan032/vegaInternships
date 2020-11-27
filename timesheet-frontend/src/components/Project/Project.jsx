import React, {useState} from 'react';
import {useForm} from "react-hook-form";
import {useDispatch, useSelector} from "react-redux";
import {deleteProjectAction, updateProjectAction} from "../../store/actions/projects/projectsActions";
import ClientSelection from "../Client/ClientSelection";
import EmployeeSelection from "../Employee/EmpoyeeSelection";

export default function Projects(props) {
    const {handleSubmit, register, errors} = useForm();
    const clients = useSelector(state => state.clients);
    const employees = useSelector(state => state.employees);
    const [isEditing, setIsEditing] = useState(false);

    const dispatch = useDispatch();

    function deleteProject(id) {
        dispatch(deleteProjectAction(id));
    }
    const [project, setProject] = useState({
        clientDto : props.project.clientDto,
        clientId : props.project.clientDto.id,
        description: props.project.description,
        id: props.project.id,
        name: props.project.name,
        status: props.project.status,
        teamLead:props.project.teamLead,
        teamLeadId:props.project.teamLead.id,

    });
    function updateProject() {
       dispatch(updateProjectAction(project));
    }

    function inputChanged(event) {
        if(event.target.value==='') return;
        switch (event.target.name) {
            case "teamLead":
                setProject({
                    ...project,
                    teamLeadId: event.target.value,
                    teamLead: employees.filter(employee => employee.id === event.target.value)[0],
                });
                break;
            case "client":
                setProject({
                    ...project,
                    clientId: event.target.value,
                    clientDto: clients.filter(client => client.id === event.target.value)[0],
                });
                break;
            default:
                setProject({
                    ...project,
                    [event.target.name]: event.target.value,
                });
        }
    }
    return (
        <div className="item">
            <div className="heading" onClick={() => setIsEditing(!isEditing)}>
                <span>{project.name}</span>
                <i>+</i>
            </div>
            {isEditing && <div className="details">
                <form onSubmit={handleSubmit(updateProject)}>
                    <ul className="form">
                        <li>
                            <label>Project name:</label>
                            <input
                                type="text"
                                className="in-text"
                                value={project.name}
                                name="name"
                                ref={register({
                                    required: 'Name is required'
                                })}
                                onChange={inputChanged}
                            />
                            <span className="input-error">{errors.name && errors.name.message}</span>
                        </li>
                        <li>
                            <label>Lead:</label>
                            <EmployeeSelection value={project.teamLeadId}
                                             onChange={inputChanged}/>
                        </li>

                    </ul>
                    <ul className="form">
                        <li>
                            <label>Description:</label>
                            <input
                                type="text"
                                className="in-text"
                                value={project.description==null?"":project.description}
                                name="description"
                                onChange={inputChanged}
                            />
                        </li>
                    </ul>
                    <ul className="form">
                        <li>
                            <label>Customer:</label>
                            <ClientSelection  value={project.clientId}
                                               onChange={inputChanged}/>
                        </li>
                        <li>
                            <label  className="myMargin">Status:</label>
                            <span className="radio">
                            <label  className="myMargin">Active:</label>
                            <input
                                type="radio"
                                value="ACTIVE"
                                name="status"
                                id="active"
                                checked={project.status==="ACTIVE"}
                                onChange={inputChanged}
                            />
                          </span>
                            <span className="radio">
                            <label className="myMargin">&nbsp;&nbsp;&nbsp;&nbsp;Inactive:</label>
                            <input
                                type="radio"
                                value="INACTIVE"
                                name="status"
                                id="inactive"
                                checked={project.status==="INACTIVE"}
                                onChange={inputChanged}
                            />
                          </span>
                            <span className="radio">
                            <label className="myMargin">Archive:</label>
                            <input
                                type="radio"
                                value="ARCHIVE"
                                name="status"
                                id="archive"
                                checked={project.status==="ARCHIVE"}
                                onChange={inputChanged}
                            />
                          </span>
                        </li>
                    </ul>
                    <div className="buttons">
                        <div className="inner">
                            <button
                                className="btn green"
                                type="submit"
                            >Save
                            </button>
                            <button
                                type="button"
                                className="btn red"
                                onClick={() => deleteProject(project.id)}
                            >Delete
                            </button>
                        </div>
                    </div>
                </form>

            </div>}
        </div>
    );
}
