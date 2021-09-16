import * as React from 'react';
import 'react-native-gesture-handler';
import {NavigationContainer, useNavigation} from '@react-navigation/native';
import {createStackNavigator, HeaderBackButton} from '@react-navigation/stack';
import {createBottomTabNavigator} from "@react-navigation/bottom-tabs";
import LoginScreen from "../view/LoginScreen";
import HomeView from "../view/HomeView";
import LoginView from "../view/LoginView";
import UserView from "../view/UserView";
import PositionView from "../view/PositionView"
import SplashScreen from "react-native-splash-screen";
import RegisterView from "../view/RegisterView";
import EmailCheckScreen from "../view/RegisterScreen/EmailCheckScreen";
import RegisterScreen from "../view/RegisterScreen/RegisterScreen";
import {Component} from "react";
import Ionicons from "react-native-vector-icons/Ionicons";
import {View} from "react-native";
import FundStockView from "../view/FundStockView";
import FundView from "../view/FundView";
import FundArchiveDetailView from "../view/FundArchiveDetailView"
import ManagerView from "../view/ManagerView";
import SearchView from "../view/SearchView";
import ManagerListView from "../view/ManagerListView";
import FundAnalysisView from "../view/FundAnalysisView";
import FundAnalysisCard from "../components/FundAnalysis/FundAnalysisCard";
import TestView from "../view/TestView";
import FundPerformanceView from "../view/FundPerformanceView";
import FundNAVView from "../view/FundNAVView";
import HomeScreen from "../view/HomeScreen"
import HomeRankFundView from "../view/HomeRankFundView";
import UserSettingView from "../view/UserSettingView";
import SettingNicknameView from "../view/SettingNicknameView";
import SettingPasswordView from "../view/SettingPasswordView";
import SettingIntroductionView from "../view/SettingIntroductionView";
import UserCompeDetailView from "../view/UserCompeDetailView";
import InvestView from "../view/InvestView";
import RiskAnalysisView from "../view/RiskAnalysisView";
import RiskAnalysisResult from "../view/RiskAnalysisResult";
import CompeListView from "../view/CompeListView";
import CompeJoinView from "../view/CompeJoinView";
import MyCompeListView from "../view/MyCompeListView";
import ForgetPasswordView from "../view/ForgetPasswordView";
import SendVerificationView from "../view/SendVerificationView";
import ResetPasswordView from "../view/ResetPasswordView";
import AboutView from "../view/AboutView";
import QuestionView from "../view/QuestionView";

const Tab = createBottomTabNavigator();
const MainStack = createStackNavigator();

function MainTabScreen() {
    return (
        <Tab.Navigator
            tabBarOptions={{
                activeTintColor: '#3c78d8',
                keyboardHidesTabBar: true
            }}
        >
            <Tab.Screen
                name="首页" component={HomeScreen}
                options={{
                    tabBarLabel: '首页',
                    tabBarIcon: ({color, focused}) => (<Ionicons
                        name={focused ? 'ios-home' : 'ios-home-outline'}
                        size={26}
                        style={{color: color}}
                    />),
                }}
            />
            <Tab.Screen
                name="自选" component={PositionView}
                options={{
                    tabBarLabel: '自选',
                    tabBarIcon: ({color, focused}) => (<Ionicons
                        name={focused ? 'star' : 'star-outline'}
                        size={26}
                        style={{color: color}}
                    />),
                }}
            />
            {/*<Tab.Screen*/}
            {/*    name="测试" component={TestView}*/}
            {/*    options={{*/}
            {/*        tabBarLabel: '测试',*/}
            {/*        tabBarIcon: ({color, focused}) => (<Ionicons*/}
            {/*            name={focused ? 'aperture' : 'aperture-outline'}*/}
            {/*            size={26}*/}
            {/*            style={{color: color}}*/}
            {/*        />),*/}
            {/*    }}*/}
            {/*/>*/}
          <Tab.Screen
              name="我的" component={UserView}
              options={{
                tabBarLabel: '我的',
                tabBarIcon: ({color, focused}) => (<Ionicons
                    name={focused ? 'aperture' : 'aperture-outline'}
                    size={26}
                    style={{color: color}}
                />),
              }}
          />
        </Tab.Navigator>
    )
}

export default class AppNavigation extends Component {
    componentDidMount() {
        SplashScreen.hide();
        console.log("hide SplashScreen");
    }

    render() {
        return (
            <NavigationContainer>
                <MainStack.Navigator
                    initialRouteName="MainTab"
                    screenOptions={{
                        headerShown: false,
                    }}
                >
                    <MainStack.Screen
                        name="FundAnalysis"
                        component={FundAnalysisView}
                    />
                    <MainStack.Screen
                        name="FundAnalysisCard"
                        component={FundAnalysisCard}
                    />
                    <MainStack.Screen
                        name="MainTab"
                        component={MainTabScreen}
                    />
                    <MainStack.Screen
                        name="Position"
                        component={PositionView}
                    />
                    <MainStack.Screen
                        name="User"
                        component={UserView}
                    />
                    <MainStack.Screen
                        name="Login"
                        component={LoginScreen}
                    />
                    <MainStack.Screen
                        name="Register"
                        component={EmailCheckScreen}
                    />
                    <MainStack.Screen
                        name="Register2"
                        component={RegisterScreen}
                    />
                    <MainStack.Screen
                        name="Stock"
                        component={FundStockView}
                    />
                    <MainStack.Screen
                        name="Fund"
                        component={FundView}
                        options={{
                          transitionSpec: {
                            open: config,
                            close: config,
                          },
                        }}
                    />
                    <MainStack.Screen
                        name="FundArchive"
                        component={FundArchiveDetailView}
                    />
                    <MainStack.Screen
                        name="Manager"
                        component={ManagerView}
                    />
                    <MainStack.Screen
                        name="ManagerList"
                        component={ManagerListView}
                    />
                    <MainStack.Screen
                        name="Search"
                        component={SearchView}
                    />
                    <MainStack.Screen
                        name="FundPerformance"
                        component={FundPerformanceView}
                    />
                    <MainStack.Screen
                        name="FundNAV"
                        component={FundNAVView}
                    />
                    <MainStack.Screen
                        name="HomeRankFund"
                        component={HomeRankFundView}
                    />
                    <MainStack.Screen
                        name="UserSetting"
                        component={UserSettingView}
                    />
                    <MainStack.Screen
                      name="SettingNickname"
                      component={SettingNicknameView}
                    />
                    <MainStack.Screen
                      name="SettingPassword"
                      component={SettingPasswordView}
                    />
                    <MainStack.Screen
                        name="SettingIntroduction"
                        component={SettingIntroductionView}
                    />
                    <MainStack.Screen
                        name="UserCompeDetail"
                        component={UserCompeDetailView}
                    />
                    <MainStack.Screen
                        name="CompeList"
                        component={CompeListView}
                    />
                    <MainStack.Screen
                        name="MyCompeList"
                        component={MyCompeListView}
                    />
                    <MainStack.Screen
                        name="CompeJoin"
                        component={CompeJoinView}
                    />
                    <MainStack.Screen
                        name="Invest"
                        component={InvestView}
                    />
                    <MainStack.Screen
                        name="RiskAnalysis"
                        component={RiskAnalysisView}
                    />
                    <MainStack.Screen
                        name="RiskAnalysisResult"
                        component={RiskAnalysisResult}
                    />
                    <MainStack.Screen
                        name="ForgetPassword"
                        component={ForgetPasswordView}
                    />
                    <MainStack.Screen
                      name="SendVerification"
                      component={SendVerificationView}
                    />
                    <MainStack.Screen
                      name="ResetPassword"
                      component={ResetPasswordView}
                    />
                    <MainStack.Screen
                      name="About"
                      component={AboutView}
                    />
                    <MainStack.Screen
                      name="Question"
                      component={QuestionView}
                    />
                    {/*<MainStack.Screen*/}
                    {/*  name="Help"*/}
                    {/*  component={HelpView}*/}
                    {/*/>*/}
                </MainStack.Navigator>
            </NavigationContainer>
        );
    }
}

const config = {
  animation: 'spring',
  config: {
    stiffness: 1000,
    damping: 500,
    mass: 3,
    overshootClamping: true,
    restDisplacementThreshold: 0.01,
    restSpeedThreshold: 0.01,
  },
};
