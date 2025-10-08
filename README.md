# Room DB 연습하다가 살을 붙힌 메모앱입니다.

## 사용한 기술 스택들
```Kotlin```, ```MVVM```, ```ViewModel```, ```Room Database```, ```LiveData```, ```ListAdapter```, ```DiffUtil```, ```ItemTouchHelper```, ```JUnit4```, ```Espresso```, ```Firebase```

## 주요 기능 설명
### 1️. 메모 저장 기능

<div align="center">
<img src="https://github.com/user-attachments/assets/755c22f3-2a92-4bd9-a8cd-fc199a7fd1f6" width="300" height="600">
</div>


* 기기 내부에 있는 Room Database를 이용하여 메모를 작성 및 저장하는 기능을 구현하였습니다.

### 2. 저장된 메모 수정 기능

<div align="center">
<img src="https://github.com/user-attachments/assets/35091eb5-3190-4ab0-b8a1-b420fb9e436a" width="300" height="600">
</div>

* 저장된 메모를 수정하는 기능을 구현하였습니다.
* 수정 시 메모의 작성일이 수정된 날짜로 변경되고 수정 즉시 Room DataBase에 반영됩니다.
* 실시간 데이터 관찰: 메모 조회 Fragment(DetailMemoFragment)는 ViewModel의 LiveData를 구독(observe)하여 현재 보고 있는 메모의 내용 변경을 실시간으로 감지합니다.
* 자동 UI 업데이트: 사용자가 메모를 수정한 후 데이터가 Room DB에 반영되면, LiveData는 이 변경을 감지하고 새로운 메모 데이터를 발행(emit)합니다. Fragment의 Observer는 수정된 데이터를 전달받아, 별도의 새로고침 코드 없이도 제목, 내용, 수정된 날짜 등의 UI를 즉시 갱신합니다.


### 3. 저장된 메모 제거 기능

<div align="center">
<img src="https://github.com/user-attachments/assets/1d554264-ca38-407c-8a7d-29ec5c339b42" width="300" height="600">
</div>

* 저장된 메모를 제거하는 기능을 구현하였습니다.
* 제거 시 해당 메모가 즉시 Room DataBase에서 제거되고 UI에 반영됩니다.
* 실시간 데이터 관찰: 메인화면(MainActivity)은 ViewModel의 LiveData를 구독(observe)하여 메모 목록의 변경을 실시간으로 감지합니다.
* 자동 UI 업데이트: Room DB에서 데이터가 삭제되면 LiveData가 새로운 목록을 발행(emit)하고, Observer가 이를 감지합니다. ListAdapter의 submitList에 새 목록이 전달되면 DiffUtil이 변경사항을 계산하여 UI를 효율적으로 갱신합니다.
  

<div align="center">
<img src="https://github.com/user-attachments/assets/4b1f0600-a52b-4f02-a068-d45257a428f4" width="300" height="600">
</div>

* ItemTouchHelper를 도입하여, 사용자가 메인 화면의 RecyclerView에서 메모를 좌로 스와이프하는 직관적인 제스처로 아이템을 제거하는 기능을 구현하였습니다.

### 4. 외부db에 저장된 메모 로드기능 및 기기에 저장된 메모 외부db에 보관 기능

<div align="center">
<img src="https://github.com/user-attachments/assets/0bbd63d3-21e8-44f7-8c54-162381140bdd" width="300" height="600">
</div>
<div align="center">
  <img width="1059" height="580" alt="image" src="https://github.com/user-attachments/assets/09abb548-02ef-40e0-88e6-0cbeaa460e1a" />
  ▲Firebase에 저장된 메모 목록들
</div>




* Firebase를 활용하여 저장된 메모를 불러오는 기능 및 기기에 저장된 메모를 Firebase로 저장하는 기능을 구현하였습니다.




***
### 추후 도입 예정인 것들

1. 정렬기능 - 메인화면에서 순번, 제목, 작성일 클릭시 정렬되게
2. 페이징 기능 - 메모가 많아질 경우 로드하는데 오래걸리므로 페이징을 통해 처리
3. compose ui로 레펙토링
4. 카톡 로그인(?) - 필요하진 않지만 그냥 연습용
