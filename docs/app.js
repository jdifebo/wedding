$('body').scrollspy({ target: '#navbar-example' })

var urls;

if (window.location.host === "poe2017.us") {
    var baseUrl = "https://poe2017.us/api/";
    urls = {
        heartbeat : baseUrl,
        getCode : code => "https://poe2017.us/api/code/" + code,
        postResponse : "https://poe2017.us/api/code/"
    }
}
else {
    var baseUrl = "http://localhost:8080/";
    urls = {
        heartbeat : baseUrl,
        getCode : code => "http://localhost:8080/code/" + code,
        postResponse : "http://localhost:8080/code/"
    }
}

$('#code-button').click(function() {
    displaySpinner();
    $.ajax({
        type: 'GET',
        url: urls.getCode(document.getElementById("secret-code").value),
    })
    .done(function(data){
        group = data;
        displayRsvpForm(group);
        $("#guest0").change(function(){
            if (document.getElementById("guest0").value === "false"){
                for (var i = 1; i < group.guests.length; i++){
                    if (group.guests[i].plusOne === true){
                        document.getElementById("plusonename" + i).value = "";
                        document.getElementById("guest" + i).value = "false";
                        document.getElementById("plusonename" + i).disabled = true;
                        document.getElementById("guest" + i).disabled = true;
                    }
                }
            }
            else if (document.getElementById("guest0").value === "true"){
                for (var i = 1; i < group.guests.length; i++){
                    if (group.guests[i].plusOne === true){
                        document.getElementById("plusonename" + i).disabled = false;
                        document.getElementById("guest" + i).disabled = false;
                    }
                }
            }
        })
    })
    .fail(function(){
        var html = `
          <div class="alert alert-danger" role="alert">
            We couldn't find that code!
          </div>
        `
        document.getElementById("rsvp-form").innerHTML = html;
    });
});

function displayRsvpForm(group) {
    function displaySingleGuest(guest, index){
        var html = `
                <div id="error` + index + `" class="alert alert-danger" role="alert" style="display: none;">
                    <strong>Oops!</strong> You must choose a response for each guest!
                </div>`
        if (guest.plusOne) {
            html += `
                <div id="plusone-error` + index + `" class="alert alert-danger" role="alert" style="display: none;">
                    <strong>Oh no!</strong> You must enter a name for a guest that is attending!
                </div>
                <div id="plusone-without-primary-error` + index + `" class="alert alert-danger" role="alert" style="display: none;">
                    <strong>Oh no!</strong> You can't have a Plus One without the primary guest attending!
                </div>
                <div id="dropdown-parent` + index + `" class="form-group">
                    <label for="guest` + index + `" class="form-control-label">Guest:</label>
                    <div class="row">
                        <div class="col-xs-6">
                            <input type="text" placeholder="Guest Name" class="form-control" id="plusonename` + index + `">
                        </div>
                        <div class="col-xs-6">
                            <select class="form-control form-control-danger" id="guest` + index + `">
                                <option></option>
                                <option value="true">Accept</option>
                                <option value="false">Decline</option>
                            </select>
                        </div>
                    </div>
                </div>
                `
        }
        else {
            html += `
                <div id="dropdown-parent` + index + `" class="form-group">
                    <label for="guest` + index + `" class="form-control-label">` + guest.name + `:</label>
                    <select class="form-control form-control-danger" id="guest` + index + `">
                    <option></option>
                    <option value="true">Accept</option>
                    <option value="false">Decline</option>
                    </select>
                </div>
                `
        }
        return html;
    }

    var html = `
          <p class="lead">RSVP for: ` + group.groupName + `</p>
          ` + group.guests.map(displaySingleGuest).join("") + `
          <div id="email-error" class="alert alert-danger" role="alert" style="display: none;">
            <strong>Oh no!</strong> That email doesn't appear to be valid!.
          </div>
          <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Optional">
            <small id="emailHelp" class="form-text text-muted">We'll spam your email with all of our selfies.</small>
          </div>
          <div class="form-group">
            <label for="dietary-restrictions">Dietary Restrictions:</label>
            <textarea class="form-control" id="dietary-restrictions" rows="1"></textarea>
          </div>
          <div class="form-group">
            <label for="comments">Other Comments:</label>
            <textarea class="form-control" id="comments" rows="3"></textarea>
          </div>
          
          <button id="submit" type="button" class="btn btn-lg btn-block btn-primary">Submit RSVP</button>`
    
    document.getElementById("rsvp-form").innerHTML = html;

    $('#submit').click(function() {
        if (validate()){
            submit();
        }
    });
}

var group = {}

function validate(){
    var valid = true;
    for (var i = 0; i < group.guests.length; i++){
        if (document.getElementById("guest" + i).value === ""){
            valid = false;
            document.getElementById("error" + i).style.display = "";
            document.getElementById("dropdown-parent" + i).className = "form-group has-danger";
            if (group.guests[i].plusOne) {
                document.getElementById("plusone-error" + i).style.display = "none";
                document.getElementById("plusone-without-primary-error" + i).style.display = "none";
            }
        }
        // Deal with plus one validation -- a name is required iff dropdown chooses accept
        else if (group.guests[i].plusOne && document.getElementById("plusonename" + i).value === "" && document.getElementById("guest" + i).value === "true"){
            valid = false;
            document.getElementById("error" + i).style.display = "none";
            document.getElementById("plusone-error" + i).style.display = "";
            document.getElementById("plusone-without-primary-error" + i).style.display = "none";
            document.getElementById("dropdown-parent" + i).className = "form-group has-danger";
        }        
        else if (group.guests[i].plusOne && document.getElementById("guest" + i).value === "true" && document.getElementById("guest0").value === "false" ){
            valid = false;
            document.getElementById("error" + i).style.display = "none";
            document.getElementById("plusone-error" + i).style.display = "none";
            document.getElementById("plusone-without-primary-error" + i).style.display = "";
            document.getElementById("dropdown-parent" + i).className = "form-group has-danger";
        }
        else {
            document.getElementById("dropdown-parent" + i).className = "form-group";
            document.getElementById("error" + i).style.display = "none";
            if (group.guests[i].plusOne) {
                document.getElementById("plusone-error" + i).style.display = "none";
                document.getElementById("plusone-without-primary-error" + i).style.display = "none";
            }
        }

    }
    emailRegexp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    if (document.getElementById("email").value !== "" && emailRegexp.test(document.getElementById("email").value) === false){
        valid = false;
        document.getElementById("email-error").style.display = "";
    }
    else {
        document.getElementById("email-error").style.display = "none";
    }
    return valid;
}

function submit(){
    var guestResponses = {};

    for (var i = 0; i < group.guests.length; i++){
        var plusOneName;
        if (group.guests[i].plusOne){
            plusOneName = document.getElementById("plusonename" + i).value;
        }
        guestResponses[group.guests[i].id] = {
            attending: document.getElementById("guest" + i).value == "true",
            plusOneName: plusOneName
        };
    }

    var email = document.getElementById("email").value;
    var dietaryRestrictions = document.getElementById("dietary-restrictions").value;
    var comments = document.getElementById("comments").value;

    var payload = {
        code : group.code,
        guestResponses : guestResponses,
        email : email,
        dietaryRestrictions : dietaryRestrictions,
        comments : comments
    };
    console.log(JSON.stringify(payload));
    displaySpinner();
    $.ajax({
        type: 'POST',
        url: urls.postResponse,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(payload),
    })
    .done(success)
    .fail(error);
}

function success(data, statusText, jqXHR) {
    if (jqXHR.status == 200){
        displayThankYou(group.groupName)
    }
    else if (jqXHR.status == 500) {
        alert("Something went wrong!  Call Paige or Joe and tell them that their website is broken!");
    }
    else {
        alert("Something went wrong!  Call Paige or Joe and tell them that their website is broken!");
    }
}


function error(jqXHR, textStatus, errorThrown) {
    alert("Something went wrong!  Call Paige or Joe and tell them that their website is broken!");
}

function displayThankYou(groupName) {
    var html = `
          <div class="alert alert-success" role="alert">
            Thank you, ` + groupName + `!
          </div>
    `
    document.getElementById("rsvp-form").innerHTML = html;

}

function displaySpinner() {
    var html = `
        <div class="spinner">
            <div class="rect1"></div>
            <div class="rect2"></div>
            <div class="rect3"></div>
            <div class="rect4"></div>
            <div class="rect5"></div>
        </div>
    `
    document.getElementById("rsvp-form").innerHTML = html;
}

function pingServer() {
    $.ajax({
        type: 'GET',
        url: urls.heartbeat,
    })
    .done(function(data){
        $("#initial-spinner").hide();
        $("#rsvp-section").show();
    })
    .fail(function(){
        $("#initial-spinner").hide();
        $("#server-error").show();
    });
}

pingServer();

$('#secret-code').keypress(function(e){
    if(e.keyCode==13)
    $('#code-button').click();
});