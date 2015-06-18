(function (o) {
    //take data from data element in table and put in array 

    var dataset = [
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        [],
        []
    ];

    //insert in timings 
    var index = 0;
    $(".TDM-table table tbody tr").each(function () {
        var time = $(this).find(".slotTime").text();
        if (time != "") {
            dataset[index][0] = time;
            index = index + 1;
        }
    });
    //insert in busyness levels
    dataset[0][1] = $(".slotOne").attr("data-busyness");
    dataset[1][1] = $(".slotTwo").attr("data-busyness");
    dataset[2][1] = $(".slotThree").attr("data-busyness");
    dataset[3][1] = $(".slotFour").attr("data-busyness");
    dataset[4][1] = $(".slotFive").attr("data-busyness");
    dataset[5][1] = $(".slotSix").attr("data-busyness");
    dataset[6][1] = $(".slotSeven").attr("data-busyness");
    dataset[7][1] = $(".slotEight").attr("data-busyness");
    dataset[8][1] = $(".slotNine").attr("data-busyness");
    dataset[9][1] = $(".slotTen").attr("data-busyness");
    dataset[10][1] = $(".slotEleven").attr("data-busyness");
    dataset[11][1] = $(".slotTwelve").attr("data-busyness");

    var i = 0;
    var n = 0;
    var barId = "columnOne";
    var busiestBar = new Array();
    var busiestPercent = 0;
    var c = 0;
    for (i = 0; i < dataset.length; i++) {
        n = dataset[i][1];
        if (dataset[i][1] > busiestPercent) {
            busiestBar[c] = i;
            busiestPercent = dataset[i][1];
        } else if (dataset[i][1] == busiestPercent) {
            c++;
            busiestBar[c] = i;
        }
        barId = getBarId(i);
        if (dataset[i][1] < 45) {
            //green
            $("." + barId + " > .TDM-bar").css("height", n);
            $("." + barId).attr('data-content', dataset[i][0]);
        } else if (dataset[i][1] >= 45 && dataset[i][1] < 59) {
            //yellow
            $("." + barId + " > .TDM-bar").css({ "background-color": "#FFFF33", "height": n });
            $("." + barId).attr('data-content', dataset[i][0]);
        } else if (dataset[i][1] >= 59 && dataset[i][1] < 73) {
            //orange
            $("." + barId + " > .TDM-bar").css({ "background-color": "#FFCC33", "height": n });
            $("." + barId).attr('data-content', dataset[i][0]);
        } else if (dataset[i][1] >= 73 && dataset[i][1] < 87) {
            //red
            $("." + barId + " > .TDM-bar").css({ "background-color": "#FF3333", "height": n });
            $("." + barId).attr('data-content', dataset[i][0]);
        } else {
            //black
            $("." + barId + " > .TDM-bar").css({ "background-color": "#B40404", "height": n });
            $("." + barId).attr('data-content', dataset[i][0]);
        }
    }

    for (var j = 0; j < busiestBar.length; j++) {
        barId = getBarId(busiestBar[j]);

        var hatchBox = document.createElement("div");
        $("." + barId).append(hatchBox);
        $(hatchBox).addClass("hatch-box");
        //if previous bar is same height 
        if (busiestBar[j] == (busiestBar[j - 1] + 1)) {
            $(hatchBox).addClass("follow-hatch");
        }
        //if next bar is same height, make hatch smaller in width
        if (busiestBar[j] == (busiestBar[j + 1] - 1)) {
            $(hatchBox).addClass("connected-hatch");
        }
        //if in between two hatches
        if (busiestBar[j] == (busiestBar[j - 1] + 1) && busiestBar[j] == (busiestBar[j + 1] - 1)) {
            $(hatchBox).addClass("middle-hatch");
        }
        $("." + barId).css({ "z-index": "3" });
    }
    busiestBar = [];

    function getBarId(i) {
        switch (i) {
            case 0:
                return "columnOne";
                break;
            case 1:
                return "columnTwo";
                break;
            case 2:
                return "columnThree";
                break;
            case 3:
                return "columnFour";
                break;
            case 4:
                return "columnFive";
                break;
            case 5:
                return "columnSix";
                break;
            case 6:
                return "columnSeven";
                break;
            case 7:
                return "columnEight";
                break;
            case 8:
                return "columnNine";
                break;
            case 9:
                return "columnTen";
                break;
            case 10:
                return "columnEleven";
                break;
            case 11:
                return "columnTwelve";
                break;
        }
    }
}(window.tfl.timingRibbon = window.tfl.timingRibbon || {}));

