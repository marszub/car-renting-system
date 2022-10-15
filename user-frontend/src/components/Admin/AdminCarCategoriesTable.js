import * as React from "react";
import { CarDBService } from "../../services/carDB-service";
import { HTTP_BAD_REQUEST, HTTP_NO_CONTENT } from "../../utils/http-status";
import { Grid, TextField, Button, Typography, Box } from "@mui/material";
import "../../styles/admin-cars-table-style.css";
import { useNavigate } from "react-router-dom";
import 'reactjs-popup/dist/index.css';

export default function AdminCarCategoriesTable(props) {
    const navigate = useNavigate();

    const listCarCategories = (data) => {
        var list = [];
        for(var i = 0; i < data.length; i++) {
            list.push(
                <tr key = {data[i].id}>
                    <th>
                        {data[i].id}
                    </th>
                    <th>
                        {data[i].carCategoryName}
                    </th>
                    <th/>
                </tr>
            )
        }
        return(list);
    };

    return(
        <table>
            <colgroup>
               <col span="1" style={{width: "10%"}}/>
               <col span="1" style={{width: "30%"}}/>
               <col span="1" style={{width: "10%"}}/>
            </colgroup>
            <thead>
                <tr>
                    <th>
                        Id
                    </th>
                    <th>
                        Name
                    </th>
                    <th>
                        <Button onClick={() => navigate("/admin/carCategories/new")}>Add Car Category</Button>
                    </th>
                </tr>
            </thead>
            <tbody>
                {listCarCategories(props.carCategories.carCategories)}
            </tbody>
        </table>
    )
}

