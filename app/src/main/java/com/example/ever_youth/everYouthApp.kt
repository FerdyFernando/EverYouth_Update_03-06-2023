package com.example.ever_youth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ever_youth.ui.theme.Ever_YouthTheme
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.absoluteValue




@Composable
fun EverYouthApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LoginPage") {
        composable("LoginPage") {
            LoginPage(navController = navController,
                onLoginClick = { navController.navigate("${"MainMenuPage"}/{unameInput}")}
                )
        }
        composable("${"MainMenuPage"}/{unameInput}") { backStackEntry ->
            MainMenuPage(username = backStackEntry.arguments?.getString("unameInput") ?: "",
                onProfileClick = { navController.navigate("ProfilePage")},
                navController = navController,
            )
        }
        composable("ProfilePage"){
            ProfilePage(navController= navController)
        }
        composable("FeedsPage"){
            FeedsPage(navController = navController)
        }
        composable("FaceScannerPage"){
            FaceScannerPage(navController = navController)
        }
        composable("ShortsPage"){
            ShortsPage(navController = navController)
        }
        composable("SearchPage"){
            SearchPage(navController = navController)
        }
    }
}

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController,
              onLoginClick: () -> Unit
              ) {
    var unameInput by remember { mutableStateOf("") }
    var passInput by remember { mutableStateOf("") }

    val unameFocusRequester = remember { FocusRequester() }
    val passFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppTitle()

        UsernameTextField(
            unameInput,
            {unameInput = it},
            unameFocusRequester,
            passFocusRequester
        )

        PasswordTextField(
            passInput,
            {passInput = it},
            passFocusRequester
        )

        TOSCaution()

        LoginButton(onLoginClick = {
            navController.popBackStack()
            navController.navigate("${"MainMenuPage"}/$unameInput")})
    }
}

@Composable
fun AppTitle(){
    Text(
        text = "Ever Youth",
        fontSize = 36.sp,
        modifier = Modifier.padding(
            top = 200.dp,
            bottom = 35.dp)
    )
}

@Composable
fun UsernameTextField(
    username: String,
    onUsernameChange: (String) -> Unit,
    unameFocusRequester: FocusRequester,
    passFocusRequester: FocusRequester
) {
    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        singleLine = true,
        modifier = Modifier
            .padding(10.dp)
            .focusRequester(unameFocusRequester),
        label = { Text(text = stringResource(R.string.user_textfield_label)) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { passFocusRequester.requestFocus()})
    )
}

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passFocusRequester: FocusRequester
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier
            .padding(bottom = 5.dp)
            .focusRequester(passFocusRequester),
        label = { Text(text = stringResource(R.string.pass_textfield_label)) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Composable
fun TOSCaution() {
    Text(
        text = stringResource(R.string.termsconditions_text),
        fontSize = 12.sp,
        modifier = Modifier.padding(bottom = 80.dp)
    )
}

@Composable
fun LoginButton(onLoginClick: () -> Unit){
    Button(
        onClick = onLoginClick,
        modifier = Modifier
            .width(200.dp)
            .height(60.dp)
    ) {
        Text("LOGIN")
    }
}

@Composable
fun MainMenuPage(
    modifier: Modifier = Modifier,
    username: String,
    onProfileClick: () -> Unit,
    navController: NavController,
){
    val imageScrollState = rememberLazyListState()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val buttonScrollState = rememberLazyListState()
    val images = listOf(
        R.drawable.androidparty,
        R.drawable.androidparty,
        R.drawable.androidparty,
        R.drawable.androidparty,
        R.drawable.androidparty,
    )

    Scaffold (
        bottomBar = {
            NavigationButtons(navController = navController, currentRoute)
        }
            ) { contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onProfileClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    // Profile picture
                    Text(text = "P")
                }
                Text(
                    text = "Welcome $username!",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            // Swipable list of images
            ImageSection(images = images, scrollState = imageScrollState)

            // "What's on your mind" text
            Text(
                text = "What's on your mind?",
                fontSize = 18.sp,
                modifier = Modifier.padding(4.dp)
            )

            // Swipable list of buttons
            ButtonSection(scrollState = buttonScrollState)
        }
    }

}

@Composable
fun ImageSection(images: List<Int>, scrollState: LazyListState) {
    val firstVisibleItemScrollState = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = scrollState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            items(images.size) { index ->
                Image(
                    painter = painterResource(images[index]),
                    contentDescription = "Image $index",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(320.dp)
                        .clip(shape = RoundedCornerShape(4.dp))
                )
            }
        }
        ScrollIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            scrollState = firstVisibleItemScrollState.value,
            itemCount = images.size,
        )
        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.firstVisibleItemIndex }
                .distinctUntilChanged()
                .collect { firstVisibleItemScrollState.value = it }
        }
    }
}

@Composable
fun ButtonSection(scrollState: LazyListState) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier.fillMaxWidth()
    ) {
        val items = List(6) { index -> index }

        items(items.chunked(2)) { rowItems ->
            Row(Modifier.fillMaxWidth()) {
                rowItems.forEach { index ->
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(15.dp)
                            .clip(shape = RoundedCornerShape(12.dp))
                            .weight(1f)
                            .height(125.dp)
                    ) {
                        Text(text = "Button $index")
                    }
                }
            }
        }
    }
}



@Composable
fun ScrollIndicator(
    modifier: Modifier = Modifier,
    scrollState: Int,
    itemCount: Int,
    indicatorWidth: Dp = 12.dp,
    indicatorHeight: Dp = 4.dp,
    indicatorPadding: Dp = 4.dp,
    indicatorColor: Color = Color.White,
    inactiveIndicatorColor: Color = Color.Gray,
) {
    Box(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = indicatorHeight)
        ) {
            for (i in 0 until itemCount) {
                val color = if (scrollState.absoluteValue == i) indicatorColor else inactiveIndicatorColor
                Box(
                    modifier = Modifier
                        .width(indicatorWidth)
                        .height(indicatorHeight)
                        .background(color, CircleShape)
                        .padding(indicatorPadding)
                )
            }
        }
    }
}

@Composable
fun ProfilePage(navController: NavController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text (text = "Profile configuration is not yet available")
    }
}

@Composable
fun SearchPage(navController: NavController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationButtons(navController = navController, currentRoute)
        }
    ) {contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text (text = "Search is not yet available")
        }
    }

}

@Composable
fun FaceScannerPage(navController: NavController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationButtons(navController = navController, currentRoute)
        }
    ) {contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text (text = "Face Scanner is not yet available")
        }
    }

}

@Composable
fun FeedsPage(navController: NavController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationButtons(navController = navController, currentRoute)
        }
    ) {contentPadding ->
        Column(
            modifier = Modifier.padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text (text = "Feeds is not yet available ")
        }
    }

}

@Composable
fun ShortsPage(navController: NavController){
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            NavigationButtons(navController = navController, currentRoute)
        }
    ) {contentPadding ->
        Column(
        modifier = Modifier.padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text (text = "Shorts is not yet available")
    }
    }

}

@Composable
fun NavigationButtons(navController: NavController, currentRoutePar: String?){
    val currentRoute = currentRoutePar

    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color.White
    ) {
        BottomNavigationItem(
            icon = { /* Home icon */ },
            label = { Text(text = "Home") },
            selected = currentRoute == "${"MainMenuPage"}/{unameInput}",
            onClick ={
                if (currentRoute != "${"MainMenuPage"}/{unameInput}"){
                    navController.popBackStack()
                }
                else {

                }
            }
        )

        BottomNavigationItem(
            icon = { /* Feeds icon */ },
            label = { Text(text = "Feeds") },
            selected = currentRoute == "FeedsPage",
            onClick = {
                if (currentRoute != "FeedsPage") {
                    navController.popBackStack(currentRoute ?: "", inclusive = false)
                    navController.navigate("FeedsPage"){
                        popUpTo("${"MainMenuPage"}/{unameInput}"){
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

        BottomNavigationItem(
            icon = { /* Main feature icon */ },
            label = { Text(text = "Main Feature") },
            selected = currentRoute == "FaceScannerPage",
            onClick = {
                if (currentRoute != "FaceScannerPage") {
                    navController.navigate("FaceScannerPage"){
                        popUpTo("${"MainMenuPage"}/{unameInput}"){
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            selectedContentColor = Color.Green,
            unselectedContentColor = Color.Gray,
            modifier = Modifier.size(48.dp)
        )

        BottomNavigationItem(
            icon = { /* Shorts icon */ },
            label = { Text(text = "Shorts") },
            selected = currentRoute == "ShortsPage",
            onClick = {
                if (currentRoute != "ShortsPage"){
                    navController.navigate("ShortsPage"){
                        popUpTo("${"MainMenuPage"}/{unameInput}"){
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )

        BottomNavigationItem(
            icon = { },
            label = { Text(text = "Search") },
            selected = currentRoute == "SearchPage",
            onClick = {
                if (currentRoute != "SearchPage"){
                    navController.navigate("SearchPage"){
                        popUpTo("${"MainMenuPage"}/{unameInput}"){
                            saveState = true
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        EverYouthApp()
    }
