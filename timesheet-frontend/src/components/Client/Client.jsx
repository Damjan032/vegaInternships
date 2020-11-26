import React, {useState} from 'react';
import {useDispatch} from 'react-redux';
import CountrySelection from "../Country/CountrySelection";
import {deleteClientAction, updateClientAction} from '../../store/actions/clients/clientsActions';
import {useForm} from "react-hook-form";

export default function Client(props) {
  const {handleSubmit, register, errors} = useForm();
  const [isEditing, setIsEditing] = useState(false);

  const dispatch = useDispatch();

  function deleteClient(id) {
    dispatch(deleteClientAction(id));
  }

  const [newClient, setClientNewClient] = useState({
    id: props.client.id,
    name: props.client.name,
    street: props.client.street,
    city: props.client.city,
    zipCode: props.client.zipCode,
    country: props.client.country,
  });

  function updateClient() {
    dispatch(updateClientAction(newClient));
  }


  function inputChanged(event) {
    setClientNewClient({
      ...newClient,
      [event.target.name]: event.target.value,
    });
  }

  return (
      <div className="item">
        <div className="heading" onClick={() => setIsEditing(!isEditing)}>
          <span>{newClient.name}</span>
          <i>+</i>
        </div>
        {isEditing && <div className="details">
          <form onSubmit={handleSubmit(updateClient)}>
            <ul className="form">
              <li>
                <label>Client name:</label>
                <input
                    type="text"
                    className="in-text"
                    value={newClient.name}
                    name="name"
                    ref={register({
                      required: 'Name is required'
                    })}
                    onChange={inputChanged}
                />
                <span className="input-error">{errors.name && errors.name.message}</span>
              </li>
              <li>
                <label>Zip/Postal code:</label>
                <input
                    type="text"
                    className="in-text"
                    value={newClient.zipCode}
                    name="zipCode"
                    onChange={inputChanged}
                />
              </li>
            </ul>
            <ul className="form">
              <li>
                <label>Address:</label>
                <input
                    type="text"
                    className="in-text"
                    value={newClient.street}
                    name="street"
                    onChange={inputChanged}
                />
              </li>
              <li>
                <label>Country:</label>
                <CountrySelection value={newClient.country}
                                  onChange={e => newClient.country = e.target.value}/>
              </li>
            </ul>
            <ul className="form last">
              <li>
                <label>City:</label>
                <input
                    type="text"
                    className="in-text"
                    value={newClient.city}
                    name="city"
                    onChange={inputChanged}
                />
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
                    onClick={() => deleteClient(newClient.id)}
                >Delete
                </button>
              </div>
            </div>
          </form>

        </div>}
      </div>
  );
}
