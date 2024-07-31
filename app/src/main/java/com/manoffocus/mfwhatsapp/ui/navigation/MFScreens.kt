package com.manoffocus.mfwhatsapp.ui.navigation

enum class MFScreens {
    MFStartScreen,
    MFChatScreen,
    MFFanInfoScreen,
    MFHomeScreen,
    MFLocationScreen,
    MFUserProfileScreen,
    MFSeasonScreen,
    MFCharacterScreen,
    MFSearchScreen,
    MFQuizScreen;
    companion object {
        fun fromRoute(route: String): MFScreens
                = when(route.substringBefore("/")){
            MFStartScreen.name ->  MFStartScreen
            MFChatScreen.name ->  MFChatScreen
            MFFanInfoScreen.name ->  MFFanInfoScreen
            MFLocationScreen.name ->  MFLocationScreen
            MFUserProfileScreen.name ->  MFUserProfileScreen
            MFSeasonScreen.name ->  MFSeasonScreen
            MFCharacterScreen.name ->  MFCharacterScreen
            MFSearchScreen.name ->  MFSearchScreen
            MFQuizScreen.name ->  MFQuizScreen
            else -> MFHomeScreen
        }
    }
}