import React from 'react';
import {useSelector} from 'react-redux';
import Project from "./Project";


export default function ProjectList(props) {
    const projects = useSelector(state => state.projects);
    console.log(projects)

    return (
        <div className="accordion-wrap clients">
            {projects.map(project => (
                <Project key={project.id} project={project}/>
            ))}
        </div>
    );
}
