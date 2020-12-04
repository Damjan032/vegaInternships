import React, {useEffect, useState} from 'react';
import NewClientDialog from "../components/Client/NewClientDialog";
import {getPageOfClientsAction} from '../store/actions/clients/clientsActions';
import {getAllCountriesAction} from '../store/actions/countries/countriesActions';
import {useDispatch, useSelector} from 'react-redux';
import LetterList from "../components/LetterList";
import ClientList from "../components/Client/ClientList";
import PaginationBar from "../components/PaginationBar";
import SearchInput from "../components/SearchInput";


export default function Clients() {
    const [isDialogVisible, setIsDialogVisible] = useState(false);
    const [activeLetter, setActiveLetter] = useState('');
    const [searchString, setStringString] = useState('');

    function dialogClosed() {
        setIsDialogVisible(false);
    }

    const pageOfClients = useSelector(state => state.clientPages);
    const dispatch = useDispatch();

    const handleAddOrDelete = async () => {
        await dispatch(getPageOfClientsAction(pageOfClients.pageNumber, activeLetter, searchString));
    };

    const handlePropagationClick = (numberOfPage) => {

        if (numberOfPage === "PREV") {
            dispatch(getPageOfClientsAction(--pageOfClients.pageNumber, activeLetter, searchString));
        }
        else if (numberOfPage === "NEXT") {
            dispatch(getPageOfClientsAction(++pageOfClients.pageNumber, activeLetter, searchString));
        } else {
            dispatch(getPageOfClientsAction(numberOfPage));
            pageOfClients.pageNumber = numberOfPage;
        }
    };

    async function handleSearch(event) {
        await setStringString(event.target.value);
        setTimeout(() => {
        }, 2000);
        await dispatch(getPageOfClientsAction(pageOfClients.pageNumber, activeLetter, event.target.value));
    }

    async function letterClicked(letter) {
        if (letter === "cancel") {
            setActiveLetter('');
        } else {
            setActiveLetter(letter);
        }
        setTimeout(() => {
        }, 2000);
        await dispatch(getPageOfClientsAction(pageOfClients.pageNumber, letter, searchString));
    }

    useEffect(() => {
        dispatch(getPageOfClientsAction(1));
        dispatch(getAllCountriesAction());
    }, [dispatch]);


    return (
        <>
            <h2>
                <i className="ico clients"/>Clients
            </h2>
            <div className="grey-box-wrap reports">
                {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                <a href="#"
                   className="link new-member-popup"
                   onClick={() => setIsDialogVisible(true)}
                >
                    Create new client
                </a>
                <NewClientDialog onAdd={handleAddOrDelete} open={isDialogVisible} onClose={dialogClosed}/>
                <SearchInput searchString={searchString} onChange={handleSearch}/>
            </div>


            <LetterList activeLetter={activeLetter} onClick={letterClicked}/>

            <ClientList onDelete={handleAddOrDelete} clients={pageOfClients.items}/>
            <PaginationBar onClick={handlePropagationClick} pageNumber={pageOfClients.pageNumber}
                           totalSize={pageOfClients.totalSize} pageSize={pageOfClients.pageSize}/>
        </>
    );
}
