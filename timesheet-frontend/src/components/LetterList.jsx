import React from 'react';
import {alphabet} from "../utils";

export default function LetterList(props) {
    const {activeLetter, onClick} = props;

    const letters = alphabet;

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
                            <a href="#" onClick={() => onClick(letter)}>{letter}</a>
                        </li>)
                })
                }
                <li className={activeLetter === '' ? 'active' : ''}>
                    <a onClick={() => onClick("cancel")}>&#10006;</a>
                </li>

            </ul>
        </div>
    );
}
