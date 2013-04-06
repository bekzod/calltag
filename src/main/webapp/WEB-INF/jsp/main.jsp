<%-- 
    Document   : main
    Created on : Apr 1, 2013, 10:27:30 PM
    Author     : bek
--%>
<%@page import="com.calltag.model.User"%>
<% User user = (User)request.getAttribute("user"); %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <!--[if IE]>
       <script>
         document.createElement('header');
         document.createElement('footer');
         document.createElement('section');
         document.createElement('nav');
       </script>
       <![endif]-->

        <link href="resources/css/main.css" rel="stylesheet" />
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    
       
        <title>TweeTalk</title>
    </head>
    <body>
        
        <p><%= user.getName()%></p>
        <p><%= user.getTwitterAccountName()%></p>
        <p><%= user.getProfilePictureUrl()%></p>
        <p><%= user.getIsCallEnabled()%></p>
        <p><%= user.getIsTextEnabled()%></p>
        
        
         <div id="container">

            <header>
                <h1><img src="resources/images/twitter-bird.jpg" ></h1>
                <div id="title" ><img src="resources/images/title.png" ></div>
            </header>	

            <section id="feature">
                <a ><img src="resources/images/inst.png"  /></a>
                <div id="feature-info">

                    <div class="callswitch">
                        <input type="checkbox" name="callswitch" class="callswitch-checkbox" id="callswitch" checked>
                        <label class="callswitch-label" for="callswitch">
                            <div class="callswitch-inner"></div>
                            <div class="callswitch-switch"></div>
                        </label>
                    </div> 
                    <div class="textswitch">
                        <input type="checkbox" name="textswitch" class="textswitch-checkbox" id="textswitch" checked>
                        <label class="textswitch-label" for="textswitch">
                            <div class="textswitch-inner"></div>
                            <div class="textswitch-switch"></div>
                        </label>
                    </div>

                </div>

            </section>
            <section id="content">
                <div class="tilt">
                    <div class="bucket">
                        <a  id="step1">Step1</a>
                        <p id='steps'>Tweet(call)</p>
                        <p>Hey guys, house party tonight at 10  $call (number) </p>
                    </div>
                </div>
                <div class="tilt">
                    <div class="bucket">
                        <a id="step2">Step2</a>
                        <p id='steps'>Tweet(text)</p>
                        <p>Hey guys, house party tonight at 10  $text (number)</p>
                    </div>
                </div>
                <div class="tilt">
                    <div class="bucket">
                        <a id="step3">Step3</a>
                        <p id='steps'>Sit back</p>
                        <p>Sit back and we ll take care of the delivery"</p>
                    </div>
                </div>
                <div class="tilt">
                    <div class="bucket">
                        <a id="step4">Step4</a>
                        <p id='steps'>Done</p>
                        <p>Friends receive sms or call "Hey guys,house party tonight at 10"</p>
                    </div>
                </div>
                <div class="savebutton_wrap">
                    <a class="savebutton_bRight slidebttn" id="savebutton_bRight" >Save</a>       
                </div>
            </section>
        </div>	
        <footer>
            <p id="copyright">&copy; Copyright Alex - Bekhzod - Rachel</p>
            <p id="back-top"><a>Back to top</a></p>
        </footer>
        
    </body>
</html>






