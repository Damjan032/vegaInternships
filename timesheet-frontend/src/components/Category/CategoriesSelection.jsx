import React from 'react';
import {useSelector} from 'react-redux';

export default function CategorySelection(props) {
    const categories = useSelector(state => state.categories);
    const {onChange, value} = props;

    return (
        <select value={value} onChange={onChange} name="categories">
            <option value={''}>Select a category</option>
            {categories.map((category) => (
                <option key={category.id} value={category.id}>
                    {category.name}
                </option>
            ))}
        </select>
    );
}
