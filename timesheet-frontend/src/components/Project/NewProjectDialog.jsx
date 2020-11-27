import {Dialog} from "@material-ui/core";
import React, {useState} from "react";
import EmployeeSelection from "../Employee/EmpoyeeSelection";
import ClientSelection from "../Client/ClientSelection";
import {useForm} from "react-hook-form";
import {useDispatch, useSelector} from "react-redux";
import {addProjectAction} from "../../store/actions/projects/projectsActions";

export default function NewProjectDialog(props) {
    const clients = useSelector(state => state.clients);
    const employees = useSelector(state => state.employees);
    const [project, setProject] = useState({
        clientDto: '',
        clientId: '',
        description: '',
        status: "ACTIVE",
        teamLead: '',
        teamLeadId: '',
        name: '',
    });
    const {handleSubmit, register, errors} = useForm();
    const {onClose, open} = props;

    const dispatch = useDispatch();

    const inputChanged = (event) => {
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
    };
    const saveNewProject = () => {
        dispatch(addProjectAction(project));
        setProject({
            clientDto: '',
            clientId: '',
            description: '',
            status: "ACTIVE",
            teamLead: '',
            teamLeadId: '',
            name: ''
        });

        onClose();
    };

    return (
        <Dialog onClose={onClose} open={open}>
            <div className="new-member-wrap">
                <div id="new-member" className="new-member-inner">
                    <h2>Create new client</h2>
                    <form onSubmit={handleSubmit(saveNewProject)}>
                        <ul className="form">
                            <li>
                                <label>Project name:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="name"
                                    value={project.name || ''}
                                    ref={register({
                                        required: 'Name is required'
                                    })}
                                    onChange={inputChanged}
                                />
                                <span className="input-error">{errors.name && errors.name.message}</span>
                            </li>
                            <li>
                                <label>Description:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="description"
                                    value={project.street}
                                    onChange={inputChanged}
                                />
                            </li>
                            <li>
                                <label>Customer:</label>
                                <ClientSelection
                                    onChange={inputChanged}/>
                            </li>
                            <li>
                                <label>Lead:</label>
                                <EmployeeSelection
                                    onChange={inputChanged}/>
                            </li>
                        </ul>
                        <div className="buttons">
                            <div className="inner">
                                <button className="btn green" type="submit">
                                    Save
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </Dialog>
    );
}