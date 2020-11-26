import React from 'react';
import {useSelector} from 'react-redux';
import Client from "./Client";


export default function ClientList(props) {
    const clients = useSelector(state => state.clients);

    return (
        <div className="accordion-wrap clients">
            {clients.map(client => (
                <Client key={client.id} client={client}/>
            ))}
        </div>
    );
}
