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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
/**
 *
 * @author bek
 */
public class TwitterListener implements UserStreamListener {
    
    @Autowired
    Phone phone;
    
    private TwitterStreamFactory twitterFactory;
    private ArrayList<TwitterStream> streams;
    
    public static final String CALL_TRIGGER = "$call";
    public static final String TEXT_TRIGGER = "$text";
        

    
    public TwitterListener(){
        streams        = new ArrayList<TwitterStream>();
        twitterFactory = new TwitterStreamFactory();
    }
    
    public void listen(AccessToken token,boolean isCall,boolean isText){
        if(!isText&&!isCall)return; // when no call or no text end here
        
        ArrayList<String> trackList = new ArrayList<String>();
        if(isText)trackList.add(TEXT_TRIGGER);
        if(isCall)trackList.add(CALL_TRIGGER);

        String[] trackWords = new String[trackList.size()];
        trackList.toArray(trackWords);
        long[] trackPeople  = {token.getUserId()};
        
        track(token,trackWords);
    }
    
    
    private void track(AccessToken token,String[] trackWords){
        TwitterStream oldStream = removeSreamWithUserId(token.getUserId()); // removing twitter stream if already  exist
        if(oldStream != null){
            oldStream.shutdown();//destroying old stream
            oldStream.cleanUp();
            oldStream = null;
        }
        
        TwitterStream stream = twitterFactory.getInstance(token);
        stream.addListener(this);
        stream.user(trackWords);
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
       handleTweet(status);
    }
    
    
    private void handleTweet(Status status){
        
        // text of the tweet
        String text = status.getText();
        
        //checking if tweet has $call or $text tag
        boolean shouldCall = text.indexOf("$call") > -1;
        if (shouldCall)text = text.replaceFirst("\\$call","");
        boolean shouldText = text.indexOf("$text") > -1;
        if (shouldText)text = text.replaceFirst("\\$text","");

        // if we have no call no text then ending here
        if(!shouldCall&&!shouldText)return;
        
        //extracting unique phone numbers in the tweet which will recieve call or text
        Set<String> phones = new HashSet<String>();
        Matcher m = Pattern.compile("(\\+\\d{12})").matcher(text);
        while (m.find()) {
            String phone = m.group();
            if (!phones.contains(phone)) {
                phones.add(phone);
            }
        }
        //removing or valid phone numbers from tweet
        text = m.replaceAll("");
        
        //itirating and making phone call or text to each phone in the tweet
        Iterator<String> iterator = phones.iterator();
        while(iterator.hasNext()){
            String phoneNumber = iterator.next();
            if(shouldCall){
               makeCall(phoneNumber,status.getId(),status.getUser());
            }else if(shouldText){
               makeText(phoneNumber,text);
            }
        }
    }
    
    
    private void makeCall(String phoneNumber,long id,User user){
        //creating endpoint for twillio which will be 
        //twillio server called when phone is picked up
        String endPoint = "calltag.heroku.com/twillio.htm";
               endPoint+= "?tweetid="+(String.valueOf(id));
               endPoint+= "&authorid="+user.getId();
               
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
    public void onDeletionNotice(long l, long l1) {

    }

    @Override
    public void onFriendList(long[] longs) {

    }

    @Override
    public void onFavorite(User user, User user1, Status status) {

    }

    @Override
    public void onUnfavorite(User user, User user1, Status status) {

    }

    @Override
    public void onFollow(User user, User user1) {

    }

    @Override
    public void onDirectMessage(DirectMessage dm) {

    }

    @Override
    public void onUserListMemberAddition(User user, User user1, UserList ul) {

    }

    @Override
    public void onUserListMemberDeletion(User user, User user1, UserList ul) {

    }

    @Override
    public void onUserListSubscription(User user, User user1, UserList ul) {

    }

    @Override
    public void onUserListUnsubscription(User user, User user1, UserList ul) {

    }

    @Override
    public void onUserListCreation(User user, UserList ul) {

    }

    @Override
    public void onUserListUpdate(User user, UserList ul) {

    }

    @Override
    public void onUserListDeletion(User user, UserList ul) {

    }

    @Override
    public void onUserProfileUpdate(User user) {

    }

    @Override
    public void onBlock(User user, User user1) {

    }

    @Override
    public void onUnblock(User user, User user1) {

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
