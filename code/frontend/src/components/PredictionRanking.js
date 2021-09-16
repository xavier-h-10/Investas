import React, {Component} from "react";

import {Avatar, Icon, ListItem} from 'react-native-elements'
import {Image, SafeAreaView, Text, View, StyleSheet, RefreshControl, ScrollView} from "react-native";
import HoldList from "./HoldList";
import PredictionList from "./PredictionList";
import {getFundPredictions} from "../service/FundPredictionService";
import Toast from "./firebase/Toast";

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



export default class PredictionRanking extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isRefreshing: false,
            fundList: [],
            error: '',
        };
    }

    onRefreshHandle() {
        this.setState({
            isRefreshing: true
        })
        setTimeout(() => {
            this.setState({
                isRefreshing: false,
            })
        }, 1000);
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

    update_prediction = (value) => {
        if(value['status'] === -1){
            this.setState({
                error: value['message'],
            })
        }
        else{
            this.setState({
                fundList: value['data']['prediction'],
            })
        }
    }

    componentDidMount() {
        getFundPredictions(this.update_prediction)
    }

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
                        <PredictionList
                            fundList={this.state.fundList}
                            preDate={'8-6'}
                            curDate1={'8-9'}
                            curDate2={'8-10'}
                            curDate3={'8-11'}
                        />

                        <Toast message={this.state.error}  onDismiss={() => this.setState({error: ''})}/>
                    </View>
                </ScrollView>
            </View>
        )
    }
}

