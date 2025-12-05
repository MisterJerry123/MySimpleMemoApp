# SimpleMemo
> "복잡함은 빼고, 기록의 본질만 남기다."

## 📖 프로젝트 개요 (Project Overview)

**SimpleMemo**는 일상 속의 중요한 아이디어와 할 일을 빠르고 간편하게 기록할 수 있는 안드로이드 메모 애플리케이션입니다.
**Room Database**를 활용하여 데이터를 안전하게 로컬에 저장하며, **MVVM 아키텍처**를 기반으로 유지보수성과 확장성을 고려하여 개발되었습니다.

복잡한 기능 없이 직관적인 UI를 제공하여 누구나 쉽게 사용할 수 있습니다.

### 🎯 개발 목표
*   **간편한 기록**: 언제 어디서나 빠르게 메모를 작성하고 저장
*   **직관적인 UX**: 스와이프 삭제 등 익숙한 제스처를 통한 편리한 사용성
*   **안정성**: Room DB를 통한 확실한 데이터 영구 저장

---

## 🚀 핵심 기능 (Key Features)

### 1. 📝 메모 작성 및 수정 (Create & Update)
사용자는 제목과 내용을 입력하여 새로운 메모를 작성할 수 있습니다.
*   **실시간 저장**: 작성된 메모는 Room DB에 즉시 저장됩니다.
*   **수정 기능**: 기존 메모를 선택하여 내용을 자유롭게 수정할 수 있습니다.

### 2. 🗑️ 스와이프 삭제 (Swipe to Delete)
리스트에서 메모를 왼쪽으로 스와이프하여 간편하게 삭제할 수 있습니다.
*   **제스처 지원**: `ItemTouchHelper`를 활용하여 직관적인 삭제 경험을 제공합니다.
*   **즉각 반영**: 삭제 시 리스트에서 즉시 제거되며 DB에서도 동기화됩니다.

### 3. 📋 메모 리스트 (Memo List)
저장된 모든 메모를 최신순(또는 사용자 정의 순서)으로 한눈에 확인할 수 있습니다.
*   **RecyclerView**: 효율적인 리스트 렌더링을 지원합니다.
*   **LiveData**: 데이터 변경 시 UI가 자동으로 업데이트됩니다.

### 4. 📺 광고 통합 (AdMob Integration)
앱 내에 AdMob 배너 및 전면 광고가 통합되어 있습니다.
*   **수익화 모델**: Google AdMob SDK를 활용하여 광고를 표시합니다.

---

## 🛠️ 시스템 구성도 (System Architecture)

### Architecture Pattern: MVVM (Model-View-ViewModel)

*   **View (Activity/Fragment)**: UI를 담당하며, ViewModel의 데이터를 관찰(Observe)하여 화면을 갱신합니다.
*   **ViewModel (MemoSharedViewModel)**: UI 관련 데이터를 관리하고, Repository(DAO)와 통신하여 비즈니스 로직을 처리합니다.
*   **Model (Room Database)**:
    *   **Entity (Memo)**: 메모 데이터의 구조를 정의합니다.
    *   **DAO (MemoDao)**: 데이터베이스 접근을 위한 메서드(CRUD)를 제공합니다.

---

## 💻 기술 스택 (Tech Stack)

| Category | Technology |
| :--- | :--- |
| **Language** | Kotlin |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **UI Framework** | XML Layout, ViewBinding |
| **Local DB** | Android Jetpack Room |
| **Concurrency** | Coroutines, LiveData |
| **Ad Network** | Google AdMob |
| **Testing** | JUnit, Espresso |

---

## 📥 설치 및 실행 방법 (Installation & Setup)

Google Play Store에서 앱을 다운로드하여 바로 사용할 수 있습니다.

*   **다운로드 링크**: [SimpleMemo - Google Play Store](https://play.google.com/store/apps/details?id=com.misterjerry.simplememo)
<a href = "https://play.google.com/store/apps/details?id=com.misterjerry.simplememo">

<img width="1471" height="1059" alt="image" src="https://github.com/user-attachments/assets/3bdada76-7bb0-425d-9d5d-4bb5af41bde3" />

</a>

---

## 📱 사용 가이드 (Usage Guide)

### 메모 추가하기
1.  메인 화면 우측 하단의 `+` 버튼을 누릅니다.
2.  제목과 내용을 입력하고 저장 버튼을 누릅니다.

### 메모 수정하기
1.  리스트에서 수정하고 싶은 메모를 클릭합니다.
2.  내용을 수정한 후 수정 완료 버튼을 누릅니다.

### 메모 삭제하기
1.  리스트에서 삭제하고 싶은 메모를 **왼쪽으로 스와이프**합니다.
2.  메모가 즉시 삭제됩니다.

---
