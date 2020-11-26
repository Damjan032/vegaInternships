import React, {useEffect, useState} from 'react';
import NewMemberDialog from "../components/Employee/NewMemberDialog"
import {useDispatch} from 'react-redux';
import {getAllEmployeesAction} from "../store/actions/employees/employeesActions";
import EmployeesList from "../components/Employee/EmployeesList";
import LetterList from "../components/LetterList";

export default function Employees() {
  const [isDialogVisible, setIsDialogVisible] = useState(false);
  const [activeLetter, setActiveLetter] = useState('');

  function dialogClosed() {
    setIsDialogVisible(false);
  }

  const dispatch = useDispatch();


  useEffect(() => {
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
            Create new member
          </a>
          <NewMemberDialog open={isDialogVisible} onClose={dialogClosed}/>
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
        <EmployeesList/>

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
