package com.example.movieappbisa

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.movieappbisa.ui.mainpage.MainActivity
import com.example.movieappbisa.util.DataDummy
import com.example.movieappbisa.util.Idling
import org.junit.After
import org.junit.Before
import org.junit.Test


class MainActivityTest {

    private val dataMovie= DataDummy.getMoviesDummy()
    private val dataTv= DataDummy.getTvShowDummy()

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(Idling.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(Idling.idlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.rv_grid_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_grid_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataMovie.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_grid_movie)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        0,
                        click()
                )
        )
        //photo
        onView(withId(R.id.poster)).check(matches(isDisplayed()))

        //title
        onView(withId(R.id.title)).check(matches(isDisplayed()))

        //rate
        onView(withId(R.id.rate)).check(matches(isDisplayed()))

        //release
        onView(withId(R.id.release)).check(matches(isDisplayed()))

        //overviw descr
        onView(withId(R.id.describe)).check(matches(isDisplayed()))

    }

    @Test
    fun loadTvShows() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_grid_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_grid_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataTv.size))
    }

    @Test
    fun loadTvDetail() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_grid_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //photo
        onView(withId(R.id.poster)).check(matches(isDisplayed()))

        //title
        onView(withId(R.id.title)).check(matches(isDisplayed()))

        //rate
        onView(withId(R.id.rate)).check(matches(isDisplayed()))

        //release
        onView(withId(R.id.release)).check(matches(isDisplayed()))

        //overviw descr
        onView(withId(R.id.describe)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavMovie(){
        //check
        onView(withId(R.id.rv_grid_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        //clik love detail
        onView(withId(R.id.favBut)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        //clik love menu
        onView(withId(R.id.fav)).perform((click()))
        onView(withId(R.id.rv_grid_movie)).check(matches(isDisplayed()))
        //scrol position clik data rv
        onView(withId(R.id.rv_grid_movie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataMovie.size))
        onView(withId(R.id.rv_grid_movie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        //unlove </3
        onView(withId(R.id.favBut)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun loadFavTv(){
        //clik menu tv, show data
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_grid_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_grid_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        //clik love detail
        onView(withId(R.id.favBut)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        //click love menu
        onView(withId(R.id.fav)).perform((click()))
        onView(withText("FavoriteTvShow")).perform(click())
        onView(withId(R.id.rv_grid_tv)).check(matches(isDisplayed()))
        //scrol position clik data rv
        onView(withId(R.id.rv_grid_tv)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dataTv.size))
        onView(withId(R.id.rv_grid_tv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        //unlove </3
        onView(withId(R.id.favBut)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

}