import React from 'react';
import Project from "./Project";


export default function ProjectList(props) {
    const {projects, onDelete} = props;
    return (
        <div className="accordion-wrap clients">
            {projects.map(project => (
                <Project onDeleteOrUpdate={onDelete} key={project.id} project={project}/>
            ))}
        </div>
    );
}
