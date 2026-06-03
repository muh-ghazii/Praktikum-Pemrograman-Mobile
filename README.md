# 📱 Praktikum Pemrograman Mobile

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

Repository ini berisi kumpulan laporan dan project praktikum mata kuliah **Pemrograman Mobile** yang dikerjakan menggunakan **Kotlin** dengan pendekatan **XML** dan **Jetpack Compose**.

---

## Identitas Mahasiswa

| Keterangan | Detail |
|---|---|
| **Nama** | Muhammad Ghazi Rakhmadi |
| **GitHub** | [@muh-ghazii](https://github.com/muh-ghazii) |
| **Mata Kuliah** | Pemrograman Mobile |

---

## Struktur Repository

```
Praktikum-Pemrograman-Mobile/
├── Modul 1/    # Pengenalan Android Studio & Kotlin
├── Modul 2/    # Layout & UI Components
├── Modul 3/    # RecyclerView & Navigation
├── Modul 4/    # ViewModel & Debugging
└── Modul 5/    # Connect to the Internet
```

---

## Daftar Modul

### Modul 1 — Pengenalan Android Studio & Kotlin
Pengenalan dasar pengembangan aplikasi Android menggunakan Android Studio dan bahasa pemrograman Kotlin.

---

### Modul 2 — Layout & UI Components
Implementasi berbagai komponen UI Android menggunakan XML Layout dan pendekatan Jetpack Compose.

---

### Modul 3 — RecyclerView & Navigation
Membangun aplikasi **FilmApp** yang menampilkan daftar film menggunakan:
- **XML**: RecyclerView + ViewBinding + Navigation Component
- **Compose**: LazyColumn + LazyRow + Navigation Compose

---

### Modul 4 — ViewModel & Debugging
Melanjutkan aplikasi FilmApp dengan menambahkan:
- **ViewModel** untuk menyimpan dan mengelola data
- **ViewModelFactory** dengan parameter bertipe String
- **StateFlow** untuk mengelola event onClick dan data list
- **Timber** untuk logging event aplikasi
- **Debugger** Android Studio (Breakpoint, Step Into, Step Over, Step Out)

---

### Modul 5 — Connect to the Internet
Melanjutkan aplikasi FilmApp dengan koneksi ke internet menggunakan:
- **Retrofit** sebagai networking library
- **KotlinX Serialization** untuk parsing JSON
- **TMDB API** sebagai sumber data film
- **Room Database** untuk penyimpanan data lokal
- **SharedPreferences** untuk menyimpan pengaturan aplikasi
- **Caching Strategy** Cache-First with TTL untuk efisiensi data
- **Coil** untuk image loading dari URL

---

## Tech Stack

| Teknologi | Kegunaan |
|---|---|
| Kotlin | Bahasa pemrograman utama |
| Jetpack Compose | Modern UI toolkit |
| XML Layout | Traditional UI approach |
| Navigation Component | Navigasi antar halaman |
| ViewModel + StateFlow | Manajemen state & data |
| Retrofit | HTTP networking |
| KotlinX Serialization | JSON parsing |
| Room Database | Local database |
| SharedPreferences | Penyimpanan data ringan |
| Coil | Image loading |
| Timber | Logging library |
| TMDB API | Sumber data film |

---

## Arsitektur

Project menggunakan arsitektur **MVVM (Model-View-ViewModel)** dengan pola **Repository** sebagai single source of truth.

```
UI (Screen/Fragment)
    ↕
ViewModel + StateFlow
    ↕
Repository (Single Source of Truth)
    ↕              ↕
Remote API      Local Database
(Retrofit)      (Room)
```

---

## Cara Menjalankan Project

1. Clone repository ini
```bash
git clone https://github.com/muh-ghazii/Praktikum-Pemrograman-Mobile.git
```

2. Buka project di **Android Studio**

3. Untuk Modul 5, tambahkan TMDB API Key di `RetrofitClient.kt`:
```kotlin
const val TMDB_API_KEY = "API_KEY_KAMU"
```

4. Jalankan aplikasi di emulator atau perangkat fisik

---
