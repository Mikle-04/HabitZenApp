    package com.example.habitzen.ui.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import com.example.habitzen.ui.Screens.HabitsScreen
    import com.example.habitzen.ui.Screens.HomeScreen

    @Composable
    fun NavGraph(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = "home"
        ){
            composable("home"){
                HomeScreen(navController = navController)
            }
            composable("habits"){
                HabitsScreen(navController = navController)
            }
        }
    }