# Giggle-Server
Giggle Server Repository


# Branch Naming Convention
<PR컨벤션>/<Jira이슈번호>/<Github이슈번호> </br></br>
ex) PR컨벤션에 맞는 현재의 작업이 ‘Feat’ 이고, Jira이슈번호가 ‘IGS-123’이며, Github이슈번호가 ‘#31’일경우엔 브랜치 명을 ‘Feat/IGS-123/31’ 과 같이 작성합니다.

# Commit Convention
<Jira이슈번호> <커밋 타입>: <내용>

| Type | 설명 | 예시 |
| --- | --- | --- |
| fix | 버그, 오류 해결 | ex) IGS-123 fix: callback error |
| add | Feat 이외의 부수적인 코드 추가/ 라이브러리 추가/ 새로운 View나 Activity 생성 | ex) IGS-123 add: LoginActivity |
| feat | 새로운 기능 구현 | ex) IGS-123 feat: google login |
| del | 쓸모없는 코드 삭제 | ex) IGS-123 del: unnecessary import package |
| remove | 파일 삭제 | ex) IGS-123 remove: 중복 파일 삭제 |
| refactor | 내부 로직은 변경 하지 않고 기존의 코드 개선하는 리팩토링 시, 세미콜론 줄바꿈 포함 | ex) IGS-123 refactor: MVP architecture to MVVM |
| chore | 그 이외의 잡일/ 버전 코드 수정, 패키지 구조 변경, 파일 이동, 가독성이나 변수명, reformat 등 | ex) IGS-123 chore: delete unnecessary import package |
| comment | 필요한 주석 추가 및 변경 | ex) IGS-123 comment: 메인 뷰컨 주석 추가 |
| docs | README나 wiki 등 내용 추가 및 변경 | ex) IGS-123 docs: README 내용 추가 |
| test | 테스트 코드 추가 | ex) IGS-123 test: 로그인 토큰 테스트 코드 추가 |
