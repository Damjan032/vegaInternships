import React from 'react';
import {useSelector} from 'react-redux';

export default function CountrySelection(props) {
    const countries = useSelector(state => state.countries);
    const {onChange, value} = props;

    return (
        <select value={value} onChange={onChange} name="country">
            <option value={null}>Select a country</option>
            {countries.map((country) => (
                <option key={country.name} value={country.name}>
                    {country.name}
                </option>
            ))}
        </select>
    );
}
