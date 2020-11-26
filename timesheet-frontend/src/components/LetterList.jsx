import React from 'react';
import {useDispatch} from "react-redux";
import {getAllClientsAction} from "../store/actions/clients/clientsActions";
import {alphabet} from "../utils";

export default function LetterList(props) {
    const {activeLetter, setActiveLetter} = props;

    const dispatch = useDispatch();


    // let letters = clients.map(cl => cl.dto.name[0].toUpperCase());
    //
    // letters = letters.filter(onlyUnique);

    const letters = alphabet;

    function letterClicked(letter) {
        setActiveLetter(null);
        dispatch(getAllClientsAction());

    }

    return (
        <div className="alpha">
            <ul>
                {alphabet.map((letter, index) => {
                    let className = '';
                    if (!letters.includes(letter)) {
                        className = 'disabled';
                    } else if (activeLetter === letter) {
                        className = 'active';
                    }
                    return (
                        <li
                            key={index}
                            className={className}
                        >
                            <a href="/#" onClick={() => letterClicked(letter)}>{letter}</a>
                        </li>)
                })
                }
            </ul>
        </div>
    );
}
