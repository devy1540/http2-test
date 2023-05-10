# 🟥 Spring Boot Http2-test

spring boot 와 kubernetes cert-manager를 통해 http2 활성화 테스트를 진행하는 서버

# 🟧 프로젝트 소개
kubernetes의 cert-manager를 통해 self-signed 인증서를 발급받아 서버에 적용하여 https / http2를 활성화 할 수 있도록 한다.

# 🟨 주의사항
인증서 파일은 git이나 외부에 공공연하게 노출되지 않도록 주의하여 사용한다.

# 🟩 개발환경
- Spring Boot 2.7.11
- Kubernetes 1.25 (for Docker Desktop)
- Cert Manager (with helm)
  - https://cert-manager.io/docs/installation/helm/

# 🟦 사전 준비
프로젝트 yaml폴더에 `clusterIssuer.yaml`, `certificate.yaml`이 구성되어있다.

1. cert-manager를 설치할 수 있도록 한다.
```bash
helm install cert-manager -n cert-manager \
--create-namespace --set installCRDs=true
```

2. clusterIssuer를 생성한다.
```bash
kubectl apply -f clusterIssuer.yaml
```
3. 인증서를 생성한다.
```bash
kubectl apply -f certificate.yaml
```

    
# 🟪 사용
## 로컬 테스트
1. secrets에 있는 인증서를 파일로 만든다.
```bash
kubectl get secrets selfsigned-cert-tls -o jsonpath='{.data.tls\.crt}' | base64 --decode >> tls.crt
kubectl get secrets selfsigned-cert-tls -o jsonpath='{.data.tls\.key}' | base64 --decode >> tls.key
```
2. src/main/resources 또는 classpath 영역에 인증서 파일을 넣어주도록 한다.
   1. 해당 프로젝트에서는 `tls.key`, `tls.crt` 이름으로 작성하여 사용한다.
   2. pem파일 또는 다른 형식의 파일을 이용할 경우 알맞게 수정 후 사용하면 된다.
3. 프로젝트를 빌드하여 정상적으로 실행되는지 확인한다.
4. api 호출 시 protocol이 http2인지 확인한다.


## Kubernetes 환경 테스트
프로젝트의 yaml 폴더에 `deployment.yaml`, `service.yaml` 구성되어있다.  

1. pod 실행 시 crt, key 파일을 바라볼 수 있도록 설정을 해준다.
```yaml
env:
  - name: SERVER_SSL_CERTIFICATE 
    value: /etc/secret-volume/tls.crt
  - name: SERVER_SSL_CERTIFICATE-PRIVATE-KEY
    value: /etc/secret-volume/tls.key
```

2. volumeMounts 설정을 해준다
```yaml
volumeMounts:
  - name: tls-secret-volume
    mountPath: /etc/secret-volume
```

3. volumes 설정을 통해 secrets에 저장된 crt와 key파일을 가져온다.
```yaml
volumes:
  - name: tls-secret-volume
    secret:
      secretName: selfsigned-cert-tls
```

4. pod 실행이 정상적으로 되는지 확인한다.
5. api 호출 시 protocol이 http2인지 확인한다.