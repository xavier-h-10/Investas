import {
    FlatList,
    SafeAreaView,
    ScrollView,
    StyleSheet,
    Text,
    View
} from "react-native";
import React, {useState} from "react";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import {Caption} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import Loading from "../components/Loading";
import {Card} from "react-native-elements";
import {getHistoryRank, getPredictionRank} from "../service/FundRateService";
import GoToFundView from "../components/GoToFundView";
import Top from "../components/Top";
import MyStatusBar from "../components/MyStatusBar";

export default function HomeRankFundView({route, navigation}) {
    const {type, subtitle, isPrediction} = route.params
    const pageSize = 20
    const [data, setData] = useState(null);
    const [page, setPage] = useState(0);

    React.useEffect(() => {
        const unsubscribe = navigation.addListener('focus', () => {
            if (isPrediction) {
                getPredictionRank(0, pageSize, type, (data) => {
                    setData(data.data.list)
                })
            } else {
                getHistoryRank(0, pageSize, type, (data) => {
                    setData(data.data.list)
                })
            }
        });
        return unsubscribe;
    }, [navigation]);

    const onEndReached = () => {
        if (isPrediction) {
            getPredictionRank(page + 1, pageSize, type, (ret) => {
                setData(data.concat(ret.data.list));
            })
        } else {
            getHistoryRank(page + 1, pageSize, type, (ret) => {
                setData(data.concat(ret.data.list));
            })
        }
        setPage(page + 1)
    }

    const renderItem = ({item, index}) => {
        const fund = item
        return (
            <GoToFundView
                name={fund.name}
                code={fund.code}
                key={index}
            >
                <View style={{flexDirection: 'row', marginTop: 10}}>
                    <View style={{width: '10%', marginRight: '3%'}}>
                        <Top number={index + 1} style={{minWidth:38,marginLeft:0,marginTop:2}}/>
                    </View>
                    <View style={{width: '61%', marginRight: '3%'}}>
                        <Text
                            numberOfLines={1}
                            style={{fontSize: 14, fontWeight: 'bold',lineHeight:20}}
                        >
                            {fund.name}
                        </Text>
                        <Caption style={[styles.caption, {marginTop: -2}]}>{fund.code}</Caption>
                    </View>
                    <View style={{width: '23%'}}>
                        <Text
                            style={{
                                fontWeight: 'bold',
                                color: numberColor(fund.rate),
                                textAlign: 'center',
                                lineHeight:20,
                            }}
                        >
                            {numberFormat(fund.rate) + '%'}
                        </Text>
                        <Caption style={[styles.caption, {marginTop: -2, textAlign: 'center'}]}>
                            {subtitle + '增长'}
                        </Caption>
                    </View>
                </View>
            </GoToFundView>
        )
    }

    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                {subtitle + '增长率排行'}
            </Header>
            <MyStatusBar/>
            <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                {
                    data === null ? <Loading/> :
                        <View>
                            <FlatList
                                data={data}
                                renderItem={renderItem}
                                keyExtractor={(item, index) => index}
                                onEndReached={onEndReached}
                                onEndReachedThreshold={0.2}
                                style={{marginBottom: 100}}
                            />
                        </View>
                }
            </Card>
        </SafeAreaView>
    )
}


const styles = StyleSheet.create({
    header: {
        height: 40,
        justifyContent: 'center',
    },
    bold: {
        fontSize: 15,
        fontWeight: 'bold',
    },
    caption: {
        fontSize: 13,
    }
})

