/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 *
 * @author bek
 */
public class UserEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;
    
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher pub) {
        publisher = pub;
    }
    
    protected void publishEvent(UserEvent event){
        publisher.publishEvent(event);
    }
    
}
