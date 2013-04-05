/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.calltag.model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
/*
**
 *
 * @author bek
 */

@Repository
@Transactional(readOnly = true)
public class UserDao {
    
   @Autowired
   private SessionFactory sessionFactory;
   
   public Session getCurrentSession(){
       return sessionFactory.getCurrentSession();
   }
    
    @Transactional(readOnly = false)
    public void addUser(User user){
        getCurrentSession().save(user);
    }
   
    @Transactional(readOnly = false) 
    public void removeUser(User user){
        getCurrentSession().delete(user);
    }
    
    @Transactional(readOnly = false) 
    public void updateUser(User user){
        getCurrentSession().update(user);
    }
    
    
    public User getUserById(long id){
    List<User> list = getCurrentSession()
                     .createSQLQuery("select * from user where id=?")
                     .setParameter(0, id)
                     .list();
        return list.size() > 0 ? list.get(0) : null;
    }
    
    
    public User getUserBySession(String sessionId) {
//        long now = System.currentTimeMillis()/1000;
        List<User> list = getCurrentSession()
                          .createSQLQuery("select * from user where session_id=?")// checking if session expired
                          .setParameter(0, sessionId)
                          .list();
        return list.size()>0?list.get(0):null;
    }

    
    public List<User> getUsers(){
      return getCurrentSession().createQuery("from user").list();
    }
    
    
}
