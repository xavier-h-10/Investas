import React, {useState} from 'react'
import {SafeAreaView, ScrollView, StyleSheet, View} from 'react-native'
import TextInput from "../components/firebase/TextInput";
import {Card, Input, Text} from 'react-native-elements'
import {theme} from '../core/theme'
import {Caption, Title} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import Header from "../components/Header";
import {Picker} from '@react-native-picker/picker';
import {HeaderBackButton} from "@react-navigation/stack";
import {buyFund, getActiveCompetitionsByFundType, getLogs, getUserPos} from "../service/FundCompeService";
import MyReadMore from "../components/MyReadMore";
import Toast from "../components/firebase/Toast";
import CompetitionLog from "../components/CompetitionLog";
import Button from "../components/firebase/Button";

export default function InvestView({route, navigation}) {
    const {code, type} = route.params
    const [selectedIndex, setSelectedIndex] = useState(0)
    const [list, setList] = useState(null)
    const [money, setMoney] = useState()
    const [error, setError] = useState('')
    const [success, setSuccess] = useState('')
    const [disabled, setDisabled] = useState(false)
    const [loading, setLoading] = useState(false)
    const [amount, setAmount] = useState(0)
    console.log('selectedIndex', selectedIndex)

    React.useEffect(() => {
        navigation.addListener('focus', () => {
            getActiveCompetitionsByFundType(type, (data) => {
                if (data.data && data.data.list) {
                    setList(data.data.list)
                    getUserPos(data.data.list[0].competitionId, code, (data) => {
                        console.log('getUserPos', data)
                        if (data.data !== null) {
                            setAmount(data.data.amount)
                        } else {
                            setAmount(0)
                        }
                    })
                }
            })
        });
    }, [navigation]);


    if (list === null) {
        return (
            <SafeAreaView>
                <Header
                    headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}
                >
                    投资比赛
                </Header>
            </SafeAreaView>
        )
    } else {
        if (list.length === 0) {
            return (
                <SafeAreaView>
                    <Header
                        headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}
                    >
                        投资比赛
                    </Header>
                    <Text style={[styles.caption, {fontSize: 20, marginTop: 20, alignSelf: 'center', width: '75%'}]}>
                        暂时还没有正在进行中的比赛哦，请进入个人页面加入比赛
                    </Text>
                </SafeAreaView>
            )
        }
        const competition = list[selectedIndex]
        const checkMoney = (text) => {
            const regExp = /^-?\d+\.?\d{0,2}$/
            if (regExp.test(text)) {
                if (parseFloat(text) > competition.surplusMoney) {
                    return '输入的金额必须小于剩余资金。'
                } else {
                    return ''
                }
            } else {
                return '输入的金额小数位数需要小于等于两位'
            }
        }
        const handleSubmit = () => {
            setDisabled(true)
            setLoading(true)
            const res = checkMoney(money)
            setError(res)
            if (res !== '') {
                setDisabled(false)
                setLoading(false)
                return
            }
            console.log('parseFloat(money)', parseFloat(money))
            buyFund(competition.competitionId, code, parseFloat(money), (data) => {
                console.log(data)
                if (data.status === 100) {
                    setSuccess("提交成功！")
                    setLoading(false)
                } else {
                    setError(data.message)
                    setDisabled(false)
                    setLoading(false)
                }
            })
        }
        return (
            <SafeAreaView>
                <Header
                    headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}
                >
                    投资比赛
                </Header>
                <ScrollView>
                    <View style={{marginBottom: 80}}>
                        <Card containerStyle={{borderRadius: 10}}>
                            <Text style={[styles.caption, {fontSize: 18}]}>选择比赛</Text>
                            <Picker
                                selectedValue={selectedIndex}
                                onValueChange={(itemValue, itemIndex) => {
                                    setSelectedIndex(itemValue)
                                    getUserPos(list[itemValue].competitionId, code, (data) => {
                                        console.log('getUserPos', data)
                                        if (data.data !== null) {
                                            setAmount(data.data.amount)
                                        } else {
                                            setAmount(0)
                                        }
                                    })
                                }}
                            >
                                {
                                    list.map((item, index) => (
                                        <Picker.Item
                                            label={item.competitionName}
                                            value={index}
                                            key={index}
                                        />
                                    ))
                                }
                            </Picker>
                            {/*<Text style={[styles.caption, {fontSize: 18}]}>选择金额（元）</Text>*/}
                            <TextInput
                                label="选择金额（元）"
                                placeholder="选择金额（元）"
                                value={money}
                                onChangeText={(text) => setMoney(text)}
                                error={error !== ''}
                                errorText={error}
                                keyboardType="decimal-pad"
                            />
                            <Button
                                loading={loading}
                                disabled={disabled}
                                mode="contained"
                                onPress={(e) => {
                                    e.preventDefault()
                                    handleSubmit()
                                }}
                                // style={{marginTop: 24}}
                            >
                                {loading ? '提交中' : '提交'}
                            </Button>
                            <View style={{marginTop: 20}}>
                                <View style={{flexDirection: 'row'}}>
                                    <Title numberOfLines={1}
                                           style={{maxWidth: '70%'}}>{competition.competitionName}</Title>
                                    <Title
                                        style={[styles.caption, {minWidth: '30%'}]}>{'  (ID:' + competition.competitionId + ')'}</Title>
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 8}}>
                                    <Title style={[styles.caption, {marginRight: 20}]}>剩余资金</Title>
                                    <Title style={{color: 'red'}}>{competition.surplusMoney.toFixed(2) + '元'}</Title>
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 8}}>
                                    <Title style={[styles.caption, {marginRight: 20}]}>已投资金</Title>
                                    <Title style={{color: 'red'}}>{amount.toFixed(2) + '元'}</Title>
                                </View>
                                <View style={{flexDirection: 'row', marginTop: 8}}>
                                    <View style={{flexDirection: 'row', width: '50%', justifyContent: 'space-between'}}>
                                        <Text style={[styles.caption, {fontSize: 14}]}>截止日期</Text>
                                        <Text style={{marginRight: 20, fontSize: 14}}>{competition.endDate}</Text>
                                    </View>
                                    <View style={{flexDirection: 'row', width: '50%', justifyContent: 'space-between'}}>
                                        <Text style={[styles.caption, {fontSize: 14}]}>参赛人数</Text>
                                        <Text style={{marginRight: 20, fontSize: 14}}>
                                            {competition.number + ' / ' + competition.capacity}
                                        </Text>
                                    </View>
                                </View>
                                <View style={{marginTop: 8}}>
                                    <MyReadMore
                                        numberOfLines={3}
                                        component={
                                            <Text style={{fontSize: 14}}>
                                                <Text style={styles.caption}>比赛描述：</Text>
                                                <Text>{competition.competitionDescription}</Text>
                                            </Text>
                                        }
                                    />
                                </View>

                            </View>
                        </Card>
                        <CompetitionLog code={code} competitionId={competition.competitionId}/>
                    </View>
                </ScrollView>
                {/*<Toast message={error} onDismiss={() => setError('')}/>*/}
                <Toast
                    type='success'
                    message={success}
                    onDismiss={() => {
                        setSuccess('');
                        navigation.goBack()
                    }}
                    duration={400}
                />
            </SafeAreaView>
        )
    }
}


const styles = StyleSheet.create({
    caption: {
        color: 'grey',
    }
})
