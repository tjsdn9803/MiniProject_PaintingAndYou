# 항해99 15기 미니프로젝트 14조 Paintings-And-You - BE

------
### Back-end Application Of 'Painting And You'

### 기술 스택 및 개발 환경

![ex_screenshot](https://tjsdn9803-cicd-bucket.s3.ap-northeast-2.amazonaws.com/images/20230720_172659.jpg)

- Java 17
- Spring Boot
- Spring Security
- MySQL
- JWT
- CORS

DovOps
- AWS EC2
- AWS RDS
- AWS S3
- AWS CodeDeploy

## 주요 기능

- CI/CD
- 회원가입
- 로그인
- 이미지와 함께 글 작성
- 작성된 글 조회
- 작성된 글 수정, 삭제

## 배포 과정

![ex_screenshot](https://tjsdn9803-cicd-bucket.s3.ap-northeast-2.amazonaws.com/images/20230720_171151.jpg)
개발이 완료되어 연결된 Github Repository의 master 브랜치에 push하게되면 Github Actions를 통해 자동으로 빌드 및 테스트를 진행하게 되고
정상적으로 통과된다면 작성된 파일을 압축시켜 클라우드 스토리지 서비스인 S3에 업로드 하고 CodeDeploy에게 배포 명령을 내리게 된다.

CodeDeploy는 S3에 있는 파일을 서버가 작동될 EC2로 옮기고 배포를 진행하여 EC2서버에서 빌드된 jar파일이 실행된다.

## 개발 과정

1. CRUD

    글 작성 시 title과 content를 입력하여 DB에 저장하고 조회, 수정, 삭제이 가능하도록 api를 구성하였습니다.
    
    그 후 S3를 이용한 이미지 업로드 기능 구현에 성공하여 이제 이미지 파일을 함께 업로드하고 조회, 수정, 삭제가 가능하도록 발전시켰습니다.


2. 로그인, 회원가입
    
    username, password, email을 입력하여 회원가입시 DB에 username, email, 암호화된 password를 저장하였고
    username, password를 이용하여 로그인 시 검증 절차를 걸친 뒤 JWT토큰을 발급
    
    
    



