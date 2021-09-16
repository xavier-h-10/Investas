import React, {Component} from "react";

import {Avatar, Icon, ListItem} from 'react-native-elements'
import {Image, SafeAreaView, Text, View, StyleSheet, RefreshControl, ScrollView} from "react-native";
import HoldList from "./HoldList";
import PredictionList from "./PredictionList";
import RecommendList from "./RecommendList";

const list = [
    {
        title: 'Appointments',
        icon: 'av-timer'
    },
    {
        title: 'Trips',
        icon: 'flight-takeoff'
    }
]



export default class RecommendRanking extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isRefreshing: false,
        };
        console.log("PossessionView created");
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

    styles = StyleSheet.create({
        subtitleView: {
            flexDirection: 'row',
            paddingLeft: 10,
            paddingTop: 5
        },
        ratingImage: {
            height: 19.21,
            width: 100
        },
        ratingText: {
            paddingLeft: 10,
            color: 'grey'
        }
    })

    render() {
        return (
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
                    <View style={{marginBottom: 80}}>
                        <RecommendList
                            userType='保守型'
                            fundList={[
                                {
                                    code: '000689',
                                    name: '前海开源新经济灵活配置混合A',
                                    nav: [3.0197, 6.89],
                                    val1: [-15.82],
                                    val2: [-25.93],
                                },
                                {
                                    code: '000689',
                                    name: '前海开源新经济灵活配置混合A',
                                    nav: [3.0197, 6.89],
                                    val1: [-15.82],
                                    val2: [-25.93],
                                },
                            ]}
                            preDate={'7-29'}
                        />
                    </View>
                </ScrollView>
            </View>
        )
    }
}
