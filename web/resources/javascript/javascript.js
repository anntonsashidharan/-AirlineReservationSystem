/**
 * Created by JJ on 5/15/16.
 */


/**
 *
 * @param type
 * @constructor
 */
function DateTimeDifference (days,hours,minutes,seconds) {
    this.days = days;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
}


function getMonthMMM(month){
    if(month==0){
        return "JAN";
    }else if (month==1){
        return "FEB";
    }else if (month==2){
        return "MAR";
    }else if (month==3){
        return "APR";
    }else if (month==4){
        return "MAY";
    }else if (month==5){
        return "JUN";
    }else if (month==6){
        return "JUL";
    }else if (month==7){
        return "AUG";
    }else if (month==8){
        return "SEP";
    }else if (month==9){
        return "OCT";
    }else if (month==10){
        return "NOV";
    }else if (month==11){
        return "DEC";
    }
}


function formatDateMMDDYYYY(date){
    var myDate = new Date(date);
    var month = myDate.getMonth() + 1 + "";
    var day = myDate.getDate() + "";
    var year = myDate.getFullYear() + "";
    if(month.length==1){
        month = "0" + month;
    }
    if(day.length==1){
        day = "0" + day;
    }
    return month + "/" + day + "/" + year;
}

/*
calculate difference between date and time
combination of date1 and time1 should less than combination of date2 and time2
 */
function getTimeDifference(date1,time1,date2,time2){
    var datetime1 = new Date(date1.getFullYear(), date1.getMonth(), date1.getDate(),
        time1.getHours(), time1.getMinutes(), time1.getSeconds());
    var datetime2 = new Date(date2.getFullYear(), date2.getMonth(), date2.getDate(),
        time2.getHours(), time2.getMinutes(), time2.getSeconds());
    var difference = datetime2-datetime1;

    if(difference<0){
        difference=-difference;
    }

    difference = Math.floor(difference/1000);
    var seconds = difference%60;

    difference = Math.floor(difference/60);
    var minutes = difference%60;

    difference = Math.floor(difference/60);
    var hours = difference%24;

    difference = Math.floor(difference/24);
    var days = difference;

    var dateTimeDifference = new DateTimeDifference(days,hours,minutes,seconds);
    return dateTimeDifference;
}

function format24hr(minuites){
    var numOfMinutes = parseInt(parseInt(minuites) % 60, 10);
    var numOfHours = parseInt(parseInt(minuites) / 60 % 24, 10);
    var time = ("00"+numOfHours).slice(-2) + ":" + ("00"+numOfMinutes).slice(-2);
    return time;
}