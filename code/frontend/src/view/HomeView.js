import React, {Component} from 'react';
import {RefreshControl, SafeAreaView, ScrollView, StyleSheet, Text, View} from 'react-native';
import {Card, Divider} from 'react-native-elements';
import Header from "../components/Header";
import PredictionRanking from "../components/PredictionRanking";
import Ionicons from "react-native-vector-icons/Ionicons";
import FundRanking from "../components/FundRanking";
import MyStatusBar from "../components/MyStatusBar";

export default class HomeView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            search: '',
            isRefreshing: false,
        };
        console.log("HomeView created");
    }


    onRefreshHandle() {
        this.setState({
            isRefreshing: true
        })
        setTimeout(() => {
            this.setState({
                isRefreshing: false,
            })
        }, 2000);
    }

    render() {
        return (
            <View>
                <Header
                    headerRight={
                        <Ionicons
                            name='search'
                            size={24}
                            onPress={() => {
                                this.props.navigation.navigate('Search')
                            }}/>
                    }
                >
                    交我赚
                </Header>

                <View style={{marginBottom: 150}}>
                    <View>

                        <ScrollView
                            refreshControl={
                                <RefreshControl
                                    refreshing={this.state.isRefreshing}
                                    onRefresh={() => {
                                        this.onRefreshHandle()
                                    }}
                                />
                            }
                        >

                            <View>
                                <Divider
                                    inset={true}
                                    insetType="middle"
                                />

                                {/*<View>*/}
                                {/*    <PredictionRanking/>*/}
                                {/*</View>*/}
                                {/*<TouchableOpacity onPress={() => this.props.navigation.navigate('Login')}>*/}
                                {/*    <Text>跳转登陆</Text>*/}
                                {/*</TouchableOpacity>*/}

                                {/*<Divider style={page.divider}/>*/}

                                <View style={offsetMargin.offsetTop}>
                                    <FundRanking type={0}/>
                                </View>
                              <View style={offsetMargin.offsetTop}>
                                <FundRanking type={1}/>
                              </View>
                            <Divider/>
                            {/*<Card wrapperStyle={{flexDirection: 'column', padding: 0}}*/}
                            {/*      containerStyle={{borderRadius: 10, padding: 5}}>*/}
                            {/*    <FundAnalysisCard profit={79} risk={91} cost={91} title={true}/>*/}
                            {/*</Card>*/}
                            <Divider/>
                            </View>
                        </ScrollView>
                    </View>
                </View>
            </View>

        )
    }
}

const page = StyleSheet.create(
    {
        text: {}
        ,
        button: {
            textAlign: 'center',
        }
        ,
        divider: {
            backgroundColor: 'blue',
        }
    }
);

const offsetMargin = StyleSheet.create(
    {
        offsetTop: {
            marginTop: -70,
        },
        offsetBottom: {
            marginBottom: -30,
        },
    }
)
