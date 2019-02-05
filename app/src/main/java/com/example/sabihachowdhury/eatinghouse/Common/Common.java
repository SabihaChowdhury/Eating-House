package com.example.sabihachowdhury.eatinghouse.Common;

import com.example.sabihachowdhury.eatinghouse.Model.User;

public class Common {

    public static User currentUser;

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "Shipping";
        else
            return "Shipped";
    }

}
