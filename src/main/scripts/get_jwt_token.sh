#!/bin/zsh

# echo $CLIENT_ID
# echo $CLIENT_SECRET
# echo $CLIENT_USERNAME
# echo $CLIENT_PASSWORD
# echo ''

curl -s $CLIENT_ID:$CLIENT_SECRET@$AUTHWEB_URL/oauth/token  \
-d grant_type=password \
-d username=$CLIENT_USERNAME \
-d password=$CLIENT_PASSWORD
