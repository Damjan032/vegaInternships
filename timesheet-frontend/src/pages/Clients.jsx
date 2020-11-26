import React, {useEffect, useState} from 'react';
import NewClientDialog from "../components/Client/NewClientDialog";
import {getAllClientsAction} from '../store/actions/clients/clientsActions';
import {getAllCountriesAction} from '../store/actions/countries/countriesActions';
import {useDispatch} from 'react-redux';
import LetterList from "../components/LetterList";
import ClientList from "../components/Client/ClientList";


export default function Clients() {
  const [isDialogVisible, setIsDialogVisible] = useState(false);
  const [activeLetter, setActiveLetter] = useState('');

  function dialogClosed() {
    setIsDialogVisible(false);
  }

  const dispatch = useDispatch();


  useEffect(() => {
    dispatch(getAllClientsAction());
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
          <NewClientDialog open={isDialogVisible} onClose={dialogClosed}/>
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

        <ClientList/>
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
              <a href="/#"> Next</a>
            </li>
          </ul>
        </div>
      </>
  );
}
