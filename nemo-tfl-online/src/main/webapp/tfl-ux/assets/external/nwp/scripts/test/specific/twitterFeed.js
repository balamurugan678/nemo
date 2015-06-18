(function (o) {
    "use strict";
    o.initialize = function () {
        o.twitterPull();
    };

    o.twitterPull = function () {
        $(window).load(function () {
            //getting the feed to show
            var twitterParent = $("#twitter").parent()
            var whichFeed = twitterParent.attr("data-feed-name");
            var numTweets;
            // check if it is a single tweet, if so only show one tweet otherwise show 3
            var classes = twitterParent.attr("class");
            var classesSplit = classes.split(" ")
            var numClasses = classesSplit.length;


            for (i = 0; i < numClasses; i++) {

                if (classesSplit[i] == "single-tweet") {
                    numTweets = 1;
                }
                else {
                    numTweets = 3;
                }

            }


            //if module classes contains single-tweet
            //      var numberOfTweets = "1";
            //  else
            //     var numberOfTweets = "3";

            $('#twitter').jtwt({
                username: whichFeed,
                count: numTweets, // The number of displayed tweets.
                convert_links: true, //Converts hashtags to links
                image_size: 0, // The size of your avatar.
                loader_text: 'Loading tweets', // loading text
                no_result: 'No tweets found' // no results text
            });
        });
    }


})(window.tfl.twitterFeed = window.tfl.twitterFeed || {});