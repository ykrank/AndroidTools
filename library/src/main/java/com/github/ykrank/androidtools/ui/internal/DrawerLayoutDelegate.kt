package com.github.ykrank.androidtools.ui.internal

import android.content.res.Configuration
import android.support.annotation.CallSuper
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentActivity
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import com.github.ykrank.androidtools.R
import com.github.ykrank.androidtools.util.ResourceUtil


/**
 * This class represents a delegate which you can use to add
 * [DrawerLayout] to [FragmentActivity].
 */
abstract class DrawerLayoutDelegate constructor(protected val mFragmentActivity: FragmentActivity, private val mDrawerLayout: DrawerLayout, private val mNavigationView: NavigationView) {
    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    private var mDrawerIndicatorEnabled = true

    /**
     * @see android.support.v7.app.AppCompatActivity.onPostCreate
     */
    @CallSuper
    fun onPostCreate() {
        setupNavDrawer()

        // Syncs the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState()
    }

    /**
     * @see android.support.v7.app.AppCompatActivity.onOptionsItemSelected
     */
    @CallSuper
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app drawer touch event.
        return mDrawerToggle.onOptionsItemSelected(item)
    }

    /**
     * @see android.support.v7.app.AppCompatActivity.onConfigurationChanged
     */
    @CallSuper
    fun onConfigurationChanged(newConfig: Configuration) {
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

    /**
     * Enables or disable the drawer indicator. The indicator defaults to enabled.
     */
    fun setDrawerIndicatorEnabled(enabled: Boolean) {
        mDrawerIndicatorEnabled = enabled
    }

    private fun setupNavDrawer() {
        // see http://stackoverflow.com/a/27487357
        mDrawerToggle = object : ActionBarDrawerToggle(mFragmentActivity, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, 0f)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                super.onDrawerSlide(drawerView, 0f)
            }

            /**
             * @see DrawerLayoutDelegate.closeDrawer
             */
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

                val tag = drawerView.getTag(R.id.tag_drawer_runnable)
                if (tag != null) {
                    val runnable = tag as Runnable
                    drawerView.setTag(R.id.tag_drawer_runnable, null)
                    runnable.run()
                }
            }
        }
        mDrawerLayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.isDrawerIndicatorEnabled = mDrawerIndicatorEnabled

        setupNavDrawerItem(mDrawerLayout, mNavigationView)
    }

    @CallSuper
    open fun onDestroy() {
        mDrawerLayout.removeDrawerListener(mDrawerToggle)
    }

    /**
     * Closes the drawer view.
     *
     * @param runnable Executes this during the [ActionBarDrawerToggle.onDrawerClosed].
     */
    fun closeDrawer(runnable: Runnable?) {
        mNavigationView.setTag(R.id.tag_drawer_runnable, runnable)
        mDrawerLayout.closeDrawer(GravityCompat.START)
    }

    fun openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START)
    }

    protected abstract fun setupNavDrawerItem(drawerLayout: DrawerLayout, navigationView: NavigationView)
}
