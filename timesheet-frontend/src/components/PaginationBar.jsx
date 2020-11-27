import React from "react";

export default function PaginationBar(props) {
    const {pageSize, pageNumber, totalSize, onClick} = props;
    //console.log(Array(Math.ceil(totalSize/pageSize) - 1 + 1).fill().map((_, idx) => 1 + idx))
    return (
        <div className="pagination">
            <ul>
                {Array(Math.ceil(totalSize / pageSize) - 1 + 1).fill().map((_, idx) => 1 + idx)
                    .map(numberOfPage => (
                        <li key={numberOfPage}>&nbsp;&nbsp;
                            <label className="link" onClick={onClick.bind(this, numberOfPage)}
                                   key={numberOfPage}>{numberOfPage}</label>
                            &nbsp;&nbsp;
                        </li>
                    ))}
                {Math.ceil(totalSize / pageSize) > pageNumber &&
                <label className="link" onClick={onClick.bind(this, "NEXT")}>Next</label>
                }

            </ul>
        </div>
    )
}