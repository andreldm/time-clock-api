#!/bin/bash

USER=rick
PASS=morty

create () {
    curl -s -X POST \
        --user $USER:$PASS \
        -w "$1 - $2 : %{http_code}\n" \
        -o /dev/null \
        -H "Content-Type: application/json" \
        -H "Accept: application/json" \
        -d "{\"pis\": $1, \"time\": \"$2\"}" \
        http://localhost:8080/api/clockin/create
}

create '0' '2018-02-17T08:00:00'
create '0' '2018-02-17T12:00:00'
create '0' '2018-02-17T14:00:00'
create '0' '2018-02-17T18:00:00'

create '67334519471' '2018-02-18T08:00:00'
create '67334519471' '2018-02-18T12:00:00'
create '67334519471' '2018-02-18T14:00:00'
create '67334519471' '2018-02-18T18:00:00'

create '67334519471' '2018-02-19T08:00:00'
create '67334519471' '2018-02-19T18:00:00'

create '67334519471' '2018-02-19T16:00:00'
create '67334519471' '2018-02-19T23:32:55'

curl -s --user $USER:$PASS -H "Accept: application/json" http://localhost:8080/api/clockin/report?period=2018-02 | jq -C -r
