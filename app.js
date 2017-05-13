$('body').scrollspy({ target: '#navbar-example' })

$('#code-button').click(function() {
    $.get("http://localhost:8081/code/" + document.getElementById("secret-code").value, function(data){
        guests = data;
        displayRsvpForm(guests);
    });
});

function displayRsvpForm(guests) {
    function displaySingleGuest(name, index){
        return `
          <div id="error` + index + `" class="alert alert-danger" role="alert" style="display: none;">
            <strong>Oops!</strong> You must choose a response for each guest!.
          </div>
          <div id="dropdown-parent` + index + `" class="form-group">
            <label for="guest` + index + `" class="form-control-label">` + name + `:</label>
            <select class="form-control form-control-danger" id="guest` + index + `">
              <option></option>
              <option value="true">Accept</option>
              <option value="false">Decline</option>
            </select>
          </div>
        `
    }

    var html = `
          <p class="lead">RSVP for: ` + guests.groupName + `</p>
          ` + guests.names.map(displaySingleGuest).join("") + `
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

var guests = {}

function validate(){
    var valid = true;
    for (var i = 0; i < guests.names.length; i++){
        if (document.getElementById("guest" + i).value === ""){
            valid = false;
            document.getElementById("error" + i).style.display = "";
            document.getElementById("dropdown-parent" + i).className = "form-group has-danger";
        }
        else {
            document.getElementById("error" + i).style.display = "none";
            document.getElementById("dropdown-parent" + i).className = "form-group";
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
    var attending = {};

    for (var i = 0; i < guests.names.length; i++){
        attending[guests.names[i]] = document.getElementById("guest" + i).value == "true";
    }

    var email = document.getElementById("email").value;
    var dietaryRestrictions = document.getElementById("dietary-restrictions").value;
    var comments = document.getElementById("comments").value;

    var payload = {
        code : guests.code,
        attending : attending,
        email : email,
        dietaryRestrictions : dietaryRestrictions,
        comments : comments
    };
    console.log(JSON.stringify(payload));
    $.ajax({
        type: 'POST',
        url: "http://localhost:8081/code/",
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify(payload),
        success: success
    });
}

function success() {
    alert("Success!");
}


function failure() {
    alert("Failure!");
}