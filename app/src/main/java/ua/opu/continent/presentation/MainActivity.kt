package ua.opu.continent.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ua.opu.continent.App
import ua.opu.continent.R
import ua.opu.continent.useсase.impl.PresenceUseCaseFirebase
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private var navController: NavController? = null

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel


    private val topLevelDestinations = setOf(getChatDestination(), getSignInDestination())

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is NavHostFragment) return
            onNavControllerActivated(f.findNavController())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        val navController = getRootNavController()
        prepareRootNavController(isSignedIn(), navController)
        onNavControllerActivated(navController)

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)

    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        navController = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        val currentDestination = navController?.currentDestination
        if (currentDestination?.id == getChatDestination())
            finish()
        if (isStartDestination(navController?.currentDestination)) {
            super.onBackPressed()
        } else {
            navController?.popBackStack()
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.setUserPresence(PresenceUseCaseFirebase.PRESENCE_OFFLINE)
    }

    override fun onSupportNavigateUp(): Boolean =
        (navController?.navigateUp() ?: false) || super.onSupportNavigateUp()

    private fun prepareRootNavController(isSignedIn: Boolean, navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraphId())
        graph.setStartDestination(
            if (isSignedIn) {
                getChatDestination()
            } else {
                getSignInDestination()
            }
        )
        navController.graph = graph
    }

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController != navController)
            this.navController = navController
    }

    private fun getRootNavController(): NavController {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }


    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        if (destination.id == getChatDestination()) return true
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    private fun isSignedIn(): Boolean {
        return viewModel.isAuthenticateUser()
    }

    private fun getMainNavigationGraphId(): Int = R.navigation.main_graph

    private fun getChatDestination(): Int = R.id.mainFragment

    private fun getSignInDestination(): Int = R.id.verificationFragment

}