/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.service;

import com.calltag.model.User;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStreamFactory;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.StatusStream;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
/**
 *
 * @author bek
 */
public class TwitterListener  implements StatusListener {
    
    @Autowired
    private Phone phone;
    
    @Autowired
    private UserService userService;
        
    private TwitterStreamFactory twitterFactory;
    private TwitterStream stream;
    
    public static final String CALL_TRIGGER = "$call";
    public static final String TEXT_TRIGGER = "$text";

    public TwitterListener(){
        twitterFactory = new TwitterStreamFactory();
    }
    
    
    private void refreshStream(){
        String[] trackWords = {TEXT_TRIGGER,CALL_TRIGGER};
//        List<User> userList = userService.getUsers();
//        long[] ids = new long[userList.size()];
        long[] ids = {39681000};
        
//        for(int i = 0;i<ids.length;i++){
//            ids[i] = userList.get(i).getId();
//        }
        
        FilterQuery query = new FilterQuery();
        query.follow(ids);
        query.track(trackWords);
        
        AccessToken token = new AccessToken("39681000-kuY8WvDVCMXGOZl0Wl3gBrQEWO00gc0SsYBaEc6Vg", "LiIc7aKFrcCwMHJkgIQrtUfOS8rKwnHzv0vEttU0Ng");

        TwitterStream newStream = twitterFactory.getInstance(token);
        newStream.addListener(this);
        newStream.filter(query);
        
        if(stream != null){
            stream.shutdown();
            stream.cleanUp();
            stream = null;
        }
        
        stream = newStream;
    }
        
        
    @Override
    public void onStatus(Status status) {
       System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
       User user = userService.getUserById(status.getId());
       if(user != null)handleTweet(user,status);
    }
    
    
    private void handleTweet(User user,Status status){
        //text and calling 
        if(!user.getIsCallEnabled() && !user.getIsTextEnabled())return;
        
        // text of the tweet
        String text = status.getText();

        //checking if tweet has CALL_TRIGGER or TEXT_TRIGGER tag
        boolean shouldCall = text.indexOf(CALL_TRIGGER) > -1 && user.getIsCallEnabled();
        if (shouldCall)text = text.replaceFirst(CALL_TRIGGER,"");
        boolean shouldText = text.indexOf(TEXT_TRIGGER) > -1 && user.getIsTextEnabled();
        if (shouldText)text = text.replaceFirst(TEXT_TRIGGER,"");
        
        if(!shouldCall && !shouldText)return;// no texting no calling
        
        //extracting unique valid phone numbers in the tweet which will recieve call or text
        Set<String> phones = new HashSet<String>();
        Matcher m = Pattern.compile("(\\+\\d{12})").matcher(text);
        while (m.find()) {
            String phone = m.group();
            if (!phones.contains(phone)) {
                phones.add(phone);
            }
        }
        text = m.replaceAll("");//removing valid phone numbers from tweet
        
        //itirating and making phone call or text to each phone in the tweet
        Iterator<String> iterator = phones.iterator();
        while(iterator.hasNext()){
            String phoneNumber = iterator.next();
            if(shouldCall){
               makeCall(phoneNumber,status.getId(),status.getUser().getId());
            }else if(shouldText){
               makeText(phoneNumber,text);
            }
        }
    }
    
    
    private void makeCall(String phoneNumber,long id,long userid){
        //creating endpoint for twillio which will be 
        //twillio server called when phone is picked up
        String endPoint = "calltag.heroku.com/twillio.htm";
               endPoint+= "?tweet_id="+id;
               endPoint+= "&author_id="+userid;
               
        phone.call(phoneNumber,endPoint);
    }
    
     
    private void makeText(String phoneNumber,String text){
        phone.text(phoneNumber, text);
    }
        
   
    
    
    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }     
    
    @Override
    public void onScrubGeo(long l, long l1) {
       
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
    }

    @Override
    public void onTrackLimitationNotice(int i) {
    }

    @Override
    public void onStallWarning(StallWarning sw) {
    }
    
}
