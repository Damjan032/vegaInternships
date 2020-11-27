import React from "react";

export default function SearchInput(props) {
    const {onChange,searchString} = props;

    return(
        <div className="search-page">
            <form>
                <input
                    value={searchString}
                    onChange={onChange}
                    type="search"
                    name="search"
                    className="in-search"
                />
            </form>
        </div>
    );
}