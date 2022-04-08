@file:JvmName("FragmentUtils")
package nz.co.test.transactions.ui.utils

import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import nz.co.kiwibank.mobile.ui.utils.ui.NavigationResultsKey


// Used to obtain observed value based on specified key
fun <T> Fragment.getNavigationResult(key: NavigationResultsKey) =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key.name)

// Result should be of a type which can be stored into a bundle.
// For values which need to be observed in prior fragment
fun <T> Fragment.setNavigationResult(key: NavigationResultsKey, result: T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key.name, result)
}

// Result should be of a type which can be stored into a bundle.
// To "reset" values observed in current fragment
fun <T> Fragment.resetNavigationResult(key: NavigationResultsKey, result: T) {
    findNavController().currentBackStackEntry?.savedStateHandle?.set(key.name, result)
}

fun <T> Fragment.removeNavigationResult(key: NavigationResultsKey) {
    findNavController().currentBackStackEntry?.savedStateHandle?.remove<T>(key.name)
}

fun Fragment.addBackPressedHandler(handleBackPressed: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handleBackPressed()
        }
    })
}

infix fun Fragment.navigateTo(direction: NavDirections) {
    findNavController().navigate(direction)
}

infix fun Fragment.navigateTo(@IdRes id: Int) {
    findNavController().navigate(id)
}