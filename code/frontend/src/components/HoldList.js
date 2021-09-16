import React, {Component} from 'react'
import {Card, ListItem, Text} from "react-native-elements";
import {Button, Pressable, ScrollView, StyleSheet, TouchableOpacity, View} from "react-native";
import {Caption} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import message from "react-native-message/index";
import GoToFundView from "./GoToFundView";
import {delPosition} from "../service/UserPositionService";
import Toast from "./firebase/Toast";

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
        <Text style={{fontSize: 13, color: numberColor(number), fontWeight: '600'}}>
            {numberFormat(Math.round(number * 100) / 100) + '%'}
        </Text>
    )
}

function List1(fundList, attr) {
    let list = []
    fundList.forEach((fund) => {
        list.push(
            <View>
                <Text style={[ {marginTop: 10,fontWeight:'700'}]}>{fund[attr]}</Text>
                <Rate number={fund[attr + 'Change']}/>
            </View>
        )
    })
    return list
}

export default class HoldList extends Component {
    constructor(props) {
        super(props);
        console.log("HoldList created");
        this.state = {
            error: '',
            success: '',
        }
    }

    handleDelete(fund) {
        delPosition({"fundCode": fund.code},
            (data) => {
                if (data.status === 100) {
                    this.setState({success: '删除成功'})
                } else {
                    this.setState({error: '删除失败'})
                }
                this.props.refresh();
            })
    }

    deleteList(fundList) {
        let list = []
        fundList.forEach((fund) => {
            list.push(
                <Pressable
                    onPress={() => {
                        this.handleDelete(fund)
                    }}
                    style={() => [{
                        height: 58,
                        justifyContent: 'center',
                    }]}>
                    {() => (
                        <Text style={{fontSize: 16, fontWeight: 'bold', textAlign: 'center', color: 'red'}}>
                            删除
                        </Text>
                    )}
                </Pressable>
            )
        })
        return list
    }

    render() {
        let {fundList, preDate, curDate} = this.props
        return (
            <View style={{flexDirection: 'row', marginTop: 0}}>
                <View style={{width: '50%'}}>
                    <ListItem key={-1} bottomDivider>
                        <View style={{height: 40, justifyContent: 'center'}}>
                            <Text style={{fontSize: 15, fontWeight: 'bold', textAlign: 'center'}}>基金名称</Text>
                        </View>
                    </ListItem>
                    {
                        fundList.map((fund, index) => (
                            <ListItem key={index} bottomDivider>
                                <GoToFundView
                                    name={fund.name}
                                    code={fund.code}
                                >
                                    <View style={styles.listItem}>
                                        <Text>{fund.name}</Text>
                                        <Caption style={styles.caption}>{fund.code}</Caption>
                                    </View>
                                </GoToFundView>
                            </ListItem>
                        ))
                    }
                </View>
                <View style={{width: '50%'}}>
                    <ScrollView horizontal={true}>
                        <VerticalItem
                            header={
                                <View style={{height: 40}}>
                                    <Text style={styles.bold}>净值</Text>
                                    <Caption style={styles.caption}>{preDate}</Caption>
                                </View>
                            }
                            body={List1(fundList, 'nav')}
                        />
                        <VerticalItem
                            header={
                                <View style={{height: 40}}>
                                    <Text style={styles.bold}>估值</Text>
                                    <Caption style={styles.caption}>{curDate}</Caption>
                                </View>
                            }
                            body={List1(fundList, 'estimateNav')}
                        />
                        <VerticalItem
                            header={
                                <View style={styles.header}>
                                    {null}
                                </View>
                            }
                            body={this.deleteList(fundList)}
                        />
                    </ScrollView>
                </View>
                <Toast message={this.state.error}
                       onDismiss={() => this.setState({error: ''})} duration={1000}/>
                <Toast message={this.state.success}
                       onDismiss={() => this.setState({success: ''})} duration={1000} type={'success'}/>
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
