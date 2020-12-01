import React from 'react';
import {useSelector} from 'react-redux';

export default function ProjectSelection(props) {
    const projects = useSelector(state => state.projects);
    const {onChange, value} = props;

    return (
        <select value={value} onChange={onChange} name="project">
            <option value={''}>Select a project</option>
            {projects.map((project) => (
                <option key={project.id} value={project.id}>
                    {project.name}
                </option>
            ))}
        </select>
    );
}
