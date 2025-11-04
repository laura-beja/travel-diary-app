\---

\# 🧭 Travel Diary

\*A Kotlin Android App built with Jetpack Compose\*

\#\# 📖 Overview

\*\*Travel Diary\*\* is a mobile application developed in \*\*Kotlin\*\* using \*\*Jetpack Compose\*\*.  
It allows users to \*\*record, view, search, edit, and delete\*\* their travel experiences.  
The app demonstrates lifecycle-aware state management, Compose navigation, and persistent data handling using \*\*Room Database\*\*.

\---

\#\# 🧩 App Architecture

The app follows \*\*MVVM (Model–View–ViewModel)\*\* architecture:

 \*\*UI Layer\*\*    
Displays data using composables and responds to user actions.  
Tech used: Jetpack Compose, Scaffold, LazyColumn, Material Design Components 

\*\*ViewModel Layer\*\*  
Holds UI state, handles navigation events, and manages coroutines.  
Tech used: \`ViewModel\`, \`MutableStateFlow\`, \`viewModelScope\`

\*\*Data Layer\*\*   
Stores and retrieves travel logs persistently.  
Tech used: Room Database, DAO, Repository pattern 

\---

\#\# 🗺️ App Features

\#\#\# 🏠 Screen 1 – Home / Log List

\* Displays all saved travel logs using a \*\*LazyColumn\*\*.  
\* Shows only the \*\*Title\*\* of each log.  
\* \*\*FAB\*\* opens the “Create Log” screen.  
\* Includes \*\*Bottom Navigation\*\* with tabs: \*Home\* and \*Search\*.  
\* Uses a \*\*ViewModel\*\* to manage the list and UI state.

\#\#\# 📋 Screen 2 – Travel Log Details

\* Displays:

  \* Title  
  \* Image (loaded from gallery using \*\*Coil\*\*)  
  \* Location (String)  
  \* Start Date & End Date  
  \* Duration (calculated automatically as “X days Y nights”)  
  \* Participants (list of Strings)  
  \* Description text  
\* Includes \*\*Edit\*\*, \*\*Delete\*\*, and \*\*Back\*\* buttons.  
\* Edits and deletions persist to the Room Database.

\#\#\# ✏️ Screen 3 – Create / Edit Log

\* Provides input fields for all travel details.  
\* Allows the user to \*\*upload an image from the device gallery\*\*.  
\* Includes \*\*Save\*\* and \*\*Back\*\* buttons.  
\* Saves new or updated records to Room via coroutine on \`Dispatchers.IO\`.

\#\#\# 🔍 Screen 4 – Search

\* Provides a search bar to filter existing travel logs by title or location.  
\* Navigated from the Bottom Navigation.  
\* Uses ViewModel state and Compose TextField to perform real-time filtering.

\---

\#\# 🧠 Lifecycle & State Management

\* \*\*ViewModel\*\* stores all travel logs in a \`MutableStateFlow\<List\<TravelLog\>\>\`.  
\* \*\*rememberSaveable\*\* is used for form fields to survive configuration changes.  
\* \*\*LaunchedEffect\*\* recalculates trip duration when dates change.  
\* \*\*DisposableEffect\*\* handles cleanup of any listeners or observers.

\---

\#\# 💾 Data Persistence

\* \*\*Room Database\*\* stores travel logs with fields:

  \* \`id\`, \`title\`, \`imageUri\`, \`location\`, \`startDate\`, \`endDate\`, \`participants\`, \`description\`.  
\* DAO provides insert, update, delete, and query methods.  
\* Repository mediates between Room and ViewModel.  
\* All database operations run on \`Dispatchers.IO\` to keep the UI responsive.

\---

\#\# 🎨 UI Design

\* Implements \*\*Material Design 3\*\* guidelines with a consistent color scheme, typography, and elevation.  
\* Uses \*\*Scaffold\*\* to structure TopAppBar, BottomNavigation, FAB, and content.  
\* \*\*Animations\*\* such as fade and slide transitions use \`animate\*AsState\` and \`AnimatedVisibility\`.

\---

\#\# ⚙️ Technologies Used

\* \*\*Kotlin\*\* (1.9.x)  
\* \*\*Jetpack Compose BOM\*\* 2024.09+  
\* \*\*Android ViewModel\*\*  
\* \*\*Room Database\*\*  
\* \*\*Coil Compose 2.7.0\*\* (for gallery image loading)  
\* \*\*Coroutines / Dispatchers.IO\*\*  
\* \*\*Navigation Component\*\*  
\* \*\*Material Design 3\*\*

\---

\#\# 🧪 Testing & Validation

\* Verified recomposition and state persistence with \`rememberSaveable\`.  
\* Ensured lifecycle safety via \`viewModelScope\` and \`DisposableEffect\`.  
\* Tested CRUD operations in Room.  
\* Checked image loading and navigation transitions on emulator (API 34).

\---

\#\# 👩‍💻 Developer

\*\*Developed by:\*\* \*Laura Bejarano\*  
\*\*Course:\*\* Programming Mobile Devices  
\*\*Semester:\*\* Autumn 2025

