import React from 'react';
import {useSelector} from 'react-redux';

export default function ClientSelection(props) {
    const clients = useSelector(state => state.clients);
    const {onChange, value} = props;

    return (
        <select value={value} onChange={onChange} name="client">
            <option value={''}>Select a client</option>
            {clients.map((client) => (
                <option key={client.id} value={client.id}>
                    {client.name}
                </option>
            ))}
        </select>
    );
}
