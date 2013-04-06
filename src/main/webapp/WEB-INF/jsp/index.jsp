<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Tweet Call</title>
    
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link href="resources/css/main.css" rel="stylesheet" />

    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
</head>

<body>
    <header>
        <div class="container">
            <div class="row">
                <div class="span12 pagination-centered">
                        <img src="resources/images/logo1.png"/>                    
                </div>
            </div>
        </div>
    </header>   
    
    <div class="container">
        <div class="row">
            <div class="span12 pagination-centered">        
                <div>
                    <h3><span class="pinkText">TweetCall</span> gives you ability to <span class="pinkText">#call</span> and <span class="pinkText">#text</span> your friends with tweet. <br/>To give it a try just sign in with your existing twitter account.</h3>
                </div>
            </div>
        </div>
            
        <div class="row">
            <div class="span12 pagination-centered">
                <a href="<%= ((String)request.getAttribute("twitter_url"))%>" class="btn btn-large btn-primary" >Sign in With Twitter</a>
            </div>
        </div> 
    </div>

    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="span12 pagination-centered">
                    <p>&copy; Copyright Alex - Bekhzod - Rachel</p>
                </div>    
            </div>
        </div>
    </footer>

   

    </body>
</html>