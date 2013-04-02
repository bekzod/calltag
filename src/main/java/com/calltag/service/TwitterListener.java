/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.service;

import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.instance.Account;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStreamFactory;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;
/**
 *
 * @author bek
 */
public class TwitterListener implements StatusListener {
    
    private TwitterStreamFactory twitterFactory;
    private ArrayList<TwitterStream> streams;
    
    public static final String CALL_TRIGGER = "#call";
    public static final String TEXT_TRIGGER = "#text";
        

    
    public TwitterListener(){
        streams        = new ArrayList<TwitterStream>();
        twitterFactory = new TwitterStreamFactory();
    }
    
    
    public void listen(AccessToken token,Boolean isCall,Boolean isText){
        if(!isText&&!isCall)return; // when no call or no text end here
        
        ArrayList<String> trackList = new ArrayList<String>();
        if(isText)trackList.add(TEXT_TRIGGER);
        if(isCall)trackList.add(CALL_TRIGGER);

        String[] trackWords = (String[]) trackList.toArray();
        long[] trackPeople  = {token.getUserId()};
        
        track(token,trackWords,trackPeople);
    }
    
    
    private void track(AccessToken token,String[] trackWords, long[] userIds){
        removeSreamWithUserId(token.getUserId()); // removing twitter stream if already  exist

        TwitterStream stream = twitterFactory.getInstance(token);
        stream.addListener(this);
        stream.filter(new FilterQuery(0,userIds,trackWords));
        
        streams.add(stream);
    }
    
    
    public TwitterStream removeSreamWithUserId(long id){
        int len = streams.size();
        for(int i = 0; i<len;i++){
            try {
                if(streams.get(i).getOAuthAccessToken().getUserId() == id){
                    return streams.remove(i);
                }
            } catch (TwitterException ex) {
                Logger.getLogger(TwitterListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    @Override
    public void onStatus(Status status) {
        System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
    }
   

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onScrubGeo(long l, long l1) {
       
    }
    
}
