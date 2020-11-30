import React, {useEffect, useState} from 'react';
import NewProjectDialog from "../components/Project/NewProjectDialog"
import {useDispatch, useSelector} from 'react-redux';
import {getPageOfProjectsAction} from "../store/actions/projects/projectsActions";
import ProjectsList from "../components/Project/ProjectList";
import LetterList from "../components/LetterList";
import {getAllEmployeesAction} from "../store/actions/employees/employeesActions";
import PaginationBar from "../components/PaginationBar";
import {getAllClientsAction} from "../store/actions/clients/clientsActions";
import SearchInput from "../components/SearchInput";

export default function Projects() {
    const [isDialogVisible, setIsDialogVisible] = useState(false);
    const [activeLetter, setActiveLetter] = useState('');
    const [searchString, setStringString] = useState('');

    function dialogClosed() {
        setIsDialogVisible(false);
    }

    const dispatch = useDispatch();
    const pageOfProjects = useSelector(state => state.projectsPage);

    const handleAddOrDelete = async () => {
        await dispatch(getPageOfProjectsAction(pageOfProjects.pageNumber, activeLetter, searchString));
        // setTimeout(dispatch(getPageOfProjectsAction(pageOfProjects.pageNumber, activeLetter, searchString)), 500);
    };

    const handlePropagationClick = (numberOfPage) => {
        if (numberOfPage === "NEXT") {
            dispatch(getPageOfProjectsAction(++pageOfProjects.pageNumber, activeLetter, searchString));
        } else {
            dispatch(getPageOfProjectsAction(numberOfPage));
            pageOfProjects.pageNumber = numberOfPage;
        }
    };

    async function handleSearch(event) {
        await setStringString(event.target.value);
        await dispatch(getPageOfProjectsAction(pageOfProjects.pageNumber, activeLetter, event.target.value));
    }

    async function letterClicked(letter) {
        if (letter === "cancel") {
            setActiveLetter('');
        } else {
            setActiveLetter(letter);
        }
        setTimeout(() => {
        }, 2000);
        await dispatch(getPageOfProjectsAction(pageOfProjects.pageNumber, letter, searchString));
    }

    useEffect(() => {
        dispatch(getAllClientsAction());
        dispatch(getPageOfProjectsAction(1));
        dispatch(getAllEmployeesAction());

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
                <NewProjectDialog onAdd={handleAddOrDelete} open={isDialogVisible} onClose={dialogClosed}/>
                <SearchInput searchString={searchString} onChange={handleSearch}/>

            </div>


            <LetterList activeLetter={activeLetter} onClick={letterClicked}/>
            <ProjectsList onDelete={handleAddOrDelete} projects={pageOfProjects.items}/>

            <PaginationBar onClick={handlePropagationClick} pageNumber={pageOfProjects.pageNumber}
                           totalSize={pageOfProjects.totalSize} pageSize={pageOfProjects.pageSize}/>
        </>
    );
}
