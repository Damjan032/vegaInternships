import React, {useEffect, useState} from 'react';
import NewProjectDialog from "../components/Project/NewProjectDialog"
import {useDispatch} from 'react-redux';
import {getAllProjectsAction} from "../store/actions/projects/projectsActions";
import ProjectsList from "../components/Project/ProjectList";
import LetterList from "../components/LetterList";
import {getAllEmployeesAction} from "../store/actions/employees/employeesActions";
import {getAllClientsAction} from "../store/actions/clients/clientsActions";

export default function Projects() {
    const [isDialogVisible, setIsDialogVisible] = useState(false);
    const [activeLetter, setActiveLetter] = useState('');

    function dialogClosed() {
        setIsDialogVisible(false);
    }

    const dispatch = useDispatch();


    useEffect(() => {
        dispatch(getAllProjectsAction());
        dispatch(getAllEmployeesAction());
        dispatch(getAllClientsAction())
    }, [dispatch]);

    return (
        <>
            <h2>
                <i className="ico team-member"/>Team members
            </h2>
            <div className="grey-box-wrap reports ico-member">
                {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                <a href="#"
                   className="link new-member-popup"
                   onClick={() => setIsDialogVisible(true)}
                >
                    Create new project
                </a>
                <NewProjectDialog open={isDialogVisible} onClose={dialogClosed}/>
                <div className="search-page">
                    <form>
                        <input
                            type="search"
                            name="search-clients"
                            className="in-search"
                        />
                    </form>
                </div>

            </div>


            <LetterList activeLetter={activeLetter} setActiveLetter={setActiveLetter}/>
            <ProjectsList/>

            <div className="pagination">
                <ul>
                    <li>
                        <a href="/#">1</a>
                    </li>
                    <li>
                        <a href="/#">2</a>
                    </li>
                    <li>
                        <a href="/#">3</a>
                    </li>
                    <li className="last">
                        <a href="/#">Next</a>
                    </li>
                </ul>
            </div>
        </>
    );
}
