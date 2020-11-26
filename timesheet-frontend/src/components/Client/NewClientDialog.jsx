import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import {Dialog} from "@material-ui/core";
import CountrySelection from "../Country/CountrySelection";

import {addClientAction} from '../../store/actions/clients/clientsActions';
import {useForm} from "react-hook-form";

export default function NewClientDialog(props) {
    const [client, setClient] = useState({
        name: '',
        street: '',
        city: '',
        zipCode: '',
        country: null,
    });
    const {handleSubmit, register, errors} = useForm();
    const {onClose, open} = props;

    const dispatch = useDispatch();

    const inputChanged = (event) => {
        setClient({
            ...client,
            [event.target.name]: event.target.value,
        });
    };
    const saveNewClient = () => {
        dispatch(addClientAction(client));
        setClient({
            name: '',
            street: '',
            city: '',
            zipCode: '',
            country: '',
        });

        onClose();
    };

    return (
        <Dialog onClose={onClose} open={open}>
            <div className="new-member-wrap">
                <div id="new-member" className="new-member-inner">
                    <h2>Create new client</h2>
                    <form onSubmit={handleSubmit(saveNewClient)}>
                        <ul className="form">
                            <li>
                                <label>Client name:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="name"
                                    value={client.name || ''}
                                    ref={register({
                                        required: 'Name is required'
                                    })}
                                    onChange={inputChanged}
                                />
                                <span className="input-error">{errors.name && errors.name.message}</span>
                            </li>
                            <li>
                                <label>street:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="street"
                                    value={client.street}
                                    onChange={inputChanged}
                                />
                            </li>
                            <li>
                                <label>City:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="city"
                                    value={client.city}
                                    onChange={inputChanged}
                                />
                            </li>
                            <li>
                                <label>Zip/Postal code:</label>
                                <input
                                    type="text"
                                    className="in-text"
                                    name="zipCode"
                                    value={client.zipCode}
                                    onChange={inputChanged}
                                />
                            </li>
                            <li>
                                <label>Country:</label>
                                <CountrySelection onChange={inputChanged}/>
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
