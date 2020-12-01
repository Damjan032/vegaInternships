import React from 'react';
import {useSelector} from 'react-redux';

export default function CategorySelection(props) {
    const categories = useSelector(state => state.categories);
    const {onChange, value, id} = props;

    return (
        <select id={id} value={value} onChange={onChange} name="categories">
            <option value={''}>Select a category</option>
            {categories.map((category) => (
                <option key={category.id} value={category.id}>
                    {category.name}
                </option>
            ))}
        </select>
    );
}
