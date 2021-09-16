import React, {Component} from 'react'
import {Card, ListItem, Text} from "react-native-elements";
import {Button, Pressable, ScrollView, StyleSheet, TouchableOpacity, View} from "react-native";
import {Caption} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import message from "react-native-message/index";
import GoToFundView from "./GoToFundView";

function VerticalItem(props) {
    const {header, body} = props
    return (
        <View {...props}>
            <ListItem key={-1} bottomDivider>
                {header}
            </ListItem>
            {
                body.map((data, index) => (
                    <ListItem key={index} bottomDivider>
                        <View style={styles.listItem}>
                            {data}
                        </View>
                    </ListItem>
                ))
            }
        </View>
    )
}

function Rate(props) {
    const {number} = props
    return (
        <Text style={{fontSize: 15, color: numberColor(number), fontWeight: 'bold'}}>{numberFormat(number) + '%'}</Text>
    )
}

function List1(fundList, rate) {
    let list = []
    fundList.forEach((fund) => {
        list.push(
            <View style={{marginTop: 20}}>
                <Rate number={fund[rate]}/>
            </View>
        )
    })
    return list
}

export default class FundRankingList extends Component {
    constructor(props) {
        super(props);
        this.state={
            type: this.props.type
        }
    }

    render() {
        const {fundList, preDate} = this.props
        console.log(fundList)
        return (
            <View style={{flexDirection: 'row', marginTop: 10}}>
                <View style={{width: '40%'}}>
                    <ListItem key={-1} bottomDivider>
                        <View style={{height: 40,justifyContent: 'center'}}>
                            <Text style={{fontSize: 15, fontWeight: 'bold', textAlign: 'center'}}>基金名称</Text>
                            <Caption style={styles.caption}>{this.state.type === 1?'一年领涨':'上月领涨'}</Caption>
                        </View>
                    </ListItem>
                    {
                        fundList.map((fund, index) => (
                            <ListItem key={index} bottomDivider>
                                <GoToFundView
                                    name={fund.fundName}
                                    code={fund.fundCode}
                                >
                                <View style={styles.listItem}>
                                    <Text>{fund.fundName}</Text>
                                    <Caption style={styles.caption}>{fund.fundCode}</Caption>
                                </View>
                                </GoToFundView>
                            </ListItem>
                        ))
                    }
                </View>
                <View style={{width: '60%'}}>
                    <ScrollView horizontal={true}>
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>昨日以来</Text>
                                </View>
                            }
                            body={List1(fundList, 'lastOneDayQuote')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>上周以来</Text>
                                </View>
                            }
                            body={List1(fundList, 'lastOneWeekQuote')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>上月以来</Text>
                                </View>
                            }
                            body={List1(fundList, 'lastOneMonthQuote')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>一年以来</Text>
                                </View>
                            }
                            body={List1(fundList, 'lastOneYearQuote')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>成立以来</Text>
                                </View>
                            }
                            body={List1(fundList, 'fromBeginningQuote')}
                        />

                    </ScrollView>
                </View>
            </View>
        )
    }
}

const styles = StyleSheet.create({
    header: {
        height: 40,
        justifyContent: 'center',
    },
    listItem: {
        height: 58,
    },
    bold: {
        fontSize: 15,
        fontWeight: 'bold',
    },
    caption: {
        fontSize: 13,
    }
})
