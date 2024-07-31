package com.manoffocus.mfwhatsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.manoffocus.common.framework.navigation.DeepLinks
import com.manoffocus.common.framework.navigation.MFScreenRoutes
import com.manoffocus.feature.chat.ui.ChatScreen
import com.manoffocus.feature.conversations.ui.ConversationsListScreen
import com.manoffocus.feature.create_chat.ui.CreateConversationScreen

@Composable
fun MFWhatsAppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = MFScreenRoutes.ConversationsList) {

        addConversationsList(navController)

        addNewConversation(navController)

        addChat(navController)

    }
}

private fun NavGraphBuilder.addConversationsList(navController: NavHostController) {

    composable(MFScreenRoutes.ConversationsList) {
        ConversationsListScreen(
            onNewConversationClick = { navController.navigate(MFScreenRoutes.NewConversation) },
            onConversationClick = { chatId ->
                navController.navigate(MFScreenRoutes.Chat.replace("{chatId}", chatId))
            }
        )
    }

}

private fun NavGraphBuilder.addNewConversation(navController: NavHostController) {

    composable(MFScreenRoutes.NewConversation) {
        CreateConversationScreen(onCreateConversation = { navController.navigate(MFScreenRoutes.Chat) })
    }
}

private fun NavGraphBuilder.addChat(navController: NavHostController) {

    composable(
        route = MFScreenRoutes.Chat,
        arguments = listOf(navArgument(MFScreenRoutes.ChatArgs.ChatId) { type = NavType.StringType }),
        deepLinks = listOf(navDeepLink { uriPattern = DeepLinks.chatRoute })
    ) { backStackEntry ->
        val chatId = backStackEntry.arguments?.getString(MFScreenRoutes.ChatArgs.ChatId)
        ChatScreen(chatId = chatId, onBack = { navController.popBackStack() })
    }
}