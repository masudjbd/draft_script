#!/usr/bin/env bash


for i in {1..120}
do
   curl -D- -u admin:admin -X POST --data '{"fields":{"project":{"key":"SAM"},"summary":"Summary sample.","description":"Desc sample.","issuetype":{"name":"Test"}}}' -H "Content-Type: application/json" http://localhost:2990/jira/rest/api/2/issue
done
