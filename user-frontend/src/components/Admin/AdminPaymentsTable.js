import * as React from "react";
import "../../styles/admin-cars-table-style.css";
import 'reactjs-popup/dist/index.css';

export default function AdminPaymentsTable(props) {

    const listPayments = (paymentData) => {
        var list = [];
        for(var i = 0; i < paymentData.length; i++) {
            list.push(
                <tr key = {paymentData[i].id}>
                    <th>
                        {paymentData[i].id}
                    </th>
                    <th>
                        {paymentData[i].rentalId}
                    </th>
                    <th>
                        {(paymentData[i].paymentValue/100).toFixed(2)}
                    </th>
                    <th>
                        {paymentData[i].paymentStatus}

                    </th>
                </tr>
            )
        }
        return(list);
    };

    return(
        <table>
            <colgroup>
               <col span="1" style={{width: "10%"}}/>
               <col span="1" style={{width: "10%"}}/>
               <col span="1" style={{width: "30%"}}/>
               <col span="1" style={{width: "20%"}}/>
            </colgroup>
            <thead>
                <tr>
                    <th>
                        Id
                    </th>
                    <th>
                        RentalId
                    </th>
                    <th>
                        Charge
                    </th>
                    <th>
                        Payment Status
                    </th>
                </tr>
            </thead>
            <tbody>
                {listPayments(props.payments.payments)}
            </tbody>
        </table>
    )
}
