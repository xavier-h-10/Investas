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

// TODO: make some bugs here
function Rate(props) {
    const {number} = props
    return (
        <Text style={{fontSize: 13, color: numberColor(number), fontWeight: 'bold'}}>{numberFormat((number/100).toFixed(2)) + '%'}</Text>
    )
}

function List1(fundList, nav, rate) {
    let list = []
    fundList.forEach((fund) => {
        list.push(
            <View>
                <Text style={[styles.bold, {marginTop: 10}]}>{(fund[nav])}</Text>
                <Rate number={fund[rate]}/>
            </View>
        )
    })
    return list
}

export default class PredictionList extends Component {
    constructor(props) {
        super(props);
    }


    render() {
        let {fundList, preDate, curDate1, curDate2, curDate3} = this.props

        return (
            <View style={{flexDirection: 'row', marginTop: 10}}>
                <View style={{width: '40%'}}>
                    <ListItem key={-1} bottomDivider>
                        <View style={[styles.header]}>
                            <Text style={{fontSize: 15, fontWeight: 'bold', textAlign: 'center'}}>基金名称</Text>
                            <Caption style={styles.caption}>交AI</Caption>
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
                                    <Text style={styles.bold}>当前净值</Text>
                                    <Caption style={styles.caption}>{preDate}</Caption>
                                </View>
                            }
                            body={List1(fundList, 'originNAV', 'originQuote')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>预测第1交易日</Text>
                                    <Caption style={styles.caption}>{curDate1}</Caption>
                                </View>
                            }
                            body={List1(fundList, 'oneDayNav', 'oneDayQuote')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>预测第2交易日</Text>
                                    <Caption style={styles.caption}>{curDate2}</Caption>
                                </View>
                            }
                            body={List1(fundList, 'twoDayNav', 'twoDayQuote')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>预测第3交易日</Text>
                                    <Caption style={styles.caption}>{curDate3}</Caption>
                                </View>
                            }
                            body={List1(fundList, 'threeDayNav', 'threeDayQuote')}
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
