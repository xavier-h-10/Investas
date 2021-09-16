import React, {Component, useState} from "react";
import {Rating, Card, ListItem, Button, Icon} from 'react-native-elements';
import {View, StyleSheet, Text, Pressable} from "react-native";
import {useNavigation} from "@react-navigation/native";
// import CircularSlider from 'react-native-elements-universe';
import CircularProgress from "../CircularProgress";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";

// 参数说明：
// profit risk cost:收益能力，抗风险波动，投资性价比，传0-100的整数
// title:是否在基金详情页里，便于复用

export default function FundAnalysisCard(props) {

    const [profit, setProfit] = useState(props.profit);
    const [risk, setRisk] = useState(props.risk);
    const [cost, setCost] = useState(props.cost);
    const [title, setTitle] = useState(props.title);
    const {code, name} = props;
    const navigation = useNavigation();

    console.log("check fund analysis card props:",code,name);

    const renderIcon = (value) => {
        console.log("value=", value);
        if ((value - 75.0) < 0)
            return null;
        else {
            console.log("icon rendered");
            return <Icon name='thumbs-up' type='font-awesome-5' size={14} color='#e23b48' style={{paddingLeft: 2}}
                         solid={true}/>;
        }
    }

    const renderTitle = (value) => {
        console.log("title=", value);
        if (value == null || value == 0)
            return null;
        else {
            console.log("title rendered");
            let res = (
                <View style={{flexDirection: 'row',width:'100%'}}>
                    <Text style={page.containerTitle}>基金分析指标</Text>
                    <Card containerStyle={page.box} wrapperStyle={{padding: 0, margin: 0}}>
                        <Text style={{color: '#a1a1a1', fontSize: 10, fontWeight: '600'}}>近一年</Text>
                    </Card>
                    <SimpleLineIcons
                        name='arrow-right'
                        size={10}
                        style={page.icon}
                        // onPress={() => {
                        //   console.log("onPress called");
                        //   navigation.navigate("FundAnalysisView");
                        // }}
                    />
                </View>

            );
            return res;
        }
    }

    return (
        <Pressable
            onPress={() => {
                console.log("onpress fund analysis card");
                navigation.navigate('FundAnalysis', {
                    fundAnalysis: {
                        code: code,
                        name: name,
                        profit: profit,
                        risk: risk,
                        cost: cost
                    }
                })
            }}
        >
            <View>
                {renderTitle(title)}
                <View style={{flexDirection: 'row',width:'100%'}}>
                    <Card containerStyle={page.leftContainer}
                          wrapperStyle={{padding: 0, margin: 0}}>
                        <Text style={page.title}>收益能力{renderIcon(profit)}</Text>
                        <Text style={page.subtitle}>超过{profit}%同类</Text>
                        <CircularProgress value={profit}/>
                    </Card>

                    <Card containerStyle={page.container} wrapperStyle={{padding: 0,margin:0}}>
                        <Text style={page.title}>抗风险波动{renderIcon(risk)}</Text>
                        <Text style={page.subtitle}>超过{risk}%同类</Text>
                        <CircularProgress value={risk}/>
                    </Card>

                    <Card containerStyle={page.container} wrapperStyle={{padding: 0,margin:0}}>
                        <Text style={page.title}>投资性价比{renderIcon(cost)}</Text>
                        <Text style={page.subtitle}>超过{cost}%同类</Text>
                        <CircularProgress value={cost}/>
                    </Card>
                </View>
            </View>
        </Pressable>
    )
}

const page = StyleSheet.create({
    leftContainer: {
        backgroundColor: '#f8f8f8',
        borderRadius: 5,
        borderWidth: 0,
        margin: 0,
        marginTop: 10,
        marginLeft:-5,
        padding: 8,
        width:'33%',
    },
    container: {
        backgroundColor: '#f8f8f8',
        borderRadius: 5,
        borderWidth: 0,
        margin: 0,
        marginLeft: 8,
        marginTop: 10,
        padding: 8,
        width:'33%',
    },
    icon: {
        marginTop: 5,
        marginLeft: 5,
    },
    containerTitle: {
        margin: 0,
        // marginLeft: 10,
        fontSize: 15,
        fontWeight: 'bold',
    },
    title: {
        color: '#565656',
        textAlign: 'center',
        fontWeight: 'bold',
        fontSize: 13,
    },
    subtitle: {
        color: '#a1a1a1',
        textAlign: 'center',
        paddingTop: 4,
        paddingBottom: 4,
        fontSize: 11,
    },
    box: {
        backgroundColor: '#f6f6f6',
        borderRadius: 2,
        borderWidth: 0,
        margin: 0,
        marginLeft: 6,
        marginTop:2,
        padding: 0,
        paddingLeft: 2,
        paddingRight: 2,
        paddingTop: 1,
        paddingBottom: 1,
        height: 17,
    }
});
// color='#3099ff';
