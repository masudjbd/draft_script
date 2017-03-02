#!/usr/bin/env bash


for i in {1..5000}
do
   curl -D- -u admin:password -X POST --data '{"fields":{"project":{"key":"SAM"},"summary":"Summary sample.","description":"Desc sample.","issuetype":{"name":"Test"}}}' -H "Content-Type: application/json" http://localhost:2999/jira/rest/api/2/issue
done
