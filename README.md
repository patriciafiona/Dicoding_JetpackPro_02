# Dicoding_JetpackPro_02

<p align="center">
  <img src="https://user-images.githubusercontent.com/32255348/117399498-e7f4cf00-af2a-11eb-9d3d-b0ec4c7e9121.gif" width="300" />
</p>

<p align="center"><i><b>Figure 1</b> Preview of Github User App</i></p>

<br/>

Result of Renew my Dicoding: "Belajar Android Jetpack Pro" Certificate (Submission 02: Repository dan LiveData)

## Information
<p align="center">
  <img src="https://www.xda-developers.com/files/2021/03/Android-Jetpack.jpg" width="500"/>
</p>
<p align="center"><i><b>Figure 2</b> Android Jetpack Logo</i></p>

Type                  : Submission 02

Platform              : Mobile - [Android](https://www.android.com/intl/id_id/)

Programming Language  : [Kotlin](https://developer.android.com/kotlin?hl=id)

Dicoding Class        : [Belajar Android Jetpack Pro](https://www.dicoding.com/academies/129)

## Testing Schenario - Indonesia language
Adapun detail skenario testing aplikasi ini adalah sebagai berikut:

- DetailMovieViewModelTest
  - Memuat Movie
    - Memanipulasi data ketika pemanggilan data movie di kelas repository.
    - Memastikan metode di kelas repository terpanggil.
    - Melakukan pengecekan data movie apakah null atau tidak.
    - Membandingkan data movie sudah sesuai dengan yang diharapkan atau tidak.
  
- DetailTvShowViewModelTest
  - Memuat Tv Show
    - Memanipulasi data ketika pemanggilan data tv show di kelas repository.
    - Memastikan metode di kelas repository terpanggil.
    - Melakukan pengecekan data tv show apakah null atau tidak.
    - Membandingkan data tv show sudah sesuai dengan yang diharapkan atau tidak.
    
- MovieViewModelTest
  - Memuat list movie
    - Memanipulasi data ketika pemanggilan data list movie di kelas repository.
    - Memastikan metode di kelas repository terpanggil.
    - Melakukan pengecekan data list movie apakah null atau tidak.
    - Melakukan pengecekan jumlah data movie apakah sudah sesuai atau belum.

- TvShowViewModelTest
  - Memuat list tv show
    - Memanipulasi data ketika pemanggilan data list tv show di kelas repository.
    - Memastikan metode di kelas repository terpanggil.
    - Melakukan pengecekan data list tv show apakah null atau tidak.
    - Melakukan pengecekan jumlah data list tv show apakah sudah sesuai atau belum.

-TmdbRepositoryTest
  - Memuat List Movie
    - Memanipulasi data ketika pemanggilan response list movie dengan data dummy
    - Memastikan metode pemanggilan response list movie terpanggil
    - Melakukan pengecekan data response list movie apakah null atau tidak
    - Melakukan pengecekan jumlah response list movie apakah sudah sesuai atau belum

  - Memuat List Tv Show
    - Memanipulasi data ketika pemanggilan response list tv show dengan data dummy
    - Memastikan metode pemanggilan response list tv show terpanggil
    - Melakukan pengecekan data response list tv show apakah null atau tidak
    - Melakukan pengecekan jumlah response list tv show apakah sudah sesuai atau belum

  - Memuat Detail Movie
    - Memanipulasi data ketika pemanggilan response detail movie dengan data dummy
    - Memastikan metode pemanggilan response detail movie terpanggil
    - Melakukan pengecekan data response detail movie apakah null atau tidak
    - Melakukan pengecekan  Id Movie pada response detail movie apakah sudah sesuai atau belum

  - Memuat Detail Tv Show
    - Memanipulasi data ketika pemanggilan response detail tv show dengan data dummy
    - Memastikan metode pemanggilan response detail tv show terpanggil
    - Melakukan pengecekan data response detail tv show apakah null atau tidak
    - Melakukan pengecekan Id Tv Show pada response detail tv show apakah sudah sesuai atau belum

- Menampilkan data list movie
  - Klik bottom navigation untuk bagian movie
  - Memastikan rv_movie dalam keadaan tampil
  - Gulir rv_movie ke posisi data terakhir
  
- Menampilkan halaman detail movie
  - Klik bottom navigation untuk bagian movie
  - Memberi tindakan klik pada data pertama di rv_movie
  - Memastikan TextView untuk keterangan halaman (Top title) tampil sesuai yang diharapkan
  - Memastikan TextView untuk judul movie tampil sesuai yang diharapkan
  - Memastikan RatingBar untuk movie dapat ditampilkan
  - Memastikan TextView tanggal perilisan movie tampil sesuai yang diharapkan
  - Memastikan TextView synopsis movie tampil sesuai yang diharapkan
  - Memastikan ImageButton untuk Movie Favorit dapat ditampilkan
  - Memastikan ImageView untuk movie backdrop dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk poster movie dapat dilihat dan ditampilkan
  - Memberikan Tindakan klik pada tombol back di halaman detail movie

- Menampilkan data list tv show
  - Klik bottom navigation untuk bagian Tv Show
  - Memastikan rv_tvShow dalam keadaan tampil
  - Gulir rv_tvShow ke posisi data terakhir

- Menampilkan halaman detail tv show
  - Klik bottom navigation untuk bagian Tv Show
  - Memberi tindakan klik pada data pertama di rv_tvShow
  - Memastikan TextView untuk keterangan halaman (Top title) tampil sesuai yang diharapkan
  - Memastikan TextView untuk judul tv show tampil sesuai yang diharapkan
  - Memastikan RatingBar untuk tv show rating dapat ditampilkan
  - Memastikan TextView tanggal perilisan tv show tampil sesuai yang diharapkan
  - Memastikan TextView synopsis tv show tampil sesuai yang diharapkan
  - Memastikan ImageButton untuk tv show Favorit dapat ditampilkan
  - Memastikan ImageView untuk movie backdrop dapat dilihat dan ditampilkan
  - Memastikan ImageView untuk poster movie dapat dilihat dan ditampilkan
  - Memastikan rv_seasonDetail dapat ditampilkan dengan baik
  - Memastikan rv_seasonDetail dapat digulir ke posisi terakhir
  - Memberikan Tindakan klik pada tombol back di halaman detail tv show

- Menampilkan hasil ekspansi dan menutup kembali daftar movie
  - Klik bottom navigation untuk bagian movie
  - Menekan tombol ekspansi pada halaman list movie untuk memperluas tampilan halaman list movie
  - Menekan tombol ekspansi kembali pada halaman list movie untuk mengembalikan tampilan halaman list movie (setengah halaman)

- Menampilkan hasil ekspansi dan menutup kembali daftar tv show
  - Klik bottom navigation untuk bagian Tv Show
  - Menekan tombol ekspansi pada halaman list tv show untuk memperluas tampilan halaman list tv show
  - Menekan tombol ekspansi kembali pada halaman list tv show untuk mengembalikan tampilan halaman list tv show (setengah halaman)
