# Getting started springboot 2.0 + jwt + oauth2

## keypair 만들기

```
keytool -genkeypair -alias jwt -keyalg RSA -keypass kpassword -keystore server.jks -storepass spassword
```

## public key 발급받기

```
keytool -list -rfc --keystore server.jks | openssl x509 -inform pem -pubkey
```

## request token

```
curl -u testapp:testkey http://localhost:80/oauth/token -d  "grant_type=password&username=tocker&password=dalgoo"
```

## access resource

```
curl -H "authorization: bearer [TOKEN]" http://localhost:80/api/user
```

