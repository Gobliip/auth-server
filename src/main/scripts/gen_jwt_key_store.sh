# export GOBLIIP_STOREPASS=batman16
# export GOBLIIP_KEYPASS=superman16
# export GOBLIIP_KEYALIAS=authServerKey

keytool -genkeypair -alias $GOBLIIP_KEYALIAS -keyalg RSA \
-dname "CN=Gobliip,OU=gobliip-auth,O=Gobliip,L=Guatemala City,S=Guatemala,C=GT" \
-keypass $GOBLIIP_KEYPASS -keystore ../resources/jwt.jks -storepass $GOBLIIP_STOREPASS