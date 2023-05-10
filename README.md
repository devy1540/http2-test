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

    
# 🟦 사용
## 로컬 테스트
1. src/main/resources 또는 classpath 영역에 인증서 파일을 넣어주도록 한다.
   1. 해당 프로젝트에서는 `tls.key`, `tls.crt` 이름으로 작성하여 사용한다.
   2. pem파일 또는 다른 형식의 파일을 이용할 경우 알맞게 수정 후 사용하면 된다.
2. 프로젝트를 빌드하여 정상적으로 실행되는지 확인한다.


## Docker / Kubernetes 환경 테스트
준비중
