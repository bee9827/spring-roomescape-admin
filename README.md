### 요구 사항
- [ ] 정상적으로 동작하지 않는 기능 수정

### 예외 사항

dto
- [ ] 시간 생성 시 시작 시간에 유효하지 않은 값이 입력되었을 때
- [ ] 예약 생성 시 예약자명, 날짜, 시간에 유효하지 않은 값이 입력 되었을 때

service
- [ ] 특정 시간에 대한 예약이 존재하는데, 그 시간을 삭제하려 할 때

### 정책
domain
- [ ] 지나간 날짜와 시간에 대한 예약 생성은 불가능하다.
- [ ] 중복 예약은 불가능하다.

TODO:
- 각 도메인별 Test