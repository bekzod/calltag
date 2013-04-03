/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.Sms;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bek
 */
public class Phone {
    
    private TwilioRestClient client;
    private Account account;
    private String fromNumber;
        
    public Phone(String sid,String auth,String from){
      client     = new TwilioRestClient(sid,auth);      
      account    = client.getAccount();
      fromNumber = from;
    }
    
    public Call call(String number,String url) throws TwilioRestException{
        CallFactory callFactory = account.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("To", number);
        callParams.put("From", fromNumber); 
        callParams.put("Url", url);
        return callFactory.create(callParams);
    }
    
    public Sms text(String number,String text) throws TwilioRestException{
        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        smsParams.put("To", number); 
        smsParams.put("From", fromNumber);
        smsParams.put("Body", text);
        return smsFactory.create(smsParams);
    }
    
}