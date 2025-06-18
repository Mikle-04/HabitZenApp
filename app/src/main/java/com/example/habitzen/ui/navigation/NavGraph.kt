    package com.example.habitzen.ui.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable

    @Composable
    fun NavGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ){
            composable("home"){
                HomeScreen()
            }
        }
    }