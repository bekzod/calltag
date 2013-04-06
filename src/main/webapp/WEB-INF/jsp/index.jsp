<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Tweet Call</title>
    
    <link href="netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
    <link href="resources/css/homepage.css" rel="stylesheet" />

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <script src="netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>

    <script type="text/javascript">
        $(function() {

            $('.slidebttn').hover(
                    function() {
                        var $this = $(this);
                        var $slidelem = $this.prev();
                        $slidelem.stop().animate({'width': '325px'}, 300);
                        $slidelem.find('span').stop(true, true).fadeIn();
                        $this.addClass('button_c');
                    },
                    function() {
                        var $this = $(this);
                        var $slidelem = $this.prev();
                        $slidelem.stop().animate({'width': '95px'}, 200);
                        $slidelem.find('span').stop(true, true).fadeOut();
                        $this.removeClass('button_c');
                    }
            );
        });
    </script>

</head>

<body>
<div id="container">
            <header>
                <h1><img src="resources/images/twitter-bird.jpg" ></h1>
                <div id="title" ><img src="resources/images/title.png" ></div>
            </header>   
            <section id="feature">
                <a ><img src="resources/images/phonecall1.jpg" onmouseover="this.src = 'resources/images/phonecall.png'" onmouseout="this.src = 'resources/images/phonecall1.jpg'" alt="Girl phonecall" /></a>
                <div id="feature-info">
                    <p>Just use our custom hashtag and allow your friends to receive phone calls listening to your tweets.</p>
                    <p>  
                    <div class="button_wrap">
                            <a class="button_aRight" id="button_aRight"><span>Use your Twitter ID</span></a>
                            <a class="button_bRight slidebttn"
                            href=<%= ((String)request.getAttribute("twitter_url"))%>  id="button_bRight" >Sign In</a>
                    </div>
            </section>
            <section id="content">
                <div class="tilt">
                    <div class="bucket">
                        <a  id="step1">Step1</a>
                        <p id='steps'>Step 1</p>

                        <p>Compose a tweet that you want to send to your friends.</p>
                    </div>
                </div>
                <div class="tilt">
                    <div class="bucket">
                        <a id="step2">Step2</a>
                        <p id='steps'>Step 2</p>
                        <p>Choose the friends you want to receive a phone call and listen to your tweet.</p>
                    </div>
                </div>
                <div class="tilt">
                    <div class="bucket">
                        <a id="step3">Step3</a>
                        <p id='steps'>Step 3</p>
                        <p>Write $call or $text followed by their phone numbers and tweet your message.</p>
                    </div>
                </div>
                <div class="tilt">
                    <div class="bucket">
                        <a id="step4">Step4</a>
                        <p id='steps'>Step 4</p>
                        <p>Let your friends receive a phone call and listen to your tweet.</p>
                    </div>
                </div>
            </section>
        </div>  
        <footer>
            <p id="copyright">&copy; Copyright Alex - Bekhzod - Rachel</p>
        </footer>
</body>
</html>








