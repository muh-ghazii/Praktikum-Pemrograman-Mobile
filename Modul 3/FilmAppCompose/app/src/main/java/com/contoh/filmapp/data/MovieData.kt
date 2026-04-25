package com.contoh.filmapp.data

import android.R.attr.rating

object MovieData {
    val movies = listOf(
        Movie(
            id = 1,
            title = "Senin Harga Naik",
            year =2026,
            genre = "Drama",
            rating = 8.3f,
            plot = "mengisahkan dilema Mutia (Nadya Arina), agen properti yang harus membujuk ibunya, Retno (Meriam Bellina), menjual rumah dan toko roti legendaris keluarga. Mutia, yang kembali setelah tiga tahun pergi, terjebak konflik kepentingan antara karir dan hubungan emosional dengan keluarganya",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BNDA3YTZkNTItODRmZS00ODBiLThjMmItNTRlYWNkZDQzNDM1XkEyXkFqcGc@._V1_FMjpg_UX1080_.jpg",
            imdbUrl = "https://www.imdb.com/title/tt37561346/"
        ),
        Movie(
            id = 2,
            title = "Tunggu Aku Sukses Nanti",
            year = 2026,
            genre = "Drama",
            rating = 7.9f,
            plot = "Naya Anindita mengisahkan perjuangan Arga (Ardit Erwandha), seorang pemuda pengangguran yang berjuang melawan tekanan sosial, ekspektasi keluarga besar, dan krisis kepercayaan diri. Film ini menyoroti sindiran keluarga saat kumpul Lebaran, tema sandwich generation, dan pencarian arti kesuksesan yang sebenarnya.",
            imageUrl = "https://www.themoviedb.org/t/p/w1280/jtU7WqfnpWGkjTvzJyZD2H6jSp1.jpg",
            imdbUrl = "https://www.imdb.com/title/tt39292550/"
        ),
        Movie (
            id = 3,
            title = "Ayah, Ini Arahnya ke Mana, Ya?",
            year = 2026,
            genre = "Drama",
            rating = 7.9f,
            plot = "berpusat pada Dira, anak perempuan yang tumbuh dalam keluarga yang tampaknya utuh namun menanggung luka batin akibat ayahnya, Yudi (Dwi Sasono), tidak pernah hadir secara emosional meski fisik ada di rumah",
            imageUrl = "https://www.themoviedb.org/t/p/w1280/oQbytInaVqNS7G7S7xXDC0MQ6xn.jpg",
            imdbUrl = "https://www.imdb.com/title/tt40110177/"
        ),
        Movie (
            id = 4,
            title = "Dilan ITB 1997",
            year = 2026,
            genre = "Drama",
            rating = 8.0f,
            plot = "mengisahkan kehidupan dewasa Dilan (Ariel Noah) sebagai mahasiswa Seni Rupa ITB pada tahun 1997, di tengah gejolak politik menjelang Reformasi. Fokus utamanya adalah dilema romansa Dilan yang sudah berpacaran dengan Ancika (Niken Anjani), namun harus menghadapi kembalinya Milea (Raline Shah) ke dalam hidupnya.",
            imageUrl = "https://www.themoviedb.org/t/p/w1280/petyKATP9csA38xHWyyJqW1x7Vz.jpg",
            imdbUrl = "https://www.imdb.com/title/tt38873792/"
        ),
        Movie (
            id = 5,
            title = "Tiba-Tiba Setan",
            year = 2026,
            genre = "Horor",
            rating = 7.9f,
            plot = "kakak-beradik yang berburu harta karun warisan ayahnya di sebuah hotel tua terbengkalai. Rencana licik salah satu saudara untuk menakuti yang lain dengan menyewa hantu palsu (Rambo & Bonar) berbalik menjadi teror nyata akibat kebangkitan roh wanita penghuni hotel",
            imageUrl = "https://www.themoviedb.org/t/p/w1280/83936B93bT8ovTgTlWSr3934uYx.jpg",
            imdbUrl = "https://www.imdb.com/title/tt39921004/"
        ),
        Movie (
            id = 6,
            title = "Hoppers",
            year = 2026,
            genre = "Animasi",
            rating = 7.8f,
            plot = "Mabel, mahasiswa pecinta hewan yang menggunakan teknologi canggih untuk memindahkan kesadarannya ke dalam robot berang-berang. Tujuannya adalah berkomunikasi dengan hewan guna menyelamatkan habitat mereka dari kehancuran, namun aksi ini justru memicu pemberontakan hewan dan kekacauan besar.",
            imageUrl = "https://www.themoviedb.org/t/p/w1280/xjtWQ2CL1mpmMNwuU5HeS4Iuwuu.jpg",
            imdbUrl = "https://www.imdb.com/title/tt26443616/"
        )
    )

    val featuredMovies = movies.take(6)
}