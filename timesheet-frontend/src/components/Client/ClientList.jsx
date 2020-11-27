import React from 'react';
import Client from "./Client";


export default function ClientList(props) {

    const {clients, onDelete} = props;
    return (
        <div className="accordion-wrap clientPages">
            {clients.map(client => (
                <Client onDeleteOrUpdate={onDelete} key={client.id} client={client}/>
            ))}
        </div>
    );
}
