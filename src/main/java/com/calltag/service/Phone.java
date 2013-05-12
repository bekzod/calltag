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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    /**
     * Initiates a call to particular phone number using twillio api
     * 
     * @param phoneNumber any valid number 
     * @param url callback url which will be called when user picks up the phone
     */
    public void call(String phoneNumber,String url){
        CallFactory callFactory = account.getCallFactory();
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("To", phoneNumber);
        callParams.put("From", fromNumber); 
        callParams.put("Url", url);
        try {
            callFactory.create(callParams);
        } catch (TwilioRestException ex) {
            Logger.getLogger(Phone.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Initiates a text to particular phone number using twillio api
     * @param phoneNumber any valid number
     * @param text text which needs to be sent as text message
     */
    public void text(String phoneNumber,String text){
        SmsFactory smsFactory = account.getSmsFactory();
        Map<String, String> smsParams = new HashMap<String, String>();
        smsParams.put("To", phoneNumber); 
        smsParams.put("From", fromNumber);
        smsParams.put("Body", text);
        try {
            smsFactory.create(smsParams);
        } catch (TwilioRestException ex) {
            Logger.getLogger(Phone.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
