import React, {Component} from 'react'
import {Card, ListItem, Text} from "react-native-elements";
import {Button, Pressable, ScrollView, StyleSheet, TouchableOpacity, View} from "react-native";
import {Caption} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import message from "react-native-message/index";

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
        <Text style={{fontSize: 13, color: numberColor(number), fontWeight: 'bold'}}>{numberFormat(number) + '%'}</Text>
    )
}

function Rate2(props) {
    const {number} = props
    return (
        <Text style={{fontSize: 16, color: numberColor(number), fontWeight: 'bold'}}>{numberFormat(number) + '%'}</Text>
    )
}


function List1(fundList, attr) {
    let list = []
    fundList.forEach((fund) => {
        list.push(
            <View>
                <Text style={[styles.bold, {marginTop: 10}]}>{fund[attr][0]}</Text>
                <Rate number={fund[attr][1]}/>
            </View>
        )
    })
    return list
}

function List2(fundList, attr) {
    let list = []
    fundList.forEach((fund) => {
        list.push(
            <View style={{marginTop: 15}}>
                <Rate2 number={fund[attr][0]}/>
            </View>
        )
    })
    return list
}

export default class RecommendList extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {userType, fundList, preDate, curDate1, curDate2, curDate3} = this.props
        return (
            <View style={{flexDirection: 'row', marginTop: 10}}>
                <View style={{width: '40%'}}>
                    <ListItem key={-1} bottomDivider>
                        <View style={[styles.header]}>
                            <Text style={{fontSize: 15, fontWeight: 'bold', textAlign: 'center'}}>{'基金推荐'}</Text>
                            <Caption style={styles.caption}>{this.props.userType}</Caption>
                        </View>
                    </ListItem>
                    {
                        fundList.map((fund, index) => (
                            <ListItem key={index} bottomDivider>
                                <View style={styles.listItem}>
                                    <Text>{fund.name}</Text>
                                    <Caption style={styles.caption}>{fund.code}</Caption>
                                </View>
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
                            body={List1(fundList, 'nav')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>近一年最大回撤</Text>
                                </View>
                            }
                            body={List2(fundList, 'val1')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    <Text style={styles.bold}>收益最大回撤</Text>
                                </View>
                            }
                            body={List2(fundList, 'val2')}
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
