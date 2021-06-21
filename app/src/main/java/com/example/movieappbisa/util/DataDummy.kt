package com.example.movieappbisa.util

import com.example.movieappbisa.entity.local.source.MovieData
import com.example.movieappbisa.entity.local.source.TvShowData
import com.example.movieappbisa.entity.remote.response.Movie
import com.example.movieappbisa.entity.remote.response.TvShow

object DataDummy {
        //GAK REMOTE
        fun getMovies(): ArrayList<MovieData> {
                return arrayListOf(
                        MovieData(
                                460465,
                                "Mortal Kombat",
                                "2021-04-07",
                                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                                7.8,
                                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg"
                        ),
                        MovieData(
                                460465,
                                "Mortal Kombat",
                                "2021-04-07",
                                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                                7.8,
                                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg"
                        )
                )

        }

        fun getTvShow(): ArrayList<TvShowData> {
                return arrayListOf(
                        TvShowData(
                                88396,
                                "The Falcon and the Winter Soldier",
                                "2021-03-19",
                                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                                7.9,
                                "/6kbAMLteGO8yyewYau6bJ683sw7.jpg"
                        )
                )
        }

        //REMOTE
        fun getMoviesDummy(): ArrayList<Movie> {
                return arrayListOf(
                        Movie(
                                460465,
                                "Mortal Kombat",
                                "2021-04-07",
                                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                                7.8,
                                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg"
                        ),
                        Movie(
                                460465,
                                "Mortal Kombat",
                                "2021-04-07",
                                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                                7.8,
                                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg"
                        )
                )

        }

        fun getTvShowDummy(): ArrayList<TvShow> {
                return arrayListOf(
                        TvShow(
                                88396,
                                "The Falcon and the Winter Soldier",
                                "2021-03-19",
                                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                                7.9,
                                "/6kbAMLteGO8yyewYau6bJ683sw7.jpg"
                        )
                )
        }
}