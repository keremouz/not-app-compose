package com.example.testapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.testapp.presentation.navigation.Routes
import com.example.testapp.ui.theme.TestAppTheme
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestAppTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Routes.Home.route
                ) {
                    composable(Routes.Home.route) {
                        HomeScreen(
                            onAddClick = {
                                navController.navigate(Routes.Add.route)
                            },
                            onGoNotes = {
                                navController.navigate(Routes.Notes.route)
                            },
                            titleColor = Color.Black,
                            goNotesTextColor = Color.LightGray
                        )
                    }

                    composable(Routes.Add.route) {
                        AddNoteScreen(
                            viewModel = viewModel,
                            onSaved = {
                                navController.navigate(Routes.Notes.route)
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(Routes.Notes.route) {
                        NotesScreen(
                            viewModel = viewModel,
                            onNoteClick = { id ->
                                navController.navigate(Routes.Detail.create(id))
                            },
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable(
                        route = Routes.Detail.route,
                        arguments = listOf(navArgument("noteId") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->

                        val noteId =
                            backStackEntry.arguments?.getInt("noteId") ?: 0

                        NoteDetailScreen(
                            viewModel = viewModel,
                            noteId = noteId,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                }
            }
        }
    }
}