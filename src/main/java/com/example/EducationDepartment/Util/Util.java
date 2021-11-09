package com.example.EducationDepartment.Util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Util {

    private final String ACCOUNT_SID = "AC31b2c9f66d33e1256230d66f8eb72516";

    private final String AUTH_TOKEN = "59b0c140cb6508a9942d592a9df496ac";

    private final String FROM_NUMBER = "+14135531059";

    public void send(String phone, String msg) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        System.out.println("Number: " + phone + " Message: " + msg);
        Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber(FROM_NUMBER), msg)
                .create();
        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction

    }

}