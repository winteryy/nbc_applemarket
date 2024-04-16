# 앱개발 숙련 주차 개인과제
## 사과마켓

### 주요 기능 실행 이미지
<div align="center">
<p align="center" width="100%">
    <image src = "./screenshots/play_001.gif" width="30%" >
</p>

#### 알림 기능
<br>

<p align="center" width="100%">
    <image src = "./screenshots/play_002.gif" width="30%" >
</p>

#### 좋아요 기능
<br>

<p align="center" width="100%">
    <image src = "./screenshots/play_003.gif" width="30%" >
</p>

#### 스크롤 상단 이동 기능(플로팅 버튼)
<br>

<p align="center" width="100%">
    <image src = "./screenshots/play_004.gif" width="30%" >
</p>

#### 삭제 기능
<br>

<p align="center" width="100%">
    <image src = "./screenshots/play_005.gif" width="30%" >
</p>

#### 종료 시 다이얼로그
<br>
</div>

### 요구사항
#### 메인 페이지 만들기
- [x]  디자인 및 화면 구성을 최대한 동일하게 해주세요. (사이즈 및 여백도 최대한 맞춰주세요.) ✨
- [x]  상품 데이터는 아래 dummy data 를 사용합니다. (더미 데이터는 자유롭게 추가 및 수정 가능)
- [x]  더미 데이터 : [이미지 링크](https://drive.google.com/file/d/1P5AnZI1N2AB7yNqwkgF-KxlUdDjkmrBu/view?usp=sharing),  [상품 리스트 링크](https://docs.google.com/spreadsheets/d/1m9VDxJ3Q7dLEjefnWBq4fCghtWIUFnpM/edit?usp=sharing&ouid=116688204055896164464&rtpof=true&sd=true)  (←링크 권한 없으면 [여기](https://drive.google.com/drive/folders/1ZYQIxmP8JAXpcxvQB3QekYZLYQiNlZqK?usp=sharing) 클릭)
- [x]  RecyclerViewer를 이용해 리스트 화면을 만들어주세요.
- [x]  상단 툴바를 제거하고 풀스크린 화면으로 세팅해주세요. 
(상태바(시간/배터리 표시하는 최상단바)는 남기고)
- [x]  상품 이미지는 모서리를 라운드 처리해주세요.
- [x]  상품 이름은 최대 두 줄이고, 그래도 넘어가면 뒷 부분에 …으로 처리해주세요.
- [x]  뒤로가기(BACK)버튼 클릭시 종료하시겠습니까? [확인][취소] 다이얼로그를 띄워주세요. (예시 비디오 참고)
- [x]  상단 종모양 아이콘을 누르면 Notification을 생성해 주세요. (예시 비디오 참고)
- [x]  상품 가격은 1000단위로 콤마(,) 처리해주세요.
- [x]  상품 아이템들 사이에 회색 라인을 추가해서 구분해주세요.
- [x]  상품 선택시 아래 상품 상세 페이지로 이동합니다.
- [x]  상품 상세페이지 이동시 intent로 객체를 전달합니다. (Parcelize 사용)
  
#### 상품 상세 페이지 만들기
- [x]  디자인 및 화면 구성을 최대한 동일하게 해주세요. (사이즈 및 여백도 최대한 맞춰주세요.) ✨
- [x]  메인화면에서 전달받은 데이터로 판매자, 주소, 아이템, 글내용, 가격등을 화면에 표시합니다.
- [x]  하단 가격표시 레이아웃을 제외하고 전체화면은 스크롤이 되어야합니다. (예시 비디오 참고)
- [x]  상단 < 버튼을 누르면 상세 화면은 종료되고 메인화면으로 돌아갑니다.

#### 선택 과제
- [x]  스크롤을 최상단으로 이동시키는 플로팅 버튼 기능 추가
- [x]  플로팅 버튼은 스크롤을 아래로 내릴 때 나타나며, 스크롤이 최상단일때 사라집니다.
- [x]  플로팅 버튼을 누르면 스크롤을 최상단으로 이동시킵니다.
- [x]  플로팅 버튼은 나타나고 사라질때 fade 효과가 있습니다.
- [x]  플로팅 버튼을 클릭하면(pressed) 아이콘 색이 변경됩니다.
- [x]  상품을 롱클릭 했을때 삭제 여부를 묻는 다이얼로그를 띄우고
- [x]  확인을 선택 시 해당 항목을 삭제하고 리스트를 업데이트한다.
- [x]  해당 상품이 삭제되었는지 확인!!
- [x]  상품 상세 화면에서 좋아요 선택시 아이콘 변경 및 Snackbar 메세지 표시
- [x]  메인 화면으로 돌아오면 해당 상품에 좋아요 표시 및 좋아요 카운트 +1
- [x]  상세 화면에서 좋아요 해제시 이전 상태로 되돌림

### What to
- 요구사항에 맞춰 UI 및 기능을 구현했습니다.
- `POST_NOTIFICATION`에 관한 권한 처리를 따로 해주었습니다.
- 앱 아이콘 또한 적절하게 변경했습니다.

### How to
#### data/DummyRepo.kt
- 더미 데이터는 `DummyRepo`를 통해 받아 오게 됩니다. 엄밀하게 아키텍처 레이어를 구분하진 않았지만, 아이템의 삭제나 업데이트 작업도 해당 `Object`에서 담당합니다.
- 데이터들을 되도록 불변 데이터로 처리할 수 있게끔 로직을 작성했습니다.
- 아이템 삭제 시에 `id` 값을 이용해 삭제하게 되는데, 해당 `id` 값을 가진 아이템이 존재하는지 이진 탐색을 통해 탐색합니다.(들고 있는 데이터 리스트는 `id`를 기준으로 정렬되어 있다고 상정)

#### model/Post.kt
- 주어진 더미 데이터를 참고해서 구성했습니다. 일반적인 케이스라면 이미지는 url 형태로 제공 받겠지만, 과제에서 이미지 파일 자체를 제공하고 있어 리소스 id값을 이용하도록 했습니다.
- `kotlin-parcelize` 플러그인을 추가하여, 간단한 방식으로 `Parcelable`을 구현했습니다.
- 좋아요 기능을 위해 `isFavorite` 프로퍼티를 추가했습니다.

#### util/Util.kt
- 앱 내에서 여러 번 이용되는 몇 가지 확장함수가 구현되어 있습니다.
- `Int` 타입을 가격 텍스트로 변환해주는 `Int.toPriceText()`와 제목과 내용 텍스트, 확인 버튼 눌렀을 시 동작할 콜백을 인자로 받아 `Dialog`를 생성하는 `Activity.makeDialog()`입니다.

#### MainActivity.kt
- 메인 페이지를 담당하는 `Activity`입니다.
- `DummyRepo`를 통해 받아온 데이터를 `RecyclerView`를 통해 보여주게 됩니다.
- `RecyclerView`에는 `addItemDecoration()`을 통해 구분선을 추가하고, `addOnScrollListener()`를 구현해 최상단으로 향하는 `FloatingButton`과 연계해 동작할 수 있도록 했습니다.<br>fade 애니메이션의 경우 `ObjectAnimator`로 간략하게 구현했습니다.
- 종료되는 상황에서 `Dialog`를 띄우는 기능은 `OnBackPressedCallBack`을 구현해 적용했습니다.
- `RecyclerView`의 아이템 클릭시, Parcelable한 Post 데이터를 그대로 `intent`로 전달합니다.<br>아이템 롱클릭시, 해당 아이템에 대해 `DummyRepo`에 삭제 요청을 보냅니다. 만약 삭제에 성공했다면 현재 보여주고 있는 데이터 리스트를 갱신합니다.
- 알림 기능의 경우, 권한의 적절한 획득을 위한 `registerForActivityResult`를 구현해 권한 요구 절차를 거치게 됩니다. 

#### ItemListAdapter.kt
- `ListAdpater`와 `DiffUtil`을 사용해 구현했습니다.
- 아이템을 바인드 할 때, 이미지를 load하는 부분들은 캐싱처리 이점을 생각해 `Coil` 라이브러리를 이용해 구현했습니다.
- `ItemTouchListener` 인터페이스를 만들어 인자로 주입 받고 있습니다. 해당 리스너를 이용해 각 아이템에 클릭 리스너와 롱클릭 리스너를 설정합니다.

#### DetailActivity.kt
- `MainActivity`로부터 전달 받은 `intent`에서 Post 데이터를 가져와 보여줍니다.
- `ScrollView`로 뷰의 일정 부분을 감싸, 스크롤을 구현했습니다.
- 받아온 Post 데이터의 초기 `isFavorite` 값으로 `favoriteState`를 초기화한 후, 좋아요 구현 로직에 이용했습니다.<br>종료하기 전, 이 `favoriteState` 값이 처음에 받아온 Post 데이터의 `isFavorite` 값과 달라졌을 때만 `DummyRepo`를 통해 아이템을 업데이트 해주고, 값이 달라졌음을 `MainActivity`에 intent result로 전달해 리스트를 갱신하도록 합니다.

### 기타
- 실제 서버를 이용하는 것이 아닌 클라이언트 차원에서 데이터를 유지하고 있고, 불변 자료형을 지향하고 있어(업데이트시, 선형 시간이 걸림) 좋아요가 눌릴 때마다 매번 업데이트 하는 것은 부하가 있을 것이라고 생각했습니다(데이터 수가 많아질 경우).<br>그래서 `DetailActivity`에서 뒤로 가는 시점에만 업데이트가 발생하도록 했는데, 이는 좋아요 버튼을 누른 후 뒤로 가기를 누르지 않고 앱이 비정상 종료됐을 때 상호 작용 결과가 반영되지 않는 취약점이 있습니다.<br>물론, 이번 과제는 런타임 중에만 데이터 변화를 유지하기 때문에 큰 의미는 없지만, 실제 서버를 이용하는 환경이었다면 매 좋아요 상호작용 발생 시마다 업데이트를 해주는게 이상적이라고 생각합니다.