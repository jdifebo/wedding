var urls;

if (window.location.host === "poe2017.us") {
    urls = {
        groups: "/api/admin/groups"
    }
}
else {
    urls = {
        // groups: "groups.json"
        groups: "http://localhost:8080/admin/groups"
    }
}

function calculateStatistics(groups) {
    let numberOfGroups = groups.length;
    let respondedGroups = groups.filter(group => group.responses.length > 0);
    let numberOfRespondedGroups = respondedGroups.length;

    let guests = groups.map(group => group.guests).reduce((acc, cur) => acc.concat(cur));
    let numberOfGuests = guests.length;
    let respondedGuests = guests.filter(guest => guest.responses.length > 0);
    let numberOfRespondedGuests = respondedGuests.length;

    let numberOfAccepts = respondedGuests.filter(guest => guest.responses[guest.responses.length - 1].attending).length;

    let html = `
    <p>Number of responded groups: ` + numberOfRespondedGroups + `/` + numberOfGroups + ` (` + (numberOfRespondedGroups / numberOfGroups * 100).toFixed(0) + `%)</p>
    <p>Number of responded guests: ` + numberOfRespondedGuests + `/` + numberOfGuests + ` (` + (numberOfRespondedGuests / numberOfGuests * 100).toFixed(0) + `%)</p>
    <p>Number of accepted guests: ` + numberOfAccepts + `/` + numberOfRespondedGuests + ` (` + (numberOfAccepts / numberOfRespondedGuests * 100).toFixed(0) + `%)</p>

    `
    $("#initial-spinner").hide();
    $("#statistics").show();
    $("#statistics").html(html);
}

function pingServer() {
    $.ajax({
        type: 'GET',
        url: urls.groups,
    })
        .done(calculateStatistics)
        .fail(function () {
            $("#initial-spinner").hide();
            $("#server-error").show();
        });
}

pingServer();
